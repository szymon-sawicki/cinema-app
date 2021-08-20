package com.cinema.app.infrastructure.configs;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan("com.cinema.app")
@PropertySource("classpath:application.properties")

public class AppSpringConfig {

    @Value("${jdbi.url}")
    private String jdbiUrl;

    @Value("${jdbi.username}")
    private String jdbiUsername;

    @Value("${jdbi.password}")
    private String jdbiPassword;

    @Bean
    public Jdbi jdbi() {

        Jdbi jdbi = Jdbi.create(jdbiUrl, jdbiUsername, jdbiPassword);
       var addressesTableSql = """
                create table if not exists addresses (
                    id integer primary key auto_increment,
                    street varchar(50) not null,
                    house_number varchar(6) not null,
                    city varchar(20) not null,
                    zip_code varchar(6)  not null
                );
                """;

        var cinemasTableSql = """
                create table if not exists cinemas (
                    id integer primary key auto_increment,
                    name varchar(20) not null,
                    address_id integer not null,
                    foreign key (address_id) references addresses (id) on delete cascade on update cascade
                );
                """;

        var cinemaRoomsTableSql = """
                create table if not exists cinema_rooms (
                    id integer primary key auto_increment,
                    name varchar(20) not null,
                    rows_number integer not null,
                    place_number integer not null,
                    cinema_id integer not null,
                    foreign key (cinema_id) references cinemas (id) on delete cascade on update cascade
                );
                """;

        var seatsTableSql = """                
                create table if not exists seats (
                    id integer primary key auto_increment,
                    row_num   integer not null,
                    place integer not null,
                    seat_type varchar(8),
                    cinema_room_id integer not null,
                    foreign key (cinema_room_id) references cinema_rooms(id) on delete cascade on update cascade
                  );
                  """;

/*        var addressesTableSql = """
                create table if not exists addresses (
                    id integer primary key auto_increment,
                    street varchar(50) not null,
                    houseNumber varchar(6) not null,
                    city varchar(20) not null,
                    zipCode varchar(6)  not null
                );
                """;

        var cinemasTableSql = """
                create table if not exists cinemas (
                    id integer primary key auto_increment,
                    name varchar(20) not null,
                    addressId integer not null,
                    foreign key (addressId) references addresses (id) on delete cascade on update cascade
                );
                """;

        var cinemaRoomsTableSql = """
                create table if not exists cinema_rooms (
                    id integer primary key auto_increment,
                    name varchar(20) not null,
                    rowsNum integer not null,
                    placeNumber integer not null,
                    cinemaId integer not null,
                    foreign key (cinemaId) references cinemas (id) on delete cascade on update cascade
                );
                """;

        var seatsTableSql = """                
                create table if not exists seats (
                    id integer primary key auto_increment,
                    rowNum integer not null,
                    place integer not null,
                    seatType varchar(8),
                    cinemaRoomId integer not null,
                    foreign key (cinemaRoomId) references cinema_rooms(id) on delete cascade on update cascade
                  );
                  """;*/



        jdbi.useHandle(handle -> handle.execute(addressesTableSql));
        jdbi.useHandle(handle -> handle.execute(cinemasTableSql));
        jdbi.useHandle(handle -> handle.execute(cinemaRoomsTableSql));
        jdbi.useHandle(handle -> handle.execute(seatsTableSql));



        return jdbi;

    }
}
