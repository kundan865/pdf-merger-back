package com.kundan.pdfMerger.controller;

import com.kundan.pdfMerger.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pdf-merger")
@RequiredArgsConstructor
@CrossOrigin("*")
public class pdfMergeController {

    private final PdfService pdfService;

    @PostMapping(
            value = "/merge",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<byte[]> mergePdfs(
            @RequestParam("files") MultipartFile[] files
    ) {

        try {
            if (files == null || files.length < 2){
                return ResponseEntity
                        .badRequest()
                        .build();
            }

            byte[] mergedPdf = pdfService.mergePdfs(files);

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=merged.pdf"
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(mergedPdf);

        } catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
