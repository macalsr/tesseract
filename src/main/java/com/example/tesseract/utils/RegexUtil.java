package com.example.tesseract.utils;

import com.google.common.base.Strings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    /**
     * Formatador para dd/MM/yyyy HH:mm:ss. Exemplo: 06/09/2022 15:20:45
     */
    public static final DateTimeFormatter DD_MM_YYYY_HH_MM_SS_BARRA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Formatador para dd/MM/yyyy. Exemplo: 26/08/2022
     */
    public static final DateTimeFormatter DD_MM_YYYY_BARRA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Formatador para dd/MM/yyyy. Exemplo: 26/08/2022
     */
    public static final DateTimeFormatter DD_M_YYYY_BARRA = DateTimeFormatter.ofPattern("dd/M/yyyy");
    /**
     * Formatador para MM/yyyy. Exemplo: 08/2022
     */
    public static final DateTimeFormatter MM_YYYY_BARRA = DateTimeFormatter.ofPattern("MM/yyyy");
    /**
     * Formatador para M/yyyy. Exemplo: 5/2022
     */
    public static final DateTimeFormatter M_YYYY_BARRA = DateTimeFormatter.ofPattern("M/yyyy");

    /**
     * Formatador para yyyy/MM/dd. Exemplo: 2022/08/26
     */
    public static final DateTimeFormatter YYYY_MM_DD_BARRA = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    /**
     * Formatador para yyyy/MM/dd. Exemplo: 30/12/2021 12:43
     */
    public static final DateTimeFormatter DD_MM_YYYY_HH_MM_BARRA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static final Pattern REGEX_NAO_DIGITO = Pattern.compile("\\D");

    public static String aplicaRegex(Pattern pattern, String notaTxt) {
        Matcher matcher = pattern.matcher(notaTxt);
        String resultado = "";
        if (matcher.find()) {
            resultado = matcher.group();
        }
        return Strings.nullToEmpty(resultado);
    }

    public static String aplicaRegex(String regex, String notaTxt) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(notaTxt);
        String resultado = "";
        if (matcher.find()) {
            resultado = matcher.group();
        }
        return Strings.nullToEmpty(resultado);
    }

    public static String tratarResultado(String resultado, Function<String, String> trataResultado) {
        return trataResultado.apply(resultado);
    }

    public static String processaRegex(String regex, String notaTxt, Function<String, String> trataResultado) {
        String resultado = aplicaRegex(regex, notaTxt);
        String resultadoTratado = tratarResultado(resultado, trataResultado);
        return resultadoTratado;
    }

    public static String processaRegex(Pattern pattern, String notaTxt, Function<String, String> trataResultado) {
        String resultado = aplicaRegex(pattern, notaTxt);
        String resultadoTratado = tratarResultado(resultado, trataResultado);
        return resultadoTratado;
    }

    public static Double converteParaDouble(String input) {
        return Double.valueOf(input);
    }

    public static Long converteParaLong(String input) {
        return Long.valueOf(input);
    }

    public static LocalDate converteParaLocalDate(String input, DateTimeFormatter formatter) {
        return LocalDate.parse(input, formatter);
    }

    public static LocalDateTime converteParaLocalDateTime(String input, DateTimeFormatter formatter) {
        return LocalDateTime.parse(input, formatter);
    }

    public static YearMonth converteParaYearMonth(String input, DateTimeFormatter formatter) {
        return YearMonth.parse(input, formatter);
    }

    public static String trocaVirgulaPorPonto(String input) {
        return input.replaceAll("\\.", "").replaceAll(",", ".");
    }

    public static String addCharToStringUsingSubString(String str, char c, int pos){
        return str.substring(0, pos) + c + str.substring(pos);
    }


    public static String removeNaoDigitos(String input) {
        return input.replaceAll(REGEX_NAO_DIGITO.pattern(), "");
    }

    public static String excluirString(String resultado, String regexASerExcluido) {
        return RegexUtil.tratarResultado(resultado, r -> r.replaceAll(regexASerExcluido, ""));
    }

    public static Integer converteParaInteger(String input) {
        return Integer.valueOf(input);
    }

    /**
     * Separador de Strings, caso haja alguma string junta no txt, como por exemplo: 0,000,00 (repare que são dois campos), pode ser utilizado o metodo abaixo.
     * Obs: Utilizar o metodo valorIr e valorInss da prefeitura de Bragança Paulista como referência.
     */
    public static String primeiroValorDaStringUnida(String str, int beginPosition, int endPosition) {
        return str.substring(beginPosition, endPosition);
    }

    /**
     * Converte o mês para numero. Exemplo: 06/SET/2022 para 06/09/2022.
     * Obs: Utilizar o metodo dataEmissão da prefeitura de Bragança Paulista como referência.
     */
    public static String converteStringMesParaStringNumero(String data) {
        String[] linhas = data.split("/");

        if (!(linhas.length > 2)) {
            return "Invalid Month";
        }

        Map<String, String> meses = new HashMap<>();
        meses.put("JAN", "01");
        meses.put("FEV", "02");
        meses.put("MAR", "03");
        meses.put("ABR", "04");
        meses.put("MAI", "05");
        meses.put("JUN", "06");
        meses.put("JUL", "07");
        meses.put("AGO", "08");
        meses.put("SET", "09");
        meses.put("OUT", "10");
        meses.put("NOV", "11");
        meses.put("DEZ", "12");

        String mes = meses.getOrDefault(linhas[1], "Invalid Month");
        return data.replaceFirst(linhas[1], mes);
    }

}
