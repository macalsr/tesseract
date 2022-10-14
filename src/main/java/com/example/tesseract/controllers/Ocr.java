package com.example.tesseract.controllers;

import com.example.tesseract.utils.ImgUtil;
import com.example.tesseract.domain.NotaFiscalSpImgToTxt;
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
    public ResponseEntity<String> leitordeImagem(@RequestParam(name = "file") MultipartFile file) throws IOException {
        //return ResponseEntity.ok(ImgUtil.leitorImagem((ImgUtil.validaArquivo(file))));
        return ResponseEntity.ok(NotaFiscalSpImgToTxt.coletaInformacoes((ImgUtil.validaArquivo(file))));

    }

}