package com.example.rbmgmt.common.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;

@Component
@Profile("aws")
@RequiredArgsConstructor
public class S3InvoiceStorage implements InvoiceStorage {

    @Value("${app.aws.s3.bucket}")
    private String bucket;

    @Value("${app.aws.region:us-east-1}")
    private String region;

    private S3Client client() {
        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    }

    @Override
    public void upload(String key, InputStream data, long contentLength, String contentType) {
        var put = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
            .build();
        client().putObject(put, RequestBody.fromInputStream(data, contentLength));
    }

    @Override
    public InputStream download(String key) {
        var get = GetObjectRequest.builder().bucket(bucket).key(key).build();
        return client().getObject(get);
    }
}


