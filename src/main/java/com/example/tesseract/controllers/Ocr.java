package com.example.tesseract.controllers;

import com.example.tesseract.domain.conversores.img.sp.ConversorImgParaLayoutUnicoImgTxtSaoPaulo;
import com.example.tesseract.domain.model.NFSe;
import com.example.tesseract.utils.ImgUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ocr")
public class Ocr {

    @PostMapping()
    public ResponseEntity<NFSe> leitordeImagem(@RequestParam(name = "file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(ConversorImgParaLayoutUnicoImgTxtSaoPaulo.converte((ImgUtil.validaArquivo(file))));
    }

}