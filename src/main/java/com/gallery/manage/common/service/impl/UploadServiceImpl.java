package com.gallery.manage.common.service.impl;

import com.gallery.manage.common.service.UploadService;
import com.gallery.manage.common.util.threadpool.ThreadPoolUtil;
import com.light.core.utils.FtpClientUtil;
import com.light.core.utils.SftpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpProtocolException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    private static final String HOST = "xgtk-file";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "h8tVs6HpxLm-vD%oLbEcNf3b";


    @Override
    public void uploadFile(File file) {
        ThreadPoolUtil.executorService.execute(() -> {
            SftpUtil sftpUtil = SftpUtil.getInstance(HOST, USERNAME, PASSWORD);
            try {
                sftpUtil.directoryExists(file.getParent());
                sftpUtil.uploadFile(new FileInputStream(file), file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                log.info("上传失败，异常信息:{}", e);
            } finally {
                sftpUtil.close();
            }
        });
    }

    @Override
    public void uploadFileSync(File file) {
        SftpUtil sftpUtil = SftpUtil.getInstance(HOST, USERNAME, PASSWORD);
        try {
            sftpUtil.directoryExists(file.getParent());
            sftpUtil.uploadFile(new FileInputStream(file), file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("上传失败，异常信息:{}", e);
        } finally {
            sftpUtil.close();
        }
    }

    @Override
    public void uploadFile(List<File> fileList) {
        ThreadPoolUtil.executorService.execute(() -> {
            if (CollectionUtils.isEmpty(fileList)) {
                return;
            }
            SftpUtil sftpUtil = SftpUtil.getInstance(HOST, USERNAME, PASSWORD);
            try {
                for (File file : fileList) {
                    String absolutePath = file.getAbsolutePath();
                    String parent = file.getParent();
                    sftpUtil.directoryExists(parent);
                    sftpUtil.uploadFile(new FileInputStream(file), absolutePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("上传失败，异常信息:{}", e);
            } finally {
                sftpUtil.close();
            }
        });
    }

    @Override
    public void uploadFileSync(List<File> fileList) {
        if (CollectionUtils.isEmpty(fileList)) {
            return;
        }
        SftpUtil sftpUtil = SftpUtil.getInstance(HOST, USERNAME, PASSWORD);
        try {
            for (File file : fileList) {
                String absolutePath = file.getAbsolutePath();
                String parent = file.getParent();
                sftpUtil.directoryExists(parent);
                sftpUtil.uploadFile(new FileInputStream(file), absolutePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("上传失败，异常信息:{}", e);
        } finally {
            sftpUtil.close();
        }
    }

    @Override
    public void uploadFile(File sourceFile, String targetPath) {
        ThreadPoolUtil.executorService.execute(() -> {
            SftpUtil sftpUtil = SftpUtil.getInstance(HOST, USERNAME, PASSWORD);
            try {
                File targetFile = new File(targetPath);
                sftpUtil.directoryExists(targetFile.getParent());
                sftpUtil.uploadFile(new FileInputStream(sourceFile), targetFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                log.info("上传失败，异常信息:{}", e);
            } finally {
                sftpUtil.close();
            }
        });
    }

    @Override
    public void uploadFile(File sourceFile, File targetFile) {
        ThreadPoolUtil.executorService.execute(() -> {
            SftpUtil sftpUtil = SftpUtil.getInstance(HOST, USERNAME, PASSWORD);
            try {
                sftpUtil.directoryExists(targetFile.getParent());
                sftpUtil.uploadFile(new FileInputStream(sourceFile), targetFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                log.info("上传失败，异常信息:{}", e);
            } finally {
                sftpUtil.close();
            }
        });
    }

    public static void main(String[] args) {
        try {
            FtpClient ftpClient = FtpClientUtil.connect("xj.gdcapi.com", "JC1.amtk", "xhllyRi3z2");
            String welcomeMsg = ftpClient.getWelcomeMsg();
            System.out.println(welcomeMsg);
            Iterator<FtpDirEntry> ftpDirEntryIterator = ftpClient.listFiles("/");
            while (ftpDirEntryIterator.hasNext()){
                FtpDirEntry ftpDirEntry = ftpDirEntryIterator.next();
                System.out.println(ftpDirEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
