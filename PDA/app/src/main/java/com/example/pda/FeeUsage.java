package com.example.pda;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FeeUsage {
    private String valid_date;
    private String detail;
    private int amount;
    private int remained;

    public FeeUsage(String valid_date, String detail, int amount, int remained){
        this.valid_date = valid_date;
        this.detail = detail;
        this.amount = amount;
        this.remained = remained;
    }

    public String getValidDate() {
        return valid_date;
    }

    public String getDetail() {
        return detail;
    }

    public int getAmount() {
        return amount;
    }

    public int getRemained() {
        return remained;
    }
}


