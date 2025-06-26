package it.uniroma3.siw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;

@SpringBootApplication
public class SiwBooks2Application implements CommandLineRunner{
	@Autowired
	AuthorRepository authorRepository;
	public static void main(String[] args) {
		SpringApplication.run(SiwBooks2Application.class, args);
	}
	
	 public void run(String... args) throws Exception {
		 List<Author> list = authorRepository.findAllByOrderBySurnameAscNameAsc();
		 /*ora stampo con un for iterando la lista altrimenti stamperebbe solo gli oggetti autore e quindi gli indirizzi
		   di memoria*/
		 System.out.println("Lista ordinata per nome e cognome crescente:");
		 for(Author autore : list) {
			 //DA VERIFICARE!
			 System.out.println(autore.getName()+""+autore.getSurname());
		 }
	 }
}
