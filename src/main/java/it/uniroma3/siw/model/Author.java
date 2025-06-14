package it.uniroma3.siw.model;

import java.time.LocalDate;

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









}
