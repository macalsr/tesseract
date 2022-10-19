package com.example.tesseract.domain.conversores.img.sp;

import com.example.tesseract.domain.model.NFSe;

import java.awt.image.BufferedImage;

public class ConversorImgParaLayoutUnicoImgTxtSaoPaulo {

    public static NFSe converte(BufferedImage img) {
        NotaFiscalSaoPaulo notaFiscalSaoPaulo = new NotaFiscalSpImgToTxt(img);
        NFSe nfse = new NFSe();

        //Dados Prefeitura e NFSe
        nfse.setNomePrefeitura(notaFiscalSaoPaulo.nomePrefeitura());
        nfse.setDataEmissao(notaFiscalSaoPaulo.dataEmissao());
        nfse.setNumeroNota(notaFiscalSaoPaulo.numeroNota());
        nfse.setCodigoVerificacao(notaFiscalSaoPaulo.codigoVerificacao());
        nfse.setNumeroRps(notaFiscalSaoPaulo.numeroRps());
        nfse.setSerieRps(notaFiscalSaoPaulo.serieRps());

        //Prestador
        nfse.setRazaoSocialPrestador(notaFiscalSaoPaulo.razaoSocialPrestador());
        nfse.setCpnjPrestador(notaFiscalSaoPaulo.cnpjPrestador());
        nfse.setEnderecoPrestador(notaFiscalSaoPaulo.enderecoPrestador());
        nfse.setUfPrestador(notaFiscalSaoPaulo.ufPrestador());
        nfse.setMunicipioPrestador(notaFiscalSaoPaulo.municipioPrestador());
        nfse.setInscricaoMunicipalPrestador(notaFiscalSaoPaulo.inscricaoMunicipalPrestador());

        //Tomador
        nfse.setRazaoSocialTomador(notaFiscalSaoPaulo.razaoSocialTomador());
        nfse.setEnderecoTomador(notaFiscalSaoPaulo.enderecoTomador());
        nfse.setCnpjTomador(notaFiscalSaoPaulo.cnpjTomador());
        nfse.setUfTomador(notaFiscalSaoPaulo.ufTomador());
        nfse.setMunicipioTomador(notaFiscalSaoPaulo.municipioTomador());
        nfse.setInscricaoMunicipalTomador(notaFiscalSaoPaulo.inscricaoMunicipalTomador());

        //Valores
        nfse.setValorServico(notaFiscalSaoPaulo.valorServico());
        nfse.setBaseCalculo(notaFiscalSaoPaulo.baseCalculo());
        nfse.setAliquota(notaFiscalSaoPaulo.aliquota());
        nfse.setValorIss(notaFiscalSaoPaulo.valorIss());
        nfse.setValorCofins(notaFiscalSaoPaulo.valorCofins());
        nfse.setValorCssl(notaFiscalSaoPaulo.valorCssl());
        nfse.setValorIrrf(notaFiscalSaoPaulo.valorIrrf());
        nfse.setValorInss(notaFiscalSaoPaulo.valorInss());
        nfse.setValorPisPasep(notaFiscalSaoPaulo.valorPisPasep());

        //Servico
        nfse.setCodigoServico(notaFiscalSaoPaulo.codigoServico());
        nfse.setDiscriminacao(notaFiscalSaoPaulo.discriminacao());

        return nfse;
    }

}