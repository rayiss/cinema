package org.rayis.cinema.entities;

import java.io.Serializable;
import java.util.Date;

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
import lombok.ToString;

@Entity
@Data	@AllArgsConstructor		@NoArgsConstructor	
public class Film implements Serializable{
	   
	   @Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	   private Long id;
	   private String titre;
	   private double duree;
	   private String realisateur;
	   private String description;
	   private Date dateSortie;
	   private String photo;
	   @OneToMany(mappedBy="film")
	   //Quand je consulte les films ce n'est pas la peine de me donner ses projections
	   //Pour cela j'utilise l'annotation @JsonProperty
	   @JsonProperty(access=Access.WRITE_ONLY) //pris en considération seulement en écriture
	   private java.util.Collection<Projection> projections;
	   @ManyToOne
	   private Categorie categorie;
}
