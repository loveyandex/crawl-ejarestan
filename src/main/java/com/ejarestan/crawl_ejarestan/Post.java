package com.ejarestan.crawl_ejarestan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;
 
public class Post {



       private long pk; // This will be your primary key field

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }


    private String phoneNumber; // New field to store phone number
    private String type; // New field to store phone number



    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    private String url;
    private String title;
    private String timeElapsed;
    private String description;
    private String category;
    private String location;
    private String srcImg; // New attribute for image source URL

    // Getters and setters (omitted for brevity)

    // Convert Post object to JSON document
    public Document toDocument() {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(this);
        return Document.parse(json);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(String timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(String srcImg) {
        this.srcImg = srcImg;
    }
}
