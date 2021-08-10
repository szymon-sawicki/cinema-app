package com.cinema.app;

import com.cinema.app.domain.address.Address;
import com.cinema.app.infrastructure.configs.AppSpringConfig;
import com.cinema.app.infrastructure.persistence.CinemaRoomDao;
import com.cinema.app.infrastructure.persistence.impl.AddressDaoImpl;
import com.cinema.app.infrastructure.persistence.impl.CinemaDaoImpl;
import com.cinema.app.infrastructure.persistence.impl.CinemaRoomDaoImpl;
import com.cinema.app.infrastructure.persistence.impl.SeatDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext(AppSpringConfig.class);

        var addressDao = context.getBean("addressDaoImpl", AddressDaoImpl.class);
/*        var cinemaDao = context.getBean("cinemaDaoImpl", CinemaDaoImpl.class);
        var cinemaRoomDao = context.getBean("cinemaRoomDaoImpl", CinemaRoomDaoImpl.class);
        var seatDao = context.getBean("seatDaoImpl", SeatDaoImpl.class);*/

        var address = Address.builder()
            .city("Gniezno")
            .houseNumber("26A")
            .zipCode("62-200")
            .build();


       System.out.println(addressDao.save(address));




    }

}
