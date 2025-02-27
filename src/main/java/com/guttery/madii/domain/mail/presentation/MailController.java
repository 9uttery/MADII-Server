package com.guttery.madii.domain.mail.presentation;

import com.guttery.madii.domain.mail.application.dto.MailVerificationCodeResponse;
import com.guttery.madii.domain.mail.application.service.MailSendService;
import com.guttery.madii.domain.mail.application.service.MailType;
import com.guttery.madii.domain.mail.application.service.MailVerifyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/mail")
@Validated
public class MailController {
    private final MailSendService mailSendService;
    private final MailVerifyService mailVerifyService;

    @GetMapping("/sign-up")
    public MailVerificationCodeResponse sendSignUpMail(
            @NotBlank @RequestParam("email") final String email
    ) {
        return mailSendService.sendMail(email, MailType.SIGN_UP);
    }

    @GetMapping("/password-reset")
    public MailVerificationCodeResponse sendPasswordResetMail(
            @NotBlank @RequestParam("email") final String email
    ) {
        return mailSendService.sendMail(email, MailType.PASSWORD_RESET);
    }

    @GetMapping("/verify")
    public void verifyEmail(
            @NotBlank @RequestParam("email") final String email,
            @NotBlank @RequestParam("code") final String code
    ) {
        mailVerifyService.verifyEmail(email, code);
    }
}
