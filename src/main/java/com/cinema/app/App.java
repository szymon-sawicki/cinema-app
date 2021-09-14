package com.cinema.app;

import com.cinema.app.infrastructure.configs.AppSpringConfig;
import com.cinema.app.infrastructure.routing.RoutingInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * main class, where routing is initialized
 * @Author Szymon Sawicki
 */

public class App {
    public static void main(String[] args) {

        // ROUTING

        var context = new AnnotationConfigApplicationContext(AppSpringConfig.class);

        var routing = context.getBean("routingInitializer", RoutingInitializer.class);

        routing.init();


    }


}
