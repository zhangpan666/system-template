package com.gallery.manage.common.util;

import com.gallery.manage.common.enums.UploadType;
import com.light.core.model.FileInfo;
import com.light.core.model.TempFileInfo;
import com.light.core.utils.DateUtils;
import com.light.core.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class UploadUtil {

    public static final String FILE_SEPARATOR = "/";
    public static final String CONNECTOR_LINE = "-";

    public static String getDate() {
        return DateUtils.format(new Date(), DateUtils.DATE_FORMAT_PATH);
    }

    private static String fileName(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return MD5Util.MD5("" + System.currentTimeMillis()).hashCode() + "";
        }
        try {
            String encode = URLEncoder.encode(fileName, "utf-8");
            if (fileName.equals(encode)) {
                return fileName;
            }
            String[] split = fileName.split("\\.");
            if (!StringUtils.isEmpty(split)) {
                return MD5Util.MD5(fileName).hashCode() + "." + split[split.length - 1];
            } else {
                return MD5Util.MD5(fileName).hashCode() + "";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return MD5Util.MD5("" + System.currentTimeMillis()).hashCode() + "";
    }

    public static String getDir(Long userId, UploadType type) {
        StringBuffer path = new StringBuffer();
        path.append(SystemUtil.getFilePathPrefix());
        path.append(FILE_SEPARATOR);
        if (type == UploadType.USER) {
            path.append("user").append(FILE_SEPARATOR).append(getDate()).append(FILE_SEPARATOR).append(userId);
        } else if (type == UploadType.SYSTEM) {
            path.append("system").append(FILE_SEPARATOR).append(getDate());
        } else if (type == UploadType.ADVERT) {
            path.append("spread").append(FILE_SEPARATOR).append(getDate());
        } else if (type == UploadType.SHOW) {
            path.append("show").append(FILE_SEPARATOR).append(getDate());
        } else if (type == UploadType.ARTICLE_TYPE) {
            path.append("articleType").append(FILE_SEPARATOR).append(getDate());
        } else if (type == UploadType.CHAT) {
            path.append("chat").append(FILE_SEPARATOR).append(getDate());
        } else if (type == UploadType.SYSTEM_GALLERY) {
            path.append("storehouse").append(FILE_SEPARATOR).append(getDate());
        } else if (type == UploadType.GUESS) {
            path.append("guess").append(FILE_SEPARATOR).append(getDate());
        } else {
            path.append("tmp").append(FILE_SEPARATOR).append(getDate());
        }
        return path.toString();
    }

    public static String getPath(Long userId, UploadType type, String fileName) {
        return getDir(userId, type) + FILE_SEPARATOR + System.currentTimeMillis() + CONNECTOR_LINE + fileName;
    }


    public static void main(String[] args) {
        System.out.println(fileName("回家.jpg"));
        System.out.println(fileName("test-.fsd"));
        System.out.println(fileName(""));
    }


    public static byte[] saveToBytes(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<TempFileInfo> fileList = new ArrayList<>();
        try {
            if (!(request instanceof MultipartHttpServletRequest)) {
                return null;
            }
            System.out.println(request.getParts().size());
            Iterator<Part> iterator = request.getParts().iterator();
            while (iterator.hasNext()) {//遍历客户端上传的所有文件
                Part part = iterator.next();
                long size = part.getSize();
                String fileName = part.getSubmittedFileName();
                fileName = fileName(fileName);
                String paramName = part.getName();
                log.debug("part.getSize()" + size);//获取上传文件的大小
                log.debug("part.getName()" + fileName);//获取上传文件的名及添加数据时的key名
                fileName = System.currentTimeMillis() + CONNECTOR_LINE + fileName;
                log.debug("上传文件名称:" + fileName);
                String path = getDir(null, null) + FILE_SEPARATOR + fileName;
                String absolutePath = SystemUtil.getFileRootDir() + path;
                log.debug("上传文件绝对路径:" + absolutePath);
                File dest = new File(absolutePath);
                if (dest.exists()) {
                    dest.delete();
                }
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                if (parameterMap.get(paramName) != null) {
                    continue;
                }
                InputStream inputStream = part.getInputStream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }
                byte[] result = output.toByteArray();
                inputStream.close();
                output.close();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TempFileInfo> saveToLocal(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<TempFileInfo> fileList = new ArrayList<>();
        try {
            if (!(request instanceof MultipartHttpServletRequest)) {
                return null;
            }
            System.out.println(request.getParts().size());
            Iterator<Part> iterator = request.getParts().iterator();
            while (iterator.hasNext()) {//遍历客户端上传的所有文件
                Part part = iterator.next();
                long size = part.getSize();
                String fileName = part.getSubmittedFileName();
                fileName = fileName(fileName);
                String paramName = part.getName();
                log.debug("part.getSize()" + size);//获取上传文件的大小
                log.debug("part.getName()" + fileName);//获取上传文件的名及添加数据时的key名
                fileName = System.currentTimeMillis() + CONNECTOR_LINE + fileName;
                log.debug("上传文件名称:" + fileName);
                String path = getDir(null, null) + FILE_SEPARATOR + fileName;
                String absolutePath = SystemUtil.getFileRootDir() + path;
                log.debug("上传文件绝对路径:" + absolutePath);
                File dest = new File(absolutePath);
                if (dest.exists()) {
                    dest.delete();
                }
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                if (parameterMap.get(paramName) != null) {
                    continue;
                }
                InputStream inputStream = part.getInputStream();
                FileOutputStream fos = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                // 获取图片的宽度 和 高度
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(dest));
                int height = sourceImg.getHeight();
                int width = sourceImg.getWidth();

                TempFileInfo tmpFileInfo = new TempFileInfo(dest.getAbsolutePath(), path, fileName, dest.length(),width,height);
                fileList.add(tmpFileInfo);
                inputStream.close();
                fos.close();
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateFilePath(String fileName, Long userId, UploadType uploadPictureType) {
        fileName = fileName(fileName);
        fileName = DateUtils.format(new Date(), DateUtils.DATE_TIME_FORMAT_COMPACT) + CONNECTOR_LINE + fileName;
        String path = getDir(userId, uploadPictureType) + FILE_SEPARATOR + fileName;
        return path;
    }


    public static List<TempFileInfo> saveToLocal(HttpServletRequest request, Long userId, UploadType uploadPictureType) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<TempFileInfo> fileList = new ArrayList<>();
        try {
            if (!(request instanceof MultipartHttpServletRequest)) {
                return null;
            }
            System.out.println(request.getParts().size());
            Iterator<Part> iterator = request.getParts().iterator();
            while (iterator.hasNext()) {//遍历客户端上传的所有文件
                Part part = iterator.next();
                long size = part.getSize();
                String fileName = part.getSubmittedFileName();
                String originalFileName = part.getSubmittedFileName();
                fileName = fileName(fileName);
                String paramName = part.getName();
                if (parameterMap.get(paramName) != null || StringUtils.isEmpty(fileName) || size == 0) {
                    continue;
                }
                log.debug("part.getSize()" + size);//获取上传文件的大小
                log.debug("part.getName()" + fileName);//获取上传文件的名及添加数据时的key名
//                String path = FILE_SEPARATOR + getDay() + FILE_SEPARATOR + userDir + FILE_SEPARATOR + System.currentTimeMillis() + CONNECTOR_LINE + fileName;
                fileName = DateUtils.format(new Date(), DateUtils.DATE_TIME_FORMAT_COMPACT) + CONNECTOR_LINE + fileName;
                log.debug("上传文件名称:" + fileName);
                String path = getDir(userId, uploadPictureType) + FILE_SEPARATOR + fileName;
                String absolutePath = SystemUtil.getFileRootDir() + path;
                log.debug("上传文件绝对路径:" + absolutePath);
                File dest = new File(absolutePath);

                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                InputStream inputStream = part.getInputStream();
                FileOutputStream fos = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                // 获取图片的宽度 和 高度
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(dest));
                int height = sourceImg.getHeight();
                int width = sourceImg.getWidth();

                TempFileInfo tmpFileInfo = new TempFileInfo(userId, dest.getAbsolutePath(), path, fileName, dest.length(),width,height);
                tmpFileInfo.setOriginalFileName(originalFileName);
                fileList.add(tmpFileInfo);
                inputStream.close();
                fos.close();
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TempFileInfo generateFilePath(HttpServletRequest request, Long userId, UploadType uploadPictureType) {
        List<TempFileInfo> tmpFileInfoList = UploadUtil.saveToLocal(request, userId, uploadPictureType);
        if (tmpFileInfoList.size() > 0) {
            return tmpFileInfoList.get(0);
        }
        return null;
    }

    public static List<TempFileInfo> saveToLocal(HttpServletRequest request, UploadType uploadPictureType) {
        return saveToLocal(request, null, uploadPictureType);
    }



    public static TempFileInfo saveToLocal(InputStream inputStream, String fileSuffix, Long userId, UploadType uploadPictureType) {
        try {
            log.debug("part.getSize()" + inputStream.available());//获取上传文件的大小
            String fileName = new StringBuilder().append(DateUtils.format(new Date(), DateUtils.DATE_TIME_FORMAT_COMPACT))
                    .append(CONNECTOR_LINE).append(UUID.randomUUID().toString()
                            .replaceAll("-", "")).append(FileUtils.FILE_CONNECT_CHAR).append(fileSuffix).toString();
            log.debug("上传文件名称:" + fileName);
            String path = getDir(userId, uploadPictureType) + FILE_SEPARATOR + fileName;
            String absolutePath = SystemUtil.getFileRootDir() + path;
            log.debug("上传文件绝对路径:" + absolutePath);
            File dest = new File(absolutePath);
            FileUtils.saveToFile(inputStream, dest);
            // 获取图片的宽度 和 高度
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(dest));
            int height = sourceImg.getHeight();
            int width = sourceImg.getWidth();
            TempFileInfo tmpFileInfo = new TempFileInfo(userId, dest.getAbsolutePath(), path, fileName, dest.length()).setHeight(height).setWidth(width);
            return tmpFileInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TempFileInfo saveToLocal(FileInfo fileInfo, Long userId, UploadType uploadPictureType) {
        try {
            InputStream inputStream = fileInfo.getInputStream();
            String fileSuffix = fileInfo.getFileSuffix();
            log.debug("part.getSize()" + inputStream.available());//获取上传文件的大小
            String fileName = new StringBuilder().append(DateUtils.format(new Date(), DateUtils.DATE_TIME_FORMAT_COMPACT))
                    .append(CONNECTOR_LINE).append(UUID.randomUUID().toString()
                            .replaceAll("-", "")).append(FileUtils.FILE_CONNECT_CHAR).append(fileSuffix).toString();
            log.debug("上传文件名称:" + fileName);
            String path = getDir(userId, uploadPictureType) + FILE_SEPARATOR + fileName;
            String absolutePath = SystemUtil.getFileRootDir() + path;
            log.debug("上传文件绝对路径:" + absolutePath);
            File dest = new File(absolutePath);
            FileUtils.saveToFile(inputStream, dest);
            // 获取图片的宽度 和 高度
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(dest));
            int height = sourceImg.getHeight();
            int width = sourceImg.getWidth();
            TempFileInfo tmpFileInfo = new TempFileInfo(userId, dest.getAbsolutePath(), path, fileName, dest.length()).setHeight(height).setWidth(width);
            return tmpFileInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TempFileInfo saveToLocal(FileInfo fileInfo, UploadType uploadPictureType) {
        return saveToLocal(fileInfo, null, uploadPictureType);
    }
}
