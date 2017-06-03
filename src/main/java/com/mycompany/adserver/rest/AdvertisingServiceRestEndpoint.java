package com.mycompany.adserver.rest;

import com.mycompany.adserver.dtos.AdvertisingDto;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/advertisingService")
public interface AdvertisingServiceRestEndpoint {

    @GET
    @Path("/{advertisingSpaceId}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    AdvertisingDto get(@PathParam("advertisingSpaceId")Long id,
                       @QueryParam("day")Integer dayOfTheWeek,
                       @QueryParam("hour")Integer hourOfTheDay,
                       @QueryParam("andCondition") Boolean and,
                       @CookieParam("cookieParam") String cookieParam, @Context HttpHeaders headers);



}
