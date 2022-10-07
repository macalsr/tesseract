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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImgUtil {

    private static final String resourcesDataPath = "src/main/resources/test-data";
    private static String datapath = "src/main/resources/tessdata";
    private static final String language = "por";

    public static BufferedImage convertPdf2Image(File file) throws IOException {
        PDDocument doc = PDDocument.load(new FileInputStream(file));
        List<PDPage> pages = doc.getDocumentCatalog().getAllPages();
        String fileName = file.getName().replace(".pdf", "");
        BufferedImage image = pages.get(0).convertToImage();
        ImageIO.write(image, "png", new File(resourcesDataPath + "output" + ".png"));
        return image;
    }

    public static MultipartFile validaArquivo(MultipartFile file) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext) && !"gif".equals(ext)) {
            throw new ImageErrorException("A extensão do arquivo não é compativel.");
        }
        return file;
    }

    public static String leitorImagem(MultipartFile file) {
        String text = "";
        try {
            BufferedImage img = ImageIO.read(file.getInputStream());
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(datapath);
            tesseract.setLanguage(language);
            text = tesseract.doOCR(img);
        } catch (IOException | TesseractException e) {
            throw new ImageErrorException("Erro ao ler arquivo");
        }
        return text;
    }

    public static void cortaImagem(MultipartFile file) throws IOException {
        String outputFiles = "src/main/resources/outputfiles/";
        List<BufferedImage> sliceOfImages = new ArrayList<>();
        BufferedImage img = ImageIO.read(file.getInputStream());
        BufferedImage model = img.getSubimage(0, 0, img.getWidth(), img.getHeight() / 5);
//        ImageIO.write(model, "png", new File(outputFiles + "imagem1.png"));
        int orin = img.getHeight();
        int modelHeight = model.getHeight();
        sliceOfImages.add(model);

        int slice = 1;
        while (slice < 5) {
            BufferedImage picture = img.getSubimage(0, 0, img.getWidth(), img.getHeight() - modelHeight);
            modelHeight = modelHeight * slice;
            sliceOfImages.add(picture);
            ImageIO.write(picture, "png", new File(outputFiles + "imagem" + slice + ".png"));
            slice++;
        }

//        BufferedImage image1 = img.getSubimage(0, 0, img.getWidth(), img.getHeight() / 5);
//        BufferedImage image2 = img.getSubimage(0, 0, img.getWidth() - image1.getWidth(), img.getHeight() / 4);
//        BufferedImage image3 = img.getSubimage(0, 0, img.getWidth() - image2.getWidth(), img.getHeight() / 3);
//        BufferedImage image4 = img.getSubimage(0, 0, img.getWidth() - image3.getWidth(), img.getHeight() / 2);
//        BufferedImage image5 = img.getSubimage(0, 0, img.getWidth() - image4.getWidth(), img.getHeight() / 1);
//        images.add(image1);
//        images.add(image2);
//        images.add(image3);
//        images.add(image4);
//        images.add(image5);
    }

}