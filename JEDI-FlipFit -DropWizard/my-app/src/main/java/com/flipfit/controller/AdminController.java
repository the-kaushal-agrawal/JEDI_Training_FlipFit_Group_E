package com.flipfit.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.validation.Validator;

import com.flipfit.model.*;
import com.flipfit.service.*;

import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {


    private Validator validator;
    private FlipFitAdminService adminService;

    public AdminController(FlipFitAdminServiceOperations adminService){

        this.adminService = adminService;
    }


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials){

        String admin_id = "kaushal.agrawal@flipfit.com";
        String admin_password = "kaushal";

        if (credentials.getUser().equals(admin_id) && credentials.getPassword().equals(admin_password)){
            System.out.println("Admin logged in");
            return Response.ok().build();
        }
        else
            return Response.status(Response.Status.UNAUTHORIZED).build();

    }

    @GET
    @Path("/viewGymOwners")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FlipFitGymOwner> viewGymOwners(){

        List<FlipFitGymOwner> gymOwnerList =  adminService.viewGymOwners();

        return gymOwnerList;

    }


    @GET
    @Path("/viewGyms")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FlipFitGym> viewGyms(){
        List<FlipFitGym> gyms =  adminService.viewGyms();

        return gyms;
    }

    @GET
    @Path("/viewUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FlipFitUser> viewUsers(){
        List<FlipFitUser> users = adminService.viewUsers();
        return users;
    }

    @PUT
    @Path("/verifyGym/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyGym(@PathParam("id") int id){
       Boolean res =  adminService.verifyGym(id);
        return Response.ok(res).build();
    }

    @PUT
    @Path("/verifyGymOwner/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyGymOwner(@PathParam("id") int id){
        Boolean res = adminService.verifyGymOwner(id);
        return Response.ok(res).build();
    }

    @GET
    @Path("/getUnverifiedGyms")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FlipFitGym> getUnverifiedGyms(){
        List<FlipFitGym> gym = adminService.getUnverifiedGyms();
        return gym;
    }

}
