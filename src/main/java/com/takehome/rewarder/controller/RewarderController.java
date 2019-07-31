package com.takehome.rewarder.controller;

import com.takehome.rewarder.models.Customer;
import com.takehome.rewarder.models.Voucher;
import com.takehome.rewarder.models.api.FileUploadModel;
import com.takehome.rewarder.service.CustomerExtractionService;
import com.takehome.rewarder.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/rewarder")
public class RewarderController {

    @Autowired
    private CustomerExtractionService customerExtractionService;

    @Autowired
    private VoucherService voucherService;

    @PostMapping("/get-vouchers/today")
    public List<Voucher> getVouchersForEligibleCustomersToday(@Validated @ModelAttribute FileUploadModel uploadModel) throws Exception {
        MultipartFile file = uploadModel.getMultipartFile();
        List<Customer> customerList = customerExtractionService.getCustomersFromFile(file.getInputStream());
        return voucherService.generateVouchersForCustomersEligibleToday(customerList);
    }

    @GetMapping("/download-sample")
    public ResponseEntity<Resource> downloadSampleFile() throws FileNotFoundException {
        File file = new File("samples/test-rewarder-upload.csv");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sampleFile.csv");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
