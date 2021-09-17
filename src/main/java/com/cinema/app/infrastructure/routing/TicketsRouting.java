package com.cinema.app.infrastructure.routing;


import com.cinema.app.application.service.TicketsService;
import com.cinema.app.application.service.exception.ScreeningsServiceException;
import com.cinema.app.application.service.exception.TicketsServiceException;
import com.cinema.app.domain.screening.dto.CreateUpdateScreeningDto;
import com.cinema.app.domain.ticket.dto.CreateTicketDto;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Locale;

import static com.cinema.app.infrastructure.routing.dto.ResponseDto.toResponse;
import static spark.Spark.*;
import static spark.Spark.delete;

@RequiredArgsConstructor
@Controller

public class TicketsRouting {

    private final TicketsService ticketsService;
    private final Gson gson;
    private final JsonTransformer jsonTransformer;

    public void routes() {

        exception(TicketsServiceException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });

        path("/tickets", () -> {

            path("/management",() -> {


                put("/status/:id/:status",
                        (request, response) -> {
                            var id = Long.valueOf(request.params(":id"));
                            var newStatus = Status.valueOf(request.params(":status").toUpperCase(Locale.ROOT));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return toResponse(ticketsService.updateTicketsStatus(id,newStatus));
                        }, jsonTransformer);

                delete("/:id",
                        (request, response) -> {
                            var id = Long.valueOf(request.params(":id"));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return toResponse(ticketsService.deleteTicket(id));
                        }, jsonTransformer
                );



            });

            post("",
                    (request, response) -> {
                        var createTicketsDto = gson.fromJson(request.body(), CreateTicketDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return toResponse(ticketsService.createTickets(createTicketsDto));
                    }
                    , jsonTransformer);

            get("/bookings/:id",(request, response) -> {
                var id = Long.valueOf(request.params(":id"));
                response.header("Content-Type", "application/json;charset=utf-8");
                return toResponse(ticketsService.mapSeatsOfScreening(id));
            },jsonTransformer);

        });


    }

}
