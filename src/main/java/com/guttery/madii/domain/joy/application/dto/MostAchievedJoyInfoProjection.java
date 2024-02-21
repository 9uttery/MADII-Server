package com.guttery.madii.domain.joy.application.dto;

public interface MostAchievedJoyInfoProjection {
    Long getJoyId();
    Integer getJoyIconNum();
    String getContents();

    Integer getIsCreatedByMe();
    Integer getAchieveCount();
    Integer getAchieveRank();
}
