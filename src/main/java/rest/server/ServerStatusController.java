package rest.server;


import com.google.gson.Gson;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mqtt.MQTTtoSQLite;

@Path("/server")
public abstract class ServerStatusController {
	
	public ServerStatusController(MQTTtoSQLite mqtt){
		this.mqtt = mqtt;
	}
	
	MQTTtoSQLite mqtt;

	public boolean getStatusMQTT(){
		return mqtt.getClient().isConnected();
	}
	
	
    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus() {
    	
    	boolean connected = getStatusMQTT();
    	String status = connected ? "CONNECTED" : "DISCONNECTED";
        System.out.println(connected + "SEE HERE");
        // Erstellt eine JSON-Struktur
        String statusJson = new Gson().toJson(connected);

        // Gibt die JSON-Struktur als Antwort zurück
        return Response.ok(statusJson).build();
    }
    
    

}
