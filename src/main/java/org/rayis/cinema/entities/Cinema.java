package org.rayis.cinema.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor 
public class Cinema implements Serializable{
	   
	   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	   private String nom;
	   private double longitude;
	   private double latitude;
	   private double altitude;
	   private int nombreSalle;
	   @OneToMany(mappedBy="cinema")
	   private java.util.Collection<Salle> salles;
	   @ManyToOne
	   private Ville ville;
}
