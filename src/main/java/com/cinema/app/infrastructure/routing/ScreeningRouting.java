package com.cinema.app.infrastructure.routing;

import com.cinema.app.application.service.CinemasService;
import com.cinema.app.application.service.ScreeningsService;
import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.domain.screening.dto.CreateUpdateScreeningDto;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.cinema.app.infrastructure.routing.dto.ResponseDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static spark.Spark.*;
import static spark.route.HttpMethod.delete;

@RequiredArgsConstructor
@Controller

public class ScreeningRouting {


    private final ScreeningsService screeningsService;
    private final Gson gson;


    public void routes() {


        path("/screenings", () -> {

            post("",
                    (request, response) -> {
                        var createScreeningDto = gson.fromJson(request.body(), CreateUpdateScreeningDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        // return ResponseDto.toResponse(screeningsService.createScreeening(createScreeningDto));
                        return screeningsService.createScreeening(createScreeningDto);
                    }
                    , new JsonTransformer());

            get("/:keyword",
                    ((request, response) -> {
                        var keyword = request.params(":keyword");
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return ResponseDto.toResponse(screeningsService.findByKeyword(keyword));
                    }
                    ), new JsonTransformer());

            get("/date/:date",
                    (request, response) -> {
                        var date = LocalDate.parse(request.params(":date"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return ResponseDto.toResponse(screeningsService.findByDate(date));
                    }, new JsonTransformer());

            put("/:id",
                    (request, response) -> {
                        var screeningToUpdate = gson.fromJson(request.body(), CreateUpdateScreeningDto.class);
                        var id = Long.valueOf(request.params(":id"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return ResponseDto.toResponse(screeningsService.updateScreening(id, screeningToUpdate));
                    }, new JsonTransformer());

            delete("/:id",
                    (request, response) -> {
                        var id = Long.valueOf(request.params(":id"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return ResponseDto.toResponse(screeningsService.deleteScreening(id));
                    }, new JsonTransformer()
            );



            /*
            get("/name/:name",
                    ((request, response) -> {
                        var name = request.params(":name");
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return ResponseDto.toResponse(cinemasService.findByName(name));
                    }
                    ));

*/

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







