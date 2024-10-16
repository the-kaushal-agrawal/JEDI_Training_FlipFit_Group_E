package com.flipfit.controller;

import com.flipfit.model.*;
import com.flipfit.service.*;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.validation.Validator;

import java.util.List;

@Path("/gymOwner")
@Produces(MediaType.APPLICATION_JSON)
public class GymOwnerController {

    
    FlipFitGymOwnerService gymOwnerService;

    public GymOwnerController(FlipFitGymOwnerServiceOperations gymOwnerService) {

        this.gymOwnerService = gymOwnerService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response login(Credentials credentials){
        if(credentials == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if(gymOwnerService.validateGymOwner(credentials.getUser(),credentials.getPassword())){
            return Response.ok().build();
        }
        else return Response.status(Response.Status.UNAUTHORIZED).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addgymwithslots")
    public Response addGymWithSlots(FlipFitGym gym){
        System.out.println(gym.getSlots()+" Kk");
        gymOwnerService.addGym(gym);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createGymOwner(FlipFitGymOwner gymOwner){
        gymOwnerService.createGymOwner(gymOwner);
        return Response.ok().build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/viewGyms/{id}")
    public List<FlipFitGym> viewMyGyms(@PathParam("id") int id) {

        List<FlipFitGym> myGyms = gymOwnerService.viewMyGyms(id);
        return myGyms;

    }
}