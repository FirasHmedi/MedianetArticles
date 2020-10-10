package com.dts.alafs.medianetarticles.model;

public class Profile {

    private int id;
    private String username;
    private String email;
    private String image;

    public Profile(int id, String username, String email, String image) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }



}
