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

    protected ScreeningInfoDaoImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<ScreeningInfo> findByKeyword(String keyword) {
        var sql = """
                select s.id as id, c.name as cinemaName, a.street as street, a.house_number as houseNumber, a.city as city, cr.name as cinemaRoomName, m.title as movieTitle, m.length as length, s.date_time as dateTime
                from addresses AS a 
                join cinemas AS c on a.id = c.address_id 
                join cinema_rooms AS cr on c.id = cr.cinema_id
                join screenings AS s on cr.id = s.cinema_room_id 
                join movies AS m on m.id = s.movie_id 
                where c.name like :keyword or a.city like :keyword or a.street like :keyword or a.city like :keyword or cr.name like 
                :keyword or m.title like :keyword
                """;

        return jdbi.withHandle(handle -> handle
                .createQuery(sql))
                .bind("keyword",keyword)
                .mapToBean(ScreeningInfo.class)
                .list();
    }
}
