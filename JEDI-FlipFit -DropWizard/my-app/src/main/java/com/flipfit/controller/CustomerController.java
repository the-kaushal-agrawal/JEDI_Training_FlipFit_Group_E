package com.flipfit.controller;


import com.flipfit.model.*;
import com.flipfit.service.*;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/user")
public class CustomerController {


    FlipFitUserService userServices;

    public CustomerController(FlipFitUserServiceOperations userServices) {
        this.userServices = userServices;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(Credentials credentials){

        if(userServices.validateUser(credentials.getUser(),credentials.getPassword())){
            return Response.ok("Login Successful").build();
        }
        else return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/viewAllGymswithSlots")
    public List<FlipFitGym> viewAllGymswithSlots(){
        List<FlipFitGym> gymList = userServices.viewAllGymsWithSlots();
        return gymList;

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/bookSlot/gymId/{id}/time/{time}/email/{email}")
    public Response bookSlot(@PathParam("id") Integer gymId, @PathParam("time") Integer time,@PathParam("email") String email){
        System.out.println(gymId + " " + time + " " + email);
        boolean booked = userServices.bookSlot(gymId, time, email);
        if(booked)
            return Response.ok().build();
        else {
            System.out.println("Booking Unsuccessful!!");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cancelSlot/{id}")
    public Response cancelSlot(@PathParam("id") Integer id){
        boolean booked = userServices.cancelSlot(id);
        if(booked)
            return Response.ok().build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllBookings/{id}")
    public List<FlipFitBookings> getAllBookings(@PathParam("id") Integer id){
        List<FlipFitBookings> myBookings = userServices.viewAllBookings(id);

        return myBookings;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createCustomer(FlipFitUser user){
        userServices.createUser(user);
        return Response.ok().build();
    }

}