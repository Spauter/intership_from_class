package com.yc.entity;

public class Film {
    private Integer fid;
    private String fname;
    private String actor;
    private String director;
    private Float price;
    private String fpic;
    private  Integer tid;
    //电影类型
    private FilmType filmType;

    @Override
    public String toString() {
        return "Film{" +
                "fid=" + fid +
                ", fname='" + fname + '\'' +
                ", actor='" + actor + '\'' +
                ", director='" + director + '\'' +
                ", price=" + price +
                ", fpic='" + fpic + '\'' +
                ", tid=" + tid +
                ", filmType=" + filmType +
                '}';
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }



    public Film(String fname,   String actor, String director, float v, String s, FilmType filmType) {
        this.fname=fname;
        this.actor=actor;
        this.director=director;
        this.price=v;
        this.fpic=s;
        this.filmType=filmType;
    }

    public Film() {

    }


    public void setFilmType(Integer tid,String tname) {
        this.filmType.setTid(tid);
        this.filmType.setTname(tname);
    }

   public FilmType getFilmType(){
        return filmType;
   }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getFpic() {
        return fpic;
    }

    public void setFpic(String fpic) {
        this.fpic = fpic;
    }


    public void setFilmType(FilmType filmType) {
        this.filmType=filmType;
    }
}
