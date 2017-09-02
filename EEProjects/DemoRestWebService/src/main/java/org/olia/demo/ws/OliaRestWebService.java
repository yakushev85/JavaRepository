package org.olia.demo.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/ticket")
public class OliaRestWebService {
	
	@Path("/info/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response info(@PathParam("id") String id) {
		JSONObject ticket = new JSONObject();
		ticket.put("ID", id);
		ticket.put("Number", 100500);
		ticket.put("Name", "Some Problem with Data");
		ticket.put("Description", "Here some description!");
		ticket.put("Priority", "High");
		
		return Response.status(200).entity(ticket.toString()).build();
	}
	
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(OliaTicket ticket) {
		return Response.status(200).entity(ticket.toString()).build();
	}
}
