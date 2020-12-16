package com.hzf.study.file.down.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成PDF文件
 */
public class PdfFile {

    /**
     * 填充html模板
     * @param templateFile 模板文件名
     * @param args 模板参数
     * @param pdfFile 生成文件路径
     */
    public static void template(String templateFile, Map<String, String> args, String pdfFile) {
        FileOutputStream output = null;
        try {
            // 读取模板文件,填充模板参数
            Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_30);
            freemarkerCfg.setTemplateLoader(new ClassTemplateLoader(PdfFile.class, "/pdfTemplate/"));
            Template template = freemarkerCfg.getTemplate(templateFile);
            StringWriter out = new StringWriter();
            if (args != null && args.size() > 0) {
                template.process(args, out);
            }
            String html = out.toString();

            // 设置字体以及字符编码
            ConverterProperties props = new ConverterProperties();
            DefaultFontProvider fontProvider = new DefaultFontProvider();
            PdfFont sysFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            FontProgram fontProgram = sysFont.getFontProgram();
            fontProvider.addFont(fontProgram, "UniGB-UCS2-H");
//            fontProvider.addStandardPdfFonts();
//            fontProvider.addFont("pdfTemplate/simsun.ttc");
//            fontProvider.addFont("pdfTemplate/STHeitibd.ttf");
            props.setFontProvider(new DefaultFontProvider(true, true, true));
            props.setCharset("utf-8");
            props.setBaseUri(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath());

            // 转换为PDF文档
            if(pdfFile.indexOf("/") > 0) {
                File path = new File(pdfFile.substring(0, pdfFile.indexOf("/")));
                if (!path.exists()) {
                    path.mkdirs();
                }
            }
            output = new FileOutputStream(pdfFile);
            PdfDocument pdf = new PdfDocument(new PdfWriter(output));
            pdf.setDefaultPageSize(PageSize.A4);
            Document document = HtmlConverter.convertToDocument(html, pdf, props);
//            PdfPage page = pdf.getPage(pdf.getNumberOfPages());
//            PageSize pageSize = pdf.getDefaultPageSize();
//            PdfCanvas pdfCanvas = new PdfCanvas(page);
//
//            pdfCanvas.saveState().moveTo(pageSize.getWidth() / 2 - 100, 100)
//                    .lineTo(pageSize.getWidth() / 2 - 100, 208)
//                    .stroke().restoreState();
//
//            pdfCanvas.roundRectangle(pageSize.getWidth() / 2 - 3 , 188, 20, 40, 3)
//                    .stroke();
//
//            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
//            pdfCanvas.beginText()
//                    .setFontAndSize(font, 12)
//                    .moveText(pageSize.getWidth() / 2 - "test".length() * 12 / 2, 45);
//            pdfCanvas.showText("test");
//            pdfCanvas.endText();

            document.getRenderer().close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Map<String, String> para = new HashMap<>();
        para.put("var1", "这个值是填充的变量");
        para.put("tab1", "<tr><td>1</td><td>第一个项目</td><td>第一个项目的具体内容</td></tr><tr><td>2</td><td>第二个项目</td><td>第二个项目的具体内容</td></tr>");
        para.put("mData0", "评级项目");
        para.put("mData1", "门店形象");
        para.put("mData2", "员工形象");
        para.put("mData3", "卫生环境");
        para.put("mData4", "设备检查");
        para.put("mData5", "服务");
        para.put("mData6", "附加评分项目");
        para.put("mData01", "21");
        para.put("mData11", "56");
        para.put("mData21", "46");
        para.put("mData31", "50");
        para.put("mData41", "80");
        para.put("mData51", "50");
        para.put("mData61", "80");
        template("template.html", para, "C:\\tmp\\" + System.currentTimeMillis() + ".pdf");
    }

}
