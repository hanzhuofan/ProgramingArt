package com.hzf.study.file.down.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author zhuofan.han
 * @Date 2020/12/8 13:46
 */
public class HtmlToPdf {
    private static final String FILE_DIR = "c:\\tmp\\";
    private static final String DEST = FILE_DIR + "HelloWorld_CN_HTML.pdf";
    private static final String HTML = "/pdfTemplate/template.html";
    private static final String FONT = "/font/NotoSansCJKsc-Regular.otf";

    public static void main(String[] args) {
        try {
            String path = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
            HtmlToPdf html = new HtmlToPdf();
            html.tomPdf(path + HTML, DEST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tomPdf(String html, String DEST) throws FileNotFoundException, IOException {
        ConverterProperties props = new ConverterProperties();
        DefaultFontProvider defaultFontProvider = new DefaultFontProvider(false, false, false);
        defaultFontProvider.addFont(FONT);
        props.setFontProvider(defaultFontProvider);
        PdfWriter writer = new PdfWriter(DEST);
        PdfDocument pdf = new PdfDocument(writer);
//        pdf.setDefaultPageSize(new PageSize(595, 14400));
        Document document = HtmlConverter.convertToDocument(new FileInputStream(html), pdf, props);
        // 将所有内容在一个页面显示
//        EndPosition endPosition = new EndPosition();
//        LineSeparator separator = new LineSeparator(endPosition);
//        document.add(separator);
//        document.getRenderer().close();
//        PdfPage page = pdf.getPage(1);
//        float y = endPosition.getY() - 36;
//        page.setMediaBox(new Rectangle(0, y, 595, 14400 - y));
        document.close();
        pdf.close();
    }

    public static ByteArrayOutputStream html2Pdf(String html) throws FileNotFoundException, IOException {
        ConverterProperties props = new ConverterProperties();
        DefaultFontProvider defaultFontProvider = new DefaultFontProvider(false, false, false);

        defaultFontProvider.addFont(FONT);
        props.setFontProvider(defaultFontProvider);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(bao);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(new PageSize(595, 14400));
        Document document = HtmlConverter.convertToDocument(html, pdf, props);
        EndPosition endPosition = new EndPosition();
        LineSeparator separator = new LineSeparator(endPosition);
        document.add(separator);
        document.getRenderer().close();
        PdfPage page = pdf.getPage(1);
        float y = endPosition.getY() - 36;
        page.setMediaBox(new Rectangle(0, y, 595, 14400 - y));
        document.close();
        return bao;
    }

    /**
     * 定义操作区域
     */
    static class EndPosition implements ILineDrawer {
        // y坐标
        protected float y;

        /**
         * @Description: 获取y坐标
         * @return
         */
        public float getY() {
            return y;
        }

        /**
         * @Description: 操作画布特定区域
         * @param pdfCanvas:操作画布
         * @param rect:操作区域
         */
        @Override
        public void draw(PdfCanvas pdfCanvas, Rectangle rect) {
            this.y = rect.getY();
        }

        /**
         * @Description: 获取行颜色
         * @return
         */
        @Override
        public Color getColor() {
            return null;
        }

        /**
         * @Description: 获取行宽
         * @return
         */
        @Override
        public float getLineWidth() {
            return 0;
        }

        /**
         * @Description: 设置行颜色
         * @param color
         */
        @Override
        public void setColor(Color color) {
        }

        /**
         * @Description: 设置行宽
         * @param lineWidth:宽度
         */
        @Override
        public void setLineWidth(float lineWidth) {
        }
    }
}
