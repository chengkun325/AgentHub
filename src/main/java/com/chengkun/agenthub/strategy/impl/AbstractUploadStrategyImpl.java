package com.chengkun.agenthub.strategy.impl;

import com.chengkun.agenthub.exception.BizException;
import com.chengkun.agenthub.strategy.UploadStrategy;
import com.chengkun.agenthub.util.FileUtil;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public abstract class AbstractUploadStrategyImpl implements UploadStrategy {

    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            // 首先计算文件的 MD5 值和文件扩展名。
            String md5 = FileUtil.getMd5(file.getInputStream());
            String extName = FileUtil.getExtName(file.getOriginalFilename());
            // 构建文件名 fileName 为 md5 + extName。
            String fileName = md5 + extName;
            // 检查文件是否已存在，如果不存在则调用 upload(path, fileName, file.getInputStream()) 方法进行上传。
            if (!exists(path + fileName)) {
                upload(path, fileName, file.getInputStream());
            }
            // 通过 getFileAccessUrl(path + fileName) 方法获取URL。
            return getFileAccessUrl(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("文件上传失败");
        }
    }

    @Override
    public String uploadFile(String fileName, InputStream inputStream, String path) {
        try {
            upload(path, fileName, inputStream);
            return getFileAccessUrl(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("文件上传失败");
        }
    }

    public abstract Boolean exists(String filePath);

    public abstract void upload(String path, String fileName, InputStream inputStream) throws IOException;

    public abstract String getFileAccessUrl(String filePath);

}
