package com.android.ql.restaurant.data;

public class TableBean {

    private String tableNum;
    private String fontNum;
    private boolean isSelected;

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public String getFontNum() {
        return fontNum;
    }

    public void setFontNum(String fontNum) {
        this.fontNum = fontNum;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
