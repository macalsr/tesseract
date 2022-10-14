package com.example.tesseract.domain;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.tesseract.utils.ImgUtil.*;

public class NotaFiscalSpImgToTxt {

    private static final Map<String, String> info = new LinkedHashMap<>();

    public static String coletaInformacoes(BufferedImage img) {

        //Capturas perfeitas
        info.put("Nome Prefeitura", leitorImagem(img.getSubimage(529, 177, 1252, 65)));
        info.put("Número RPS", leitorImagem(convert2GreyScale(img.getSubimage(921, 385, 112, 36))));

        //Before
        //info.put("Série RPS", leitorImagem(convert2GreyScale(img.getSubimage(800, 384, 717, 38))));

        //After
        info.put("Série RPS", leitorImagem(convert2GreyScale(img.getSubimage(790, 388, 730, 41))));

        info.put("Data da Emissão", leitorImagem(convert2GreyScale(img.getSubimage(1833, 295, 226, 50))));

        info.put("CNPJ Prestador", removeNaoDigitos(leitorImagem(convert2GreyScale(img.getSubimage(668, 496, 324, 49)))));
        info.put("Nome/Razão Social Prestador", leitorImagem(convert2GreyScale(img.getSubimage(785, 545, 1228, 42))));
        info.put("Endereço Prestador", leitorImagem(convert2GreyScale(img.getSubimage(640, 585, 1599, 50))));
        info.put("Munícipio Prestador", leitorImagem(convert2GreyScale(img.getSubimage(640, 630, 462, 49))));
        info.put("UF Prestador", leitorImagem(convert2GreyScale(img.getSubimage(1471, 625, 83, 63))));
        info.put("Inscrição Municipal Prestador", leitorImagem(convert2GreyScale(img.getSubimage(1721, 481, 311, 62))));

        info.put("Nome/Razão Social Tomador", leitorImagem(convert2GreyScale(img.getSubimage(561,775, 990, 47))));
        info.put("CNPJ Tomador", removeNaoDigitos(leitorImagem(convert2GreyScale(img.getSubimage(438, 818, 657, 47)))));
        info.put("Endereço Tomador", leitorImagem(convert2GreyScale(img.getSubimage(418, 866, 1820, 44))));
        info.put("Munícipio Tomador", leitorImagem(convert2GreyScale(img.getSubimage(418, 909, 615, 44))));
        info.put("UF Tomador", leitorImagem(convert2GreyScale(img.getSubimage(1119, 909, 93, 46))));
        info.put("Inscrição Municipal Tomador", leitorImagem(convert2GreyScale(img.getSubimage(1721, 814, 425,54))));

        //info.put("Código de verificação", leitorImagem(convert2GreyScale(img.getSubimage(1915, 394, 241,40))));
        info.put("Código de verificação", leitorImagem(img.getSubimage(1912, 393, 246,40)));
        info.put("Valor do serviço", leitorImagem(convert2GreyScale(img.getSubimage(1489, 2027, 403,53))));
        info.put("Código do serviço", leitorImagem(convert2GreyScale(img.getSubimage(242, 2197, 104,44))));
        info.put("Base de cálculo", leitorImagem(convert2GreyScale(img.getSubimage(780, 2285, 278,38))));
        info.put("Alíquota", leitorImagem(convert2GreyScale(img.getSubimage(1225, 2290, 88,34))));
        info.put("Valor ISS", leitorImagem(convert2GreyScale(img.getSubimage(1645, 2288, 179,36))));

        //Capturas em bloco
//        info.put("Prestador de serviços", leitorImagem(convert2GreyScale(img.getSubimage(244, 446, 2002, 261))));

        //Capturas em bloco
//        info.put("Tomador de serviços", leitorImagem(convert2GreyScale(img.getSubimage(243, 724, 2001, 232))));

        //Possiveis tratamentos
        //Exemplo 1
//        info.put("Numero da Nota", leitorImagem(convert2GreyScale(img.getSubimage(1857, 209, 382, 48))));

        //Exemplo 2
//        info.put("Numero da Nota", leitorImagem(convert2GreyScale(img.getSubimage(1833, 173, 414, 259))));

        return info.toString().replaceAll("=", ": ");
    }

}
