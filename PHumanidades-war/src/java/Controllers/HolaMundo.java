/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.sun.xml.ws.config.metro.parser.jsr109.ResAuthType;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hugo
 */
@Path("/saludar")
public class HolaMundo {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hola() {
        JsonObject mensaje = Json.createObjectBuilder()
                .add("mensaje", "Hola mundo")
                .add("version", "1.0")
                .add("status", "Exito")
                .build();
        return Response.ok(mensaje).build();
    }
}
