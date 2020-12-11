//package com.hzf.study.file.down.pdf;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//
///**
// * @Author zhuofan.han
// * @Date 2020/12/8 13:46
// */
//public class HelloWorld {
//    private static final String FILE_DIR = "c:\\tmp\\";
//    public static void main(String[] args) {
//        try {
//            //Step 1—Create a Document.
//            Document document = new Document();
//            //Step 2—Get a PdfWriter instance.
//            PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createSamplePDF.pdf"));
//            //Step 3—Open the Document.
//            document.open();
//            //Step 4—Add content.
//            document.add(new Paragraph("Hello World"));
//            //Step 5—Close the Document.
//            document.close();
//        } catch (DocumentException | FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
