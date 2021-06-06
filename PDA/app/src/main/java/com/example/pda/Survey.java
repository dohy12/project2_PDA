package com.example.pda;

public class Survey {
    private int select; //뭐 골랐는지  -1:셀렉트 안함
    private String surveyTitle;
    private String[] surveyList;
    private int[] surveyList_count;

    public Survey(int select, String surveyTitle, String[] surveyList, int[] surveyList_count){
        this.select = select;
        this.surveyTitle = surveyTitle;
        this.surveyList = surveyList;
        this.surveyList_count = surveyList_count;
    }

    public int getSelect() {
        return select;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public String[] getSurveyList() {
        return surveyList;
    }

    public int[] getSurveyList_count() {
        return surveyList_count;
    }
}
