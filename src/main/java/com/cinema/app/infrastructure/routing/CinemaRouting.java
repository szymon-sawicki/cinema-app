package com.cinema.app.infrastructure.routing;

import com.cinema.app.application.service.CinemasService;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import static spark.Spark.*;

@RequiredArgsConstructor


public class CinemaRouting {


    private final CinemasService cinemasService;
    private final Gson gson;


    public void routes() {

        // TODO jak udokumentować API

        path("/cinemas", () -> {

                    post("",
                            (request, response) -> {
                                var createCinemaDto = gson.fromJson(request.body(), CreateUpdateCinemaDto.class);
                                return cinemasService.addCinema(createCinemaDto);
                            }, new JsonTransformer());

                });
    }
}

/*

                    get("/body_type/:type",
                            (request, response) -> {
                                var bodyType = CarBodyType.valueOf(request.params(":type").toUpperCase(Locale.ROOT));
                                var priceFrom = new BigDecimal(request.queryParams("price_from"));
                                var priceTo = new BigDecimal(request.queryParams("price_to"));
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return ResponseDto.toResponse(carsService.findCarsWithBodyTypeAndPriceInRange(bodyType, priceFrom, priceTo));
                            },
                            new JsonTransformer());
*/

        /**
         * exception handling
         */

/*
        exception(CinemaServiceException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });

        path("/error/", () -> {
            get("/:message",
                    (request, response) -> {
                        var message = request.params(":message");
                        response.header("Content-type", "application/json;charset=utf-8");
                        response.status(500);
                        var responseBody = ResponseDto.toError(message);
                        return toJson(responseBody);
                    }, new JsonTransformer()
            );
        });

        internalServerError((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            var responseBody = ResponseDto.toError("Unknown internal server error");
            return toJson(responseBody);
        });

        notFound((request, response) -> {
            response.header("Content-type", "application/json;charset=utf-8");
            response.status(404);
            var responseBody = ResponseDto.toError("Not found");
            return toJson(responseBody);
        });

    }


    private static <T> String toJson(T data) {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(data);

    }

*/

