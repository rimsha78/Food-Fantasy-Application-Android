


package com.example.food_fantasy.ui.dishes;

        import java.lang.Object;
        import java.lang.String;

public class Dishes_template {
    int dish_img;
    String dish_title;
    String dish_des;
    String dish_id;

    public Dishes_template(int dish_img, String dish_title,String dish_des){
        this.dish_img = dish_img;
        this.dish_title=dish_title;
        this.dish_des=dish_des;
    }

    public Dishes_template(String dish_id, String dish_title,String dish_des){
        this.dish_img = 0;
        this.dish_id = dish_id;
        this.dish_title=dish_title;
        this.dish_des=dish_des;
    }

    public int getDish_img() {
        return dish_img;
    }

    public void setDish_img(int logo) {
        this.dish_img = dish_img;
    }

    public String getDish_title() {
        return dish_title;
    }

    public void setDish_title(String dish_title) {
        this.dish_title = dish_title;
    }



    public String getDish_des() {
        return dish_des;
    }
    public String getDish_id() {
        return dish_id;
    }

    public void setDish_des(String dish_des) {
        this.dish_title = dish_des;
    }


}