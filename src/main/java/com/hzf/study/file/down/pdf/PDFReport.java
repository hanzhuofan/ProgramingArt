//package com.hzf.study.file.down.pdf;
//
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtils;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.CategoryAxis;
//import org.jfree.chart.axis.ValueAxis;
//import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
//import org.jfree.chart.labels.StandardPieToolTipGenerator;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PiePlot;
//import org.jfree.chart.plot.PiePlot3D;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.title.TextTitle;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.data.general.DefaultPieDataset;
//import org.jfree.data.general.PieDataset;
//
//import java.awt.*;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//
///**
// * @Author zhuofan.han
// * @Date 2020/12/8 13:46
// */
//public class PDFReport {
//    private static final String FILE_DIR = "C:\\tmp\\";
//
//    public static void main(String[] args) {
//PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
//        try {
//            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//            Font fontChinese_title = new Font(bfChinese, 20, Font.BOLD, BaseColor.BLACK);
//            Font fontChinese_content = new Font(bfChinese, 10, Font.NORMAL, BaseColor.BLACK);
//            //页面大小
//            Rectangle rect = new Rectangle(PageSize.A4);
//            //页面背景色
//            rect.setBackgroundColor(BaseColor.WHITE);
//            Document document = new Document(rect);
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createPDF.pdf"));
//            //PDF版本(默认1.4)
//            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
//            //文档属性
//            document.addTitle("Title@sample");
//            document.addAuthor("Author@rensanning");
//            document.addSubject("Subject@iText sample");
//            document.addKeywords("Keywords@iText");
//            document.addCreator("Creator@iText");
//            //页边空白
//            document.setMargins(10, 20, 30, 40);
//            document.open();
//
//            PdfPTable table = new PdfPTable(3);
//            PdfPCell cell;
//            cell = new PdfPCell(new Phrase("门店选择：西安1店\n标签快选：--\n起止时间：2020/12/1-2020/12/8", fontChinese_title));
//            cell.setColspan(4);
//            table.addCell(cell);
//
//            document.add(table);
//            document.add(new PdfDiv());
//
//            table = new PdfPTable(3);
//            cell = new PdfPCell(new Phrase("区域考评统计图", fontChinese_title));
//            cell.setColspan(4);
//            table.addCell(cell);
//            cell = new PdfPCell(new Phrase("区域考评统计图", fontChinese_title));
//            cell.setColspan(4);
//            table.addCell(cell);
//
//            document.add(table);
//
//            document.newPage();
//            /**
//             * 生成统计图
//             */
//            PieDataset dataset = pieDataSet();
//            JFreeChart chart = ChartFactory.createPieChart(
//                    "区域考评统计图", // chart title
//                    dataset,// data
//                    true,// include legend
//                    true,
//                    false
//            );
//            PiePlot plot = (PiePlot) chart.getPlot();
//            //设置Label字体
//            plot.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
//            //设置legend字体
//            chart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 48));
//            // 图片中显示百分比:默认方式
////            plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
////                    StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
//            // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
////            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})",
////                    NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
//            // 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
//            plot.setLabelGenerator(null);
//            plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
//            // 设置背景色为白色
//            chart.setBackgroundPaint(Color.white);
//            plot.setBackgroundPaint(Color.white);
//            // 指定图片的透明度(0.0-1.0)
//            plot.setForegroundAlpha(1.0f);
//            // 指定显示的饼图上圆形(false)还椭圆形(true)
//            plot.setCircular(true);
//            // 去边框
//            plot.setOutlineVisible(false);
//            // 设置图标题的字体
//            java.awt.Font font = new java.awt.Font("黑体", java.awt.Font.BOLD, 20);
//            TextTitle title = new TextTitle("项目状态分布");
//            title.setFont(font);
//            chart.setTitle(title);
//            FileOutputStream fos_jpg = null;
//            try {
//                fos_jpg = new FileOutputStream(FILE_DIR + "logo.png");
//                ChartUtils.writeChartAsPNG(fos_jpg, chart ,600, 600);
//                fos_jpg.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            document.newPage();
//            Paragraph pieParagraph = new Paragraph("02、饼状图测试", fontChinese_content);
//            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
//            document.add(pieParagraph);
//            Image pieImage = Image.getInstance(FILE_DIR + "logo.png");
//            pieImage.setAlignment(Image.ALIGN_CENTER);
//            pieImage.scaleAbsolute(100, 100);
//            document.add(pieImage);
//
//            /**
//             * 折线图
//             */
//            CategoryDataset lineDataset = lineDataset();
//            JFreeChart lineChart = ChartFactory.createLineChart("java图书销量",
//                    "java图书",
//                    "销量",
//                    lineDataset,
//                    PlotOrientation.VERTICAL,
//                    true,
//                    true,
//                    true
//            );
//            lineChart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 16));
//            //获取title
//            lineChart.getTitle().setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 16));
//
//            //获取绘图区对象
//            CategoryPlot linePlot = lineChart.getCategoryPlot();
//            linePlot.setBackgroundAlpha(0.1f);
//
//            //获取坐标轴对象
//            CategoryAxis lineAxis = linePlot.getDomainAxis();
//            //设置坐标轴字体
//            lineAxis.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
//            //设置坐标轴标尺值字体（x轴）
//            lineAxis.setTickLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
//            //获取数据轴对象（y轴）
//            ValueAxis rangeAxis = linePlot.getRangeAxis();
//            rangeAxis.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
//
//
//            /*
//             * 生成图片
//             */
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream(FILE_DIR + "logo.png");
//                ChartUtils.writeChartAsJPEG(fos, 0.7f, lineChart, 600, 300);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            Paragraph lineParagraph = new Paragraph("02、折线图测试", fontChinese_content);
//            lineParagraph.setAlignment(Paragraph.ALIGN_LEFT);
//            document.add(lineParagraph);
//            Image image = Image.getInstance(FILE_DIR + "logo.png");
//            image.setAlignment(Image.ALIGN_CENTER);
//            image.scaleAbsolute(600, 300);
//            document.add(image);
//
//            System.out.println("over");
//            document.close();
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static CategoryDataset lineDataset() {
//        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
//        //添加第一季度数据
//        dataSet.addValue(6000, "第一季度", "J2SE类");
//        dataSet.addValue(3000, "第一季度", "J2ME类");
//        dataSet.addValue(12000, "第一季度", "J2EE类");
//        //添加第二季度数据
//        dataSet.addValue(8000, "第二季度", "J2SE类");
//        dataSet.addValue(4000, "第二季度", "J2ME类");
//        dataSet.addValue(6000, "第二季度", "J2EE类");
//        //添加第三季度数据
//        dataSet.addValue(5000, "第三季度", "J2SE类");
//        dataSet.addValue(4000, "第三季度", "J2ME类");
//        dataSet.addValue(8000, "第三季度", "J2EE类");
//        //添加第四季度数据
//        dataSet.addValue(8000, "第四季度", "J2SE类");
//        dataSet.addValue(2000, "第四季度", "J2ME类");
//        dataSet.addValue(9000, "第四季度", "J2EE类");
//        return dataSet;
//    }
//
//    private static PieDataset pieDataSet() {
//        DefaultPieDataset dataset = new DefaultPieDataset();
//        dataset.setValue("立即督导", new Double(47.83));
//        dataset.setValue("待改善", new Double(21.74));
//        dataset.setValue("良好", new Double(30.43));
//        return dataset;
//    }
//}
