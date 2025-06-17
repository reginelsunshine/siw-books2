package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	private String surname;

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
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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
