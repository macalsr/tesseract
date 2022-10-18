package com.example.tesseract.domain.conversores.img.sp;

import com.example.tesseract.utils.ImgUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColetorDeCoordenadas {

    private static final String output = "src/test/resources/outputfiles/";

    private BufferedImage img;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/imagens/sao-paulo1.pdf");
        InputStream stream =  new FileInputStream(file);
        MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);
        img = ImgUtil.validaArquivo(multipartFileToSend);
    }

    @Test
    void gravaImagem() throws IOException {
//        BufferedImage novaImagem = img.getSubimage(241, 1129, 2005, 887);
//        String text = ImgUtil.leitorImagem(novaImagem, "eng");
//        System.out.println(text);
//        ImageIO.write(img, "png", new File(output + "novaImagem.png"));
    }

}