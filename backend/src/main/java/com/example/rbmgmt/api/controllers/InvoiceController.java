package com.example.rbmgmt.api.controllers;

import com.example.rbmgmt.common.storage.InvoiceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceStorage storage;

    @PostMapping(value = "/{key}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_OPS')")
    public ResponseEntity<Void> upload(@PathVariable String key, @RequestParam("file") MultipartFile file) throws Exception {
        storage.upload(key, file.getInputStream(), file.getSize(), file.getContentType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{key}")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_OPS','READONLY')")
    public ResponseEntity<byte[]> download(@PathVariable String key) throws Exception {
        try (InputStream in = storage.download(key)) {
            byte[] bytes = in.readAllBytes();
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + key)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
        }
    }
}


