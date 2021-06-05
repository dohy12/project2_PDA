package com.example.pda;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class PayReqInfos {
    private String GroupId;
    private int P_ID;
    private String buyer_name;

    public PayReqInfos(String GroupId, int P_ID, String buyer_name) {
        this.GroupId = GroupId;
        this.P_ID = P_ID;
        this.buyer_name = buyer_name;
    }
}