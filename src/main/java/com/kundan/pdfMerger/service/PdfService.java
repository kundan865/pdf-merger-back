package com.kundan.pdfMerger.service;

import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    public byte[] mergePdfs(MultipartFile[] files) throws IOException {
        PDFMergerUtility merger = new PDFMergerUtility();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        merger.setDestinationStream(outputStream);

        for (MultipartFile file : files){
            if(file.isEmpty()){
                continue;
            }

            RandomAccessReadBuffer source =
                    new RandomAccessReadBuffer(file.getInputStream());

            merger.addSource(source);
        }

        merger.mergeDocuments(null);
        return outputStream.toByteArray();
    }
}
