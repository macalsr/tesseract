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
import java.util.List;
import java.util.regex.Pattern;

public class ImgUtil {

    private static final Pattern REGEX_QUEBRA_LINHA = Pattern.compile("\\n");
    private static final String datapath = "src/main/resources/tessdata";

    public static BufferedImage validaArquivo(MultipartFile file) throws IOException {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext) && !"gif".equals(ext)) {
            if ("pdf".equals(ext)) {
                PDDocument document = PDDocument.load(file.getInputStream());
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                BufferedImage image = list.get(0).convertToImage(BufferedImage.TYPE_INT_RGB, 200);
                //Serve como teste, basta criar o dir "output" e a imagem é gravada lá
                //ImageIO.write(image, "png", new File(output + "imagem.png"));
                document.close();
                return image;
            }
            throw new ImageErrorException("Formato de arquivo inválido");
        }
        return ImageIO.read(file.getInputStream());
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

    public static BufferedImage calcularCoordenas(BufferedImage img, int x, int y, int w, int h) {
        return img.getSubimage(
                img.getWidth() * x / img.getWidth(),
                img.getHeight() * y / img.getHeight(),
                img.getWidth() * w / img.getWidth(),
                img.getHeight() * h / img.getHeight()
        );
    }

}