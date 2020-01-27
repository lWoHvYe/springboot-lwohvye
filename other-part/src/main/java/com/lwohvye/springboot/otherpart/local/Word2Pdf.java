package com.lwohvye.springboot.otherpart.local;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Word2Pdf {
    /**
     * 获取license
     *
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Word2Pdf.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 支持DOC, DOCX, OOXML, RTF, HTML, OpenDocument, PDF, EPUB, XPS, SWF等相互转换<br>
     *
     * @param
     */
    public static void doc2pdf(String inPath, String outPath) {

//		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
////			return;
//		}

        try {
            File file = new File(outPath); // 新建一个空白pdf文档
            if(!file.exists()){
                FileOutputStream os = new FileOutputStream(file);
                Document doc = new Document(inPath); // Address是将要被转化的word文档
                doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML,
                // OpenDocument, PDF,
                // EPUB, XPS, SWF 相互转换
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Word2Pdf.doc2pdf("E:/伊吾项目相关.doc","E:/伊吾项目相关.pdf");
    }
}
