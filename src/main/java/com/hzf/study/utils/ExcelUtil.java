package com.hzf.study.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.OnceAbsoluteMerge;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuofan.han
 * @date 2021/7/30
 */
//@Service
public class ExcelUtil {
    public static void main(String[] args) {
        new ExcelUtil().init();
    }

    List<DemoData> dataList = new ArrayList<>();

    List<OnceAbsoluteMergeStrategy> mergeStrategies = new ArrayList<>();

    //    @PostConstruct
    public void init() {
        String templateFileName = "D:\\Code\\programming\\src\\main\\resources\\excelTemplate\\Inspection签到报表.xls";
        String fileName = "D:\\Code\\programming\\src\\main\\resources\\excelTemplate\\" + System.currentTimeMillis() + ".xls";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(templateFileName, DemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).headRowNumber(3).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }

        initData();

//        OnceAbsoluteMergeStrategy loopMergeStrategy = new OnceAbsoluteMergeStrategy(3, 7, 0, 0);
//        OnceAbsoluteMergeStrategy loopMergeStrategy2 = new OnceAbsoluteMergeStrategy(8, 12, 0, 0);

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 9);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 9);
        contentWriteFont.setFontName("宋体");

        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setWrapped(true);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        ExcelWriterBuilder writerBuilder = EasyExcel.write(fileName, DemoData.class);
        for (OnceAbsoluteMergeStrategy mergeStrategy : mergeStrategies) {
            writerBuilder.registerWriteHandler(mergeStrategy);
        }
        writerBuilder
//                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new CustomCellWriteHandler())
                .withTemplate(templateFileName).sheet().needHead(false).doWrite(dataList);
    }

    private void initData() {
        int start = 3;
        for (int i = 0; i < 200; i++) {
            String name = RandomStringUtils.randomAlphanumeric(10);
            String number = RandomStringUtils.randomAlphanumeric(10);
            String department = RandomStringUtils.randomAlphanumeric(10);
            String position = RandomStringUtils.randomAlphanumeric(10);
            int sum = RandomUtils.nextInt(1, 100);
            if (sum != 1) {
                mergeStrategies.add(new OnceAbsoluteMergeStrategy(start, start + sum - 1, 0, 0));
            }
            start += sum;
            for (int j = 0; j < sum; j++) {
                DemoData data = new DemoData();
                data.setName(name);
                data.setNumber(number);
                data.setDepartment(department);
                data.setPosition(position);
                data.setDate(RandomStringUtils.randomAlphanumeric(10));
                data.setTime(RandomStringUtils.randomAlphanumeric(10));
                data.setLongitude(RandomStringUtils.randomAlphanumeric(20));
                data.setLatitude(RandomStringUtils.randomAlphanumeric(20));
                data.setPlace(RandomStringUtils.randomAlphanumeric(20));
                data.setAddress(RandomStringUtils.randomAlphanumeric(100));
                data.setVisitor(RandomStringUtils.randomAlphanumeric(10));
                data.setDescription(RandomStringUtils.randomAlphanumeric(200));
                data.setUrl1("图");
                data.setUrl2("图");
                data.setUrl3("图");
                data.setUrl4("图");
                data.setUrl5("图");
                dataList.add(data);
            }
        }
    }

    @Data
    public static class CustomCellWriteHandler extends AbstractCellWriteHandler {

        @Override
        public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
            if (cell.getColumnIndex() == 12 || cell.getColumnIndex() == 13 || cell.getColumnIndex() == 14 || cell.getColumnIndex() == 15 || cell.getColumnIndex() == 16) {
                CreationHelper helper = writeSheetHolder.getSheet().getWorkbook().getCreationHelper();
                Hyperlink hyperlink = helper.createHyperlink(HyperlinkType.URL);
//                hyperlink.setAddress("https://static.dingtalk.com/media/lADPDh0cPgzJsEjNBQDNAlA_592_1280.jpg");
                hyperlink.setAddress("https://static.dingtalk.com/media/"+ RandomStringUtils.randomAlphanumeric(10) +"_592_1280.jpg");
                cell.setHyperlink(hyperlink);
            }
        }
    }

    /**
     * 解析监听器，
     * 每解析一行会回调invoke()方法。
     * 整个excel解析结束会执行doAfterAllAnalysed()方法
     */
    @Data
    public static class DemoDataListener extends AnalysisEventListener {

        private List<Object> datas = new ArrayList<>();

        /**
         * 逐行解析
         * object : 当前行的数据
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            //当前行
            // context.getCurrentRowNum()
            if (object != null) {
                datas.add(object);
            }
        }


        /**
         * 解析完所有数据后会调用该方法
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //解析结束销毁不用的资源
            System.out.println(datas.toString());
        }

    }

    @Data
//    @OnceAbsoluteMerge
    public static class DemoData {
        //        @ContentLoopMerge(eachRow = 2)
//        @ExcelProperty("字符串标题")
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String name;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String number;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String department;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String position;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String date;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String time;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String longitude;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String latitude;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String place;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String address;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String visitor;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String description;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String url1;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String url2;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String url3;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String url4;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER, wrapped = true, borderLeft = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN)
        private String url5;
    }
}
