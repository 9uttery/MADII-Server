package com.guttery.madii.common.util;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSpecifierUtils {
    public static OrderSpecifier<Double> makeRandom() {
        return Expressions.numberTemplate(Double.class, "function('rand')").asc();
    }
}
