package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.MoviesServiceException;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.dto.CreateMovieDto;
import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.movie.dto.validator.CreateMovieDtoValidator;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.dao.MovieEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MoviesService {

    private final MovieEntityDao movieEntityDao;

    public GetMovieDto addMovie(CreateMovieDto createMovieDto)   {
        Validator.validate(new CreateMovieDtoValidator(),createMovieDto);

        if(movieEntityDao.findByTitle(createMovieDto.getTitle()).isPresent()) {
            throw new MoviesServiceException("movie with title " + createMovieDto.getTitle() + " is already present in database");
        }

        var movie = createMovieDto.toMovie().toEntity();

        return movieEntityDao
                .save(movie)
                .orElseThrow(() -> new MoviesServiceException("cannot add movie"))
                .toMovie()
                .toGetMovieDto();
    }

    public GetMovieDto findByTitle(String title) {
        if (title == null) {
            throw new MoviesServiceException("title is null");
        }
        if(!title.matches("[\\w\\s\\-']{3,30}+")) {
            throw new MoviesServiceException("title have wrong format");
        }
        return  movieEntityDao
                .findByTitle(title)
                .orElseThrow(() -> new MoviesServiceException("cannot find movie with title: " + title))
                .toMovie()
                .toGetMovieDto();
    }

    public List<GetMovieDto> findByGenre(MovieGenre movieGenre) {
        if(movieGenre == null) {
            throw new MoviesServiceException("movie genre is null");
        }

        return movieEntityDao.findMoviesByGenre(movieGenre)
                .stream()
                .map(movieEntity -> movieEntity.toMovie().toGetMovieDto())
                .toList();
    }



}
