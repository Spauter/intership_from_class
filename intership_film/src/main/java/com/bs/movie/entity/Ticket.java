package com.bs.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.sql.Date;

@Data
//@AllArgsConstructor
public class Ticket {
    Integer tid;
    String mname;
    String uname;
    String date;
    String hname;
    String starttime;
    Float prize;
    String genre;

    public Ticket(Integer tid, String mname, String uname, String date, String hname, String starttime, Float prize, String genre) {
        this.tid = tid;
        this.mname = mname;
        this.uname = uname;
        this.date = date;
        this.hname = hname;
        this.starttime = starttime;
        this.prize = prize;
        this.genre = genre;
    }
}
