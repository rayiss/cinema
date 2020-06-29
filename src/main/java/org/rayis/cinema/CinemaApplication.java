package org.rayis.cinema;

import org.rayis.cinema.entities.Film;
import org.rayis.cinema.entities.Salle;
import org.rayis.cinema.entities.Ticket;
import org.rayis.cinema.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner, WebMvcConfigurer {
	
	@Autowired
	private ICinemaInitService cinemaInitService;
	@Autowired
	private RepositoryRestConfiguration restConfiguration; //Inclure le Id dans la reponse Rest
	
	public static void main(String[] args){
		SpringApplication.run(CinemaApplication.class, args);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/resources/**")
        .addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/images/**")
        .addResourceLocations("classpath:/static/");
	}
	
	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
		cinemaInitService.initVilles();
		cinemaInitService.initCinemas();
		cinemaInitService.initSalles();
		cinemaInitService.initPlaces();
		cinemaInitService.initSeances();
		cinemaInitService.initCategories();
		cinemaInitService.initFilms();
		cinemaInitService.initProjections();
		cinemaInitService.initTickets();
		
	}

}
