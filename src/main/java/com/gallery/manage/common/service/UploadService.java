package com.gallery.manage.common.service;

import java.io.File;
import java.util.List;

public interface UploadService {

    void uploadFile(File file);

    void uploadFileSync(File file);

    void uploadFile(List<File> fileList);

    void uploadFileSync(List<File> fileList);

    void uploadFile(File sourceFile, String targetPath);

    void uploadFile(File sourceFile, File targetFile);
}
