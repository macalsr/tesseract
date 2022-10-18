package com.example.tesseract.domain.conversores.img.sp;

import com.example.tesseract.domain.model.NFSe;
import com.example.tesseract.utils.ImgUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversorImgParaLayoutUnicoImgTxtSaoPauloTest {

    private ConversorImgParaLayoutUnicoImgTxtSaoPaulo conversorImgParaLayoutUnicoImgTxtSaoPaulo;
    private BufferedImage img;

    private NFSe nfse;

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
    void nomePrefeitura() {
        assertEquals("PREFEITURA DO MUNICÍPIO DE SÃO PAULO", nfse.getNomePrefeitura());
    }

    @Test
    void informacoesNotaFiscal() {
        assertEquals(507403L, nfse.getNumeroNota());
        assertEquals(593874L, nfse.getNumeroRps());
        assertEquals("S", nfse.getSerieRps());
        assertEquals(LocalDateTime.of(2022, 8, 1, 10, 17, 45), nfse.getDataEmissao());
        assertEquals("TA88-JKBL", nfse.getCodigoVerificacao());
    }

    @Test
    void informacoesPrestador() {
        assertEquals("EMPRESA FOLHA DA MANHA S.A.", nfse.getRazaoSocialPrestador());
        assertEquals("60579703000148", nfse.getCpnjPrestador());
        assertEquals("10025243", nfse.getInscricaoMunicipalPrestador());
        assertEquals("SÃO PAULO", nfse.getMunicipioPrestador());
        assertEquals("AL BARAO DE LIMEIRA 425, ANDAR 2 AO 11 - CAMPOS ELISEOS", nfse.getEnderecoPrestador());
        assertEquals("SP", nfse.getUfPrestador());
    }

    @Test
    void informacoesTomador() {
        assertEquals("JM PORTAL PARANÁ LTDA", nfse.getRazaoSocialTomador());
        assertEquals("24629720000189", nfse.getCnpjTomador());
        assertEquals("", nfse.getInscricaoMunicipalTomador());
        assertEquals("CURITIBA", nfse.getMunicipioTomador());
        assertEquals("AV ALAMEDA DOUTOR CARLOS DE CARVALHO 417, SOBRELOJA - CENTRO", nfse.getEnderecoTomador());
        assertEquals("PR", nfse.getUfTomador());
    }

    @Test
    void informacoesValores() {
        assertEquals(Double.valueOf(1088.39), nfse.getValorServico());
        assertEquals(Double.valueOf(1088.39), nfse.getBaseCalculo());
        assertEquals(Double.valueOf(2.90), nfse.getAliquota());
        assertEquals(Double.valueOf(31.56), nfse.getValorIss());
        assertEquals(Double.valueOf(0.0), nfse.getValorPisPasep());
        assertEquals(Double.valueOf(0.0), nfse.getValorCofins());
        assertEquals(Double.valueOf(0.0), nfse.getValorIrrf());
        assertEquals(Double.valueOf(0.0), nfse.getValorInss());
        assertEquals(Double.valueOf(0.0), nfse.getValorCssl());
    }

    @Test
    void informacoesServico() {
        assertEquals("02961", nfse.getCodigoServico());
    }

}