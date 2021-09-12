package com.cinema.app.infrastructure.routing;

import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.cinema.app.infrastructure.routing.dto.ResponseDto;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static com.cinema.app.infrastructure.routing.dto.ResponseDto.*;
import static spark.Spark.*;


@RequiredArgsConstructor
@Controller

public class ErrorsRouting {

    private final Gson gson;
    private final JsonTransformer jsonTransformer;

    public void routes() {


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
