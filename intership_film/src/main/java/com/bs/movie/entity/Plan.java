package com.bs.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;


@Data
@AllArgsConstructor
public class Plan {
    Integer pid;
    String hname;
    String mname;
    String starttime;
    String date;
    Float prize;
    String capatity;
}
