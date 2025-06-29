package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Author {
	/*NB PER ID: ho dovuto definire un autoincrement con setval all'interno di pgadmin per
	  poter aggiungere nuovi autori*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //cambio da AUTO  a IDENTITY ...
	private Long id;
	private String name;

	private String surname;
	
	 @Basic(fetch = FetchType.LAZY)
	    @Column(name = "image", columnDefinition = "bytea")
	    private byte[] image;

	    @Column(name = "image_type")
	    private String imageType;

	    // getter e setter

	    public byte[] getImage() {
	        return image;
	    }

	    public void setImage(byte[] image) {
	        this.image = image;
	    }

	    public String getImageType() {
	        return imageType;
	    }

	    public void setImageType(String imageType) {
	        this.imageType = imageType;
	    }
	
	@ManyToMany(mappedBy = "authors")
	private Set<Book> books = new HashSet<>();

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	//se non è (ancora) morto non serve settarlo, naturalmente
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfDeath;

	private String nationality;
	
	//TODO gestione immagini (un autore ha UNA SOLA immagine)

	//TODO metodi setter & getter
	//TODO metodi equals & hashCode
	/*------> due oggetti Author li considero uguali se hanno stesso nome,cognome,nazionalità e data di nascita
             eventualmente anche stessa data di morte (effettuando prima un controllo se dateOfDeath!=null) */


	public Set<Book> getBooks() {
		return books;
	}
	
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname.trim();
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(LocalDate dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, dateOfDeath, id, name, nationality, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(dateOfDeath, other.dateOfDeath)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(nationality, other.nationality) && Objects.equals(surname, other.surname);
	}





}
