package com.chengkun.agenthub.strategy.impl;

import com.chengkun.agenthub.config.properties.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service("minioUploadStrategyImpl")
public class MinioUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Autowired
    private MinioProperties minioProperties;

    /* 
     * 文件存在性检查
     */
    @Override
    public Boolean exists(String filePath) {
        boolean exist = true;
        try {
            // 检查指定路径的文件是否存在于 MinIO 中。
            getMinioClient()
                    .statObject(StatObjectArgs.builder().bucket(minioProperties.getBucketName()).object(filePath).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @SneakyThrows
    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        // 将输入流 inputStream 中的文件上传到 MinIO 中。
        // 构建上传参数时，指定了存储桶名称、对象路径、输入流、内容长度等信息。
        getMinioClient().putObject(
                PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(path + fileName).stream(
                                inputStream, inputStream.available(), -1)
                        .build());
    }

    /* 
     * 获取文件访问 URL
     */
    @Override
    public String getFileAccessUrl(String filePath) {
        return minioProperties.getUrl() + filePath;
    }

    /**
     * @return
     * 用于与 MinIO 服务器进行连接和操作，使用了 MinioProperties 中的配置信息。
     */
    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
