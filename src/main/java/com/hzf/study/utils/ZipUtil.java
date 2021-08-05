package com.hzf.study.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * <p>zip 工具类</p>
 *
 * <p>注意：此类中用到的压缩类ZipEntry等都来自于org.apache.tools包而非java.util包</p>
 * <p>依赖：ant-1.10.11.jar</p>
 *
 * @author zhuofan.han
 * @date 2021/8/5 10:42
 */
@Slf4j
public class ZipUtil {
    /**
     * 使用GBK编码可以避免压缩中文文件名乱码
     */
    private static final String CHINESE_CHARSET = "UTF-8";

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    private ZipUtil() {
        // 私用构造主法.因为此类是工具类.
    }

    /**
     * <p>
     * 压缩文件
     * </p>
     *
     * @param sourceFolder 需压缩文件 或者 文件夹 路径
     * @param zipFilePath  压缩文件输出路径
     * @throws Exception
     */
    public static void zip(String sourceFolder, String zipFilePath) throws Exception {
        log.debug("开始压缩 [" + sourceFolder + "] 到 [" + zipFilePath + "]");
        OutputStream out = new FileOutputStream(zipFilePath);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ZipOutputStream zos = new ZipOutputStream(bos);
        // 解决中文文件名乱码  
        zos.setEncoding(CHINESE_CHARSET);
        File file = new File(sourceFolder);
        String basePath = null;
        if (file.isDirectory()) {
            basePath = file.getPath();
        } else {
            basePath = file.getParent();
        }
        zipFile(file, basePath, zos);
        zos.closeEntry();
        zos.close();
        bos.close();
        out.close();
        log.debug("压缩 [" + sourceFolder + "] 完成！");
    }

    /**
     * <p>
     * 压缩文件
     * </p>
     *
     * @param sourceFolders 一组 压缩文件夹 或 文件
     * @param zipFilePath   压缩文件输出路径
     * @throws Exception
     */
    public static void zip(String[] sourceFolders, String zipFilePath) throws Exception {
        OutputStream out = new FileOutputStream(zipFilePath);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ZipOutputStream zos = new ZipOutputStream(bos);
        // 解决中文文件名乱码  
        zos.setEncoding(CHINESE_CHARSET);

        for (String sourceFolder : sourceFolders) {
            log.debug("开始压缩 [" + sourceFolder + "] 到 [" + zipFilePath + "]");
            File file = new File(sourceFolder);
            zipFile(file, file.getParent(), zos);
        }

        zos.closeEntry();
        zos.close();
        bos.close();
        out.close();
        log.debug("压缩 " + Arrays.toString(sourceFolders) + " 完成！");
    }

    /**
     * <p>
     * 递归压缩文件
     * </p>
     *
     * @param parentFile
     * @param basePath
     * @param zos
     * @throws Exception
     */
    private static void zipFile(File parentFile, String basePath, ZipOutputStream zos) throws Exception {
        File[] files;
        if (parentFile.isDirectory()) {
            files = parentFile.listFiles();
        } else {
            files = new File[1];
            files[0] = parentFile;
        }
        String pathName;
        InputStream is;
        BufferedInputStream bis;
        byte[] cache = new byte[CACHE_SIZE];
        for (File file : files) {
            if (file.isDirectory()) {
                log.debug("目录：" + file.getPath());

                basePath = basePath.replace('\\', '/');
                if (basePath.endsWith("/")) {
                    pathName = file.getPath().substring(basePath.length()) + "/";
                } else {
                    pathName = file.getPath().substring(basePath.length() + 1) + "/";
                }

                zos.putNextEntry(new ZipEntry(pathName));
                zipFile(file, basePath, zos);
            } else {
                pathName = file.getPath().substring(basePath.length());
                pathName = pathName.replace('\\', '/');
                if (pathName.charAt(0) == '/') {
                    pathName = pathName.substring(1);
                }

                log.debug("压缩：" + pathName);

                is = new FileInputStream(file);
                bis = new BufferedInputStream(is);
                zos.putNextEntry(new ZipEntry(pathName));
                int nRead = 0;
                while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                    zos.write(cache, 0, nRead);
                }
                bis.close();
                is.close();
            }
        }
    }

    /**
     * 解压zip文件
     *
     * @param zipFileName     待解压的zip文件路径，例如：c:\\a.zip
     * @param outputDirectory 解压目标文件夹,例如：c:\\a\
     */
    public static void unZip(String zipFileName, String outputDirectory)
            throws Exception {
        log.debug("开始解压 [" + zipFileName + "] 到 [" + outputDirectory + "]");

        try (ZipFile zipFile = new ZipFile(zipFileName)) {

            Enumeration<?> e = zipFile.getEntries();

            ZipEntry zipEntry;

            createDirectory(outputDirectory, "");

            while (e.hasMoreElements()) {

                zipEntry = (ZipEntry) e.nextElement();

                log.debug("解压：" + zipEntry.getName());

                if (zipEntry.isDirectory()) {

                    String name = zipEntry.getName();

                    name = name.substring(0, name.length() - 1);

                    File f = new File(outputDirectory + File.separator + name);

                    f.mkdir();

                    log.debug("创建目录：" + outputDirectory + File.separator + name);

                } else {

                    String fileName = zipEntry.getName();

                    fileName = fileName.replace('\\', '/');

                    if (fileName.contains("/")) {

                        createDirectory(outputDirectory, fileName.substring(0,
                                fileName.lastIndexOf("/")));

                    }

                    File f = new File(outputDirectory + File.separator
                            + zipEntry.getName());

                    f.createNewFile();

                    InputStream in = zipFile.getInputStream(zipEntry);

                    FileOutputStream out = new FileOutputStream(f);

                    byte[] by = new byte[1024];

                    int c;

                    while ((c = in.read(by)) != -1) {

                        out.write(by, 0, c);

                    }

                    in.close();

                    out.close();

                }

            }
            log.debug("解压 [" + zipFileName + "] 完成！");

        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        }

    }

    /**
     * 创建目录
     *
     * @param directory
     * @param subDirectory
     * @author hezhao
     * @Time 2017年7月28日 下午7:10:05
     */
    private static void createDirectory(String directory, String subDirectory) {

        String[] dir;

        File fl = new File(directory);

        try {

            if ("".equals(subDirectory) && !fl.exists()) {

                fl.mkdir();

            } else if (!"".equals(subDirectory)) {

                dir = subDirectory.replace('\\', '/').split("/");

                StringBuilder directoryBuilder = new StringBuilder(directory);
                for (String s : dir) {

                    File subFile = new File(directoryBuilder + File.separator + s);

                    if (!subFile.exists()) {
                        subFile.mkdir();
                    }

                    directoryBuilder.append(File.separator).append(s);

                }

            }

        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        }

    }

    /**
     * 无需解压直接读取Zip文件和文件内容
     *
     * @param file 文件
     * @throws Exception
     * @author hezhao
     * @Time 2017年7月28日 下午3:23:10
     */
    public static void readZipFile(String file) throws Exception {
        java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        java.util.zip.ZipInputStream zin = new java.util.zip.ZipInputStream(in);
        java.util.zip.ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                log.info("file - " + ze.getName() + " : "
                        + ze.getSize() + " bytes");
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zipFile.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();
    }


    public static void main(String[] args) throws Exception {
        try {
//          readZipFile("D:\\new1\\文字.zip");

            //压缩文件
//          String sourceFolder = "D:/新建文本文档.txt";
//          String zipFilePath = "D:/新建文本文档.zip";
//          ZipUtil.zip(sourceFolder, zipFilePath);

            //压缩文件夹
//          String sourceFolder = "D:/fsc1";  
//          String zipFilePath = "D:/fsc1.zip";  
//          ZipUtil.zip(sourceFolder, zipFilePath);  

            //压缩一组文件
          String [] paths = {"C:\\Users\\zhuofan.han\\Desktop\\StoreVue SRP 通報機制_1.0_20210719.docx","C:\\Users\\zhuofan.han\\Desktop\\Inspection签到报表20210601-20210611.xlsx"};
          zip(paths, "C:\\Users\\zhuofan.han\\Desktop\\韩卓帆.zip");

//          unZip("D:\\FastStoneCapturecn.zip", "D:/fsc2");  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

