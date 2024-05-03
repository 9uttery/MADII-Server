package com.guttery.madii.domain.mail.application.service;

import lombok.Getter;

@Getter
public enum MailType {
    SIGN_UP("sign_up_mail.html"),
    PASSWORD_RESET("password_reset_mail.html");

    private final String templateFileName;

    MailType(final String templateFileName) {
        this.templateFileName = templateFileName;
    }
}
