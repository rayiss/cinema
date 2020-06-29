package org.rayis.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.rayis.cinema.dao.CategorieRepository;
import org.rayis.cinema.dao.CinemaRepository;
import org.rayis.cinema.dao.FilmRepository;
import org.rayis.cinema.dao.PlaceRepository;
import org.rayis.cinema.dao.ProjectionRepository;
import org.rayis.cinema.dao.SalleRepository;
import org.rayis.cinema.dao.SeanceRepository;
import org.rayis.cinema.dao.TicketRepository;
import org.rayis.cinema.dao.VilleRepository;
import org.rayis.cinema.entities.Categorie;
import org.rayis.cinema.entities.Cinema;
import org.rayis.cinema.entities.Film;
import org.rayis.cinema.entities.Place;
import org.rayis.cinema.entities.Projection;
import org.rayis.cinema.entities.Salle;
import org.rayis.cinema.entities.Seance;
import org.rayis.cinema.entities.Ticket;
import org.rayis.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Pour la couche métier on utilise l'annotation service
@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public void initVilles() {
		Stream.of("Yaoundé","Bamenda","Bertoua","Mbandjock","Obala").forEach(nomVille->{
			Ville ville = new Ville();
			ville.setNom(nomVille);
			villeRepository.save(ville);
		});
		
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(ville->{
			Stream.of("CanalOlympia","iMAX","MagaRama","FOUNOUN","MediaScreen").forEach(nomCinema->{
				Cinema cinema = new Cinema();
				cinema.setNom(nomCinema);
				cinema.setNombreSalle(3+(int)(Math.random()*7));
				cinema.setVille(ville);
				cinemaRepository.save(cinema);
			});
		});
		
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema->{
			for (int i = 0; i < cinema.getNombreSalle(); i++) {
				Salle salle = new Salle();
				salle.setNom("Salle "+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlaces(15+(int)(Math.random()*20));
				salleRepository.save(salle);
			}
		});
		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{
			for (int i = 0; i < salle.getNombrePlaces(); i++) {
				Place place = new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});
		
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","14:30","16:25","19:00","21:00").forEach(heureDebut->{
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(heureDebut));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void initCategories() {
		Stream.of("Histoire","Action","Fiction","Drame").forEach(nomCat->{
			Categorie categorie = new Categorie();
			categorie.setNom(nomCat);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initFilms() {
		double[] durees = new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("Game of Thrones","Kaamelott","Stranger Things","Walking Dead","Breaking Bad","Friends").forEach(titreFilm->{
			Film film = new Film();
			film.setTitre(titreFilm);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(titreFilm.replaceAll(" ", ""));
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
		
	}

	@Override
	public void initProjections() {
		double[] prix = new double[] {30,50,60,70,90,100};
		List<Film> films = filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int index = new Random().nextInt(films.size());
					Film film = films.get(index);
						seanceRepository.findAll().forEach(seance->{
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prix[new Random().nextInt(prix.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionRepository.save(projection);
						});
					
				});
			});
		});
		
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(projection->{
			projection.getSalle().getPlaces().forEach(place->{
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjection(projection);
				ticket.setReservee(false);
				ticketRepository.save(ticket);
			});
		});
		
	}

}
