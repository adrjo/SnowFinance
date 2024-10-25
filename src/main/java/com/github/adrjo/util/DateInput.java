package com.github.adrjo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateInput {


    public enum DateType {
        YEAR,
        MONTH,
        DAY,
        WEEK
    }

    private DateType type;
    private Date date;

    public DateInput(DateType type, String... inputs) {
        try {
            this.type = type;
            switch (type) {
                case YEAR -> this.date = new SimpleDateFormat("yyyy").parse(inputs[0]);
                case MONTH -> this.date = new SimpleDateFormat("yyyy-MM").parse(inputs[0] + "-" + inputs[1]);
                case DAY -> this.date = new SimpleDateFormat("yyyy-MM-dd").parse(inputs[0] + "-" + inputs[1] + "-" + inputs[2]);
                case WEEK -> {
                    this.date = new SimpleDateFormat("yyyy-ww").parse(inputs[0] + "-" + inputs[1]);
                    // Move date 1 day forward due to formatter parsing week 1 day off for some reason
                    this.date.setTime(this.date.getTime() + TimeUnit.DAYS.toMillis(1));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public DateInput(DateType dateType, Integer... inputs) {
        this(dateType,
                Arrays.stream(inputs)
                        .map(String::valueOf)
                        .toArray(String[]::new));
    }

    public Date getDate() {
        return date;
    }

    public DateType getDateType() {
        return type;
    }
}
