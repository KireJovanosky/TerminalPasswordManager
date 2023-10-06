package com.passManager.entity;

public class PasswordEntity {

    private Integer id;
    private String site;
    private String title;
    private Integer strong;
    private String password;

    public PasswordEntity(String title, String site, Integer strong) {
        this.title = title;
        this.site = site;
        this.strong = strong;
    }

    public PasswordEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer isStrong() {
        return strong;
    }

    public void setStrong(Integer strong) {
        this.strong = strong;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
