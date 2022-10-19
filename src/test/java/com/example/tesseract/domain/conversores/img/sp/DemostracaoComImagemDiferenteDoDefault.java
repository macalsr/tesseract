package com.example.tesseract.domain.conversores.img.sp;

import com.example.tesseract.domain.model.NFSe;
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

import static com.example.tesseract.utils.ImgUtil.calcularCoordenadas;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DemostracaoComImagemDiferenteDoDefault {

    private ConversorImgParaLayoutUnicoImgTxtSaoPaulo conversorImgParaLayoutUnicoImgTxtSaoPaulo;
    private static final String output = "src/test/resources/outputfiles/";
    private NFSe nfse;
    private BufferedImage img;

    @BeforeEach
    void setUp() throws IOException {
        conversorImgParaLayoutUnicoImgTxtSaoPaulo = new ConversorImgParaLayoutUnicoImgTxtSaoPaulo();
        File file = new File("src/test/resources/imagens/sao-paulo1.pdf");
        InputStream stream =  new FileInputStream(file);
        MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);
        img = ImgUtil.validaArquivo(multipartFileToSend);
        nfse = conversorImgParaLayoutUnicoImgTxtSaoPaulo.converte(img);
    }

    @Test
    void demostracaoComImagemResolucaoDiferenteDoPadrao() throws IOException {
        BufferedImage novaImagem = calcularCoordenadas(img,521, 176, 1272, 213);
        ImageIO.write(img, "png", new File(output + "novaImagem.png"));
        assertEquals("PREFEITURA DO MUNICÍPIO DE SÃO PAULO", nfse.getNomePrefeitura());
    }

}