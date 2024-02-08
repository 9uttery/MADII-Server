package com.guttery.madii.common.converter;


import org.springframework.core.convert.converter.Converter;

import java.time.YearMonth;

public class StringToYearMonthConverter implements Converter<String, YearMonth> {
    @Override
    public YearMonth convert(final String string) {
        return YearMonth.parse(string);
    }
}
