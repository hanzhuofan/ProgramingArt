package com.hzf.study.file.down.pdf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuofan.han
 * @date 2021-1-5 16:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class SpiderWebPlotUtil extends SpiderWebPlot {
    private static final long serialVersionUID = 4005814203754627127L;
    private int ticks = DEFAULT_TICKS;
    private static final int DEFAULT_TICKS = 5;
    private NumberFormat format = NumberFormat.getInstance();
    private static final double PERPENDICULAR = 90;
    private static final double TICK_SCALE = 0.015;
    private int valueLabelGap = DEFAULT_GAP;
    private static final int DEFAULT_GAP = 10;
    private static final double THRESHOLD = 15;
    private boolean drawRing = false;
    private double max = 1;
    private boolean webFilled = true;
    private ArrayList<LabelDeception> labelDeceptionList;

    private SpiderWebPlotUtil(CategoryDataset createCategoryDataset) {
        super(createCategoryDataset);
        labelDeceptionList = new ArrayList<>();
    }

    /**
     * Draw pictures, support adding rings
     *
     * @param g2
     *            the graphics device.
     * @param area
     *            the area within which the plot should be drawn.
     * @param anchor
     *            the anchor point (<code>null</code> permitted).
     * @param parentState
     *            the state from the parent plot, if there is one.
     * @param info
     *            collects info about the drawing.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
        // adjust for insets...
        RectangleInsets insets = getInsets();
        insets.trim(area);
        if (info != null) {
            info.setPlotArea(area);
            info.setDataArea(area);
        }
        drawBackground(g2, area);
        drawOutline(g2, area);
        Shape savedClip = g2.getClip();
        g2.clip(area);
        Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getForegroundAlpha()));
        if (!DatasetUtils.isEmptyOrNull(this.getDataset())) {
            int seriesCount = 0, catCount = 0;
            if (this.getDataExtractOrder() == TableOrder.BY_ROW) {
                seriesCount = this.getDataset().getRowCount();
                catCount = this.getDataset().getColumnCount();
            } else {
                seriesCount = this.getDataset().getColumnCount();
                catCount = this.getDataset().getRowCount();
            }
            // ensure we have a maximum value to use on the axes
            if (this.getMaxValue() == DEFAULT_MAX_VALUE) {
                calculateMaxValue(seriesCount, catCount);
            }
            // Next, setup the plot area
            // adjust the plot area by the interior spacing value
            double gapHorizontal = area.getWidth() * getInteriorGap();
            double gapVertical = area.getHeight() * getInteriorGap();
            double X = area.getX() + gapHorizontal / 2;
            double Y = area.getY() + gapVertical / 2;
            double W = area.getWidth() - gapHorizontal;
            double H = area.getHeight() - gapVertical;
            double headW = area.getWidth() * this.headPercent;
            double headH = area.getHeight() * this.headPercent;
            // make the chart area a square
            double min = Math.min(W, H) / 2;
            X = (X + X + W) / 2 - min;
            Y = (Y + Y + H) / 2 - min;
            W = 2 * min;
            H = 2 * min;
            Point2D centre = new Point2D.Double(X + W / 2, Y + H / 2);
            Rectangle2D radarArea = new Rectangle2D.Double(X, Y, W, H);
            // draw the axis and category label
            calculateAllLabelLocation(g2, catCount, radarArea);
            optAllLabelLocation();
            for (int cat = 0; cat < catCount; cat++) {
                double angle = getStartAngle() + (getDirection().getFactor() * cat * 360 / catCount);
                // If there are only two categories, set a fixed angle
                if (catCount == 2 && cat == 1) {
                    angle = 0;
                }
                Point2D endPoint = getWebPoint(radarArea, angle, 1);
                // 1 = end of axis
                Line2D line = new Line2D.Double(centre, endPoint);
                g2.setPaint(this.getAxisLinePaint());
                g2.setStroke(this.getAxisLineStroke());
                g2.setPaint(new Color(125,140,173));
                g2.draw(line);
                drawLabel(g2, radarArea, 0.0, cat, angle, 360.0 / catCount);
            }
            if (this.isDrawRing()) {
                // Draw a circle
                // Using 90 degrees as the axis, calculate the x and y coordinates of each ring
                Point2D topPoint = getWebPoint(radarArea, 90, 1);
                // The radius of the apex circle
                double topPointR = centre.getY() - topPoint.getY();
                // The radius of each scale
                double step = topPointR / this.getTicks();
                for (int p = this.getTicks(); p >= 1; p--) {
                    double r = p * step;
                    double upperLeftX = centre.getX() - r;
                    double upperLeftY = centre.getY() - r;
                    double d = 2 * r;
                    Ellipse2D ring = new Ellipse2D.Double(upperLeftX, upperLeftY, d, d);
                    g2.setPaint(new Color(125,140,173));
                    g2.draw(ring);
                }
            } else {
                // Draw polygon
                for (int p = this.getTicks(); p >= 1; p--) {
                    for (int i = 0; i < catCount; i++) {
                        // Using 90 degrees as the axis, calculate the x and y coordinates of each ring
                        Point2D topPoint1 = getWebPoint(radarArea, 90 - (360 * i / catCount), 0.2 * p);
                        Point2D topPoint2 = getWebPoint(radarArea, 90 - (360 * (i + 1) / catCount), 0.2 * p);

                        Line2D ring = new Line2D.Double(topPoint1, topPoint2);
                        g2.setPaint(new Color(125,140,173));
                        g2.draw(ring);
                    }
                }
            }
            // Now actually plot each of the series polygons..
            for (int series = 0; series < seriesCount; series++) {
                this.setSeriesPaint(new Color(253, 186, 64));
                this.setSeriesOutlinePaint(new Color(253, 186, 64));
                this.setSeriesOutlineStroke(new BasicStroke(8, 0, 1, 2, null, 0));
                drawRadarPoly(g2, radarArea, centre, info, series, catCount, headH, headW);
            }
        } else {
            drawNoDataMessage(g2, area);
        }
        g2.setClip(savedClip);
        g2.setComposite(originalComposite);
        drawOutline(g2, area);
    }

    private void calculateAllLabelLocation(Graphics2D g2, int catCount, Rectangle2D radarArea) {
        for (int cat = 0; cat < catCount; cat++) {
            double angle = getStartAngle() + (getDirection().getFactor() * cat * 360 / catCount);
            // If there are only two categories, set a fixed angle
            if (catCount == 2 && cat == 1) {
                angle = 0;
            }
            FontRenderContext frc = g2.getFontRenderContext();

            String label = getLabelStr(cat);
            Rectangle2D labelBounds = getLabelFont().getStringBounds(label, frc);
            LineMetrics lm = getLabelFont().getLineMetrics(label, frc);
            double ascent = lm.getAscent();
            Point2D labelLocation = calculateLabelLocation(labelBounds, ascent, radarArea, angle);
            LabelDeception labelDeception = new LabelDeception(labelLocation, angle, ascent, labelBounds.getWidth());
            labelDeceptionList.add(labelDeception);
        }
    }

    private String getLabelStr(int cat) {
        String label;
        if (getDataExtractOrder() == TableOrder.BY_ROW) {
            // if series are in rows, then the categories are the column keys
            label = getLabelGenerator().generateColumnLabel(getDataset(), cat);
        } else {
            // if series are in columns, then the categories are the row keys
            label = getLabelGenerator().generateRowLabel(getDataset(), cat);
        }
        label = label.length() > 10 ? label.substring(0, 10) + "..." : label;
        return label;
    }

    private void optAllLabelLocation() {
        List<LabelDeception> firstArea = labelDeceptionList.stream().filter(item -> item.getAngle() > 0)
                .sorted((a, b) -> (int) (b.getAngle() - a.getAngle())).collect(Collectors.toList());
        List<LabelDeception> otherArea = labelDeceptionList.stream().filter(item -> item.getAngle() < 0)
                .sorted((a, b) -> (int) (b.getAngle() - a.getAngle())).collect(Collectors.toList());
        otherArea.addAll(firstArea);
        for (int i = 1; i < otherArea.size() - 1; i++) {
            LabelDeception labelDeception = otherArea.get(i);
            double angle = labelDeception.getAngle();
            if (angle < 0 && angle >= -90) {
                double x = labelDeception.getLabelLocation().getX() + labelDeception.getWidth();
                double y = labelDeception.getLabelLocation().getY();
                LabelDeception labelDeception2 = otherArea.get(i - 1);
                double x2 = labelDeception2.getLabelLocation().getX();
                double y2 = labelDeception2.getLabelLocation().getY() + labelDeception2.getHeight();
                if (y < y2 && x > x2) {
                    labelDeception.getLabelLocation().setLocation(labelDeception.getLabelLocation().getX(), y2);
                }
            } else if (angle < -90 && angle >= -180) {
                double x = labelDeception.getLabelLocation().getX();
                double y = labelDeception.getLabelLocation().getY();
                LabelDeception labelDeception2 = otherArea.get(i + 1);
                double x2 = labelDeception2.getLabelLocation().getX() + labelDeception2.getWidth();
                double y2 = labelDeception2.getLabelLocation().getY() + labelDeception2.getHeight();
                if (y < y2 && x < x2) {
                    LabelDeception labelDeception1 = otherArea.get(i - 1);
                    double y1 = labelDeception1.getLabelLocation().getY();
                    if (labelDeception1.getAngle() <= -90) {
                        y1 -= labelDeception.getHeight();
                    }
                    labelDeception.getLabelLocation().setLocation(labelDeception.getLabelLocation().getX(), y1);
                }
            } else if (angle < -180 && angle >= -270 || angle == 90) {
                double x = labelDeception.getLabelLocation().getX();
                double y = labelDeception.getLabelLocation().getY() - labelDeception.getHeight();
                LabelDeception labelDeception2 = otherArea.get(i + 1);
                double x2 = labelDeception2.getLabelLocation().getX() - labelDeception2.getWidth();
                double y2 = labelDeception2.getLabelLocation().getY();
                if (y < y2 && x > x2 && (labelDeception2.getAngle() < -180 || labelDeception2.getAngle() == 90)) {
                    labelDeception2.getLabelLocation().setLocation(labelDeception2.getLabelLocation().getX(), y);
                }
            } else {
                double x = labelDeception.getLabelLocation().getX() + labelDeception.getWidth();
                double y = labelDeception.getLabelLocation().getY();
                LabelDeception labelDeception2 = otherArea.get(i + 1);
                double x2 = labelDeception2.getLabelLocation().getX();
                double y2 = labelDeception2.getLabelLocation().getY() - labelDeception2.getHeight();
                if (y > y2 && x > x2) {
                    LabelDeception labelDeception1 = otherArea.get(i - 1);
                    double y1 = labelDeception1.getLabelLocation().getY();
                    if (labelDeception1.getAngle() >= 0) {
                        y1 += labelDeception.getHeight();
                    }
                    labelDeception.getLabelLocation().setLocation(labelDeception.getLabelLocation().getX(), y1);
                }
            }
        }
    }

    @Override
    protected Point2D calculateLabelLocation(Rectangle2D labelBounds,
                                             double ascent,
                                             Rectangle2D plotArea,
                                             double startAngle) {
        Arc2D arc1 = new Arc2D.Double(plotArea, startAngle, 0, Arc2D.OPEN);
        Point2D point1 = arc1.getEndPoint();

        double deltaX = -(point1.getX() - plotArea.getCenterX()) * getAxisLabelGap();
        double deltaY = -(point1.getY() - plotArea.getCenterY()) * getAxisLabelGap();

        double labelX = point1.getX() - deltaX;
        double labelY = point1.getY() - deltaY;

        if (labelX == plotArea.getCenterX() || (deltaX < 5 && deltaX > -5)) {
            labelX -= labelBounds.getWidth() / 2;
        } else if (labelX < plotArea.getCenterX()) {
            labelX -= labelBounds.getWidth();
        }

        if (labelY == plotArea.getCenterY() || (deltaY < 5 && deltaY > -5)) {
            labelY += ascent / 2;
        } else if (labelY > plotArea.getCenterY()) {
            labelY += ascent;
        }

        return new Point2D.Double(labelX, labelY);
    }

    @Override
    protected void drawLabel(Graphics2D g2, Rectangle2D plotArea, double value, int cat, double startAngle,
                             double extent) {

        String label = getLabelStr(cat);
        Point2D labelLocation = labelDeceptionList.get(cat).getLabelLocation();

        Composite saveComposite = g2.getComposite();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setPaint(getLabelPaint());
        g2.setFont(getLabelFont());
        g2.drawString(label, (float)labelLocation.getX(), (float)labelLocation.getY());
        g2.setComposite(saveComposite);
    }

    @Override
    public void drawOutline(Graphics2D g2, Rectangle2D area) {

    }

    @Override
    protected void drawRadarPoly(Graphics2D g2, Rectangle2D plotArea, Point2D centre, PlotRenderingInfo info,
                                 int series, int catCount, double headH, double headW) {

        Polygon polygon = new Polygon();

        EntityCollection entities = null;
        if (info != null) {
            entities = info.getOwner().getEntityCollection();
        }

        // plot the data...
        for (int cat = 0; cat < catCount; cat++) {

            Number dataValue = getPlotValue(series, cat);

            if (dataValue != null) {
                double value = dataValue.doubleValue();

                if (value >= 0) {
                    // draw the polygon series...
                    // Finds our starting angle from the centre for this axis

                    double angle = getStartAngle() + (getDirection().getFactor() * cat * 360 / catCount);

                    Point2D point = getWebPoint(plotArea, angle, value / this.getMaxValue());
                    polygon.addPoint((int)point.getX(), (int)point.getY());

                    // put an elipse at the point being plotted..

                    Paint paint = getSeriesPaint(series);
                    Paint outlinePaint = getSeriesOutlinePaint(series);
                    Stroke outlineStroke = getSeriesOutlineStroke(series);

                    Ellipse2D head =
                            new Ellipse2D.Double(point.getX() - headW / 2, point.getY() - headH / 2, headW, headH);
                    g2.setPaint(paint);
                    // g2.fill(head);
                    g2.setStroke(new BasicStroke(2, 0, 1, 2, null, 0));
                    g2.setPaint(Color.RED);
                    g2.draw(head);

                    if (entities != null) {
                        int row, col;
                        if (this.getDataExtractOrder() == TableOrder.BY_ROW) {
                            row = series;
                            col = cat;
                        } else {
                            row = cat;
                            col = series;
                        }
                        String tip = null;
                        // if (this.toolTipGenerator != null) {
                        // tip = this.toolTipGenerator.generateToolTip(
                        // this.dataset, row, col);
                        // }

                        String url = null;
                        // if (this.urlGenerator != null) {
                        // url = this.urlGenerator.generateURL(this.dataset,
                        // row, col);
                        // }

                        Shape area = new Rectangle((int)(point.getX() - headW), (int)(point.getY() - headH),
                                (int)(headW * 2), (int)(headH * 2));
                        CategoryItemEntity entity = new CategoryItemEntity(area, tip, url, this.getDataset(),
                                this.getDataset().getRowKey(row), this.getDataset().getColumnKey(col));
                        entities.add(entity);
                    }

                }
            }
        }
        // Plot the polygon
        Paint paint = getSeriesPaint(series);
        g2.setPaint(paint);
        g2.setStroke(new BasicStroke(8, 0, 1, 2, null, 0));
        g2.draw(polygon);

        // Lastly, fill the web polygon if this is required
        if (this.webFilled) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            g2.setPaint(new Color(70, 130, 180));
            g2.fill(polygon);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getForegroundAlpha()));
        }
    }

    /**
     * Get the maximum value of the classification
     *
     * @param seriesCount
     *            the number of series
     * @param catCount
     *            the number of categories
     */
    private void calculateMaxValue(int seriesCount, int catCount) {
        double v = 0;
        Number nV = null;
        for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
            for (int catIndex = 0; catIndex < catCount; catIndex++) {
                nV = getPlotValue(seriesIndex, catIndex);
                if (nV != null) {
                    v = nV.doubleValue();
                    if (v > this.getMaxValue()) {
                        this.setMaxValue(v);
                    }
                }
            }
        }
        this.setMaxValue(max);
    }

    public synchronized static void saveAsFile(DefaultCategoryDataset dataset, String outputPath, int weight, int height) {
        try (FileOutputStream out = new FileOutputStream(outputPath)) {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            JFreeChart chart = createChart(dataset);
            ChartUtils.writeChartAsJPEG(out, 0.5f, chart, weight, height);
            out.flush();
        } catch (IOException e) {
            log.info("[SpiderWebPlotUtil::saveAsFile] raise IOException");
        }
    }

    private static JFreeChart createChart(DefaultCategoryDataset dataset) {
        SpiderWebPlotUtil spiderWebPlot = new SpiderWebPlotUtil(dataset);
        spiderWebPlot.setOutlineVisible(false);
        spiderWebPlot.setBaseSeriesOutlinePaint(Color.WHITE);
        spiderWebPlot.setOutlinePaint(Color.WHITE);
        spiderWebPlot.setSeriesOutlinePaint(Color.WHITE);
        spiderWebPlot.setBackgroundPaint(Color.WHITE);
        spiderWebPlot.setLabelFont(new Font("黑体", Font.BOLD, 48));
        spiderWebPlot.setLabelPaint(new Color(125,140,173));
        spiderWebPlot.setAxisLineStroke(new BasicStroke(8, 0, 1, 2, null, 0));
        JFreeChart jFreeChart = new JFreeChart(spiderWebPlot);
        jFreeChart.setBorderVisible(false);
        jFreeChart.setElementHinting(false);
        jFreeChart.setSubtitles(Collections.emptyList());
        return jFreeChart;
    }

    @Data
    @AllArgsConstructor
    static class LabelDeception {
        private Point2D labelLocation;
        private double angle;
        private double height;
        private double width;
    }

    public static void main(String[] args) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < 28; i++) {
            String a = "哈";
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(a);
            }
            sb.append(i);
            String groupName = sb.toString();
            dataset.addValue(0.5, "", groupName);
        }
        saveAsFile(dataset, "C:\\tmp\\logo.png", 2304, 1296);
    }
}
