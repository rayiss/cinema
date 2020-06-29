package org.rayis.cinema.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity	
@Data	@AllArgsConstructor	@NoArgsConstructor	
public class Categorie {
	   @Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	   private Long id;
	   @Column(length=75)
	   private String nom;
	   @OneToMany(mappedBy="categorie")
	 //Quand je consulte la catégorie ce n'est pas la peine de me donner ses films
	   //Pour cela j'utilise l'annotation @JsonProperty
	   @JsonProperty(access=Access.WRITE_ONLY) //pris en considération seulement en écriture
	   private java.util.Collection<Film> films;
}
