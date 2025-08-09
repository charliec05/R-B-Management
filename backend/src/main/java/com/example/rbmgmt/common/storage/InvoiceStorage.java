package com.example.rbmgmt.common.storage;

import java.io.InputStream;

public interface InvoiceStorage {
    void upload(String key, InputStream data, long contentLength, String contentType);
    InputStream download(String key);
}


