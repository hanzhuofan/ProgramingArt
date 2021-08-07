package com.hzf.study.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author zhuofan.han
 * @date 2021/8/6
 */
@Slf4j
public class PicUtil {
    private static final Integer ZERO = 0;
    private static final Integer ONE_ZERO_TWO_FOUR = 1024;
    private static final Integer NINE_ZERO_ZERO = 900;
    private static final Integer THREE_TWO_SEVEN_FIVE = 3275;
    private static final Integer TWO_ZERO_FOUR_SEVEN = 2047;
    private static final Double ZERO_EIGHT_FIVE = 0.85;
    private static final Double ZERO_SIX = 0.6;
    private static final Double ZERO_FOUR_FOUR = 0.44;
    private static final Double ZERO_FOUR = 0.4;

    /**
     * Compress pictures according to the specified size
     *
     * @param imageBytes
     *            Source image byte array
     * @param desFileSize
     *            Specify the image size in kb
     * @return Picture byte array after compression quality
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
        long start = System.currentTimeMillis();
        if (imageBytes == null || imageBytes.length <= ZERO || imageBytes.length < desFileSize * ONE_ZERO_TWO_FOUR) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / ONE_ZERO_TWO_FOUR);
        try {
            while (imageBytes.length > desFileSize * ONE_ZERO_TWO_FOUR) {
                double scale = BigDecimalUtil.div(imageBytes.length, desFileSize * ONE_ZERO_TWO_FOUR);
                scale = BigDecimalUtil.sqrt(new BigDecimal(scale), 2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                if (scale > ZERO_EIGHT_FIVE) {
                    scale = ZERO_EIGHT_FIVE;
                }

                log.info("{} {}", imageBytes.length, scale);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream).scale(scale).toOutputStream(outputStream);
                // Thumbnails.of(inputStream).scale(accuracy).outputQuality(accuracy).toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            log.info("Original image size={}kb | Compressed size={}kb | Cost={}ms", srcSize / ONE_ZERO_TWO_FOUR,
                imageBytes.length / ONE_ZERO_TWO_FOUR, System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.error("[Image compression] msg=Image compression failed!", e);
        }
        return imageBytes;
    }

    /**
     * Automatic adjustment accuracy
     *
     * @param size
     *            Source image size
     * @return Image compression quality ratio
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < NINE_ZERO_ZERO) {
            accuracy = ZERO_EIGHT_FIVE;
        } else if (size < TWO_ZERO_FOUR_SEVEN) {
            accuracy = ZERO_SIX;
        } else if (size < THREE_TWO_SEVEN_FIVE) {
            accuracy = ZERO_FOUR_FOUR;
        } else {
            accuracy = ZERO_FOUR;
        }
        return accuracy;
    }

    public static byte[] readFileToByteArray(File f) {
        try (InputStream is = new FileInputStream(f); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] b = new byte[1024];
            int n;
            while ((n = is.read(b)) != -1) {
                out.write(b, 0, n);
            }
            return out.toByteArray();
        } catch (Exception e) {
            log.error("[Image readFileToByteArray]", e);
        }
        return new byte[0];
    }

    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory()) {
            dir.mkdirs();
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath + "\\" + fileName))) {
            bos.write(bytes);
        } catch (Exception e) {
            log.error("[Image getFileByBytes]", e);
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\zhuofan.han\\Desktop\\app2.0\\image");
            for (File f : Objects.requireNonNull(file.listFiles())) {
                byte[] imageBytes = compressPicForScale(FileUtils.readFileToByteArray(f), 16);
                FileUtils.writeByteArrayToFile(new File("C:\\Users\\zhuofan.han\\Desktop\\app2.0\\" + f.getName()),
                    imageBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
