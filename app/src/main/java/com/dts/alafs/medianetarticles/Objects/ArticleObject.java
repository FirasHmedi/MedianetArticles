package com.dts.alafs.medianetarticles.Objects;

import org.json.JSONArray;
import org.json.JSONException;

public class ArticleObject {

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
    private String[] comments;
    private int commentaire;



    public int getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(int commentaire) {
        this.commentaire = commentaire;
    }

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

    public String[] getComments() { return comments; }
    public void setComments(JSONArray comments) throws JSONException {

        this.comments = new String[comments.length()];
        for (int i = 0; i < comments.length(); i++) {
            this.comments[i] = String.valueOf(comments.get(i));
        }
    }

    public String toString(){
        return "article object : "+id+" "+title+" "+likes;
    }


}
