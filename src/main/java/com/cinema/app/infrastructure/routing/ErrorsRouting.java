package com.cinema.app.infrastructure.routing;

import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.cinema.app.infrastructure.routing.dto.ResponseDto;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static spark.Spark.*;


@RequiredArgsConstructor
@Controller

public class ErrorsRouting {

    private final Gson gson;


    public void routes() {


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
