package com.example.rbmgmt.common.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
@Profile("aws")
@RequiredArgsConstructor
public class SqsStockEventPublisher implements StockEventPublisher {

    @Value("${app.aws.sqs.queueUrl}")
    private String queueUrl;

    @Value("${app.aws.region:us-east-1}")
    private String region;

    private SqsClient client() {
        return SqsClient.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    }

    @Override
    public void publishReceived(String sku, String warehouseCode, String lotNo) {
        String body = String.format("{\"type\":\"STOCK_RECEIVED\",\"sku\":\"%s\",\"warehouseCode\":\"%s\",\"lotNo\":\"%s\"}", sku, warehouseCode, lotNo);
        client().sendMessage(SendMessageRequest.builder().queueUrl(queueUrl).messageBody(body).build());
    }
}


