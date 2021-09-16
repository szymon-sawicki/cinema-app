package com.cinema.app.infrastructure.routing;

import com.cinema.app.SampleDataLoader;
import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import spark.Spark;

import static com.cinema.app.infrastructure.routing.dto.ResponseDto.toError;
import static com.cinema.app.infrastructure.routing.dto.ResponseDto.toResponse;
import static spark.Spark.*;


@RequiredArgsConstructor
@Controller

/**
 * class responsible for handling exceptions, starting Spark and initializing all controllers
 * @Author Szymon Sawicki
 */

public class RoutingInitializer {

    private final Gson gson;
    private final JsonTransformer jsonTransformer;

    private final CinemaRouting cinemaRouting;
    private final MoviesRouting moviesRouting;
    private final ScreeningRouting screeningRouting;
    private final TicketsRouting ticketsRouting;
    private final UsersRouting usersRouting;

    private final SampleDataLoader sampleDataLoader;

    public void init() {

        Spark.initExceptionHandler(e -> System.out.println(e.getMessage()));



        Spark.port(8000);

        ticketsRouting.routes();
        cinemaRouting.routes();
        screeningRouting.routes();
        moviesRouting.routes();
        usersRouting.routes();

        exception(ValidatorException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });

        path("/initializer", () -> {
            get("",
                    (request, response) -> {
                        response.header("Content-type", "application/json;charset=utf-8");
                        return toResponse(sampleDataLoader.loadSampleData());
                    }, jsonTransformer
            );
        });

        path("/error/", () -> {
            get("/:message",
                    (request, response) -> {
                        var message = request.params(":message");
                        response.header("Content-type", "application/json;charset=utf-8");
                        response.status(500);
                        return toError(message);
                    }, jsonTransformer
            );
        });

        internalServerError((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            return toError("Unknown internal server error");
        });

        notFound((request, response) -> {
            response.header("Content-type", "application/json;charset=utf-8");
            response.status(404);
            return toError("Not found");
        });


    }
}
