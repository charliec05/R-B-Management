package com.example.rbmgmt.common.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Profile({"default","dev"})
@RequiredArgsConstructor
public class LocalInvoiceStorage implements InvoiceStorage {

    @Value("${app.storage.local-dir:./invoices}")
    private String localDir;

    @Override
    public void upload(String key, InputStream data, long contentLength, String contentType) {
        try {
            Path dir = Path.of(localDir);
            Files.createDirectories(dir);
            try (OutputStream out = Files.newOutputStream(dir.resolve(key))) {
                data.transferTo(out);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream download(String key) {
        try {
            return Files.newInputStream(Path.of(localDir).resolve(key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


