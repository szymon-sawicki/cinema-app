package com.cinema.app.domain.movie;

import com.cinema.app.domain.movie.Movie;

import java.util.function.Function;

public interface MovieUtils {

    Function<Movie,Long> toId = movie -> movie.id;

}
