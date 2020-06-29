package org.rayis.cinema.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.rayis.cinema.dao.FilmRepository;
import org.rayis.cinema.dao.TicketRepository;
import org.rayis.cinema.entities.Film;
import org.rayis.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

//Création d'une API rest en utilisant un Rest Controller: il est plutôt conseillé d'utiliser Spring Data Rest en utilisant
// l'annotation @RepositoryRestResource EX: voir classe FilmRepository
@RestController
@CrossOrigin("http://localhost:4200") //Autorise les pages de ce domaine à envoyer des requêtes au backend (ou mettre * pour tout domaine)
public class CinemaRestController {
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	@GetMapping("/listFilms")
	public List<Film> films(){
		return filmRepository.findAll();//Retourne la liste des films au format JSON
	}
	//Consulter une image
	//Il faut par défaut mettre produces=MediaType.IMAGE_JPEG_VALUE sinon ça sera envoyé au format JSON
	@GetMapping(path="/images/{id}",produces=MediaType.IMAGE_JPEG_VALUE)
	public byte[] images(@PathVariable(name="id")Long id) throws Exception{
		Film film = filmRepository.findById(id).get();
		String nomPhoto = film.getPhoto();
		//On récupère le fichier image sans utiliser d'adresse absolu
		// l'adresse est le répertoire de l'utilisateur courant (user.home)
		File photo = new File(System.getProperty("user.home")+"/cinema/images/"+nomPhoto+".jpg");
//		File photo = new File("src/main/resources/static/images/"+nomPhoto+".jpg");
//		System.out.println(new ClassPathResource("Voici le lien de image :"+"images/"+nomPhoto+".jpg"));
		Path path = Paths.get(photo.toURI());
		return Files.readAllBytes(path);
	}
	//Méthode permettant de payer un ticket
	@PostMapping("/payerTickets")
	@Transactional
	//Comme les tickets sont issus des données de la requête, on utilise l'annotation @RequestBody
	// ces données sont envoyées dans le corps de la requête au format JSON
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
		List<Ticket> ticketsVendus = new ArrayList<Ticket>();
		ticketForm.getIdTickets().forEach(idTicket->{
			Ticket ticket = ticketRepository.findById(idTicket).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setReservee(true);
			ticket.setCodePayement(ticketForm.getCodePayement());
			ticketRepository.save(ticket);
			ticketsVendus.add(ticket);
		});
		return ticketsVendus;
	}
}
@Data
class TicketForm{
	private String nomClient;
	private int codePayement;
	private List<Long> idTickets = new ArrayList<>();
}
