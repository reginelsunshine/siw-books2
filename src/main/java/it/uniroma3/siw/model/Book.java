package it.uniroma3.siw.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
@Entity
public class Book {
	//@NotBlank   ----->da mettere quando ti occuperai della validazione
	private String title;
	
	private Integer yearOfPublication;
	
	//TODO gestione immagini (una o più immagini per libro)
	
	@ManyToMany         /*un libro può essere scritto da uno o più autori e un autore può scrivere uno o 
	                      o più libri*/
	private Set<Author> authors;
	
}
