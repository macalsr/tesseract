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
import java.util.ArrayList;
import java.util.List;

public class ImgUtil {
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

    public static String coletaInformacoes(BufferedImage img) {
        List<String> info = new ArrayList<>();

        //Número da Nota
        BufferedImage numeroNota = img.getSubimage(767, 4, 161, 41);

        //Código de Verificação
        BufferedImage codigoDeVerificacao = img.getSubimage(767, 89, 161, 38);

        //Prestador de serviços
        BufferedImage prestadorServicos = img.getSubimage(117, 135, 725, 117);

        //Serve somente para demostração, não faz diferença
        info.add(leitorImagem(numeroNota));
        info.add(leitorImagem(codigoDeVerificacao));
        info.add(leitorImagem(prestadorServicos));

        return info.toString();
    }
}