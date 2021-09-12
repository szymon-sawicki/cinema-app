package com.cinema.app.infrastructure.routing;

import com.cinema.app.application.service.UsersService;
import com.cinema.app.application.service.exception.UsersServiceException;
import com.cinema.app.domain.user.dto.CreateUpdateUserDto;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static com.cinema.app.infrastructure.routing.dto.ResponseDto.toResponse;
import static spark.Spark.*;

@RequiredArgsConstructor
@Controller

public class UsersRouting {

    private final UsersService usersService;
    private final Gson gson;
    private final JsonTransformer jsonTransformer;

    public void routes() {

        exception(UsersServiceException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });

        path("/users", () -> {

            post("",
                    (request, response) -> {
                        var createUpdateUserDto = gson.fromJson(request.body(), CreateUpdateUserDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(usersService.createUser(createUpdateUserDto));
                    },jsonTransformer);

            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(usersService.findAll());
                    }, jsonTransformer);

            put("/:id",
                    (request, response) -> {
                        var id = Long.valueOf(request.params(":id"));
                        var userToUpdate = gson.fromJson(request.body(), CreateUpdateUserDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(usersService.updateUser(id, userToUpdate));
                    }, jsonTransformer);

            delete("/:id",
                    (request, response) -> {
                        var id = Long.valueOf(request.params(":id"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(usersService.deleteUser(id));
                    });

            get("/username/:username",
                    (request, response) -> {
                var username = request.params(":username");
                response.header("Content-Type", "application/json;charset=utf-8");
                return toResponse(usersService.findByUsername(username));
            },jsonTransformer);

            get("/mail/:mail",
                    (request, response) -> {
                var mail = request.params(":mail");
                response.header("Content-Type", "application/json;charset=utf-8");
                return toResponse(usersService.findByMail(mail));
            },jsonTransformer);

        });

    }

}
