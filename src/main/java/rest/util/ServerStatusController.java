package rest.util;


import com.google.gson.Gson;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mqtt.MQTTManager;

@Path("/server")
public abstract class ServerStatusController {
	
	public ServerStatusController(){
	}

	
    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus() {
    	
    	boolean connected = MQTTManager.getInstance().isConnected();
        String statusJson = new Gson().toJson(connected);

        return Response.ok(statusJson).build();
    }
    
    

}
