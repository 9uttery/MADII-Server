package com.guttery.madii.domain.mail.presentation;

import com.guttery.madii.domain.mail.application.MailSendService;
import com.guttery.madii.domain.mail.application.MailVerifyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
@Validated
public class MailController {
    private final MailSendService mailSendService;
    private final MailVerifyService mailVerifyService;

    @GetMapping("/sign-up")
    public void sendSignUpMail(
            @NotBlank @RequestParam("email") final String email
    ) {
        mailSendService.sendSignUpMail(email);
    }

    @GetMapping("/password-reset")
    public void sendPasswordResetMail(
            @NotBlank @RequestParam("email") final String email
    ) {
        mailSendService.sendSignUpMail(email); // TODO: 추후 수정 필요
    }

    @GetMapping("/verify")
    public void verifyEmail(
            @NotBlank @RequestParam("email") final String email,
            @NotBlank @RequestParam("code") final String code
    ) {
        mailVerifyService.verifyEmail(email, code);
    }
}
