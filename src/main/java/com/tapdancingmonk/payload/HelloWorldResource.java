package com.tapdancingmonk.payload;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.inject.servlet.RequestScoped;

@Path("/")
public class HelloWorldResource {

    @RequestScoped
    @GET
    @Produces("text/plain")
    public String helloWorld() {
        return "Hello World, We Are Guiced!";
    }
}
