package com.guttery.madii.domain.user.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_user")
@Access(AccessType.FIELD)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Embedded
    private LoginInfo loginInfo;
    @Embedded
    private SocialInfo socialInfo;
    @Embedded
    private OnboardingInfo onboardingInfo;

    private User(LoginInfo loginInfo, SocialInfo socialInfo, OnboardingInfo onboardingInfo) {
        this.loginInfo = loginInfo;
        this.socialInfo = socialInfo;
        this.onboardingInfo = onboardingInfo;
    }

    public static User createNormalUser(String loginId, String password) {
        return new User(new LoginInfo(loginId, password), null, null);
    }

    public static User createSocialUser(String socialId, SocialProvider provider) {
        return new User(null, new SocialInfo(socialId, provider), null);
    }

    public void updateOnboardingInfo(OnboardingInfo onboardingInfo) {
        this.onboardingInfo = onboardingInfo;
    }

    public void modifyInfo(OnboardingInfo onboardingInfo) {
        this.onboardingInfo = onboardingInfo;
    }

    public void linkSocialInfo(SocialInfo socialInfo) {
        this.socialInfo = socialInfo;
    }

    public void unlinkSocialInfo() {
        this.socialInfo = null;
    }

}
