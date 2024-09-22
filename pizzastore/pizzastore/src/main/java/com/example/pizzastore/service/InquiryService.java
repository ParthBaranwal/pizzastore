package com.example.pizzastore.service;

import com.example.pizzastore.dto.InquiryDTO;
import com.example.pizzastore.dto.InquiryRequestDTO;
import com.example.pizzastore.model.Inquiry;
import com.example.pizzastore.model.User;
import com.example.pizzastore.repository.InquiryRepository;
import com.example.pizzastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private UserRepository userRepository;

    public InquiryDTO createInquiry(InquiryRequestDTO inquiryRequest) {
        User user = userRepository.findById(inquiryRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Inquiry inquiry = new Inquiry();
        inquiry.setUser(user);
        inquiry.setQuestion(inquiryRequest.getQuestion());
        inquiry.setCreatedDate(new Date());

        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        return convertToDTO(savedInquiry);
    }

    public InquiryDTO replyToInquiry(Long inquiryId, String reply) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        inquiry.setReply(reply);
        inquiry.setReplyDate(new Date());

        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        return convertToDTO(savedInquiry);
    }



    public InquiryDTO getInquiryById(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        return convertToDTO(inquiry);
    }

    public List<InquiryDTO> getInquiriesByUserId(Long userId) {
        List<Inquiry> inquiries = inquiryRepository.findByUserId(userId);
        return inquiries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InquiryDTO convertToDTO(Inquiry inquiry) {
        InquiryDTO dto = new InquiryDTO();
        dto.setId(inquiry.getId());
        dto.setQuestion(inquiry.getQuestion());
        dto.setReply(inquiry.getReply());
        dto.setCreatedDate(inquiry.getCreatedDate());
        dto.setReplyDate(inquiry.getReplyDate());
        return dto;
    }
}
