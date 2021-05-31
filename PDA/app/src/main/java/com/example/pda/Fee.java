package com.example.pda;

import java.time.LocalDate;

public class Fee {
    private boolean isPayed;
    private String feeTitle;
    private LocalDate feeDate;
    private int feeAmount;

    public Fee(boolean isPayed, String feeTitle, LocalDate feeDate, int feeAmount){
        this.isPayed = isPayed;
        this.feeTitle = feeTitle;
        this.feeDate = feeDate;
        this.feeAmount = feeAmount;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public String getFeeTitle() {
        return feeTitle;
    }

    public LocalDate getFeeDate() {
        return feeDate;
    }

    public int getFeeAmount() {
        return feeAmount;
    }
}
