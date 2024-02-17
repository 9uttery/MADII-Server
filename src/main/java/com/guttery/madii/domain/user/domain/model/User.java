package com.guttery.madii.domain.user.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_user")
@Access(AccessType.FIELD)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Embedded
    private LoginInfo loginInfo;
    @Embedded
    private SocialInfo socialInfo;
    @Embedded
    private UserProfile userProfile;
    private Boolean agreesMarketing;
    @Enumerated(EnumType.STRING)
    private Role role;

    private User(LoginInfo loginInfo, SocialInfo socialInfo, UserProfile userProfile, Boolean agreesMarketing, Role role) {
        this.loginInfo = loginInfo;
        this.socialInfo = socialInfo;
        this.userProfile = userProfile;
        this.agreesMarketing = agreesMarketing;
        this.role = role;
    }

    public static User createNormalUser(String loginId, String password, Boolean agreesMarketing) {
        return new User(new LoginInfo(loginId, password), null, null, agreesMarketing, Role.ROLE_USER);
    }

    public static User createSocialUser(String socialId, SocialProvider provider) {
        return new User(null, new SocialInfo(socialId, provider), null, null, Role.ROLE_USER);
    }

    public void updateUserProfile(String nickname, String profileImage) {
        this.userProfile = new UserProfile(nickname, profileImage);
    }

    public void updateMarketingAgreement(Boolean agreesMarketing) {
        this.agreesMarketing = agreesMarketing;
    }

    public void linkSocialInfo(SocialInfo socialInfo) {
        this.socialInfo = socialInfo;
    }

    public void unlinkSocialInfo() {
        this.socialInfo = null;
    }

    public String getEncryptedPassword() {
        return loginInfo.getPassword();
    }

    public boolean agreedMarketing() {
        return agreesMarketing;
    }

    public boolean hasProfile() {
        return userProfile != null;
    }

    public boolean matchesUserId(final Long userId) {
        return this.userId.equals(userId);
    }
}
