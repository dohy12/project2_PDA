package com.example.pda;

import java.io.Serializable;

public class Board_Image implements Serializable {
    int I_ID;
    String image_src;
    int B_ID;

    public Board_Image(int I_ID, String image_src, int B_ID) {
        this.I_ID = I_ID;
        this.image_src = image_src;
        this.B_ID = B_ID;
    }

    public int getI_ID() { return I_ID; }
    public void setI_ID(int I_ID) { this.I_ID = I_ID; }

    public String getImageSrc() { return image_src; }
    public void setImageSrc(String image_src) { this.image_src = image_src; }

    public int getB_ID() { return B_ID; }
    public void setB_ID(int B_ID) { this.B_ID = B_ID; };
}
