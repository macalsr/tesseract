package com.example.tesseract;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;

@RestController
@RequestMapping("/ocr")
public class Ocr {

    private final String datapath = "src/main/resources/tessdata";
    String language = "por";

    @PostMapping()
    public ResponseEntity<String> traduzir(@RequestParam(name="file") MultipartFile file) throws Exception{
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext)&&!"gif".equals(ext)) {
            return ResponseEntity.badRequest().build();
        }
        String resultado = "";

        try {
            BufferedImage img = ImageIO.read(file.getInputStream());
            Tesseract tesseract = new Tesseract();
            //Path da pasta pai onde fica a pasta "tessdata"
            tesseract.setDatapath(datapath);
            resultado = "";
            //lingua: por, eng etc...
            tesseract.setLanguage(language);
            resultado = tesseract.doOCR(img);
        } catch (IOException e) {
            throw new Exception("Erro ao ler arquivo");
        }
        return ResponseEntity.ok(resultado);
    }

}