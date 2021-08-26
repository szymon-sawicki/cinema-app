package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.MoviesServiceException;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.dto.CreateMovieDto;
import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.movie.dto.validator.CreateMovieDtoValidator;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.MovieDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MoviesService {

    private final MovieDao movieDao;

    public GetMovieDto addMovie(CreateMovieDto createMovieDto)   {
        Validator.validate(new CreateMovieDtoValidator(),createMovieDto);

        if(movieDao.findByName(createMovieDto.getName()).isPresent()) {
            throw new MoviesServiceException("movie with name " + createMovieDto.getName() + " is already present in database");
        }

        var movie = createMovieDto.toMovie();

        return movieDao
                .save(movie)
                .orElseThrow(() -> new MoviesServiceException("cannot add movie"))
                .toGetMovieDto();
    }

    public GetMovieDto findByName(String name) {
        if (name == null) {
            throw new MoviesServiceException("name is null");
        }
        if(!name.matches("[\\w\\s\\-']{3,30}+")) {
            throw new MoviesServiceException("name have wrong format");
        }
        return  movieDao
                .findByName(name)
                .orElseThrow(() -> new MoviesServiceException("cannot find movie with name: " + name))
                .toGetMovieDto();
    }

    public List<GetMovieDto> findByGenre(MovieGenre movieGenre) {
        if(movieGenre == null) {
            throw new MoviesServiceException("movie genre is null");
        }

        return movieDao.findMoviesByGenre(movieGenre)
                .stream()
                .map(Movie::toGetMovieDto)
                .toList();
    }



}
