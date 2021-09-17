package com.cinema.app.infrastructure.routing;

import com.cinema.app.application.service.MoviesService;
import com.cinema.app.application.service.exception.MoviesServiceException;
import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Locale;

import static com.cinema.app.infrastructure.routing.dto.ResponseDto.*;
import static spark.Spark.*;

@RequiredArgsConstructor
@Controller

public class MoviesRouting {

    private final MoviesService moviesService;
    private final Gson gson;
    private final JsonTransformer jsonTransformer;

    public void routes() {


        exception(MoviesServiceException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });

        path("/movies", () -> {

            path("/management",() -> {
                post("",
                        (request, response) -> {
                            var createMovieDto = gson.fromJson(request.body(), CreateUpdateMovieDto.class);
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return toResponse(moviesService.addMovie(createMovieDto));
                        },jsonTransformer);

                delete("/:id",
                        (request, response) -> {
                            var id = Long.valueOf(request.params(":id"));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return toResponse(moviesService.deleteMovie(id));
                        });

                put("/:id",
                        (request, response) -> {
                            var id = Long.valueOf(request.params(":id"));
                            var createUpdateDto = gson.fromJson(request.body(),CreateUpdateMovieDto.class);
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return toResponse(moviesService.updateMovie(id,createUpdateDto));
                        }, jsonTransformer);

            });

            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(moviesService.findAll());
                    }, jsonTransformer);

            get("/:title",
                    (request, response) -> {
                var title = request.params(":title");
                response.header("Content-Type", "application/json;charset=utf-8");
                return toResponse(moviesService.findByTitle(title));
            }, jsonTransformer);

            get("/genre/:genre",
                    (request, response) -> {
                var genre = MovieGenre.valueOf(request.params(":genre").toUpperCase(Locale.ROOT));
                response.header("Content-Type", "application/json;charset=utf-8");
                return toResponse(moviesService.findByGenre(genre));
            },jsonTransformer);

        });


    }

}
