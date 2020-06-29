package org.rayis.cinema.dao;

import org.rayis.cinema.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
//toutes les méthodes seront accessibles via une API REST
@RepositoryRestResource
@CrossOrigin("http://localhost:4200")
public interface PlaceRepository extends JpaRepository<Place, Long>{

}
