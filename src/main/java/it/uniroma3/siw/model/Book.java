package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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


	@Basic(fetch = FetchType.LAZY)  
	@Column(name = "image", columnDefinition = "bytea")
	private byte[] image;
	@Column(name = "image_type")
	private String imageType;

	    public String getImageType() { return imageType; }
	    public void setImageType(String imageType) { this.imageType = imageType; }



	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@ManyToMany(fetch = FetchType.LAZY)         /*un libro può essere scritto da uno o più autori e un autore può scrivere uno o 
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

	//GESTIONE DELLE RECENSIONI DA PARTE DI UTENTI
	//nb: a volte da errore poiché non riesce a suggerire gli import ad es di list ecc
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();

	public List<Review> getReviews() {
		return reviews;
	}


}
