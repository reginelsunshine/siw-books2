package it.uniroma3.siw.model;

import java.util.Objects;
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
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(Integer yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authors, id, title, yearOfPublication);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(authors, other.authors) && Objects.equals(id, other.id)
				&& Objects.equals(title, other.title) && Objects.equals(yearOfPublication, other.yearOfPublication);
	}


}
