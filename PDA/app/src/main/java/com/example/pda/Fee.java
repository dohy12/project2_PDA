package com.example.pda;

import java.time.LocalDate;

public class Fee {
    private boolean isPayed;
    private boolean isValid;
    private String feeTitle;
    private String feeDate;
    private int feeAmount;
    public int P_ID;

    public Fee(boolean isPayed, boolean isValid, String feeTitle, String feeDate, int feeAmount){
        this.isPayed = isPayed;
        this.isValid = isValid;
        this.feeTitle = feeTitle;
        this.feeDate = feeDate;
        this.feeAmount = feeAmount;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getFeeTitle() {
        return feeTitle;
    }

    public String getFeeDate() {
        return feeDate;
    }

    public int getFeeAmount() {
        return feeAmount;
    }

    public int getP_ID() {
        return P_ID;
    }
}
