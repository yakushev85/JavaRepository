package org.olia.demo.ws.demosoap;

import javax.jws.*;

@WebService
public class OliaService {
	private static final String PARAM_ID = "id";
	private static final String PARAM_TICKET = "ticket";
	
	public OliaService() {}
	
	@WebMethod
	public OliaTicket info(@WebParam(name=PARAM_ID) String id) {
		OliaTicket ticket = new OliaTicket();
		ticket.setId(id);
		ticket.setNum(100500);
		ticket.setName("Some Problem with Data");
		ticket.setDescription("Here some description!");
		ticket.setPriority("High");
		
		return ticket;
	}
	
	@WebMethod
	public String create(@WebParam(name=PARAM_TICKET) OliaTicket ticket) {
		return ticket.toString();
	}
}
