package com.bs.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
    Integer mid;
    String mname;
    String director;
    String genre;
    Float rate_num;
    Integer runtime;
    String pubdate;
    String starts;
    Integer votes;
    String summary;
    String image;

}
