package com.android.ql.restaurant.data;

public class TicketBean {

    public static enum TicketStatus {
        ORDERING(1, "正在排队"), COMPLEMENT(2, "已完成"), CANCEL(3, "已取消");
        public int status;
        public String des;

        TicketStatus(int status, String des) {
            this.status = status;
            this.des = des;
        }

        public static String getDesByStatus(int status) {
            switch (status) {
                case 1:
                    return "正在排队";
                case 2:
                    return "已完成";
                case 3:
                    return "已取消";
                default:
                    return "";
            }
        }
    }

    private long ticket_id;
    private String ticket_shop;
    private String ticket_table;
    private String ticket_uid;
    private String ticket_letter;
    private int ticket_number;
    private String ticket_dates;
    private int ticket_is_state;
    private String ticket_is_show;
    private String ticket_times;
    private String ticket_letter_key;
    private int ticket_cou; // 用餐人数
    private String ticket_shop_name;
    private int ticket_count;

    public int getTicket_count() {
        return ticket_count;
    }

    public void setTicket_count(int ticket_count) {
        this.ticket_count = ticket_count;
    }

    public long getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(long ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_shop() {
        return ticket_shop;
    }

    public void setTicket_shop(String ticket_shop) {
        this.ticket_shop = ticket_shop;
    }

    public String getTicket_table() {
        return ticket_table;
    }

    public void setTicket_table(String ticket_table) {
        this.ticket_table = ticket_table;
    }

    public String getTicket_uid() {
        return ticket_uid;
    }

    public void setTicket_uid(String ticket_uid) {
        this.ticket_uid = ticket_uid;
    }

    public String getTicket_letter() {
        return ticket_letter;
    }

    public void setTicket_letter(String ticket_letter) {
        this.ticket_letter = ticket_letter;
    }

    public int getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(int ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getTicket_dates() {
        return ticket_dates;
    }

    public void setTicket_dates(String ticket_dates) {
        this.ticket_dates = ticket_dates;
    }


    public int getTicket_is_state() {
        return ticket_is_state;
    }

    public void setTicket_is_state(int ticket_is_state) {
        this.ticket_is_state = ticket_is_state;
    }

    public String getTicket_is_show() {
        return ticket_is_show;
    }

    public void setTicket_is_show(String ticket_is_show) {
        this.ticket_is_show = ticket_is_show;
    }

    public String getTicket_times() {
        return ticket_times;
    }

    public void setTicket_times(String ticket_times) {
        this.ticket_times = ticket_times;
    }

    public String getTicket_letter_key() {
        return ticket_letter_key;
    }

    public void setTicket_letter_key(String ticket_letter_key) {
        this.ticket_letter_key = ticket_letter_key;
    }

    public int getTicket_cou() {
        return ticket_cou;
    }

    public void setTicket_cou(int ticket_cou) {
        this.ticket_cou = ticket_cou;
    }

    public String getTicket_shop_name() {
        return ticket_shop_name;
    }

    public void setTicket_shop_name(String ticket_shop_name) {
        this.ticket_shop_name = ticket_shop_name;
    }
}
