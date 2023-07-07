package tech.dongfei.ssyx.product.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.dongfei.ssyx.product.service.FileUploadService;

import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    @SneakyThrows
    @Override
    public String uploadFile(MultipartFile file) {
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
        String objectName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String currentDateTime = new DateTime().toString("yyyy/MM/dd");
        objectName = currentDateTime + "/" + uuid + objectName;

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName).object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(putObjectArgs);
        return endpoint + "/" + bucketName + "/" + objectName;
    }
}
