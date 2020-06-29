package org.rayis.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="ticketProj", types = {Ticket.class})
public interface ProjectionTickets {
	public Long getId();
	public String getNomClient();
	public double getPrix();
	public Integer getCodePayement();
	public boolean getReservee();
	public Place getPlace();
	
}
