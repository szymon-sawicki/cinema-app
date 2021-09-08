package com.cinema.app.infrastructure.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Bean
    public Jdbi jdbi() {

        Jdbi jdbi = Jdbi.create(jdbiUrl, jdbiUsername, jdbiPassword);

        var moviesTableSql = """
                  create table if not exists movies (
                  id integer primary key auto_increment,
                  title varchar(40) not null ,
                  movie_genre varchar(15) not null,
                  premiere_date date not null,
                  length integer not null
                  );               
                """;

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
                    name varchar(50) not null,
                    address_id integer not null,
                    foreign key (address_id) references addresses (id) on delete cascade on update cascade
                );
                """;

        var cinemaRoomsTableSql = """
                create table if not exists cinema_rooms (
                    id integer primary key auto_increment,
                    name varchar(50) not null,
                    rows_num integer not null,
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

        var screeningsTableSql = """
                create table if not exists screenings (
                    id integer primary key auto_increment,
                    movie_id integer not null,
                    cinema_room_id integer not null,
                    date_time datetime not null,
                    foreign key (movie_id) references movies(id) on delete cascade on update cascade,
                    foreign key (cinema_room_id) references cinema_rooms(id) on delete cascade on update cascade
                );
                """;

        var ticketsTableSql = """
                create table if not exists tickets (
                    id integer primary key auto_increment,
                    screening_id integer not null,
                    user_id integer not null,
                    seat_id integer not null,
                    price decimal not null,
                    discount integer,
                    status varchar(15),
                    foreign key (screening_id) references screenings(id) on delete cascade on update cascade,
                    foreign key (user_id) references users(id) on delete cascade on update cascade,
                    foreign key (seat_id) references seats(id) on delete cascade on update cascade
                );
                """;

        var usersTableSql = """
                create table if not exists users (
                id integer primary key auto_increment,
                username varchar(20) not null,
                password varchar(100) not null,
                name varchar(30) not null,
                mail varchar(50) not null,
                role varchar(10) not null,
                birth_date date,
                creation_date date not null,
                gender varchar(15))
                """;

        jdbi.useHandle(handle -> handle.execute(moviesTableSql));
        jdbi.useHandle(handle -> handle.execute(addressesTableSql));
        jdbi.useHandle(handle -> handle.execute(cinemasTableSql));
        jdbi.useHandle(handle -> handle.execute(cinemaRoomsTableSql));
        jdbi.useHandle(handle -> handle.execute(seatsTableSql));
        jdbi.useHandle(handle -> handle.execute(screeningsTableSql));
        jdbi.useHandle(handle -> handle.execute(usersTableSql));
        jdbi.useHandle(handle -> handle.execute(ticketsTableSql));



        return jdbi;

    }
}
