package com.cinema.app;

import com.cinema.app.domain.address.Address;
import com.cinema.app.infrastructure.configs.AppSpringConfig;
import com.cinema.app.infrastructure.persistence.impl.AddressDaoImpl;
import org.eclipse.jetty.client.Origin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext(AppSpringConfig.class);

        var addressDao = context.getBean("addressDaoImpl", AddressDaoImpl.class);

         var address = Address.builder()
            .city("Gniezno")
            .houseNumber("26A")
            .zipCode("62-200")
            .build();

        System.out.println(addressDao.save(address));

        System.out.println(addressDao.findAll());

        // TODO wyszukiwanie wszystkich kin z miasta za pomoca dao ? Trzeba zrobic join i specjalny obiekt?


    }

}
