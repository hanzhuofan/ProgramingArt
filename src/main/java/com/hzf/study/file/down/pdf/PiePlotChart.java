package com.hzf.study.file.down.pdf;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlotState;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author zhuofan.han
 * @date 2021/4/20 13:50
 */
public class PiePlotChart extends PiePlot {

    static Color grayBackground = new Color(244, 245, 249);

    int prefix1 = -100;

    int prefix2 = 250;

    int prefix3 = 350;

    public PiePlotChart(DefaultPieDataset dataset) {
        super(dataset);
    }

    /**
     * 提供静态方法：获取报表图形1：饼状图
     *
     * @param title 标题
     * @param data  数据
     * @param font  字体
     */
    public static void createPort(String title, Map<String, Double> data, Font font) {
        try {
            //如果不使用Font,中文将显示不出来

            DefaultPieDataset pds = new DefaultPieDataset();

            //获取迭代器：
            Set<Entry<String, Double>> set = data.entrySet();
            Iterator iterator = set.iterator();
            Entry entry;
            while (iterator.hasNext()) {
                entry = (Entry) iterator.next();
                pds.setValue(entry.getKey().toString(), Double.parseDouble(entry.getValue().toString()));
            }
            /**
             * 生成一个饼图的图表
             *
             * 分别是:显示图表的标题、需要提供对应图表的DateSet对象、是否显示图例、是否生成贴士以及是否生成URL链接
             */
//            JFreeChart chart = ChartFactory.createPieChart(title, pds, true, false, true);
            PiePlot plot = new PiePlotChart(pds);
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator());
            plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));
            JFreeChart chart = new JFreeChart(title, font, plot, false);
            //设置图片标题的字体
//            chart.getTitle().setFont(font);

            //得到图块,准备设置标签的字体
//            PiePlot plot = (PiePlot) chart.getPlot();

            //设置分裂效果,需要指定分裂出去的key
//            for (String key : data.keySet()) {
//                plot.setExplodePercent(key, 0.01);
//            }

            //设置标签字体
//            plot.setLabelLinksVisible(false);
            plot.setLabelFont(font);
//            plot.setLabelPaint(Color.WHITE);
            plot.setLabelBackgroundPaint(Color.WHITE);
            plot.setLabelOutlinePaint(Color.WHITE);
            plot.setOutlinePaint(Color.WHITE);
            plot.setShadowPaint(grayBackground);
            plot.setLabelShadowPaint(Color.WHITE);
            plot.setSectionOutlinesVisible(false);
//            plot.setDefaultSectionOutlinePaint(Color.BLACK);
//            plot.setDefaultSectionPaint(Color.WHITE);
//            plot.setAutoPopulateSectionPaint(false);
            //设置标签生成器(默认{0})
            //{0}:key {1}:value {2}:百分比 {3}:sum
            NumberFormat percentInstance = NumberFormat.getPercentInstance();
            percentInstance.setRoundingMode(RoundingMode.FLOOR);
            percentInstance.setMaximumFractionDigits(2);
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}票){2}", NumberFormat.getNumberInstance(), percentInstance));

            /**
             * 设置开始角度(弧度计算)
             *
             * 度    0°    30°        45°        60°        90°        120°    135°    150°    180°    270°    360°
             * 弧度    0    π/6        π/4        π/3        π/2        2π/3    3π/4    5π/6    π        3π/2    2π
             */
//            plot.setStartAngle(0);
//            plot.setCircular(false);
            plot.setMinimumArcAngleToDraw(0.0000000000000000001);

            //设置plot的背景图片
//            img = ImageIO.read(new File("f:/test/2.jpg"));
//            plot.setBackgroundImage(img);

            //设置plot的前景色透明度
            plot.setForegroundAlpha(0.8f);

            //设置plot的背景色透明度
            plot.setBackgroundAlpha(0.0f);

            //设置图例项目字体
//            chart.getLegend().setItemFont(font);
//            chart.getLegend().setVisible(false);
//            plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}{2}"));

            //设置背景图片,设置最大的背景
//            Image img = ImageIO.read(new File("f:/test/1.jpg"));
//            chart.setBackgroundImage(img);
            chart.setBackgroundPaint(Color.WHITE);

            //将内存中的图片写到本地硬盘
            ChartUtils.saveChartAsJPEG(new File("C:/storemonitor/pdf/aa.png"), chart, 2880, 1296);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawPie(Graphics2D g2, Rectangle2D plotArea,
                        PlotRenderingInfo info) {

        PiePlotState state = initialise(g2, plotArea, this, null, info);

        // adjust the plot area for interior spacing and labels...
        double labelReserve = 0.0;
        if (super.getLabelGenerator() != null && !super.getSimpleLabels()) {
            labelReserve = super.getLabelGap() + super.getMaximumLabelWidth();
        }
        double gapHorizontal = plotArea.getWidth() * labelReserve * 2.0;
        double gapVertical = plotArea.getHeight() * super.getInteriorGap() * 2.0;


//        if (DEBUG_DRAW_INTERIOR) {
//            double hGap = plotArea.getWidth() * super.getInteriorGap();
//            double vGap = plotArea.getHeight() * super.getInteriorGap();
//
//            double igx1 = plotArea.getX() + hGap;
//            double igx2 = plotArea.getMaxX() - hGap;
//            double igy1 = plotArea.getY() + vGap;
//            double igy2 = plotArea.getMaxY() - vGap;
//            g2.setPaint(Color.GRAY);
//            g2.draw(new Rectangle2D.Double(igx1, igy1, igx2 - igx1,
//                    igy2 - igy1));
//        }

        double linkX = plotArea.getX() + gapHorizontal / 2;
        double linkY = plotArea.getY() + gapVertical / 2;
        double linkW = plotArea.getWidth() - gapHorizontal;
        double linkH = plotArea.getHeight() - gapVertical;

        // make the link area a square if the pie chart is to be circular...
        if (super.isCircular()) {
            double min = Math.min(linkW, linkH) / 2;
            linkX = (linkX + linkX + linkW) / 2 - min;
            linkY = (linkY + linkY + linkH) / 2 - min;
            linkW = 2 * min;
            linkH = 2 * min;
        }

        // the link area defines the dog leg points for the linking lines to
        // the labels
        Rectangle2D linkArea = new Rectangle2D.Double(linkX, linkY, linkW,
                linkH);
        state.setLinkArea(linkArea);

//        if (false) {
//            g2.setPaint(Color.BLUE);
//            g2.draw(linkArea);
        g2.setPaint(grayBackground);
        g2.fill(new Ellipse2D.Double(linkArea.getX() + prefix1 / 2, linkArea.getY() + prefix1 / 2,
                linkArea.getWidth() - prefix1, linkArea.getHeight() - prefix1));
//        }

        // the explode area defines the max circle/ellipse for the exploded
        // pie sections.  it is defined by shrinking the linkArea by the
        // linkMargin factor.
        double lm = 0.0;
        if (!super.getSimpleLabels()) {
            lm = super.getLabelLinkMargin();
        }
        double hh = linkArea.getWidth() * lm * 2.0;
        double vv = linkArea.getHeight() * lm * 2.0;
        Rectangle2D explodeArea = new Rectangle2D.Double(linkX + hh / 2.0,
                linkY + vv / 2.0, linkW - hh, linkH - vv);

        state.setExplodedPieArea(explodeArea);

        // the pie area defines the circle/ellipse for regular pie sections.
        // it is defined by shrinking the explodeArea by the explodeMargin
        // factor.
        double maximumExplodePercent = getMaximumExplodePercent();
        double percent = maximumExplodePercent / (1.0 + maximumExplodePercent);

        double h1 = explodeArea.getWidth() * percent;
        double v1 = explodeArea.getHeight() * percent;
        Rectangle2D pieArea = new Rectangle2D.Double(explodeArea.getX()
                + h1 / 2.0, explodeArea.getY() + v1 / 2.0,
                explodeArea.getWidth() - h1, explodeArea.getHeight() - v1);

//        if (DEBUG_DRAW_PIE_AREA) {
//            g2.setPaint(Color.GREEN);
//            g2.draw(pieArea);
//        }
        state.setPieArea(pieArea);
        state.setPieCenterX(pieArea.getCenterX());
        state.setPieCenterY(pieArea.getCenterY());
        state.setPieWRadius(pieArea.getWidth() / 2.0);
        state.setPieHRadius(pieArea.getHeight() / 2.0);

        // plot the data (unless the dataset is null)...
        if ((super.getDataset() != null) && (super.getDataset().getKeys().size() > 0)) {

            List keys = super.getDataset().getKeys();
            double totalValue = DatasetUtils.calculatePieDatasetTotal(
                    super.getDataset());

            int passesRequired = state.getPassesRequired();
            for (int pass = 0; pass < passesRequired; pass++) {
                double runningTotal = 0.0;
                for (int section = 0; section < keys.size(); section++) {
                    Number n = super.getDataset().getValue(section);
                    if (n != null) {
                        double value = n.doubleValue();
                        if (value > 0.0) {
                            runningTotal += value;
                            drawItem(g2, section, explodeArea, state, pass);
                        }
                    }
                }
            }
            if (super.getSimpleLabels()) {
                drawSimpleLabels(g2, keys, totalValue, plotArea, linkArea,
                        state);
            } else {
                drawLabels(g2, keys, totalValue, plotArea, linkArea, state);
            }

        } else {
            drawNoDataMessage(g2, plotArea);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        g2.setPaint(grayBackground);
        g2.fill(new Ellipse2D.Double(linkArea.getX() + prefix2 / 2, linkArea.getY() + prefix2 / 2,
                linkArea.getWidth() - prefix2, linkArea.getHeight() - prefix2));

        g2.setPaint(Color.WHITE);
        g2.fill(new Ellipse2D.Double(linkArea.getX() + prefix3 / 2, linkArea.getY() + prefix3 / 2,
                linkArea.getWidth() - prefix3, linkArea.getHeight() - prefix3));
    }

    public static void main(String[] args) {
        Font font = new Font("微软雅黑", Font.BOLD, 30);
        Map<String, Double> map = new HashMap<>(4);
        map.put("天使-彦", (double) 1000);
        map.put("雄兵连-蔷薇", (double) 700);
        map.put("太阳之光-蕾娜", (double) 600);
        map.put("辅助-琴女", (double) 400);
        PiePlotChart.createPort("", map, font);
    }
}
