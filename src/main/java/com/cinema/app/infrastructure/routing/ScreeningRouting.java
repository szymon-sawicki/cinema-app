package com.cinema.app.infrastructure.routing;

import com.cinema.app.application.service.ScreeningsService;
import com.cinema.app.application.service.exception.ScreeningsServiceException;
import com.cinema.app.domain.screening.dto.CreateUpdateScreeningDto;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

import static com.cinema.app.infrastructure.routing.dto.ResponseDto.toResponse;
import static spark.Spark.*;

@RequiredArgsConstructor
@Controller

public class ScreeningRouting {


    private final ScreeningsService screeningsService;
    private final Gson gson;
    private final JsonTransformer jsonTransformer;


    public void routes() {


        exception(ScreeningsServiceException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });


        path("/screenings", () -> {

            path("/management",()-> {

                post("",
                        (request, response) -> {
                            var createScreeningDto = gson.fromJson(request.body(), CreateUpdateScreeningDto.class);
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return toResponse(screeningsService.createScreeening(createScreeningDto));
                        }, jsonTransformer);

                put("/:id",
                        (request, response) -> {
                            var screeningToUpdate = gson.fromJson(request.body(), CreateUpdateScreeningDto.class);
                            var id = Long.valueOf(request.params(":id"));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return toResponse(screeningsService.updateScreening(id, screeningToUpdate));
                        }, jsonTransformer);

                delete("/:id",
                        (request, response) -> {
                            var id = Long.valueOf(request.params(":id"));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return toResponse(screeningsService.deleteScreening(id));
                        }, jsonTransformer);


            });


            get("/:keyword",
                    ((request, response) -> {
                        var keyword = request.params(":keyword");
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(screeningsService.findByKeyword(keyword));
                    }
                    ), jsonTransformer);

            get("/date/:date",
                    (request, response) -> {
                        var date = LocalDate.parse(request.params(":date"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(screeningsService.findByDate(date));
                    }, jsonTransformer);

            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(screeningsService.findAll());
                    }, jsonTransformer);
        });
    }

}
