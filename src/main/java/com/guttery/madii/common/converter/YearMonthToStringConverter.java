package com.guttery.madii.common.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.YearMonth;

public class YearMonthToStringConverter implements Converter<YearMonth, String> {
    @Override
    public String convert(final YearMonth yearMonth) {
        return yearMonth.toString();
    }
}
