package com.example.tesseract.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class NFSe {

    private String nomePrefeitura;
    private Long numeroNota;
    private Long numeroRps;
    private String serieRps;
    private LocalDateTime dataEmissao;
    private String cpnjPrestador;
    private String razaoSocialPrestador;
    private String discriminacao;
    private String enderecoPrestador;
    private String codigoServico;
    private String municipioPrestador;
    private String ufPrestador;
    private String inscricaoMunicipalPrestador;
    private String razaoSocialTomador;
    private String cnpjTomador;
    private String enderecoTomador;
    private String municipioTomador;
    private String ufTomador;
    private String inscricaoMunicipalTomador;
    private String codigoVerificacao;
    private Double baseCalculo;
    private Double aliquota;
    private Double valorIss;
    private Double valorServico;
    private Double valorInss;
    private Double valorIrrf;
    private Double valorCssl;
    private Double valorCofins;
    private Double valorPisPasep;

}
