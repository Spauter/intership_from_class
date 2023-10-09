package com.bs.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hall {
    Integer hid;
    String hname;
    Integer capacity;

}
