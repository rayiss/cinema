package org.rayis.cinema.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data	@AllArgsConstructor	@NoArgsConstructor
public class Ville implements Serializable{
	   
	   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	   private String nom;
	   private double longitude;
	   private double latitude;
	   private double altitude;
	   @OneToMany(mappedBy="ville")
	   private java.util.Collection<Cinema> cinemas;
}
