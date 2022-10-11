package com.example.tesseract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

@SuppressWarnings("unchecked")
public class ConvertPDFPagesToImages {
    public static void main(String[] args) {
        try {
            String sourceDir = "C:/tmp/DVARLVN-5863-TERESINA.pdf"; // Pdf files are read from this folder
            String destinationDir = "C:/tmp/img/"; // converted images from pdf document are saved here

            File sourceFile = new File(sourceDir);
            File destinationFile = new File(destinationDir);
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
                System.out.println("Folder Created -> " + destinationFile.getAbsolutePath());
            }
            if (sourceFile.exists()) {
                System.out.println("Images copied to Folder: " + destinationFile.getName());
                PDDocument document = PDDocument.load(sourceDir);
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                System.out.println("Total files to be converted -> " + list.size());

                String fileName = sourceFile.getName().replace(".pdf", "");
                int pageNumber = 1;
                for (PDPage page : list) {
                    BufferedImage image = page.convertToImage().getSubimage(0, 0, 2000, 3000);
                    File outputfile = new File(destinationDir + fileName + "_" + pageNumber + ".png");
                    System.out.println("Image Created -> " + outputfile.getName());
                    ImageIO.write(image, "png", outputfile);
                    pageNumber++;
                }
                document.close();
                System.out.println("Converted Images are saved at -> " + destinationFile.getAbsolutePath());
            } else {
                System.err.println(sourceFile.getName() + " File not exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}