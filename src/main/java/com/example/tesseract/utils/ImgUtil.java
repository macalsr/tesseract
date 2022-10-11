package com.example.tesseract.utils;

import com.example.tesseract.exception.ImageErrorException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ImgUtil {
    public static final Pattern REGEX_NAO_DIGITO = Pattern.compile("\\D");

    private ImgUtil() {
    }
    private static final String datapath = "src/main/resources/tessdata";
    private static final String language = "por";

    public static BufferedImage validaArquivo(MultipartFile file) throws IOException {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext) && !"gif".equals(ext)) {
            if("pdf".equals(ext)){
                PDDocument document = PDDocument.load(file.getInputStream());
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                BufferedImage image = list.get(0).convertToImage();
                document.close();
                return image;
            }
            throw new ImageErrorException("Formato de arquivo inválido");
        }
        return ImageIO.read(file.getInputStream());
    }

    public static String leitorImagem(BufferedImage img) {
        String text = "";
        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(datapath);
            tesseract.setLanguage(language);
            text = tesseract.doOCR(img);
        } catch (TesseractException e) {
            throw new ImageErrorException("Erro ao ler arquivo");
        }
        return text;
    }

    public static BufferedImage convert2GreyScale(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                int avg = (r + g + b) / 3;

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                img.setRGB(x, y, p);
            }
        }
        return img;
    }

    public static BufferedImage retiraBorda(BufferedImage file) {
        BufferedImage img = file.getSubimage(115, 80, 965, 1190);
        return convert2GreyScale(img);
    }

    public static String removeNaoDigitos(String input) {
        return input.replaceAll(REGEX_NAO_DIGITO.pattern(), "");
    }

    public static String coletaInformacoes(BufferedImage img) {
        Map<String, String> info = new HashMap<>();

        info.put("Nome Prefeitura", leitorImagem(convert2GreyScale(img.getSubimage(256,84, 599,32))));

        info.put("Número da Nota", leitorImagem(convert2GreyScale(img.getSubimage(879,99, 193,24))));
        info.put("Data da Emissão", leitorImagem(convert2GreyScale(img.getSubimage(880,142, 233,23))));
        info.put("Código verificação", leitorImagem(convert2GreyScale(img.getSubimage(880,186, 207,22))));
        info.put("CNPJ Prestador", removeNaoDigitos(leitorImagem(convert2GreyScale(img.getSubimage(316,237, 165,26)))));
        info.put("Razão Social Prestador", leitorImagem(convert2GreyScale(img.getSubimage(376,259, 268,24))));
        info.put("Endereço Prestador", leitorImagem(convert2GreyScale(img.getSubimage(309,281, 646,24))));
        info.put("Município Prestador", leitorImagem(convert2GreyScale(img.getSubimage(307,302, 156,37))));
        info.put("UF Prestador", leitorImagem(convert2GreyScale(img.getSubimage(706,301, 48,28))).toUpperCase());
        info.put("Inscrição municipal", leitorImagem(convert2GreyScale(img.getSubimage(823,231, 100,36))));

        info.put("Razão Social Tomador", leitorImagem(convert2GreyScale(img.getSubimage(269,367, 223,30))));
        info.put("CNPJ Tomador", removeNaoDigitos(leitorImagem(convert2GreyScale(img.getSubimage(210,393, 165,25)))));
        info.put("Endereço Tomador", leitorImagem(convert2GreyScale(img.getSubimage(200,412, 752,29))));
        info.put("Município Tomador", leitorImagem(convert2GreyScale(img.getSubimage(201,437, 83,22))));
        info.put("UF Tomador", leitorImagem(convert2GreyScale(img.getSubimage(536,435, 38,24))));
        info.put("Discriminação do Serviço", leitorImagem(convert2GreyScale(img.getSubimage(119,540, 871,76))));
        info.put("Valor do Serviço", leitorImagem(convert2GreyScale(img.getSubimage(712,973, 115,26))));
        info.put("Código do Serviço", leitorImagem(convert2GreyScale(img.getSubimage(117,1056, 49,20))));
        info.put("Base de Calculo", leitorImagem(convert2GreyScale(img.getSubimage(438,1097, 68,18))));
        info.put("Alíquota", leitorImagem(convert2GreyScale(img.getSubimage(594,1097, 36,17))));
        info.put("Valor ISS", leitorImagem(convert2GreyScale(img.getSubimage(829,1098, 44,17))));
        info.put("Outras Informações", leitorImagem(convert2GreyScale(img.getSubimage(117,1056, 957,67))));

        return info.toString().replaceAll("=", " = ");
    }
}