package org.rayis.cinema.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data	@AllArgsConstructor @NoArgsConstructor
public class Place {
		
	   @Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	   private int numero;
	   private double longitude;
	   private double latitude;
	   private double altitude;
	   @ManyToOne
	   private Salle salle;
	   @OneToMany(mappedBy="place")
		 //Quand je consulte les Places ce n'est pas la peine de me donner ses tickets
		   //Pour cela j'utilise l'annotation @JsonProperty
	   @JsonProperty(access=Access.WRITE_ONLY) //pris en considération seulement en écriture
	   private java.util.Collection<Ticket> tickets;
}
