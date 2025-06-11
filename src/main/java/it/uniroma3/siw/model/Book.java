package it.uniroma3.siw.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;

/* annoto la classe con @... per comunicare al framework che va predisposta una tabella per memorizzare
   gli oggetti della classe Book */
@Entity
public class Book {
	/* annoto il seguente identificatore con @... per comunicare al framework che la variabile "id" corrisponde ad una 
	   chiave primaria il cui valore deve essere creato automaticamente dal DBMS ogni volta che viene inserita una 
	   nuova ennupla Book */
	 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	//@NotBlank   ----->da mettere quando ti occuperai della validazione
	private String title;
	
	private Integer yearOfPublication;
	
	//TODO gestione immagini (una o più immagini per libro)
	
	@ManyToMany         /*un libro può essere scritto da uno o più autori e un autore può scrivere uno o 
	                      o più libri*/
	private Set<Author> authors;
	
	
	//TODO metodi setter & getter
		//TODO metodi equals & hashCode
	// ------> due oggetti Book li considero uguali se hanno stesso titolo, anno di pubblicazione e stessi autori
	
	
}
