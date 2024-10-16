package com.flipfit.app;

import com.flipfit.controller.AdminController;
import com.flipfit.controller.CustomerController;
import com.flipfit.controller.GymOwnerController;
import com.flipfit.service.FlipFitAdminServiceOperations;
import com.flipfit.service.FlipFitGymOwnerServiceOperations;
import com.flipfit.service.FlipFitUserServiceOperations;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<Configuration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<Configuration> b) {
    }

    @Override
    public void run(Configuration c, Environment e) throws Exception {
        LOGGER.info("Registering REST resources");


        System.out.println("HERE");

        e.jersey().register(new CustomerController(new FlipFitUserServiceOperations()));
       e.jersey().register(new GymOwnerController(new FlipFitGymOwnerServiceOperations()));
       e.jersey().register(new AdminController(new FlipFitAdminServiceOperations()));
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}