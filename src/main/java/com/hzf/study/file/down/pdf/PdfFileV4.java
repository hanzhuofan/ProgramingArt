package com.hzf.study.file.down.pdf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.hzf.study.utils.FileUtil;
import com.hzf.study.utils.Result;
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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 生成PDF文件
 */
public class PdfFileV4 {
    private static String BASE_PATH;
    private static String INFO;
    private static String DETAIL;

    static {
        try {
            BASE_PATH = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
            INFO = BASE_PATH + File.separator + "pdfTemplate" + File.separator + "json" + File.separator + "info.json";
            DETAIL = BASE_PATH + File.separator + "pdfTemplate" + File.separator + "json" + File.separator + "detail" +
                    ".json";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static FileUtil fileUtil = new FileUtil();

    /**
     * 填充html模板
     *
     * @param templateFile 模板文件名
     * @param args         模板参数
     * @param pdfFile      生成文件路径
     */
    public static void template(String templateFile, Map<String, String> args, String pdfFile) {
        FileOutputStream output = null;
        try {
            // 读取模板文件,填充模板参数
            Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_30);
            freemarkerCfg.setTemplateLoader(new ClassTemplateLoader(PdfFileV4.class, "/pdfTemplate/"));
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
            props.setFontProvider(new DefaultFontProvider(true, true, true));
            props.setCharset("utf-8");
            props.setBaseUri(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath());

            // 转换为PDF文档
            if (pdfFile.indexOf("/") > 0) {
                File path = new File(pdfFile.substring(0, pdfFile.indexOf("/")));
                if (!path.exists()) {
                    path.mkdirs();
                }
            }
            output = new FileOutputStream(pdfFile);
            PdfDocument pdf = new PdfDocument(new PdfWriter(output));
            pdf.setDefaultPageSize(PageSize.A4);
            Document document = HtmlConverter.convertToDocument(html, pdf, props);

            document.getRenderer().close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        InspectDetailResponse detail = buildDataDetails();
        List<InspectInfoResponseV3> infos = buildDataInfo();
        InspectInfoItemResponseV3 info = infos.get(0).getInfo();

        Map<String, String> para = new HashMap<>();
        String path;
        Integer status = detail.getStatus();
        switch (status) {
            case 0:
                path = "./pdfTemplate/static/image/liji.png";
                break;
            case 1:
                path = "./pdfTemplate/static/image/wait.png";
                break;
            default:
                path = "./pdfTemplate/static/image/good.png";
        }
        para.put("reportStatus", path);
        para.put("storeName", detail.getStoreName());
        para.put("tagName", detail.getTagName().substring(0, detail.getTagName().length() - 2));
        para.put("mode", detail.getMode() == 0 ? "远程巡检" : "def现场巡检abc");
        para.put("modePic", detail.getMode() == 0 ? "./pdfTemplate/static/image/yuancheng.png" : "./pdfTemplate/static/image/position.png");
        para.put("ts", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getTs()));
        para.put("submitterName", detail.getSubmitterName());
        para.put("comment", detail.getComment());
        para.put("totalScore", info.getTotalScore().toString());

        buildSpiderPlot(info.getSummary());

        String summary = buildSummary(info.getSummary());
        para.put("summary", summary);

        StringBuilder sb = new StringBuilder();
        String focalItems = buildFocalItems(info.getFocalItems());
        String ignoredItems = buildIgnoredItems(info.getIgnoredItems());
        String feedback = buildFeedback(info.getFeedback());
        String signature = buildSignature(info.getSignature());
        sb.append(focalItems).append(ignoredItems).append(feedback).append(signature);
        para.put("item", sb.toString());

        String groups = buildGroup(detail.getGroups());
        para.put("groups", groups);

        String feedbacks = buildFeedbacks(detail.getFeedbacks());
        para.put("feedbacks", feedbacks);

        template("templateV4.html", para, "C:\\tmp\\" + System.currentTimeMillis() + ".pdf");
    }

    private static final String FEEDBACK = "<div data-v-3799202a=\"\" style=\"margin-bottom: 20px;\">\n" +
            "                            <div data-v-3799202a=\"\" class=\"content-title\">问题反馈</div>\n";
    private static final String FEEDBACK_ITEM =
            "                            <div data-v-3799202a=\"\" class=\"content-detail\">\n" +
            "                                <div data-v-3799202a=\"\" class=\"content-detail-title\"\n" +
            "                                     style=\"background-color: rgb(255, 255, 255); min-height: 20px;" +
            "\">\n" +
            "                                    <div data-v-3799202a=\"\" class=\"detail-title\">\n" +
            "                                        <p data-v-3799202a=\"\" class=\"title1\">${subject}</p>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                                ${detail}" +
            "                            </div>\n";
    private static final String FEEDBACK_ITEM_DETAIL =
            "                                <div data-v-3799202a=\"\" class=\"content-detail-main\">\n" +
            "                                    <p data-v-3799202a=\"\" class=\"cdm-title\">问题描述：</p>\n" +
            "                                    ${voice}" +
            "                                    ${comment}" +
            "                                    <div data-v-3799202a=\"\" class=\"cdm-pic\">\n" +
            "                                        ${pic}" +
            "                                    </div>\n" +
            "                                </div>\n";
    private static final String FEEDBACK_END =
            "                        </div>";

    private static String buildFeedbacks(List<InspectFeedbackResponse> feedbacks) {
        StringBuilder sb = new StringBuilder();
        if (!feedbacks.isEmpty()) {
            sb.append(FEEDBACK);
            for (int i = 0; i < feedbacks.size(); i++) {
                InspectFeedbackResponse feedback = feedbacks.get(i);
                Set<Attachment> attachments = feedback.getAttachment();
                String description = feedback.getDescription();
                String tmp = FEEDBACK_ITEM;
                tmp = tmp.replace("${subject}", (i + 1) + "." + feedback.getSubject());
                tmp = tmp.replace("${description}", i + "." + description);
                tmp = buildAttachments(tmp, description, attachments, FEEDBACK_ITEM_DETAIL);
                sb.append(tmp);
            }
            sb.append(FEEDBACK_END);
        }
        return sb.toString();
    }

    private static final String GROUP = "<div data-v-3799202a=\"\" style=\"border-bottom: 1px solid rgb(244, 245, " +
            "249);\">\n" +
            "                            <div class=\"content-title\" data-v-3799202a=\"\">${groupName}</div>\n";
    private static final String GROUP_ITEM =
            "                            <div data-v-3799202a=\"\" class=\"content-detail\">\n" +
                    "                                <div data-v-3799202a=\"\" class=\"content-detail-title\">\n" +
                    "                                    <div data-v-3799202a=\"\" class=\"detail-title\"><p " +
                    "data-v-3799202a=\"\" class=\"title1\">\n" +
                    "                                        ${groupName}</p>\n" +
                    "                                        <p data-v-3799202a=\"\" " +
                    "class=\"title2\">${description}</p></div> <!---->\n" +
                    "                                    <!----> <!---->\n" +
                    "                                    ${titleBtn}" +
                    "                                </div>\n" +
                    "                                ${detail}" +
                    "                            </div>\n";

    private static final String GROUP_ITEM_DETAIL = "<div data-v-3799202a=\"\" class=\"content-detail-main\" " +
            "style=\"padding-bottom: 20px;\">\n" +
            "                                    <p data-v-3799202a=\"\" class=\"cdm-title\">评论详情：</p>\n" +
            "                                    ${voice}" +
            "                                    ${comment}" +
            "                                    <div data-v-3799202a=\"\" class=\"cdm-pic\">\n" +
            "                                        ${pic}" +
            "                                    </div>\n" +
            "                                </div>\n";
    private static final String VOICE = "<div data-v-3799202a=\"\" class=\"cdm-voice\">\n" +
            "                                        <div data-v-3799202a=\"\" class=\"speech-info\">\n" +
            "                                            <i data-v-3799202a=\"\" class=\"iconfont icon-yuyin " +
            "icon-speech\"></i>\n" +
            "                                        </div>\n" +
            "                                    </div>";
    private static final String COMMENT = "<div data-v-3799202a=\"\" class=\"cdm-word\">\n" +
            "                                        <span data-v-3799202a=\"\">${comment}</span>\n" +
            "                                    </div>\n";
    private static final String PIC = "<div data-v-3799202a=\"\" style=\"height:50px;\" class=\"source-details\">\n" +
            "                                            <div data-v-3799202a=\"\" class=\"img-content\">\n" +
            "                                                <img data-v-3799202a=\"\" title=\"\"\n" +
            "                                                     src=\"${url}\"\n" +
            "                                                     style=\"height:50px;\"\n" +
            "                                                     class=\"imgLittle imgInner\">\n" +
            "                                            </div> <!---->\n" +
            "                                        </div>";
    private static final String NORMAL_BTN = "<div data-v-3799202a=\"\" class=\"title-btn\">\n评分：${score}\n</div>\n";
    private static final String IGNORE_BTN = "<div class=\"ignore-btn\" data-v-3799202a=\"\">不适用</div>\n";
    private static final String GROUP_END =
            "                        </div>";

    private static String buildGroup(List<InspectDetailGroupResponse> groups) {
        StringBuilder sb = new StringBuilder();
        for (InspectDetailGroupResponse group : groups) {
            String tmp = GROUP;
            tmp = tmp.replace("${groupName}", group.getGroupName());
            sb.append(tmp);
            int i = 1;
            for (InspectDetailItemResponse item : group.getItems()) {
                tmp = GROUP_ITEM;
                tmp = tmp.replace("${groupName}", i++ + "." + item.getSubject());
                String description = item.getDescription();
                tmp = tmp.replace("${description}", StringUtils.hasLength(description) ? description : "");
                String comment = item.getComment();
                tmp = tmp.replace("${comment}", comment);
                if (item.getGrade() == -1) {
                    tmp = tmp.replace("${titleBtn}", IGNORE_BTN);
                } else {
                    tmp = tmp.replace("${titleBtn}", NORMAL_BTN);
                    if (group.getGroupType() == 1) {
                        tmp = tmp.replace("${score}", String.valueOf(item.getGrade()));
                    } else {
                        if (item.getGrade() == 0) {
                            tmp = tmp.replace("${score}", "不合格");
                        } else {
                            tmp = tmp.replace("${score}", "合格");
                        }
                    }
                }
                Set<Attachment> attachments = item.getAttachment();
                tmp = buildAttachments(tmp, comment, attachments, GROUP_ITEM_DETAIL);

                sb.append(tmp);
            }
            sb.append(GROUP_END);
        }
        return sb.toString();
    }

    private static String buildAttachments(String tmp, String comment, Set<Attachment> attachments, String detail) {
        if (StringUtils.hasLength(comment) || !attachments.isEmpty()) {
            tmp = tmp.replace("${detail}", detail);
            Map<Integer, List<Attachment>> collect =
                    attachments.stream().collect(Collectors.groupingBy(Attachment::getMediaType));
            List<Attachment> yuyin = collect.get(0);
            if (yuyin != null && !yuyin.isEmpty()) {
                tmp = tmp.replace("${voice}", VOICE);
            } else {
                tmp = tmp.replace("${voice}", "");
            }

            if (StringUtils.hasLength(comment)) {
                tmp = tmp.replace("${comment}", COMMENT);
                tmp = tmp.replace("${comment}", comment);
            } else {
                tmp = tmp.replace("${comment}", "");
            }

            List<Attachment> picList =
                    attachments.stream().filter(a -> a.getMediaType() != 0).collect(Collectors.toList());
            StringBuilder pic = new StringBuilder();
            for (Attachment attachment : picList) {
                String url = attachment.getUrl();
                if (url.endsWith("mp4")) {
                    url = "./pdfTemplate/static/image/video_thumbnail.4f3d3a7.png";
                }
                pic.append(PIC.replace("${url}", url));
            }
            tmp = tmp.replace("${pic}", pic);
        } else {
            tmp = tmp.replace("${detail}", "");
        }
        return tmp;
    }

    private static final String sign = "<div class=\"details-content el-col el-col-6\" data-v-3799202a=\"\">\n" +
            "                    <div class=\"details\" data-v-3799202a=\"\">\n" +
            "                        <div class=\"item-header\" data-v-3799202a=\"\">\n" +
            "                            <div style=\"margin-left: 7px\">\n" +
            "                                <i class=\"iconfont icontemp icon-fankui\" data-v-3799202a=\"\"></i>\n" +
            "                                <span class=\"title-lable\" data-v-3799202a=\"\">签名</span>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"item-content item-img\" data-v-3799202a=\"\">\n" +
            "                            <img alt=\"\" data-v-3799202a=\"\" height=\"157px\" width=\"151px\"\n" +
            "                                 src=\"${content}\">\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>";

    private static String buildSignature(InspectSignatureResponse signature) {
        if (signature == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String tmp = sign;
        tmp = tmp.replace("${content}", signature.getContent());
        sb.append(tmp);
        return sb.toString();
    }

    private static final String BASE = "<div class=\"details-content el-col el-col-6\" data-v-3799202a=\"\">\n" +
            "                    <div class=\"details\" data-v-3799202a=\"\">\n" +
            "                        <div class=\"item-header\" data-v-3799202a=\"\">\n" +
            "                            <div style=\"margin-left: 7px\">\n" +
            "                                <i class=\"iconfont icontemp ${icon}\" data-v-3799202a=\"\"></i>\n" +
            "                                <span class=\"title-lable\" data-v-3799202a=\"\">${title}</span>\n" +
            "                                <span class=\"count\" data-v-3799202a=\"\" style=\"margin-left: " +
            "60px\">${num}</span>\n" +
            "                                <span class=\"blag\" data-v-3799202a=\"\">个</span>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"item-content\" data-v-3799202a=\"\">\n" +
            "                            <div class=\"el-menuscrollbar el-scrollbar\" data-v-3799202a=\"\" " +
            "style=\"height: 100%;\">\n" +
            "                                <div class=\"el-scrollbar__wrap\" style=\"margin-bottom: -4px; " +
            "margin-right: -4px;\">\n" +
            "                                    <div class=\"el-scrollbar__view\">\n" +
            "                                        <div data-v-3799202a=\"\" class=\"item-details\">\n" +
            "                                            <span data-v-3799202a=\"\" class=\"item-name\">";
    private static final String BASE_ITEM = "<i class=\"iconfont item-blag icon-yuandian\" " +
            "data-v-3799202a=\"\"></i>\n" +
            "                                            ${name}\n" +
            "                                            <br>";
    private static final String BASE_END = "</span>\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>";

    private static final String[] icons = new String[]{
            "icon-zhongxindingwei", "icon-hulve", "icon-fankui", "icon-fankui"
    };

    private static final String[] titles = new String[]{
            "重点关注项", "不适用项", "问题反馈", "签名"
    };

    private static String buildFocalItems(List<FocalItemResponseV2> focalItems) {
        List<String> names = focalItems.stream().map(FocalItemResponseV2::getName).collect(Collectors.toList());
        StringBuilder sb = buildItems(names, 0);
        return sb.toString();
    }

    private static String buildIgnoredItems(List<IgnoredItemResponse> focalItems) {
        List<String> names = focalItems.stream().map(IgnoredItemResponse::getName).collect(Collectors.toList());
        StringBuilder sb = buildItems(names, 1);
        return sb.toString();
    }

    private static String buildFeedback(List<FeedbackItemResponse> feedback) {
        List<String> names = feedback.stream().map(FeedbackItemResponse::getSubject).collect(Collectors.toList());
        StringBuilder sb = buildItems(names, 2);
        return sb.toString();
    }

    private static StringBuilder buildItems(List<String> names, int i) {
        StringBuilder sb = new StringBuilder();
        String tmp = BASE;
        tmp = tmp.replace("${icon}", icons[i]);
        tmp = tmp.replace("${title}", titles[i]);
        tmp = tmp.replace("${num}", String.valueOf(names.size()));
        sb.append(tmp);
        if (!names.isEmpty()) {
            for (String name : names) {
                tmp = BASE_ITEM;
                tmp = tmp.replace("${name}", name);
                sb.append(tmp);
            }
        }
        sb.append(BASE_END);
        return sb;
    }

    private static void buildSpiderPlot(List<InspectItemResponseV3> summary) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (InspectItemResponseV3 item : summary) {
            int a = item.getNumOfQualifiedItems();
            int b = item.getNumOfTotalItems();
            DecimalFormat df = new DecimalFormat("0.00");
            String format = df.format((float) a / b);
            dataset.addValue(Double.valueOf(format), "", item.getGroupName());
        }
        String path = BASE_PATH + File.separator + "pdfTemplate" + File.separator + "static" + File.separator +
                "image" + File.separator + "logo.png";
        MySpiderWebPlot.saveAsFile(dataset, path, 1920, 1080);
    }

    private static final String TABLE_0 = "<table class=\"table table-bordered\" data-v-3799202a=\"\">\n" +
            "                        <thead data-v-3799202a=\"\">\n" +
            "                        <tr data-v-3799202a=\"\">\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\"></th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head0}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head1}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head2}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head3}</th>\n" +
            "                        </tr>\n" +
            "                        </thead>\n" +
            "                        <tbody data-v-3799202a=\"\">\n" +
            "                        <tr data-v-3799202a=\"\" style=\"vertical-align: middle;\">\n" +
            "                            <td data-v-3799202a=\"\" rowspan=\"${rowspan}\" style=\"vertical-align: " +
            "middle;" +
            "\">\n" +
            "                                <div style=\"margin-top: -2px\">\n" +
            "                                    <span data-v-3799202a=\"\">${head4}</span>\n" +
            "                                </div>\n" +
            "                            </td>\n" +
            "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: nowrap;" +
            "\">\n" +
            "                                <div style=\"margin-top: -2px\">\n" +
            "                                    <span class=\"item-name\" data-v-3799202a=\"\">${groupName}</span>\n" +
            "                                    <span class=\"count-blag\" " +
            "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
            "                                </div>\n" +
            "                            </td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfQualifiedItems}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfUnqualifiedItems}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
            "                        </tr>\n";
    private static final String TABLE_0_ITEM =
            "                        <tr data-v-3799202a=\"\" style=\"background-color: rgb(247, 248, 252);\">\n" +
                    "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: " +
                    "nowrap;" +
                    "\">\n" +
                    "                                <div style=\"margin-top: -2px\">\n" +
                    "                                    <span class=\"item-name\" " +
                    "data-v-3799202a=\"\">${groupName}</span>\n" +
                    "                                    <span class=\"count-blag\" " +
                    "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
                    "                                </div>\n" +
                    "                            </td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${numOfQualifiedItems}</span></td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${numOfUnqualifiedItems}</span></td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
                    "                        </tr>\n";
    private static final String TABLE_0_ITEM1 =
            "                        <tr data-v-3799202a=\"\">\n" +
                    "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: " +
                    "nowrap;" +
                    "\">\n" +
                    "                                <div style=\"margin-top: -2px\">\n" +
                    "                                    <span class=\"item-name\" " +
                    "data-v-3799202a=\"\">${groupName}</span>\n" +
                    "                                    <span class=\"count-blag\" " +
                    "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
                    "                                </div>\n" +
                    "                            </td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${numOfQualifiedItems}</span></td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${numOfUnqualifiedItems}</span></td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
                    "                        </tr>\n";

    private static final String TABLE_1 = "<table class=\"table table-bordered\" data-v-3799202a=\"\">\n" +
            "                        <thead data-v-3799202a=\"\"><!---->\n" +
            "                        <tr data-v-3799202a=\"\">\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\"></th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head0}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head1}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head2}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head3}</th>\n" +
            "                        </tr> <!---->\n" +
            "                        </thead>\n" +
            "                        <tbody data-v-3799202a=\"\">\n" +
            "                        <tr data-v-3799202a=\"\" style=\"vertical-align: middle;\">\n" +
            "                            <td data-v-3799202a=\"\" rowspan=\"${rowspan}\" style=\"vertical-align: " +
            "middle;" +
            "\"><!---->\n" +
            "                                <div style=\"margin-top: -2px\">\n" +
            "                                    <span data-v-3799202a=\"\">${head4}</span> <!---->\n" +
            "                                </div>\n" +
            "                            </td>\n" +
            "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: nowrap;" +
            "\">\n" +
            "                                <div style=\"margin-top: -2px\">\n" +
            "                                    <span class=\"item-name\" data-v-3799202a=\"\">${groupName}</span>\n" +
            "                                    <span class=\"count-blag\" " +
            "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
            "                                </div>\n" +
            "                            </td> <!----> <!---->\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${totalScore}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${actualScore}</span></td>\n" +
            "                        </tr>";
    private static final String TABLE_1_ITEM =
            "                        <tr data-v-3799202a=\"\" style=\"background-color: rgb(247, 248, 252);\">\n" +
                    "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: " +
                    "nowrap;" +
                    "\">\n" +
                    "                                <div style=\"margin-top: -2px\">\n" +
                    "                                    <span class=\"item-name\" " +
                    "data-v-3799202a=\"\">${groupName}</span>\n" +
                    "                                    <span class=\"count-blag\" " +
                    "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
                    "                                </div>\n" +
                    "                            </td> <!----> <!---->\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${totalScore}</span></td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${actualScore}</span></td>\n" +
                    "                        </tr>";
    private static final String TABLE_1_ITEM1 =
            "                        <tr data-v-3799202a=\"\">\n" +
                    "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: " +
                    "nowrap;" +
                    "\">\n" +
                    "                                <div style=\"margin-top: -2px\">\n" +
                    "                                    <span class=\"item-name\" " +
                    "data-v-3799202a=\"\">${groupName}</span>\n" +
                    "                                    <span class=\"count-blag\" " +
                    "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
                    "                                </div>\n" +
                    "                            </td> <!----> <!---->\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${totalScore}</span></td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
                    "                            <td data-v-3799202a=\"\"><span " +
                    "data-v-3799202a=\"\">${actualScore}</span></td>\n" +
                    "                        </tr>";

    private static final String TABLE_2 = "<table class=\"table table-bordered\" data-v-3799202a=\"\">\n" +
            "                        <thead data-v-3799202a=\"\"><!----> <!---->\n" +
            "                        <tr data-v-3799202a=\"\">\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\"></th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 20%;\">${head0}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 15%;\">${head1}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 15%;\">${head2}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 15%;\">${head3}</th>\n" +
            "                            <th data-v-3799202a=\"\" scope=\"col\" style=\"width: 15%;\">${head4}</th>\n" +
            "                        </tr>\n" +
            "                        </thead>\n" +
            "                        <tbody data-v-3799202a=\"\">\n" +
            "                        <tr data-v-3799202a=\"\" style=\"vertical-align: middle;\">\n" +
            "                            <td data-v-3799202a=\"\" rowspan=\"${rowspan}\" style=\"vertical-align: " +
            "middle;" +
            "\"><!----> <!---->\n" +
            "                                <div style=\"margin-top: -2px\">\n" +
            "                                    <span data-v-3799202a=\"\">${head5}</span>\n" +
            "                                </div>\n" +
            "                            </td>\n" +
            "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: nowrap;" +
            "\">\n" +
            "                                <div style=\"margin-top: -2px\">\n" +
            "                                    <span class=\"item-name\" data-v-3799202a=\"\">${groupName}</span>\n" +
            "                                    <span class=\"count-blag\" " +
            "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
            "                                </div>\n" +
            "                            </td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfQualifiedItems}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfUnqualifiedItems}</span></td> <!---->\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${actualScore}</span></td>\n" +
            "                        </tr>\n";
    private static final String TABLE_2_ITEM = "<tr data-v-3799202a=\"\" style=\"background-color: rgb(247, 248, 252)" +
            ";\">\n" +
            "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: nowrap;" +
            "\">\n" +
            "                                <div style=\"margin-top: -2px\">\n" +
            "                                    <span class=\"item-name\" data-v-3799202a=\"\">${groupName}</span>\n" +
            "                                    <span class=\"count-blag\" " +
            "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
            "                                </div>\n" +
            "                            </td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfQualifiedItems}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfUnqualifiedItems}</span></td> <!---->\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${actualScore}</span></td>\n" +
            "                        </tr>\n";
    private static final String TABLE_2_ITEM1 = "<tr data-v-3799202a=\"\">\n" +
            "                            <td data-v-3799202a=\"\" style=\"word-break: keep-all; white-space: nowrap;" +
            "\">\n" +
            "                                <div style=\"margin-top: -2px\">\n" +
            "                                    <span class=\"item-name\" data-v-3799202a=\"\">${groupName}</span>\n" +
            "                                    <span class=\"count-blag\" " +
            "data-v-3799202a=\"\">${numOfTotalItems}</span>\n" +
            "                                </div>\n" +
            "                            </td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfQualifiedItems}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfUnqualifiedItems}</span></td> <!---->\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${numOfIgnored}</span></td>\n" +
            "                            <td data-v-3799202a=\"\"><span " +
            "data-v-3799202a=\"\">${actualScore}</span></td>\n" +
            "                        </tr>\n";

    private static final String TABLE_END =
            "                        </tbody>\n" +
                    "                    </table>";

    private static final String[] TABLE_TMP = new String[]{
            TABLE_0, TABLE_1, TABLE_2
    };

    private static final Map<Integer, List<String>> TABLE_HEAD_MAP = new HashMap<Integer, List<String>>() {
        {
            put(0, new ArrayList<String>() {
                {
                    add("项目");
                    add("合格");
                    add("不合格");
                    add("不适用");
                    add("合格率评估项");
                }
            });
            put(1, new ArrayList<String>() {
                {
                    add("项目");
                    add("项目总分值");
                    add("不适用");
                    add("得分");
                    add("巡检评分项");
                }
            });
            put(2, new ArrayList<String>() {
                {
                    add("项目");
                    add("合格");
                    add("不合格");
                    add("不适用");
                    add("得分");
                    add("附加评分项");
                }
            });
        }
    };

    private static String buildSummary(List<InspectItemResponseV3> summary) {
        Map<Integer, List<InspectItemResponseV3>> listMap =
                summary.stream().collect(Collectors.groupingBy(InspectItemResponseV3::getType));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 2; i++) {
            List<InspectItemResponseV3> table = listMap.get(i);
            if (table.isEmpty()) {
                continue;
            }
            List<String> tableHead = TABLE_HEAD_MAP.get(i);
            String tmp = TABLE_TMP[i];
            for (int j = 0; j < tableHead.size(); j++) {
                tmp = tmp.replace("${head" + j + "}", tableHead.get(j));
            }
            tmp = tmp.replace("${rowspan}", String.valueOf(table.size()));
            InspectItemResponseV3 row0 = table.get(0);
            switch (i) {
                case 0:
                    table0ReplaceValue(sb, tmp, row0);
                    if (table.size() > 1) {
                        for (int j = 1; j < table.size(); j++) {
                            if (j % 2 == 1) {
                                tmp = TABLE_0_ITEM;
                            } else {
                                tmp = TABLE_0_ITEM1;
                            }
                            InspectItemResponseV3 row = table.get(j);
                            table0ReplaceValue(sb, tmp, row);
                        }
                    }
                    break;
                case 1:
                    table1ReplaceValue(sb, tmp, row0);
                    if (table.size() > 1) {
                        for (int j = 1; j < table.size(); j++) {
                            if (j % 2 == 1) {
                                tmp = TABLE_1_ITEM;
                            } else {
                                tmp = TABLE_1_ITEM1;
                            }
                            InspectItemResponseV3 row = table.get(j);
                            table1ReplaceValue(sb, tmp, row);
                        }
                    }
                    break;
                case 2:
                    table2ReplaceValue(sb, tmp, row0);
                    if (table.size() > 1) {
                        for (int j = 1; j < table.size(); j++) {
                            if (j % 2 == 1) {
                                tmp = TABLE_2_ITEM;
                            } else {
                                tmp = TABLE_2_ITEM1;
                            }
                            InspectItemResponseV3 row = table.get(j);
                            table2ReplaceValue(sb, tmp, row);
                        }
                    }
                    break;
                default:
            }
            sb.append(TABLE_END);
        }
        return sb.toString();
    }

    private static void table0ReplaceValue(StringBuilder sb, String tmp, InspectItemResponseV3 row) {
        tmp = tmp.replace("${groupName}", row.getGroupName());
        tmp = tmp.replace("${numOfTotalItems}", row.getNumOfTotalItems().toString());
        tmp = tmp.replace("${numOfQualifiedItems}", row.getNumOfQualifiedItems().toString());
        tmp = tmp.replace("${numOfUnqualifiedItems}", row.getNumOfUnqualifiedItems().toString());
        tmp = tmp.replace("${numOfIgnored}", row.getNumOfIgnored().toString());
        sb.append(tmp);
    }

    private static void table1ReplaceValue(StringBuilder sb, String tmp, InspectItemResponseV3 row) {
        tmp = tmp.replace("${groupName}", row.getGroupName());
        tmp = tmp.replace("${numOfTotalItems}", row.getNumOfTotalItems().toString());
        tmp = tmp.replace("${totalScore}", row.getTotalScore().toString());
        tmp = tmp.replace("${numOfIgnored}", row.getNumOfIgnored().toString());
        tmp = tmp.replace("${actualScore}", row.getActualScore().toString());
        sb.append(tmp);
    }

    private static void table2ReplaceValue(StringBuilder sb, String tmp, InspectItemResponseV3 row) {
        tmp = tmp.replace("${groupName}", row.getGroupName());
        tmp = tmp.replace("${numOfTotalItems}", row.getNumOfTotalItems().toString());
        tmp = tmp.replace("${numOfQualifiedItems}", row.getNumOfQualifiedItems().toString());
        tmp = tmp.replace("${numOfUnqualifiedItems}", row.getNumOfUnqualifiedItems().toString());
        tmp = tmp.replace("${numOfIgnored}", row.getNumOfIgnored().toString());
        tmp = tmp.replace("${actualScore}", row.getActualScore().toString());
        sb.append(tmp);
    }

    private static InspectDetailResponse buildDataDetails() {
        Result result = getResult(DETAIL);
        JSONObject data = (JSONObject) result.getData();
        return data.toJavaObject(InspectDetailResponse.class);
    }

    private static List<InspectInfoResponseV3> buildDataInfo() {
        Result result = getResult(INFO);
        JSONArray data = (JSONArray) result.getData();
        List<InspectInfoResponseV3> inspectInfoResponseV3s = JSONArray.parseArray(data.toJSONString(),
                InspectInfoResponseV3.class);
        return inspectInfoResponseV3s;
    }

    private static Result getResult(String path) {
        File file = fileUtil.getFile(path);
        String data = fileUtil.readString(file);
        return JSONObject.parseObject(data, Result.class);
    }

    @Getter
    @Setter
    public static class InspectDetailResponse {
        @JSONField(ordinal = 1)
        private Integer id;

        @JSONField(ordinal = 2)
        private Long ts;

        @JSONField(ordinal = 3)
        private Integer mode;

        @JSONField(ordinal = 4)
        private String tagName;

        @JSONField(ordinal = 5)
        private String storeId;

        @JSONField(ordinal = 6)
        private String storeName;

        @JSONField(ordinal = 7)
        private String submitter;

        @JSONField(ordinal = 8)
        private String submitterName;

        @JSONField(ordinal = 9)
        private Integer status;

        @JSONField(ordinal = 10)
        private String comment;

        @JSONField(ordinal = 11)
        private List<InspectDetailGroupResponse> groups = new LinkedList<>();

        @JSONField(ordinal = 12)
        List<InspectFeedbackResponse> feedbacks = new LinkedList<>();

        @JSONField(ordinal = 13)
        private InspectSignatureResponse signature;
    }

    @Getter
    @Setter
    public static class InspectDetailGroupResponse {
        @JSONField(ordinal = 1)
        private Integer groupId;

        @JSONField(ordinal = 2)
        private String groupName;

        @JSONField(ordinal = 3)
        private Integer groupType;

        @JSONField(ordinal = 4)
        private List<InspectDetailItemResponse> items = new LinkedList<>();
    }

    @Getter
    @Setter
    public static class InspectDetailItemResponse {

        @JSONField(ordinal = 1)
        private Integer id;

        @JSONField(ordinal = 2)
        private String subject;

        @JSONField(ordinal = 3)
        private String description;

        @JSONField(ordinal = 4)
        private Integer grade;

        @JSONField(ordinal = 5)
        private Integer itemScore;

        @JSONField(ordinal = 6)
        private String comment;

        @JSONField(ordinal = 7)
        private Set<Attachment> attachment;
    }

    @Getter
    @Setter
    public static class InspectFeedbackResponse {

        private Long ts;

        private String subject;

        private String description;

        private Set<Attachment> attachment = new LinkedHashSet<>();
    }

    @Getter
    @Setter
    public static class InspectSignatureResponse {
        private Integer type;

        private String content;
    }

    @Data
    public static class Attachment {
        private Integer mediaType;

        private String url;

        private Integer deviceId = -1;
    }

    @Data
    public static class InspectInfoResponseV3 {
        @JSONField(ordinal = 1)
        private Integer id;

        @JSONField(ordinal = 2)
        private InspectInfoItemResponseV3 info;
    }

    @Getter
    @Setter
    public static class InspectInfoItemResponseV3 {
        @JSONField(ordinal = 1)
        private String comment;

        @JSONField(ordinal = 2)
        private List<InspectItemResponseV3> summary = new LinkedList<>();

        @JSONField(ordinal = 3)
        private List<IgnoredItemResponse> ignoredItems = new LinkedList<>();

        @JSONField(ordinal = 4)
        private List<FocalItemResponseV2> focalItems = new LinkedList<>();

        @JSONField(ordinal = 5)
        private List<FeedbackItemResponse> feedback = new LinkedList<>();

        @JSONField(ordinal = 6)
        private InspectSignatureResponse signature;

        @JSONField(ordinal = 7)
        private Integer totalScore;
    }

    @Getter
    @Setter
    public static class InspectItemResponseV3 {
        @JSONField(ordinal = 1)
        private Integer groupId;

        @JSONField(ordinal = 2)
        private Integer type;

        @JSONField(ordinal = 3)
        private String groupName;

        @JSONField(ordinal = 4)
        private Integer numOfIgnored = 0;

        @JSONField(ordinal = 5)
        private Integer numOfQualifiedItems = 0;

        @JSONField(ordinal = 6)
        private Integer numOfUnqualifiedItems = 0;

        @JSONField(ordinal = 7)
        private Integer numOfTotalItems = 0;

        @JSONField(ordinal = 8)
        private Integer totalScore = 0;

        @JSONField(ordinal = 9)
        private Integer actualScore = 0;
    }

    @Getter
    @Setter
    public static class IgnoredItemResponse {
        @JSONField(ordinal = 1)
        private Integer id;

        @JSONField(ordinal = 2)
        private String name;
    }

    @Getter
    @Setter
    public static class FocalItemResponseV2 {
        @JSONField(ordinal = 1)
        private Integer id;

        @JSONField(ordinal = 2)
        private String name;

        @JSONField(ordinal = 3)
        private String description;

        @JSONField(ordinal = 4)
        private Integer itemScore;

        @JSONField(ordinal = 5)
        private Integer grade;

        @JSONField(ordinal = 6)
        private String comment;

        @JSONField(ordinal = 7)
        private Integer type;

        @JSONField(ordinal = 8)
        private Set<Attachment> attachment;

    }

    @Getter
    @Setter
    public static class FeedbackItemResponse {
        @JSONField(ordinal = 1)
        private Integer id;

        @JSONField(ordinal = 2)
        private String subject;

        @JSONField(ordinal = 3)
        private String description;

        @JSONField(ordinal = 4)
        private Set<Attachment> attachment;
    }
}
