package com.hzf.study.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String templateFileName = "D:\\Code\\programming\\src\\main\\resources\\excelTemplate\\Inspection签到报表.xlsx";
        String fileName = "D:\\Code\\programming\\src\\main\\resources\\excelTemplate\\" + System.currentTimeMillis() + ".xlsx";
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

        List<Map<String, Object>> cellStyleList = new ArrayList<>();
        XSSFColor backgroundColor = CustomCellStyleHandler.getRGBColor(243, 29, 101);
        cellStyleList.add(CustomCellStyleHandler.createBackgroundColorCellStyleMap("Sheet1", 0, 0, backgroundColor));

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
//                .registerWriteHandler(new CustomCellStyleHandler(cellStyleList))
                .withTemplate(templateFileName).sheet().needHead(false).doWrite(dataList);
    }

    private void initData() {
        int start = 3;
        for (int i = 0; i < 100; i++) {
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

    /**
     * 自定义单元格样式处理器（支持字体样式、背景颜色、边框样式、对齐方式、自动换行）
     */
    public static class CustomCellStyleHandler implements RowWriteHandler {
        /**
         * sheet名称KEY
         */
        public static final String KEY_SHEET_NAME = "sheetName";
        /**
         * 列索引key
         */
        public static final String KEY_COL_INDEX = "colIndex";
        /**
         * 行索引key
         */
        public static final String KEY_ROW_INDEX = "rowIndex";
        /**
         * 字体名称key
         */
        public static final String KEY_FONT_NAME = "fontName";
        /**
         * 字体大小key
         */
        public static final String KEY_FONT_HEIGHT = "fontHeight";
        /**
         * 字体颜色key
         */
        public static final String KEY_FONT_COLOR = "fontColor";
        /**
         * 字体加粗key
         */
        public static final String KEY_FONT_BOLD = "fontBold";
        /**
         * 字体斜体key
         */
        public static final String KEY_FONT_ITALIC = "fontItalic";
        /**
         * 字体下划线key
         */
        public static final String KEY_FONT_UNDER_LINE = "fontUnderLine";
        /**
         * 字体上标下标key
         */
        public static final String KEY_FONT_TYPE_OFFSET = "fontTypeOffset";
        /**
         * 字体删除线key
         */
        public static final String KEY_FONT_STRICKEOUT = "fontStrikeout";
        /**
         * 背景颜色key
         */
        public static final String KEY_BACKGROUND_COLOR = "backgroundColor";

        /**
         * 上边框线条类型key
         */
        public static final String KEY_BORDER_TOP = "borderTop";
        /**
         * 右边框线条类型key
         */
        public static final String KEY_BORDER_RIGHT = "borderRight";
        /**
         * 下边框线条类型key
         */
        public static final String KEY_BORDER_BOTTOM = "borderBottom";
        /**
         * 左边框线条类型key
         */
        public static final String KEY_BORDER_LEFT = "borderLeft";
        /**
         * 上边框线条颜色key
         */
        public static final String KEY_TOP_BORDER_COLOR = "topBorderColor";
        /**
         * 上边框线条颜色key
         */
        public static final String KEY_RIGHT_BORDER_COLOR = "rightBorderColor";
        /**
         * 下边框线条颜色key
         */
        public static final String KEY_BOTTOM_BORDER_COLOR = "bottomBorderColor";
        /**
         * 左边框线条颜色key
         */
        public static final String KEY_LEFT_BORDER_COLOR = "leftBorderColor";
        /**
         * 水平对齐方式key
         */
        public static final String KEY_HORIZONTAL_ALIGNMENT = "horizontalAlignment";
        /**
         * 垂直对齐方式key
         */
        public static final String KEY_VERTICAL_ALIGNMENT = "verticalAlignment";
        /**
         * 自动换行方式key
         */
        public static final String KEY_WRAP_TEXT = "wrapText";
        /**
         * sheet页名称列表
         */
        private List<String> sheetNameList;
        private List<Map<String, Object>> cellStyleList = new ArrayList<>();

        /**
         * 自定义样式适配器构造方法
         *
         * @param cellStyleList 样式信息
         */
        public CustomCellStyleHandler(List<Map<String, Object>> cellStyleList) {
            if (cellStyleList == null || cellStyleList.size() <= 0) {
                return;
            }
            cellStyleList = cellStyleList.stream().filter(x ->
                    //判断sheet名称KEY是否存在
                    x.keySet().contains(KEY_SHEET_NAME) == true && x.get(KEY_SHEET_NAME) != null
                            && StringUtils.isNotBlank(x.get(KEY_SHEET_NAME).toString())
                            //判断列索引KEY是否存在
                            && x.keySet().contains(KEY_COL_INDEX) == true && x.get(KEY_COL_INDEX) != null
                            && StringUtils.isNotBlank(x.get(KEY_COL_INDEX).toString())
                            //判断行索引KEY是否存在
                            && x.keySet().contains(KEY_ROW_INDEX) == true && x.get(KEY_ROW_INDEX) != null
                            && StringUtils.isNotBlank(x.get(KEY_ROW_INDEX).toString())
                            //字体样式
                            //判断字体名称KEY是否存在
                            && x.keySet().contains(KEY_FONT_NAME) == true
                            && (x.get(KEY_FONT_NAME) == null || x.get(KEY_FONT_NAME) != null
                            && StringUtils.isNotBlank(x.get(KEY_FONT_NAME).toString()))
                            //判断字体大小KEY是否存在
                            && x.keySet().contains(KEY_FONT_HEIGHT) == true
                            && (x.get(KEY_FONT_HEIGHT) == null || x.get(KEY_FONT_HEIGHT) instanceof Double)
                            //判断字体颜色KEY是否存在
                            && x.keySet().contains(KEY_FONT_COLOR) == true
                            && (x.get(KEY_FONT_COLOR) instanceof IndexedColors || x.get(KEY_FONT_COLOR) instanceof XSSFColor
                            || x.get(KEY_FONT_COLOR) == null)
                            //判断字体加粗KEY是否存在
                            && x.keySet().contains(KEY_FONT_BOLD) == true
                            && (x.get(KEY_FONT_BOLD) == null || x.get(KEY_FONT_BOLD) instanceof Boolean)
                            //判断字体斜体KEY是否存在
                            && x.keySet().contains(KEY_FONT_ITALIC) == true
                            && (x.get(KEY_FONT_ITALIC) == null || x.get(KEY_FONT_ITALIC) instanceof Boolean)
                            //判断字体下划线KEY是否存在
                            && x.keySet().contains(KEY_FONT_UNDER_LINE) == true
                            && (x.get(KEY_FONT_UNDER_LINE) == null || x.get(KEY_FONT_UNDER_LINE) instanceof Byte)
                            //判断字体上标下标KEY是否存在
                            && x.keySet().contains(KEY_FONT_TYPE_OFFSET) == true
                            && (x.get(KEY_FONT_TYPE_OFFSET) == null || x.get(KEY_FONT_TYPE_OFFSET) instanceof Short)
                            //判断字体删除线KEY是否存在
                            && x.keySet().contains(KEY_FONT_STRICKEOUT) == true
                            && (x.get(KEY_FONT_STRICKEOUT) == null || x.get(KEY_FONT_STRICKEOUT) instanceof Boolean)
                            //判断背景颜色KEY是否存在
                            && x.keySet().contains(KEY_BACKGROUND_COLOR) == true
                            && (x.get(KEY_BACKGROUND_COLOR) instanceof IndexedColors || x.get(KEY_BACKGROUND_COLOR) instanceof XSSFColor
                            || x.get(KEY_BACKGROUND_COLOR) == null)
                            //字体样式
                            // 判断上边框线条类型KEY是否存在
                            && x.keySet().contains(KEY_BORDER_TOP) == true
                            && (x.get(KEY_BORDER_TOP) == null || x.get(KEY_BORDER_TOP) instanceof BorderStyleEnum)
                            // 判断右边框线条类型KEY是否存在
                            && x.keySet().contains(KEY_BORDER_RIGHT) == true
                            && (x.get(KEY_BORDER_RIGHT) == null || x.get(KEY_BORDER_RIGHT) instanceof BorderStyleEnum)
                            // 判断下边框线条类型KEY是否存在
                            && x.keySet().contains(KEY_BORDER_BOTTOM) == true
                            && (x.get(KEY_BORDER_BOTTOM) == null || x.get(KEY_BORDER_BOTTOM) instanceof BorderStyleEnum)
                            // 判断左边框线条类型KEY是否存在
                            && x.keySet().contains(KEY_BORDER_LEFT) == true
                            && (x.get(KEY_BORDER_LEFT) == null || x.get(KEY_BORDER_LEFT) instanceof BorderStyleEnum)
                            // 判断上边框线条颜色KEY是否存在
                            && x.keySet().contains(KEY_LEFT_BORDER_COLOR) == true
                            && (x.get(KEY_LEFT_BORDER_COLOR) instanceof IndexedColors || x.get(KEY_LEFT_BORDER_COLOR) instanceof XSSFColor
                            || x.get(KEY_LEFT_BORDER_COLOR) == null)
                            // 判断右边框线条颜色KEY是否存在
                            && x.keySet().contains(KEY_RIGHT_BORDER_COLOR) == true
                            && (x.get(KEY_RIGHT_BORDER_COLOR) instanceof IndexedColors || x.get(KEY_RIGHT_BORDER_COLOR) instanceof XSSFColor
                            || x.get(KEY_RIGHT_BORDER_COLOR) == null)
                            // 判断下边框线条颜色KEY是否存在
                            && x.keySet().contains(KEY_BOTTOM_BORDER_COLOR) == true
                            && (x.get(KEY_BOTTOM_BORDER_COLOR) instanceof IndexedColors || x.get(KEY_BOTTOM_BORDER_COLOR) instanceof XSSFColor
                            || x.get(KEY_BOTTOM_BORDER_COLOR) == null)
                            // 判断左边框线条颜色KEY是否存在
                            && x.keySet().contains(KEY_LEFT_BORDER_COLOR) == true
                            && (x.get(KEY_LEFT_BORDER_COLOR) instanceof IndexedColors || x.get(KEY_LEFT_BORDER_COLOR) instanceof XSSFColor
                            || x.get(KEY_LEFT_BORDER_COLOR) == null)
                            //对齐方式
                            // 判断水平对齐方式KEY是否存在
                            && x.keySet().contains(KEY_HORIZONTAL_ALIGNMENT) == true
                            && (x.get(KEY_HORIZONTAL_ALIGNMENT) == null || x.get(KEY_HORIZONTAL_ALIGNMENT) instanceof HorizontalAlignment)
                            // 判断垂直对齐方式KEY是否存在
                            && x.keySet().contains(KEY_VERTICAL_ALIGNMENT) == true
                            && (x.get(KEY_VERTICAL_ALIGNMENT) == null || x.get(KEY_VERTICAL_ALIGNMENT) instanceof VerticalAlignment)
                            // 判断自动换行方式KEY是否存在
                            && x.keySet().contains(KEY_WRAP_TEXT) == true
                            && (x.get(KEY_WRAP_TEXT) == null || x.get(KEY_WRAP_TEXT) instanceof Boolean)

            ).collect(Collectors.toList());
            this.cellStyleList = cellStyleList;
            sheetNameList = this.cellStyleList.stream().map(x -> x.get(KEY_SHEET_NAME).toString()).collect(Collectors.toList());
        }

        /**
         * 生成字体名称样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param fontName    字体名称（默认宋体）
         * @return
         */
        public static Map<String, Object> createFontNameCellStyleMap(String sheetName, int rowIndex, int columnIndex, String fontName) {
            return createFontCellStyleMap(sheetName, rowIndex, columnIndex, fontName, null, null, null, null, null, null, null);
        }

        /**
         * 生成字体名称大小信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param fontHeight  字体大小
         * @return
         */
        public static Map<String, Object> createFontHeightCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , Double fontHeight) {
            return createFontCellStyleMap(sheetName, rowIndex, columnIndex, null, fontHeight, null, null, null, null, null, null);
        }

        /**
         * 得到RBG自定义颜色
         *
         * @param redNum   红色数值
         * @param greenNum 绿色数值
         * @param blueNum  蓝色数值
         * @return
         */
        public static XSSFColor getRGBColor(int redNum, int greenNum, int blueNum) {
            XSSFColor color = new XSSFColor(new byte[]{(byte) redNum, (byte) greenNum, (byte) blueNum}, new DefaultIndexedColorMap());
            return color;
        }

        /**
         * 生成字体颜色样式信息（支持自定义RGB颜色）
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param redNum      红色数值
         * @param greenNum    绿色数值
         * @param blueNum     蓝色数值
         * @return
         */
        public static Map<String, Object> createFontColorCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , int redNum, int greenNum, int blueNum) {
            XSSFColor fontColor = getRGBColor(redNum, greenNum, blueNum);
            return createFontColorCellStyleMap(sheetName, rowIndex, columnIndex, fontColor);
        }

        /**
         * 生成字体颜色样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param fontColor   字体颜色
         * @return
         */
        public static Map<String, Object> createFontColorCellStyleMap(String sheetName, int rowIndex, int columnIndex, Object fontColor) {
            return createFontCellStyleMap(sheetName, rowIndex, columnIndex, null, null, fontColor, null, null, null, null, null);
        }

        /**
         * 生成字体加粗样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param fontBold    字体加粗
         * @return
         */
        public static Map<String, Object> createFontBoldCellStyleMap(String sheetName, int rowIndex, int columnIndex, Boolean fontBold) {
            return createFontCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, fontBold, null, null, null, null);
        }

        /**
         * 生成字体斜体样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param fontItalic  字体斜体
         * @return
         */
        public static Map<String, Object> createFontItalicCellStyleMap(String sheetName, int rowIndex, int columnIndex, Boolean fontItalic) {
            return createFontCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null, fontItalic, null, null, null);
        }

        /**
         * 生成字体下划线样式信息
         *
         * @param sheetName     sheet页名称
         * @param rowIndex      行号
         * @param columnIndex   列号
         * @param fontUnderLine 字体下划线
         * @return
         */
        public static Map<String, Object> createFontUnderLineCellStyleMap(String sheetName, int rowIndex, int columnIndex, Byte fontUnderLine) {
            return createFontCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null, null, fontUnderLine, null, null);
        }

        /**
         * 生成字体上标下标样式信息
         *
         * @param sheetName      sheet页名称
         * @param rowIndex       行号
         * @param columnIndex    列号
         * @param fontTypeOffset 字体上标下标
         * @return
         */
        public static Map<String, Object> createFontTypeOffsetCellStyleMap(String sheetName, int rowIndex, int columnIndex, Short fontTypeOffset) {
            return createFontCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null, null, null, fontTypeOffset, null);
        }

        /**
         * 生成字体删除线样式信息
         *
         * @param sheetName     sheet页名称
         * @param rowIndex      行号
         * @param columnIndex   列号
         * @param fontStrikeout 字体删除线
         * @return
         */
        public static Map<String, Object> createFontStrikeoutCellStyleMap(String sheetName, int rowIndex, int columnIndex, Boolean fontStrikeout) {
            return createFontCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null, null, null, null, fontStrikeout);
        }

        /**
         * 生成字体样式信息
         *
         * @param sheetName      sheet页名称
         * @param rowIndex       行号
         * @param columnIndex    列号
         * @param fontName       字体名称（默认宋体）
         * @param fontHeight     字体大小
         * @param fontColor      字体颜色
         * @param fontBold       字体加粗
         * @param fontItalic     字体斜体
         * @param fontUnderLine  字体下划线
         * @param fontTypeOffset 字体上标下标
         * @param fontStrikeout  字体删除线
         * @return
         */
        public static Map<String, Object> createFontCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , String fontName, Double fontHeight, Object fontColor, Boolean fontBold, Boolean fontItalic, Byte fontUnderLine
                , Short fontTypeOffset, Boolean fontStrikeout) {
            return createCellStyleMap(sheetName, rowIndex, columnIndex, fontName, fontHeight, fontColor, fontBold, fontItalic
                    , fontUnderLine, fontTypeOffset, fontStrikeout, null);
        }

        /**
         * 生成背景颜色样式信息
         *
         * @param sheetName       sheet页名称
         * @param rowIndex        行号
         * @param columnIndex     列号
         * @param backgroundColor 背景颜色
         * @return
         */
        public static Map<String, Object> createBackgroundColorCellStyleMap(String sheetName, int rowIndex, int columnIndex, Object backgroundColor) {
            return createCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null, null, null, null, null, backgroundColor);
        }

        /**
         * 生成背景颜色样式信息（支持自定义RGB颜色）
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param redNum      红色数值
         * @param greenNum    绿色数值
         * @param blueNum     蓝色数值
         * @return
         */
        public static Map<String, Object> createBackgroundColorCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , int redNum, int greenNum, int blueNum) {
            XSSFColor backgroundColor = getRGBColor(redNum, greenNum, blueNum);
            return createBackgroundColorCellStyleMap(sheetName, rowIndex, columnIndex, backgroundColor);
        }

        /**
         * 生成样式信息
         *
         * @param sheetName       sheet页名称
         * @param rowIndex        行号
         * @param columnIndex     列号
         * @param fontName        字体名称（宋体）
         * @param fontHeight      字体大小
         * @param fontColor       字体颜色
         * @param fontBold        字体加粗
         * @param fontItalic      字体斜体
         * @param fontUnderLine   字体下划线
         * @param fontTypeOffset  字体上标下标
         * @param fontStrikeout   字体删除线
         * @param backgroundColor 背景颜色
         * @return
         */
        public static Map<String, Object> createCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , String fontName, Double fontHeight, Object fontColor, Boolean fontBold, Boolean fontItalic, Byte fontUnderLine
                , Short fontTypeOffset, Boolean fontStrikeout, Object backgroundColor) {
            return createCellStyleMap(sheetName, rowIndex, columnIndex, fontName, fontHeight, fontColor, fontBold, fontItalic
                    , fontUnderLine, fontTypeOffset, fontStrikeout, backgroundColor, null, null, null, null, null, null, null, null);
        }

        /**
         * 生成上边框线条颜色样式信息
         *
         * @param sheetName      sheet页名称
         * @param rowIndex       行号
         * @param columnIndex    列号
         * @param topBorderColor 上边框线条颜色
         * @return
         */
        public static Map<String, Object> createTopBorderColorCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , Object topBorderColor) {
            return createBorderColorCellStyleMap(sheetName, rowIndex, columnIndex, topBorderColor, null, null, null);
        }

        /**
         * 生成右边框线条颜色样式信息
         *
         * @param sheetName        sheet页名称
         * @param rowIndex         行号
         * @param columnIndex      列号
         * @param rightBorderColor 右边框线条颜色
         * @return
         */
        public static Map<String, Object> createRightBorderColorCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , Object rightBorderColor) {
            return createBorderColorCellStyleMap(sheetName, rowIndex, columnIndex, null, rightBorderColor, null, null);
        }

        /**
         * 生成下边框线条颜色样式信息
         *
         * @param sheetName         sheet页名称
         * @param rowIndex          行号
         * @param columnIndex       列号
         * @param bottomBorderColor 下边框线条颜色
         * @return
         */
        public static Map<String, Object> createBottomBorderColorCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , Object bottomBorderColor) {
            return createBorderColorCellStyleMap(sheetName, rowIndex, columnIndex, null, null, bottomBorderColor, null);
        }

        /**
         * 生成左边框线条颜色样式信息
         *
         * @param sheetName       sheet页名称
         * @param rowIndex        行号
         * @param columnIndex     列号
         * @param leftBorderColor 左边框线条颜色
         * @return
         */
        public static Map<String, Object> createLeftBorderColorCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , Object leftBorderColor) {
            return createBorderColorCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, leftBorderColor);
        }

        /**
         * 生成上边框线条类型样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param borderTop   上边框线条类型
         * @return
         */
        public static Map<String, Object> createTopBorderLineTypeCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , BorderStyleEnum borderTop) {
            return createBorderLineTypeCellStyleMap(sheetName, rowIndex, columnIndex, borderTop, null, null, null);
        }

        /**
         * 生成右边框线条类型样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param borderRight 右边框线条类型
         * @return
         */
        public static Map<String, Object> createRightBorderLineTypeCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , BorderStyleEnum borderRight) {
            return createBorderLineTypeCellStyleMap(sheetName, rowIndex, columnIndex, null, borderRight, null, null);
        }

        /**
         * 生成下边框线条类型样式信息
         *
         * @param sheetName    sheet页名称
         * @param rowIndex     行号
         * @param columnIndex  列号
         * @param borderBottom 下边框线条类型
         * @return
         */
        public static Map<String, Object> createBottomBorderLineTypeCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , BorderStyleEnum borderBottom) {
            return createBorderLineTypeCellStyleMap(sheetName, rowIndex, columnIndex, null, null, borderBottom, null);
        }

        /**
         * 生成左边框线条类型样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param borderLeft  左边框线条类型
         * @return
         */
        public static Map<String, Object> createLeftBorderLineTypeCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , BorderStyleEnum borderLeft) {
            return createBorderLineTypeCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, borderLeft);
        }

        /**
         * 生成边框线条颜色样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param borderColor 边框线条颜色
         * @return
         */
        public static Map<String, Object> createBorderColorCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , Object borderColor) {
            return createBorderCellStyleMap(sheetName, rowIndex, columnIndex, null, borderColor);
        }

        /**
         * 生成边框线条颜色样式信息
         *
         * @param sheetName         sheet页名称
         * @param rowIndex          行号
         * @param columnIndex       列号
         * @param topBorderColor    上边框线条颜色
         * @param rightBorderColor  右边框线条颜色
         * @param bottomBorderColor 下边框线条颜色
         * @param leftBorderColor   左边框线条颜色
         * @return
         */
        public static Map<String, Object> createBorderColorCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , Object topBorderColor, Object rightBorderColor, Object bottomBorderColor, Object leftBorderColor) {
            return createBorderCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null
                    , topBorderColor, rightBorderColor, bottomBorderColor, leftBorderColor);
        }

        /**
         * 生成边框线条类型样式信息
         *
         * @param sheetName      sheet页名称
         * @param rowIndex       行号
         * @param columnIndex    列号
         * @param borderLineType 边框线条类型
         * @return
         */
        public static Map<String, Object> createBorderLineTypeCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , BorderStyleEnum borderLineType) {
            return createBorderCellStyleMap(sheetName, rowIndex, columnIndex, borderLineType, null);
        }

        /**
         * 生成边框线条类型样式信息
         *
         * @param sheetName    sheet页名称
         * @param rowIndex     行号
         * @param columnIndex  列号
         * @param borderTop    上边框线条类型
         * @param borderRight  右边框线条类型
         * @param borderBottom 下边框线条类型
         * @param borderLeft   左边框线条类型
         * @return
         */
        public static Map<String, Object> createBorderLineTypeCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , BorderStyleEnum borderTop, BorderStyleEnum borderRight, BorderStyleEnum borderBottom, BorderStyleEnum borderLeft) {
            return createBorderCellStyleMap(sheetName, rowIndex, columnIndex, borderTop, borderRight, borderBottom, borderLeft
                    , null, null, null, null);
        }

        /**
         * 生成边框样式信息
         *
         * @param sheetName      sheet页名称
         * @param rowIndex       行号
         * @param columnIndex    列号
         * @param borderLineType 边框线条类型
         * @param borderColor    边框线条颜色
         * @return
         */
        public static Map<String, Object> createBorderCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , BorderStyleEnum borderLineType, Object borderColor) {
            return createBorderCellStyleMap(sheetName, rowIndex, columnIndex, borderLineType, borderLineType, borderLineType, borderLineType
                    , borderColor, borderColor, borderColor, borderColor);
        }

        /**
         * 生成边框样式信息
         *
         * @param sheetName         sheet页名称
         * @param rowIndex          行号
         * @param columnIndex       列号
         * @param borderTop         上边框线条类型
         * @param borderRight       右边框线条类型
         * @param borderBottom      下边框线条类型
         * @param borderLeft        左边框线条类型
         * @param topBorderColor    上边框线条颜色
         * @param rightBorderColor  右边框线条颜色
         * @param bottomBorderColor 下边框线条颜色
         * @param leftBorderColor   左边框线条颜色
         * @return
         */
        public static Map<String, Object> createBorderCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , BorderStyleEnum borderTop, BorderStyleEnum borderRight, BorderStyleEnum borderBottom, BorderStyleEnum borderLeft, Object topBorderColor
                , Object rightBorderColor, Object bottomBorderColor, Object leftBorderColor) {
            return createCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null, null, null, null, null
                    , null, borderTop, borderRight, borderBottom, borderLeft, topBorderColor, rightBorderColor
                    , bottomBorderColor, leftBorderColor);
        }

        /**
         * 生成样式信息
         *
         * @param sheetName         sheet页名称
         * @param rowIndex          行号
         * @param columnIndex       列号
         * @param fontName          字体名称（宋体）
         * @param fontHeight        字体大小
         * @param fontColor         字体颜色
         * @param fontBold          字体加粗
         * @param fontItalic        字体斜体
         * @param fontUnderLine     字体下划线
         * @param fontTypeOffset    字体上标下标
         * @param fontStrikeout     字体删除线
         * @param backgroundColor   背景颜色
         * @param borderTop         上边框线条类型
         * @param borderRight       右边框线条类型
         * @param borderBottom      下边框线条类型
         * @param borderLeft        左边框线条类型
         * @param topBorderColor    上边框线条颜色
         * @param rightBorderColor  右边框线条颜色
         * @param bottomBorderColor 下边框线条颜色
         * @param leftBorderColor   左边框线条颜色
         * @return
         */
        public static Map<String, Object> createCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , String fontName, Double fontHeight, Object fontColor, Boolean fontBold, Boolean fontItalic, Byte fontUnderLine
                , Short fontTypeOffset, Boolean fontStrikeout, Object backgroundColor, BorderStyleEnum borderTop, BorderStyleEnum borderRight
                , BorderStyleEnum borderBottom, BorderStyleEnum borderLeft, Object topBorderColor, Object rightBorderColor, Object bottomBorderColor
                , Object leftBorderColor) {
            return createCellStyleMap(sheetName, rowIndex, columnIndex, fontName, fontHeight, fontColor, fontBold, fontItalic
                    , fontUnderLine, fontTypeOffset, fontStrikeout, backgroundColor, borderTop, borderRight, borderBottom
                    , borderLeft, topBorderColor, rightBorderColor, bottomBorderColor, leftBorderColor, null, null);
        }

        /**
         * 生成水平对齐方式信息
         *
         * @param sheetName           sheet页名称
         * @param rowIndex            行号
         * @param columnIndex         列号
         * @param horizontalAlignment 水平对齐方式
         * @return
         */
        public static Map<String, Object> createHorizontalAlignmentCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , HorizontalAlignment horizontalAlignment) {
            return createAlignmentCellStyleMap(sheetName, rowIndex, columnIndex, horizontalAlignment, null);
        }

        /**
         * 生成垂直对齐方式信息
         *
         * @param sheetName         sheet页名称
         * @param rowIndex          行号
         * @param columnIndex       列号
         * @param verticalAlignment 垂直对齐方式
         * @return
         */
        public static Map<String, Object> createVerticalAlignmentCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , VerticalAlignment verticalAlignment) {
            return createAlignmentCellStyleMap(sheetName, rowIndex, columnIndex, null, verticalAlignment);
        }

        /**
         * 生成对齐方式信息
         *
         * @param sheetName           sheet页名称
         * @param rowIndex            行号
         * @param columnIndex         列号
         * @param horizontalAlignment 水平对齐方式
         * @param verticalAlignment   垂直对齐方式
         * @return
         */
        public static Map<String, Object> createAlignmentCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
            return createCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null
                    , null, null, null, null, null, null, null
                    , null, null, null, null, null, null
                    , horizontalAlignment, verticalAlignment);
        }

        /**
         * 生成样式信息
         *
         * @param sheetName           sheet页名称
         * @param rowIndex            行号
         * @param columnIndex         列号
         * @param fontName            字体名称（宋体）
         * @param fontHeight          字体大小
         * @param fontColor           字体颜色
         * @param fontBold            字体加粗
         * @param fontItalic          字体斜体
         * @param fontUnderLine       字体下划线
         * @param fontTypeOffset      字体上标下标
         * @param fontStrikeout       字体删除线
         * @param backgroundColor     背景颜色
         * @param borderTop           上边框线条类型
         * @param borderRight         右边框线条类型
         * @param borderBottom        下边框线条类型
         * @param borderLeft          左边框线条类型
         * @param topBorderColor      上边框线条颜色
         * @param rightBorderColor    右边框线条颜色
         * @param bottomBorderColor   下边框线条颜色
         * @param leftBorderColor     左边框线条颜色
         * @param horizontalAlignment 水平对齐方式
         * @param verticalAlignment   垂直对齐方式
         * @return
         */
        public static Map<String, Object> createCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , String fontName, Double fontHeight, Object fontColor, Boolean fontBold, Boolean fontItalic, Byte fontUnderLine
                , Short fontTypeOffset, Boolean fontStrikeout, Object backgroundColor, BorderStyleEnum borderTop, BorderStyleEnum borderRight
                , BorderStyleEnum borderBottom, BorderStyleEnum borderLeft, Object topBorderColor, Object rightBorderColor, Object bottomBorderColor
                , Object leftBorderColor, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
            return createCellStyleMap(sheetName, rowIndex, columnIndex, fontName, fontHeight, fontColor, fontBold, fontItalic
                    , fontUnderLine, fontTypeOffset, fontStrikeout, backgroundColor, borderTop, borderRight, borderBottom
                    , borderLeft, topBorderColor, rightBorderColor, bottomBorderColor, leftBorderColor, horizontalAlignment, verticalAlignment, null);
        }

        /**
         * 生成自动换行样式信息
         *
         * @param sheetName   sheet页名称
         * @param rowIndex    行号
         * @param columnIndex 列号
         * @param wrapText    自动换行
         * @return
         */
        public static Map<String, Object> createWrapTextCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , Boolean wrapText) {
            return createCellStyleMap(sheetName, rowIndex, columnIndex, null, null, null, null, null
                    , null, null, null, null, null, null, null
                    , null, null, null, null, null, null, null
                    , wrapText);
        }

        /**
         * 生成样式信息
         *
         * @param sheetName           sheet页名称
         * @param rowIndex            行号
         * @param columnIndex         列号
         * @param fontName            字体名称（宋体）
         * @param fontHeight          字体大小
         * @param fontColor           字体颜色
         * @param fontBold            字体加粗
         * @param fontItalic          字体斜体
         * @param fontUnderLine       字体下划线
         * @param fontTypeOffset      字体上标下标
         * @param fontStrikeout       字体删除线
         * @param backgroundColor     背景颜色
         * @param borderTop           上边框线条类型
         * @param borderRight         右边框线条类型
         * @param borderBottom        下边框线条类型
         * @param borderLeft          左边框线条类型
         * @param topBorderColor      上边框线条颜色
         * @param rightBorderColor    右边框线条颜色
         * @param bottomBorderColor   下边框线条颜色
         * @param leftBorderColor     左边框线条颜色
         * @param horizontalAlignment 水平对齐方式
         * @param verticalAlignment   垂直对齐方式
         * @param wrapText            自动换行
         * @return
         */
        public static Map<String, Object> createCellStyleMap(String sheetName, int rowIndex, int columnIndex
                , String fontName, Double fontHeight, Object fontColor, Boolean fontBold, Boolean fontItalic, Byte fontUnderLine
                , Short fontTypeOffset, Boolean fontStrikeout, Object backgroundColor, BorderStyleEnum borderTop, BorderStyleEnum borderRight
                , BorderStyleEnum borderBottom, BorderStyleEnum borderLeft, Object topBorderColor, Object rightBorderColor, Object bottomBorderColor
                , Object leftBorderColor, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, Boolean wrapText) {
            Map<String, Object> map = new HashMap<>();
            //sheet页名称
            map.put(KEY_SHEET_NAME, sheetName);
            //行号
            map.put(KEY_ROW_INDEX, rowIndex);
            //列号
            map.put(KEY_COL_INDEX, columnIndex);

            //设置字体样式
            //字体名称（比如宋体）
            fontName = fontName != null && StringUtils.equals(fontName, "") ? "宋体" : fontName;
            map.put(KEY_FONT_NAME, fontName);
            //字体大小
            fontHeight = fontHeight != null && fontHeight <= 0 ? null : fontHeight;
            map.put(KEY_FONT_HEIGHT, fontHeight);
            //字体颜色
            fontColor = fontColor != null && (fontColor instanceof IndexedColors == false && fontColor instanceof XSSFColor == false)
                    ? null : fontColor;
            map.put(KEY_FONT_COLOR, fontColor);
            //字体加粗
            map.put(KEY_FONT_BOLD, fontBold);
            //字体斜体
            map.put(KEY_FONT_ITALIC, fontItalic);
            //字体下划线
            fontUnderLine = fontUnderLine != null && (fontUnderLine != Font.U_NONE && fontUnderLine != Font.U_SINGLE && fontUnderLine != Font.U_DOUBLE
                    && fontUnderLine != Font.U_DOUBLE_ACCOUNTING && fontUnderLine != Font.U_SINGLE_ACCOUNTING) ? null : fontUnderLine;
            map.put(KEY_FONT_UNDER_LINE, fontUnderLine);
            //字体上标下标
            fontTypeOffset = fontTypeOffset != null && (fontTypeOffset != Font.SS_NONE && fontTypeOffset != Font.SS_SUB && fontTypeOffset != Font.SS_SUPER)
                    ? null : fontTypeOffset;
            map.put(KEY_FONT_TYPE_OFFSET, fontTypeOffset);
            //字体删除线
            map.put(KEY_FONT_STRICKEOUT, fontStrikeout);

            //背景颜色
            backgroundColor = backgroundColor != null && (backgroundColor instanceof IndexedColors == false && backgroundColor instanceof XSSFColor == false)
                    ? null : backgroundColor;
            map.put(KEY_BACKGROUND_COLOR, backgroundColor);

            //边框样式
            //上边框线条类型
            map.put(KEY_BORDER_TOP, borderTop);
            //右边框线条类型
            map.put(KEY_BORDER_RIGHT, borderRight);
            //下边框线条类型
            map.put(KEY_BORDER_BOTTOM, borderBottom);
            //左边框线条类型
            map.put(KEY_BORDER_LEFT, borderLeft);
            //上边框颜色类型
            map.put(KEY_TOP_BORDER_COLOR, topBorderColor);
            //右边框颜色类型
            map.put(KEY_RIGHT_BORDER_COLOR, rightBorderColor);
            //下边框颜色类型
            map.put(KEY_BOTTOM_BORDER_COLOR, bottomBorderColor);
            //左边框颜色类型
            map.put(KEY_LEFT_BORDER_COLOR, leftBorderColor);

            //对齐方式
            //水平对齐方式
            map.put(KEY_HORIZONTAL_ALIGNMENT, horizontalAlignment);
            //垂直对齐方式
            map.put(KEY_VERTICAL_ALIGNMENT, verticalAlignment);

            //自动换行
            map.put(KEY_WRAP_TEXT, wrapText);
            return map;
        }

        @Override
        public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row
                , Integer relativeRowIndex, Boolean isHead) {
            Sheet sheet = writeSheetHolder.getSheet();
            //不需要添加样式，或者当前sheet页不需要添加样式
            if (cellStyleList == null || cellStyleList.size() <= 0 || sheetNameList.contains(sheet.getSheetName()) == false) {
                return;
            }
            //获取当前行的样式信息
            List<Map<String, Object>> cellMapList = cellStyleList.stream().filter(x ->
                    StringUtils.equals(x.get(KEY_SHEET_NAME).toString(), sheet.getSheetName())
                            && Integer.parseInt(x.get(KEY_ROW_INDEX).toString()) == relativeRowIndex
            ).collect(Collectors.toList());
            //该行不需要设置样式
            if (cellMapList == null || cellMapList.size() <= 0) {
                return;
            }
            for (Map<String, Object> map : cellMapList) {
                //设置单元格样式
                setCellStyle(map, row);
            }
            //删除已添加的样式信息
            cellStyleList.removeAll(cellMapList);
            //重新获取要添加的sheet页姓名
            sheetNameList = cellStyleList.stream().map(x -> x.get(KEY_SHEET_NAME).toString()).collect(Collectors.toList());
        }

        /**
         * 给单元格设置样式
         *
         * @param map 样式信息
         * @param row 行对象
         */
        private void setCellStyle(Map<String, Object> map, Row row) {
            //列索引
            int colIndex = Integer.parseInt(map.get(KEY_COL_INDEX).toString());
            if (colIndex < 0) {
                return;
            }
            //背景颜色
            Object backgroundColor = map.get(KEY_BACKGROUND_COLOR);
            //自动换行
            Boolean wrapText = map.get(KEY_WRAP_TEXT) == null ? null : (Boolean) map.get(KEY_WRAP_TEXT);

            //边框样式
            Cell cell = row.getCell(colIndex);
            if (cell == null) {
                cell = row.createCell(colIndex);
            }
            XSSFCellStyle style = (XSSFCellStyle) cell.getRow().getSheet().getWorkbook().createCellStyle();
            // 克隆出一个 style
            style.cloneStyleFrom(cell.getCellStyle());
            //设置背景颜色
            if (backgroundColor != null) {
                //使用IndexedColors定义的颜色
                if (backgroundColor instanceof IndexedColors) {
                    style.setFillForegroundColor(((IndexedColors) backgroundColor).getIndex());
                }
                //使用自定义的RGB颜色
                else if (backgroundColor instanceof XSSFColor) {
                    style.setFillForegroundColor((XSSFColor) backgroundColor);
                }
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
            //设置自动换行
            if (wrapText != null) {
                style.setWrapText(wrapText);
            }
            //设置字体样式
            setFontStyle(row, style, map);
            //设置边框样式
            setBorderStyle(style, map);
            //设置对齐方式
            setAlignmentStyle(style, map);
            cell.setCellStyle(style);
        }

        /**
         * 设置字体样式
         *
         * @param row   行对象
         * @param style 单元格样式
         * @param map   样式信息
         */
        private void setFontStyle(Row row, XSSFCellStyle style, Map<String, Object> map) {
            //字体名称
            String fontName = map.get(KEY_FONT_NAME) == null ? null : map.get(KEY_FONT_NAME).toString();
            //字体大小
            Double fontHeight = map.get(KEY_FONT_HEIGHT) == null ? null : (Double) map.get(KEY_FONT_HEIGHT);
            //字体颜色
            Object fontColor = map.get(KEY_FONT_COLOR);
            //字体加粗
            Boolean fontBold = map.get(KEY_FONT_BOLD) == null ? null : (Boolean) map.get(KEY_FONT_BOLD);
            //字体斜体
            Boolean fontItalic = map.get(KEY_FONT_ITALIC) == null ? null : (Boolean) map.get(KEY_FONT_ITALIC);
            //字体下划线
            Byte fontUnderLine = map.get(KEY_FONT_UNDER_LINE) == null ? null : (Byte) map.get(KEY_FONT_UNDER_LINE);
            //字体上标下标
            Short fontTypeOffset = map.get(KEY_FONT_TYPE_OFFSET) == null ? null : (Short) map.get(KEY_FONT_TYPE_OFFSET);
            //字体删除线
            Boolean fontStrikeout = map.get(KEY_FONT_STRICKEOUT) == null ? null : (Boolean) map.get(KEY_FONT_STRICKEOUT);
            //不需要设置字体样式
            if (fontName == null && fontHeight == null && fontColor == null && fontBold == null && fontItalic == null
                    && fontUnderLine == null && fontTypeOffset == null && fontStrikeout == null) {
                return;
            }
            XSSFFont font = null;
            //样式存在字体对象时，使用原有的字体对象
            if (style.getFontIndex() != 0) {
                font = style.getFont();
            }
            //样式不存在字体对象时，创建字体对象
            else {
                font = (XSSFFont) row.getSheet().getWorkbook().createFont();
                //默认字体为宋体
                font.setFontName("宋体");
            }
            //设置字体名称
            if (fontName != null) {
                font.setFontName(fontName);
            }
            //设置字体大小
            if (fontHeight != null) {
                font.setFontHeight(fontHeight);
            }
            //设置字体颜色
            if (fontColor != null) {
                //使用IndexedColors定义的颜色
                if (fontColor instanceof IndexedColors) {
                    font.setColor(((IndexedColors) fontColor).getIndex());
                }
                //使用自定义的RGB颜色
                else if (fontColor instanceof XSSFColor) {
                    font.setColor((XSSFColor) fontColor);
                }
            }
            //设置字体加粗
            if (fontBold != null) {
                font.setBold(fontBold);
            }
            //设置字体斜体
            if (fontItalic != null) {
                font.setItalic(fontItalic);
            }
            //设置字体下划线
            if (fontUnderLine != null) {
                font.setUnderline(fontUnderLine);
            }
            //设置字体上标下标
            if (fontTypeOffset != null) {
                font.setTypeOffset(fontTypeOffset);
            }
            //设置字体删除线
            if (fontStrikeout != null) {
                font.setStrikeout(fontStrikeout);
            }
            style.setFont(font);
        }

        /**
         * 设置边框样式
         *
         * @param style 单元格样式
         * @param map   样式信息
         */
        private void setBorderStyle(XSSFCellStyle style, Map<String, Object> map) {
            //上边框线条类型
            BorderStyle borderTop = map.get(KEY_BORDER_TOP) == null ? null : (BorderStyle) map.get(KEY_BORDER_TOP);
            //右边框线条类型
            BorderStyle borderRight = map.get(KEY_BORDER_RIGHT) == null ? null : (BorderStyle) map.get(KEY_BORDER_RIGHT);
            //下边框线条类型
            BorderStyle borderBottom = map.get(KEY_BORDER_BOTTOM) == null ? null : (BorderStyle) map.get(KEY_BORDER_BOTTOM);
            //左边框线条类型
            BorderStyle borderLeft = map.get(KEY_BORDER_LEFT) == null ? null : (BorderStyle) map.get(KEY_BORDER_LEFT);
            //上边框颜色类型
            Object topBorderColor = map.get(KEY_TOP_BORDER_COLOR);
            //右边框颜色类型
            Object rightBorderColor = map.get(KEY_RIGHT_BORDER_COLOR);
            //下边框颜色类型
            Object bottomBorderColor = map.get(KEY_BOTTOM_BORDER_COLOR);
            //左边框颜色类型
            Object leftBorderColor = map.get(KEY_LEFT_BORDER_COLOR);
            //不需要设置边框样式
            if (borderTop == null && borderRight == null && borderBottom == null && borderLeft == null && topBorderColor == null
                    && rightBorderColor == null && bottomBorderColor == null && leftBorderColor == null) {
                return;
            }
            //设置上边框线条类型
            if (borderTop != null) {
                style.setBorderTop(borderTop);
            }
            //设置右边框线条类型
            if (borderRight != null) {
                style.setBorderRight(borderRight);
            }
            //设置下边框线条类型
            if (borderBottom != null) {
                style.setBorderBottom(borderBottom);
            }
            //设置左边框线条类型
            if (borderLeft != null) {
                style.setBorderLeft(borderLeft);
            }
            //设置上边框线条颜色
            if (topBorderColor != null) {
                //使用IndexedColors定义的颜色
                if (topBorderColor instanceof IndexedColors) {
                    style.setTopBorderColor(((IndexedColors) topBorderColor).getIndex());
                }
                //使用自定义的RGB颜色
                else if (topBorderColor instanceof XSSFColor) {
                    style.setTopBorderColor((XSSFColor) topBorderColor);
                }
            }
            //设置右边框线条颜色
            if (rightBorderColor != null) {
                //使用IndexedColors定义的颜色
                if (rightBorderColor instanceof IndexedColors) {
                    style.setRightBorderColor(((IndexedColors) rightBorderColor).getIndex());
                }
                //使用自定义的RGB颜色
                else if (rightBorderColor instanceof XSSFColor) {
                    style.setRightBorderColor((XSSFColor) rightBorderColor);
                }
            }
            //设置下边框线条颜色
            if (bottomBorderColor != null) {
                //使用IndexedColors定义的颜色
                if (bottomBorderColor instanceof IndexedColors) {
                    style.setBottomBorderColor(((IndexedColors) bottomBorderColor).getIndex());
                }
                //使用自定义的RGB颜色
                else if (bottomBorderColor instanceof XSSFColor) {
                    style.setBottomBorderColor((XSSFColor) bottomBorderColor);
                }
            }
            //设置左边框线条颜色
            if (leftBorderColor != null) {
                //使用IndexedColors定义的颜色
                if (leftBorderColor instanceof IndexedColors) {
                    style.setLeftBorderColor(((IndexedColors) leftBorderColor).getIndex());
                }
                //使用自定义的RGB颜色
                else if (topBorderColor instanceof XSSFColor) {
                    style.setLeftBorderColor((XSSFColor) leftBorderColor);
                }
            }
        }

        /**
         * 设置对齐方式
         *
         * @param style 单元格样式
         * @param map   样式信息
         */
        private void setAlignmentStyle(XSSFCellStyle style, Map<String, Object> map) {
            //水平对齐方式
            HorizontalAlignment horizontalAlignment = map.get(KEY_HORIZONTAL_ALIGNMENT) == null ? null : (HorizontalAlignment) map.get(KEY_HORIZONTAL_ALIGNMENT);
            //垂直对齐方式
            VerticalAlignment verticalAlignment = map.get(KEY_VERTICAL_ALIGNMENT) == null ? null : (VerticalAlignment) map.get(KEY_VERTICAL_ALIGNMENT);
            //不需要设置对齐方式
            if (horizontalAlignment == null && verticalAlignment == null) {
                return;
            }
            //设置水平对齐方式
            if (horizontalAlignment != null) {
                style.setAlignment(horizontalAlignment);
            }
            //设置垂直对齐方式
            if (verticalAlignment != null) {
                style.setVerticalAlignment(verticalAlignment);
            }

        }
    }

    @Data
    public static class CustomCellWriteHandler implements CellWriteHandler {

        @Override
        public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
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
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String name;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String number;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String department;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String position;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String date;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String time;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String longitude;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String latitude;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String place;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String address;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String visitor;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String description;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String url1;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String url2;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String url3;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String url4;
        @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 9, color = 12, underline = Font.U_SINGLE)
        @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER, wrapped = BooleanEnum.TRUE, borderLeft = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN)
        private String url5;
    }
}
