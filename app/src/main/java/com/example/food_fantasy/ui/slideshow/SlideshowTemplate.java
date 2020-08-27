package com.example.food_fantasy.ui.slideshow;

public class SlideshowTemplate {


    String order_id;
    String date;
    String total_amount;

    public SlideshowTemplate(String order_id, String date,String total_amount){
        this.order_id = order_id;
        this.date=date;
        this.total_amount=total_amount;
    }

    public String getOrder_id() {
        return order_id ;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDate_id() {
        return date ;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_amount() {
        return total_amount ;
    }
    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

}
