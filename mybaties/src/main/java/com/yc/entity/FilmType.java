package com.yc.entity;

import java.util.List;

public class FilmType {
    private Integer tid;
    private String  tname;
    private List<Film> filmList;
    private FilmType filmType;

    public void setFilmType(FilmType filmType) {
        this.filmType.setTid(tid);
        this.filmType.setTname(tname);
        this.filmType = filmType;
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
    }

    public Integer getTid(){
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @Override
    public String toString() {
        return "FilmType{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                '}';
    }

}
