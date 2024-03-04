package rest.server;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mqtt.MQTTtoSQLite;

@Path("/server")
public class ServerStatusController {
	
	public ServerStatusController(MQTTtoSQLite mqtt){
		connected = mqtt.getClient().isConnected();
	}
	
	boolean connected;

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus() {

        

        Map<String, Boolean> status = new HashMap<>();
        status.put("connected", connected);

        return Response.ok(new Gson().toJson(status)).build();
    }
}
