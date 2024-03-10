package rest.util;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/server")
public class ServerController {

    @GET
    @Path("/database/connect")
    public Response connectDatabase() {
        // Logik zum Verbinden mit der Datenbank
        return Response.ok("Datenbank verbunden").build();
    }

    @POST
    @Path("/database/disconnect")
    public Response disconnectDatabase() {
        // Logik zum Trennen der Datenbankverbindung
        return Response.ok("Datenbank getrennt").build();
    }

    @POST
    @Path("/mqtt/connect")
    public Response connectMQTT() {
        // Logik zum Verbinden mit MQTT
        return Response.ok("MQTT verbunden").build();
    }

    @POST
    @Path("/mqtt/disconnect")
    public Response disconnectMQTT() {
        // Logik zum Trennen der MQTT-Verbindung
        return Response.ok("MQTT getrennt").build();
    }
}

