package com.dts.alafs.medianetarticles.model;

import java.util.Arrays;

public class Article {

    private int id;
    private String title;
    private String summary;
    private String content;
    private String authorNom;
    private String categTitle;
    private String[] ImgArr;
    private String vidId;
    private String PodUrl;
    private int likes;
    private String date;
    private int commentsNbr;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCategorieTitle() {
        return categTitle;
    }
    public void setCategorieTitle(String categorieTitle) {
        this.categTitle = categorieTitle;
    }

    public String getAuthorNom() {
        return authorNom;
    }
    public void setAuthorNom(String authorNom) {
        this.authorNom = authorNom;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getImgArr() {
        return ImgArr;
    }
    public void setImgArr(String[] array) {

        this.ImgArr = new String[array.length];
            for (int i = 0; i < array.length; i++) {
            this.ImgArr[i] = array[i];
        }

    }
    public String getPodUrl() {
        return PodUrl;
    }
    public void setPodUrl(String podUrl) {
        PodUrl = podUrl;
    }

    public String getVidId() {
        return vidId;
    }
    public void setVidId(String vidId) {
        vidId = vidId;
    }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCommentsNbr() {
        return commentsNbr;
    }

    public void setCommentsNbr(int commentsNbr) {
        this.commentsNbr = commentsNbr;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", title:'" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", authorNom='" + authorNom + '\'' +
                ", categTitle='" + categTitle + '\'' +
                ", ImgArr=" + Arrays.toString(ImgArr) +
                ", vidId='" + vidId + '\'' +
                ", PodUrl='" + PodUrl + '\'' +
                ", likes=" + likes +
                ", date='" + date + '\'' +
                ", commentsNbr=" + commentsNbr +
                '}';
    }
}
