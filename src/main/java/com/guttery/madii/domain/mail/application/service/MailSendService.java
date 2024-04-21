package com.guttery.madii.domain.mail.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.mail.application.dto.MailVerificationCodeResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailSendService {
    private static final Long EXPIRE_TIME = 1000 * 60 * 3L + 1000 * 10; // 3분 10초

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final RedisTemplate<String, String> redisTemplate;

    public MailVerificationCodeResponse sendSignUpMail(final String email) {
        final String code = MailVerificationCodeGenerator.generate(); // 인증코드 생성
        final MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
            message.setSubject("[MADII] 회원가입 인증번호 발송"); // 이메일 제목
            message.setText(setContext(code), "utf-8", "html"); // 내용 설정(Template Process)
            message.setFrom(new InternetAddress("madii.service.cs@gmail.com", "마디"));
        } catch (final MessagingException | UnsupportedEncodingException e) {
            throw CustomException.of(ErrorDetails.MAIL_SEND_FAILED);
        }

        redisTemplate.opsForValue().set(email, code, EXPIRE_TIME, TimeUnit.MILLISECONDS); // Redis에 인증코드 저장
        javaMailSender.send(message); // 이메일 전송

        return new MailVerificationCodeResponse(code);
    }

    private String setContext(final String code) { // 타임리프 설정하는 코드
        final Context context = new Context();
        context.setVariable("code", code); // Template에 전달할 데이터 설정

        return springTemplateEngine.process("mail", context); // mail.html
    }

}
