package com.example.tesseract.domain.conversores.img.sp;

import java.time.LocalDateTime;

public interface NotaFiscalSaoPaulo {

    String nomePrefeitura();
    Long numeroRps();
    Long numeroNota();
    String serieRps();
    LocalDateTime dataEmissao();
    String cnpjPrestador();
    String razaoSocialPrestador();
    String enderecoPrestador();
    String municipioPrestador();
    String ufPrestador();
    String inscricaoMunicipalPrestador();
    String razaoSocialTomador();
    String cnpjTomador();
    String enderecoTomador();
    String municipioTomador();
    String ufTomador();
    String inscricaoMunicipalTomador();
    String codigoVerificacao();
    Double valorServico();
    String codigoServico();
    String discriminacao();
    Double baseCalculo();
    Double aliquota();
    Double valorIss();
    Double valorInss();
    Double valorIrrf();
    Double valorCssl();
    Double valorCofins();
    Double valorPisPasep();

}