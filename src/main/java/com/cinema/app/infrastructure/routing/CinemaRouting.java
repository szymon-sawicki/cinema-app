package com.cinema.app.infrastructure.routing;

import com.cinema.app.application.service.CinemasService;
import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.cinema.app.infrastructure.routing.dto.ResponseDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static spark.Spark.*;

@RequiredArgsConstructor
@Controller

public class CinemaRouting {


    private final CinemasService cinemasService;
    private final Gson gson;


    public void routes() {


        path("/cinemas", () -> {

                    post("",
                            (request, response) -> {
                                var createCinemaDto = gson.fromJson(request.body(), CreateUpdateCinemaDto.class);
                                //response.header("Content-Type", "application/json;charset=utf-8");
                               // return ResponseDto.toResponse(cinemasService.addCinema(createCinemaDto));
                                return cinemasService.addCinema(createCinemaDto);
                            }, new JsonTransformer());

                    get("/city/:city",
                            ((request, response) -> {
                                var city = request.params(":city");
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return ResponseDto.toResponse(cinemasService.findByCity(city));
                            }
                            ), new JsonTransformer());

                    get("/name/:name",
                            ((request, response) -> {
                                var name = request.params(":name");
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return ResponseDto.toResponse(cinemasService.findByName(name));
                            }),
                            new JsonTransformer());



                });



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
                        return gson.toJson(responseBody);
                    }, new JsonTransformer()
            );
        });

        internalServerError((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            var responseBody = ResponseDto.toError("Unknown internal server error");
            return gson.toJson(responseBody);
        });

        notFound((request, response) -> {
            response.header("Content-type", "application/json;charset=utf-8");
            response.status(404);
            var responseBody = ResponseDto.toError("Not found");
            return gson.toJson(responseBody);
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

        /*
         * exception handling
         */







