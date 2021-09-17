package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.infrastructure.persistence.dao.ScreeningInfoDao;
import com.cinema.app.infrastructure.persistence.entity.view.ScreeningInfo;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public class ScreeningInfoDaoImpl extends AbstractCrudDao<ScreeningInfo, Long> implements ScreeningInfoDao {

    protected ScreeningInfoDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    private final String selectAndJoinExpression = """
                select s.id as id, c.name as cinemaName, a.street as street, a.house_number as houseNumber, a.city as city, cr.name as cinemaRoomName, m.title as movieTitle, m.length as length, s.date_time as dateTime
                from addresses a 
                join cinemas c on a.id = c.address_id 
                join cinema_rooms cr on c.id = cr.cinema_id
                join screenings s on cr.id = s.cinema_room_id 
                join movies m on m.id = s.movie_id 
            """;


    // TODO how to include wildcard ??

    @Override
    public List<ScreeningInfo> findByKeyword(String keyword) {
        var sql = selectAndJoinExpression + """
                where c.name like :keyword or a.city like :keyword or a.street like :keyword or c.name like :keyword or cr.name like 
                :keyword or m.title like :keyword
                """;

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("keyword", keyword)
                .mapToBean(ScreeningInfo.class)
                .list());
    }

    @Override
    public List<ScreeningInfo> findByCinema(Long cinemaId) {

        var sql = selectAndJoinExpression + "where c.id = :cinemaId";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("cinemaId", cinemaId)
                .mapToBean(ScreeningInfo.class)
                .list());
    }

    @Override
    public List<ScreeningInfo> findByMovie(Long movieId) {

        var sql = selectAndJoinExpression + "where m.id = :movieId";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("movieId", movieId)
                .mapToBean(ScreeningInfo.class)
                .list());
    }

    @Override
    public List<ScreeningInfo> findByDate(LocalDate date) {

        var sql = selectAndJoinExpression + "where DATE(date_time) = :date";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("date", date)
                .mapToBean(ScreeningInfo.class)
                .list());
    }

    @Override
    public List<ScreeningInfo> findAll() {

        return jdbi.withHandle(handle -> handle
                .createQuery(selectAndJoinExpression)
                .mapToBean(ScreeningInfo.class)
                .list());
    }

}
