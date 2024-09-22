package com.example.pizzastore.dto;

import java.util.Date;

public class InquiryDTO {
    private Long id;
    private String question;
    private String reply;
    private Date createdDate;
    private Date replyDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getReply() {
        return reply;
    }
    public void setReply(String reply) {
        this.reply = reply;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Date getReplyDate() {
        return replyDate;
    }
    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }
}
