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


    public void routes() {


        exception(ScreeningsServiceException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });


        path("/screenings", () -> {

            post("",
                    (request, response) -> {
                        var createScreeningDto = gson.fromJson(request.body(), CreateUpdateScreeningDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(screeningsService.createScreeening(createScreeningDto));
                    }
                    , new JsonTransformer());

            get("/:keyword",
                    ((request, response) -> {
                        var keyword = request.params(":keyword");
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(screeningsService.findByKeyword(keyword));
                    }
                    ), new JsonTransformer());

            get("/date/:date",
                    (request, response) -> {
                        var date = LocalDate.parse(request.params(":date"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(screeningsService.findByDate(date));
                    }, new JsonTransformer());

            put("/:id",
                    (request, response) -> {
                        var screeningToUpdate = gson.fromJson(request.body(), CreateUpdateScreeningDto.class);
                        var id = Long.valueOf(request.params(":id"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(screeningsService.updateScreening(id, screeningToUpdate));
                    }, new JsonTransformer());

            delete("/:id",
                    (request, response) -> {
                        var id = Long.valueOf(request.params(":id"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(screeningsService.deleteScreening(id));
                    }, new JsonTransformer()
            );
        });
    }

}
