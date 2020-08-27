package com.example.food_fantasy.ui.dashboard_baker;

import java.lang.Object;
import java.lang.String;

public class Dashboard_template {
    int logo;
    String caption;

    public Dashboard_template(int logo, String caption){
        this.logo = logo;
        this.caption = caption;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
