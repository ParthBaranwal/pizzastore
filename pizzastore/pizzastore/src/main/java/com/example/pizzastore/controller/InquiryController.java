package com.example.pizzastore.controller;

import com.example.pizzastore.dto.InquiryDTO;
import com.example.pizzastore.dto.InquiryRequestDTO;
import com.example.pizzastore.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inquiries")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<InquiryDTO> createInquiry(@RequestBody InquiryRequestDTO inquiryRequest) {
        InquiryDTO inquiryDTO = inquiryService.createInquiry(inquiryRequest);
        return ResponseEntity.ok(inquiryDTO);
    }

    @PutMapping("/{inquiryId}/reply")
    public ResponseEntity<InquiryDTO> replyToInquiry(@PathVariable Long inquiryId, @RequestBody String reply) {
        // Remove surrounding quotes if any
        reply = reply.replaceAll("^\"|\"$", "");

        InquiryDTO inquiryDTO = inquiryService.replyToInquiry(inquiryId, reply);
        return ResponseEntity.ok(inquiryDTO);
    }


    @GetMapping("/{inquiryId}")
    public ResponseEntity<InquiryDTO> getInquiryById(@PathVariable Long inquiryId) {
        InquiryDTO inquiryDTO = inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.ok(inquiryDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InquiryDTO>> getInquiriesByUserId(@PathVariable Long userId) {
        List<InquiryDTO> inquiries = inquiryService.getInquiriesByUserId(userId);
        return ResponseEntity.ok(inquiries);
    }
}
