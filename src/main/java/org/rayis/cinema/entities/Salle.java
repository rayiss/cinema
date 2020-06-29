package org.rayis.cinema.entities;

import java.io.Serializable;

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
@Data	@NoArgsConstructor	@AllArgsConstructor
public class Salle implements Serializable{
	   
	   @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	   private Long id;
	   private String nom;
	   private int nombrePlaces;
	   @ManyToOne()
	 //Quand je consulte les salles ce n'est pas la peine de me donner leur cinema
	   //Pour cela j'utilise l'annotation @JsonProperty
	   @JsonProperty(access=Access.WRITE_ONLY) //pris en considération seulement en écriture
	   private Cinema cinema;
	   @OneToMany(mappedBy="salle")
	 //Quand je consulte la salle ce n'est pas la peine de me donner ses places
	   //Pour cela j'utilise l'annotation @JsonProperty
	   @JsonProperty(access=Access.WRITE_ONLY) //pris en considération seulement en écriture
	   public java.util.Collection<Place> places;
	   @OneToMany(mappedBy="salle")
	 //Quand je consulte la salle ce n'est pas la peine de me donner ses projections
	   //Pour cela j'utilise l'annotation @JsonProperty
	   @JsonProperty(access=Access.WRITE_ONLY) //pris en considération seulement en écriture
	   public java.util.Collection<Projection> projections;

}
