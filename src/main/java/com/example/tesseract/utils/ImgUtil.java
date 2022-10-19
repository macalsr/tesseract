package com.example.tesseract.utils;

import com.example.tesseract.domain.exceptions.ImageErrorException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class ImgUtil {

    private static final Pattern REGEX_QUEBRA_LINHA = Pattern.compile("\\n");
    private static final String datapath = "src/main/resources/tessdata";
    private static final String output = "src/test/resources/outputfiles/";

    public static BufferedImage validaArquivo(MultipartFile file) throws IOException {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext) && !"gif".equals(ext)) {
            if ("pdf".equals(ext)) {
                PDDocument document = PDDocument.load(file.getInputStream());
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                BufferedImage image = conversorImagem(list);
                //Serve como teste, basta criar o dir "output" e a imagem é gravada lá
                //ImageIO.write(image, "png", new File(output + "imagem.png"));
                document.close();
                return image;
            }
            throw new ImageErrorException("Formato de arquivo inválido");
        }
        return ImageIO.read(file.getInputStream());
    }

    public static BufferedImage conversorImagem(List<PDPage> list) throws IOException {
        return list.get(0).convertToImage(BufferedImage.TYPE_INT_RGB, 300);
    }

    public static String leitorImagem(BufferedImage img, String language) {
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

    public static String removeQuebraLinha(String input) {
        return input.replaceAll(REGEX_QUEBRA_LINHA.pattern(), "");
    }

    public static BufferedImage calcularCoordenadas(BufferedImage img, double x, double y, double w, double h) throws IOException {
        BufferedImage img2 =  img.getSubimage(
                (int) ((x / 2479) * img.getWidth()),
                (int) ((y / 3508) * img.getHeight()),
                (int) ((w / 2479) * img.getWidth()),
                (int) ((h / 3508) * img.getHeight())
        );
        ImageIO.write(img2, "png", new File(output + "imagem.png"));
        return img2;
    }

}