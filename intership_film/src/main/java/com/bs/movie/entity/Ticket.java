package com.bs.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ticket {
    Integer tid;
    String mname;
    String uname;
    String date;
    String hname;
    String starttime;
    Float prize;
    String genre;

}
