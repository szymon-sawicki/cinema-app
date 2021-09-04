package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.infrastructure.persistence.dao.ScreeningInfoDao;
import com.cinema.app.infrastructure.persistence.entity.view.ScreeningInfo;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScreeningInfoDaoImpl implements ScreeningInfoDao {

    private final Jdbi jdbi;

    private String selectAndJoinExpression = """
                select s.id as id, c.name as cinemaName, a.street as street, a.house_number as houseNumber, a.city as city, cr.name as cinemaRoomName, m.title as movieTitle, m.length as length, s.date_time as dateTime
                from addresses a 
                join cinemas c on a.id = c.address_id 
                join cinema_rooms cr on c.id = cr.cinema_id
                join screenings s on cr.id = s.cinema_room_id 
                join movies m on m.id = s.movie_id 
            """;

    protected ScreeningInfoDaoImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<ScreeningInfo> findByKeyword(String keyword) {
        var sql = selectAndJoinExpression + """
                where c.name like :keyword or a.city like :keyword or a.street like :keyword or a.city like :keyword or cr.name like 
                :keyword or m.title like :keyword
                """;

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("keyword",keyword)
                .mapToBean(ScreeningInfo.class)
                .list());
    }

    @Override
    public List<ScreeningInfo> findByCinema(Long cinemaId) {

        var sql = selectAndJoinExpression + "where c.id = :cinemaId";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("cinemaId",cinemaId)
                .mapToBean(ScreeningInfo.class)
                .list());
    }

    @Override
    public List<ScreeningInfo> findByMovie(Long movieId) {

        var sql = selectAndJoinExpression + "where m.id = :movieId";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("movieId",movieId)
                .mapToBean(ScreeningInfo.class)
                .list());
    }
}
