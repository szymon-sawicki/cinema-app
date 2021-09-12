package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.MoviesServiceException;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;
import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.movie.dto.validator.CreateUpdateMovieDtoValidator;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.dao.MovieEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

/**
 * service class used to manage movies
 * @Author Szymon Sawicki
 */

public class MoviesService {

    private final MovieEntityDao movieEntityDao;

    /**
     * method creating new movie
     * @param createUpdateMovieDto movie to create
     * @return inserted movie
     */

    public GetMovieDto addMovie(CreateUpdateMovieDto createUpdateMovieDto)   {
        Validator.validate(new CreateUpdateMovieDtoValidator(), createUpdateMovieDto);

        if(movieEntityDao.findByTitle(createUpdateMovieDto.getTitle()).isPresent()) {
            throw new MoviesServiceException("movie with title " + createUpdateMovieDto.getTitle() + " is already present in database");
        }

        var movie = createUpdateMovieDto.toMovie().toEntity();

        return movieEntityDao
                .save(movie)
                .orElseThrow(() -> new MoviesServiceException("cannot add movie"))
                .toMovie()
                .toGetMovieDto();
    }

    /**
     * searching movie by title
     * @param title movie's title to search
     * @return movie with given title or empty optional
     */

    public GetMovieDto findByTitle(String title) {
        if (title == null) {
            throw new MoviesServiceException("title is null");
        }
        if(!title.matches("[\\w\\s\\-'.,]{2,50}+")) {
            throw new MoviesServiceException("title have wrong format");
        }
        return  movieEntityDao
                .findByTitle(title)
                .orElseThrow(() -> new MoviesServiceException("cannot find movie with title: " + title))
                .toMovie()
                .toGetMovieDto();
    }

    /**
     * finding all movies
     * @return list with all movies
     */

    public List<GetMovieDto> findAll() {
        return movieEntityDao.findAll()
                .stream()
                .map(movieEntity -> movieEntity.toMovie().toGetMovieDto())
                .toList();
    }

    /**
     * searching movies by genre
     * @param movieGenre genre to be searched
     * @return list with movies of given genre
     */

    public List<GetMovieDto> findByGenre(MovieGenre movieGenre) {
        if(movieGenre == null) {
            throw new MoviesServiceException("movie genre is null");
        }

        return movieEntityDao.findMoviesByGenre(movieGenre)
                .stream()
                .map(movieEntity -> movieEntity.toMovie().toGetMovieDto())
                .toList();
    }

    /**
     * method deleting movie
     * @param id movie to delete
     * @return deleted movie
     */

    public GetMovieDto deleteMovie(Long id) {
        if(id == null) {
            throw new MoviesServiceException("id is null");
        }
        if(id <= 0) {
            throw new MoviesServiceException("id is 0 or negative");
        }

        return movieEntityDao.deleteById(id)
                .orElseThrow(() -> new MoviesServiceException("movie cannot be deleted"))
                .toMovie()
                .toGetMovieDto();
    }

    /**
     * method updating movie
     * @param id movie to update
     * @param createUpdateMovieDto updating data
     * @return updated movie
     */

    public GetMovieDto updateMovie(Long id, CreateUpdateMovieDto createUpdateMovieDto) {
        Validator.validate(new CreateUpdateMovieDtoValidator(),createUpdateMovieDto);

        if(id == null) {
            throw new MoviesServiceException("id is null");
        }
        if(id <= 0) {
            throw new MoviesServiceException("id is 0 or negative");
        }

        var movieToUpdate = createUpdateMovieDto.toMovie().toEntity();

        return movieEntityDao.update(id,movieToUpdate)
                .orElseThrow(() -> new MoviesServiceException("cannot update movie"))
                .toMovie()
                .toGetMovieDto();
    }



}
