package com.github.adrjo.gui;

import com.github.adrjo.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                case WEEK -> this.date = new SimpleDateFormat("yyyy-ww").parse(inputs[0] + "-" + inputs[1]);
            }
            System.out.println(Helper.DATE_FORMAT.format(this.date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getDate() {
        return date;
    }
}
