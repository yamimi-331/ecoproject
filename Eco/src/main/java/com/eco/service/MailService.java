package com.eco.service;

public interface MailService {
    public String sendAuthCode(String toEmail);
    public boolean existsByEmail(String email);
}
