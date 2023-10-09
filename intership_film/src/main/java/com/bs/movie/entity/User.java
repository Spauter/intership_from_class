package com.bs.movie.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User{
    String uid;
    String uname;
    String email;
    String pwd;
    String status;
}
