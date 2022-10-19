package com.example.tesseract.domain.conversores.img.sp;

import java.io.IOException;
import java.time.LocalDateTime;

public interface NotaFiscalSaoPaulo {

    String nomePrefeitura() throws IOException;

    Long numeroRps() throws IOException;

    Long numeroNota() throws IOException;

    String serieRps() throws IOException;

    LocalDateTime dataEmissao() throws IOException;

    String cnpjPrestador() throws IOException;

    String razaoSocialPrestador() throws IOException;

    String enderecoPrestador() throws IOException;

    String municipioPrestador() throws IOException;

    String ufPrestador() throws IOException;

    String inscricaoMunicipalPrestador() throws IOException;

    String razaoSocialTomador() throws IOException;

    String cnpjTomador() throws IOException;

    String enderecoTomador() throws IOException;

    String municipioTomador() throws IOException;

    String ufTomador() throws IOException;

    String inscricaoMunicipalTomador() throws IOException;

    String codigoVerificacao() throws IOException;

    Double valorServico() throws IOException;

    String codigoServico() throws IOException;

    String discriminacao() throws IOException;

    Double baseCalculo() throws IOException;

    Double aliquota() throws IOException;

    Double valorIss() throws IOException;

    Double valorInss() throws IOException;

    Double valorIrrf() throws IOException;

    Double valorCssl() throws IOException;

    Double valorCofins() throws IOException;

    Double valorPisPasep() throws IOException;

}