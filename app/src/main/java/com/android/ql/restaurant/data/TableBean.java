package com.android.ql.restaurant.data;

public class TableBean {

    private boolean isSelected;

    private long table_id;
    private long table_shop;
    private String table_name;
    private String table_intro;
    private int table_count;
    private String table_max;
    private String table_min;

    public long getTable_id() {
        return table_id;
    }

    public void setTable_id(long table_id) {
        this.table_id = table_id;
    }

    public long getTable_shop() {
        return table_shop;
    }

    public void setTable_shop(long table_shop) {
        this.table_shop = table_shop;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_intro() {
        return table_intro;
    }

    public void setTable_intro(String table_intro) {
        this.table_intro = table_intro;
    }

    public int getTable_count() {
        return table_count;
    }

    public void setTable_count(int table_count) {
        this.table_count = table_count;
    }

    public String getTable_max() {
        return table_max;
    }

    public void setTable_max(String table_max) {
        this.table_max = table_max;
    }

    public String getTable_min() {
        return table_min;
    }

    public void setTable_min(String table_min) {
        this.table_min = table_min;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
