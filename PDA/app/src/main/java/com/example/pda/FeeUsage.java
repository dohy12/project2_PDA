package com.example.pda;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FeeUsage {
    private LocalDate useDate;
    private String detail;
    private int amount;
    private int remained;

    public FeeUsage(LocalDate useDate, String detail, int amount, int remained){
        this.useDate = useDate;
        this.detail = detail;
        this.amount = amount;
        this.remained = remained;
    }

    public LocalDate getUseDate() {
        return useDate;
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


