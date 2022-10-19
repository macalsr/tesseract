package com.example.tesseract.domain.conversores.img.sp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static com.example.tesseract.utils.ImgUtil.*;
import static com.example.tesseract.utils.RegexUtil.*;

public class NotaFiscalSpImgToTxt implements NotaFiscalSaoPaulo {

    private static final Pattern REGEX_DATA_EMISSAO = Pattern.compile("((?s).*)\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}:\\d{2}");
    private static final Pattern REGEX_CNPJ_PRESTADOR = Pattern.compile("CP.*");
    private static final Pattern REGEX_RAZAO_SOCIAL_PRESTADOR = Pattern.compile("Razão\\sSocial:\\s.*");
    private static final Pattern REGEX_ENDERECO_PRESTADOR = Pattern.compile("Endereço:\\s.*");
    private static final Pattern REGEX_MUNICIPIO_PRESTADOR = Pattern.compile("Município.*");
    private static final Pattern REGEX_UF_PRESTADOR = Pattern.compile("UF:\\s.*");
    private static final Pattern REGEX_NOME_PREFEITURA = Pattern.compile("PREFEITURA.*");
    private static final Pattern REGEX_NUMERO_RPS = Pattern.compile("NOTA.*\\nRPS\\sN\\D\\s((?s).*)\\sS\\Dr");
    private static final Pattern REGEX_SERIE_RPS = Pattern.compile(".*S\\Drie\\s((?s).*),.*");
    private static final Pattern REGEX_INSCRICAO_MUNICIPAL_PRESTADOR = Pattern.compile("Municipal:\\s.*");
    private static final Pattern REGEX_RAZAO_SOCIAL_TOMADOR = Pattern.compile("Social:\\s.*");
    private static final Pattern REGEX_CNPJ_TOMADOR = Pattern.compile("CP.*");
    private static final Pattern REGEX_ENDERECO_TOMADOR = Pattern.compile("CPF.*\\n.*");
    private static final Pattern REGEX_MUNICIPIO_TOMADOR = Pattern.compile("CEP.*\\nMun\\D{6}:\\s.*");
    private static final Pattern REGEX_UF_TOMADOR = Pattern.compile("UF:\\s\\D{2}");
    private static final Pattern REGEX_INSCRICAO_MUNICIPAL_TOMADOR = Pattern.compile("Ins\\D{6}\\sMunicipal:.*");
    private static final Pattern REGEX_VALOR_SERVICO = Pattern.compile("\\D\\D\\s.*");
    private static final Pattern REGEX_CODIGO_SERVICO = Pattern.compile("\\d{5}");
    private static final Pattern REGEX_BASE_CALCULO = Pattern.compile("Base.*\\n.*");
    private static final Pattern REGEX_ALIQUOTA = Pattern.compile("Al.*\\n.*");
    private static final Pattern REGEX_VALOR_ISS = Pattern.compile("Valor.*\\n.*");
    private static final Pattern REGEX_CONJUNTO_VALORES = Pattern.compile(".*\\n.*");

    private BufferedImage img;

    public NotaFiscalSpImgToTxt(BufferedImage img) {
        this.img = img;
    }

    @Override
    public String nomePrefeitura() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 521, 176, 1272, 213), "por");
        String regex = aplicaRegex(REGEX_NOME_PREFEITURA, slice);
        return regex.toUpperCase();
    }

    @Override
    public Long numeroRps() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 581, 328, 1149, 99), "eng");
        String regex = aplicaRegex(REGEX_NUMERO_RPS, slice);
        String resultado = tratarResultado(regex, r -> r.replaceAll("NOTA.*\\nRPS\\sN\\D\\s|\\sS\\Dr", "").trim());
        return converteParaLong(resultado);
    }

    @Override
    public Long numeroNota() throws IOException {
        String slice = removeQuebraLinha(leitorImagem(calcularCoordenadas(img, 1830, 205, 418, 52), "eng"));
        return converteParaLong(slice);
    }

    @Override
    public String serieRps() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 786, 385, 727, 36), "eng");
        String regex = aplicaRegex(REGEX_SERIE_RPS, slice);
        String resultado = tratarResultado(regex, r -> r.replaceAll(".*S\\Drie\\s|,.*", "").trim());
        return resultado;
    }

    @Override
    public LocalDateTime dataEmissao() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 239, 176, 2008, 257), "por");
        String resultado = aplicaRegex(REGEX_DATA_EMISSAO, slice);
        String secondResult = aplicaRegex("\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}:\\d{2}", resultado);
        LocalDateTime dataEmissao = converteParaLocalDateTime(secondResult, DD_MM_YYYY_HH_MM_SS_BARRA);
        return dataEmissao;
    }

    @Override
    public String cnpjPrestador() throws IOException {
        String cnpjPrestador = leitorImagem(calcularCoordenadas(img, 240, 445, 2004, 262), "eng");
        String resultado = aplicaRegex(REGEX_CNPJ_PRESTADOR, cnpjPrestador);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("CPF\\D{5}:|Insc.*", ""));
        return removeNaoDigitos(resultadoTratado);
    }

    @Override
    public String razaoSocialPrestador() throws IOException {
        String razaoSocialPrestador = leitorImagem(calcularCoordenadas(img, 240, 445, 2004, 262), "por");
        String resultado = aplicaRegex(REGEX_RAZAO_SOCIAL_PRESTADOR, razaoSocialPrestador);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("Razão\\sSocial:\\s", ""));
        return resultadoTratado.toUpperCase();
    }

    @Override
    public String enderecoPrestador() throws IOException {
        String enderecoPrestador = leitorImagem(calcularCoordenadas(img, 240, 445, 2004, 262), "por");
        String resultado = aplicaRegex(REGEX_ENDERECO_PRESTADOR, enderecoPrestador);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("Endereço:\\s| -\\sCEP:.*", ""));
        return resultadoTratado.toUpperCase();
    }

    @Override
    public String municipioPrestador() throws IOException {
        String municipioPrestador = leitorImagem(calcularCoordenadas(img, 240, 445, 2004, 262), "por");
        String resultado = aplicaRegex(REGEX_MUNICIPIO_PRESTADOR, municipioPrestador);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("Município:\\s|\\sUF.*", ""));
        return resultadoTratado.toUpperCase();
    }

    @Override
    public String ufPrestador() throws IOException {
        String ufPrestador = leitorImagem(calcularCoordenadas(img, 240, 445, 2004, 262), "por");
        String resultado = aplicaRegex(REGEX_UF_PRESTADOR, ufPrestador);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("UF:\\s", ""));
        return resultadoTratado.toUpperCase();
    }

    @Override
    public String inscricaoMunicipalPrestador() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 240, 445, 2004, 262), "por");
        String resultado = aplicaRegex(REGEX_INSCRICAO_MUNICIPAL_PRESTADOR, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("Municipal:\\s", ""));
        return removeNaoDigitos(resultadoTratado);
    }

    @Override
    public String razaoSocialTomador() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 244, 725, 1997, 227), "por");
        String resultado = aplicaRegex(REGEX_RAZAO_SOCIAL_TOMADOR, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("Social:\\s", ""));
        return resultadoTratado.toUpperCase();
    }

    @Override
    public String cnpjTomador() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 244, 725, 1997, 227), "eng");
        String resultado = aplicaRegex(REGEX_CNPJ_TOMADOR, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("CPF\\D{5}:|Insc.*", ""));
        return removeNaoDigitos(resultadoTratado);
    }

    @Override
    public String enderecoTomador() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 244, 725, 1997, 227), "por");
        String resultado = aplicaRegex(REGEX_ENDERECO_TOMADOR, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("CPF.*\\nEnd\\D{5}:\\s|\\s-\\sCEP:.*", ""));
        return resultadoTratado.toUpperCase();
    }

    @Override
    public String municipioTomador() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 244, 725, 1997, 227), "por");
        String resultado = aplicaRegex(REGEX_MUNICIPIO_TOMADOR, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("CEP.*\\nMun\\D{6}:\\s|\\sUF:.*", ""));
        return resultadoTratado.toUpperCase();
    }

    @Override
    public String ufTomador() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 244, 725, 1997, 227), "por");
        String resultado = aplicaRegex(REGEX_UF_TOMADOR, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("UF:\\s", ""));
        return resultadoTratado.toUpperCase();
    }

    @Override
    public String inscricaoMunicipalTomador() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 244, 725, 1997, 227), "por");
        String resultado = aplicaRegex(REGEX_INSCRICAO_MUNICIPAL_TOMADOR, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("Ins\\D{6}\\sMunicipal:", ""));
        return removeNaoDigitos(resultadoTratado);
    }

    @Override
    public String codigoVerificacao() throws IOException {
        String codigoVerificacao = removeQuebraLinha(leitorImagem(calcularCoordenadas(img, 1830, 390, 420, 43), "eng"));
        return codigoVerificacao.toUpperCase();
    }

    @Override
    public Double valorServico() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 1426, 2028, 810, 51), "por");
        String resultado = aplicaRegex(REGEX_VALOR_SERVICO, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("\\D\\D\\s", ""));
        String valor = resultadoTratado.length() > 1 ? trocaVirgulaPorPonto(resultadoTratado) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

    @Override
    public String codigoServico() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 240, 2164, 2008, 78), "por");
        String resultado = aplicaRegex(REGEX_CODIGO_SERVICO, slice);
        //String resultadoTratado = RegexUtil.tratarResultado(resultado, r -> r.replaceAll("\\D\\D\\s", ""));
        return removeNaoDigitos(resultado);
    }

    @Override
    public Double baseCalculo() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 663, 2247, 395, 77), "por");
        String resultado = aplicaRegex(REGEX_BASE_CALCULO, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("Base.*\\n", ""));
        String valor = resultadoTratado.length() > 1 ? trocaVirgulaPorPonto(resultadoTratado) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

    @Override
    public Double aliquota() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 1063, 2247, 290, 76), "por");
        String resultado = aplicaRegex(REGEX_ALIQUOTA, slice);
        String resultadoTratado = removeNaoDigitos(tratarResultado(resultado, r -> r.replaceAll("Al.*\\n", "")));
        String valor = resultadoTratado.length() > 1 ? addCharToStringUsingSubString(resultadoTratado, '.', resultadoTratado.length() - 2) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

    @Override
    public Double valorIss() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 1359, 2247, 468, 77), "eng");
        String resultado = aplicaRegex(REGEX_VALOR_ISS, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll("Valor.*\\n", ""));
        String valor = resultadoTratado.length() > 1 ? trocaVirgulaPorPonto(resultadoTratado) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

    @Override
    public String discriminacao() throws IOException {
        String slice = removeQuebraLinha(leitorImagem(calcularCoordenadas(img, 243, 1134, 2004, 883), "eng"));
        return slice.toUpperCase();
    }

    @Override
    public Double valorInss() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 239, 2087, 396, 73), "eng");
        String resultado = aplicaRegex(REGEX_CONJUNTO_VALORES, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll(".*\\n", ""));
        String valor = resultadoTratado.length() > 1 ? trocaVirgulaPorPonto(resultadoTratado) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

    @Override
    public Double valorIrrf() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 639, 2087, 395, 73), "eng");
        String resultado = aplicaRegex(REGEX_CONJUNTO_VALORES, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll(".*\\n", ""));
        String valor = resultadoTratado.length() > 1 ? trocaVirgulaPorPonto(resultadoTratado) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

    @Override
    public Double valorCssl() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 1039, 2087, 395, 73), "eng");
        String resultado = aplicaRegex(REGEX_CONJUNTO_VALORES, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll(".*\\n", ""));
        String valor = resultadoTratado.length() > 1 ? trocaVirgulaPorPonto(resultadoTratado) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

    @Override
    public Double valorCofins() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 1441, 2087, 395, 73), "eng");
        String resultado = aplicaRegex(REGEX_CONJUNTO_VALORES, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll(".*\\n", ""));
        String valor = resultadoTratado.length() > 1 ? trocaVirgulaPorPonto(resultadoTratado) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

    @Override
    public Double valorPisPasep() throws IOException {
        String slice = leitorImagem(calcularCoordenadas(img, 1842, 2087, 395, 73), "eng");
        String resultado = aplicaRegex(REGEX_CONJUNTO_VALORES, slice);
        String resultadoTratado = tratarResultado(resultado, r -> r.replaceAll(".*\\n", ""));
        String valor = resultadoTratado.length() > 1 ? trocaVirgulaPorPonto(resultadoTratado) : removeNaoDigitos(resultadoTratado);
        return valor.equals("") ? 0.0 : converteParaDouble(valor);
    }

}