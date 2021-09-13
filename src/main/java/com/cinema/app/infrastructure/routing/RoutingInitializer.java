package com.cinema.app.infrastructure.routing;

import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.domain.user.User;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.util.DateCache;
import org.springframework.stereotype.Controller;
import spark.Spark;

import static com.cinema.app.infrastructure.routing.dto.ResponseDto.toError;
import static spark.Spark.*;


@RequiredArgsConstructor
@Controller

public class RoutingInitializer {

    private final Gson gson;
    private final JsonTransformer jsonTransformer;
    private final CinemaRouting cinemaRouting;
    private final MoviesRouting moviesRouting;
    private final ScreeningRouting screeningRouting;
    private final TicketsRouting ticketsRouting;
    private final UsersRouting usersRouting;


    public void init() {

        Spark.initExceptionHandler(e -> System.out.println(e.getMessage()));

        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

        Spark.port(8000);

        ticketsRouting.routes();
        cinemaRouting.routes();
        screeningRouting.routes();
        moviesRouting.routes();
        usersRouting.routes();

        exception(ValidatorException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });

        path("/error/", () -> {
            get("/:message",
                    (request, response) -> {
                        var message = request.params(":message");
                        response.header("Content-type", "application/json;charset=utf-8");
                        response.status(500);
                        var responseBody = toError(message);
                        return toError(gson.toJson(responseBody));
                    }, jsonTransformer
            );
        });

        internalServerError((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            var responseBody = toError("Unknown internal server error");
            return toError(gson.toJson(gson.toJson(responseBody)));
        });

        notFound((request, response) -> {
            response.header("Content-type", "application/json;charset=utf-8");
            response.status(404);
            var responseBody = toError("Not found");
            // return gson.toJson(responseBody);
            return toError(gson.toJson(responseBody));
        });


    }
}
