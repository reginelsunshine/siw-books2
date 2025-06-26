package it.uniroma3.siw.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.controller.validator.AuthorValidator;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.AuthorRepository;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AuthorController {
	@Autowired 
	private AuthorService authorService;

	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorValidator authorValidator;

	//voglio l'elenco di tutti gli autori
	@GetMapping("/author")
	public String getAuthors(Model model) {
		//QUI NON MI STAVA PRENDENDO model.addAttribute poiché l'IMPORT non era quello corretto, attenzione!
		//DA CONTROLLARE DIFFERENZA TRA USARE this.authorRepository.findAll() e this.authorService.findAll()
		model.addAttribute("authors", this.authorRepository.findAll());
		return "/authors.html";
	}

	@GetMapping("/author/{id}")
	public String getAuthor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("author", this.authorService.findById(id));
		return "author.html";
	}

	@GetMapping("/admin/indexAuthor")
	public String getIndexAuthor(Model model) {
		return "/admin/indexAuthor.html";
	}

	// ====== INSERIMENTO NUOVO AUTORE ======
	@GetMapping("/admin/formAddAuthor")
	public String formAddAuthor(Model model) {
		model.addAttribute("author", new Author());
		return "/admin/formAddAuthor.html";
	}



	//NB: MANCAVA IL GETMAPPING PER L'INSERIMENTO
	@PostMapping("/admin/formAddAuthor")
	public String addAuthor(@Valid @ModelAttribute("author") Author author,
	                        BindingResult bindingResult,
	                        Model model) {

	    authorValidator.validate(author, bindingResult);
	    
	    if (authorService.alreadyExists(author)) {
	        bindingResult.reject("duplicate", "author.duplicate");
	    }


	    if (bindingResult.hasErrors()) {
	        return "/admin/formAddAuthor.html";
	    }
	    authorService.save(author);
	    return "redirect:/admin/indexAuthor";
	}

	// ====== MODIFICA AUTORE ======
	@GetMapping("/admin/formUpdateAuthor")
	public String showUpdateForm(@RequestParam(name = "id", required = false) Long id, Model model) {
		model.addAttribute("authors", this.authorRepository.findAll());

		if (id != null) {
			Author author = this.authorRepository.findById(id).orElse(null);
			if (author != null) {
				model.addAttribute("author", author);
			}
		}
		return "/admin/formUpdateAuthor.html";
	}

	@PostMapping("/admin/formUpdateAuthor")
	public String updateAuthor(@RequestParam("id") Long id,
	                           @RequestParam("name") String name,
	                           @RequestParam("surname") String surname,
	                           @RequestParam("dateOfBirth") String dateOfBirth,
	                           @RequestParam(value = "dateOfDeath", required = false) String dateOfDeath,
	                           @RequestParam(value = "nationality", required = false) String nationality,
	                           @RequestParam("imageFile") MultipartFile imageFile) {
	    
	    Author author = authorService.findById(id);
	    if (author == null) {
	        return "redirect:/admin/formUpdateAuthor"; // oppure pagina di errore
	    }

	    author.setName(name);
	    author.setSurname(surname);
	    author.setDateOfBirth(LocalDate.parse(dateOfBirth));
	    
	    if (dateOfDeath != null && !dateOfDeath.isEmpty()) {
	        author.setDateOfDeath(LocalDate.parse(dateOfDeath));
	    } else {
	        author.setDateOfDeath(null);
	    }

	    author.setNationality(nationality);

	    if (!imageFile.isEmpty()) {
	        try {
	            byte[] imageBytes = imageFile.getBytes();
	            author.setImage(imageBytes);
	            author.setImageType(imageFile.getContentType());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    authorRepository.save(author);
	    return "redirect:/admin/indexAuthor";
	}
	
	@GetMapping("/author/image/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getAuthorImage(@PathVariable Long id) {
	    Author author = authorService.findById(id);
	    if (author == null || author.getImage() == null) {
	        return ResponseEntity.notFound().build();
	    }

	    MediaType mediaType;
	    try {
	        mediaType = MediaType.parseMediaType(author.getImageType());
	    } catch (Exception e) {
	        mediaType = MediaType.APPLICATION_OCTET_STREAM;
	    }

	    return ResponseEntity
	            .ok()
	            .contentType(mediaType)
	            .body(author.getImage());
	}




	// ====== ELIMINA AUTORE ======
	// Metodo GET per mostrare la pagina di conferma eliminazione autore
	@GetMapping("/admin/formDeleteAuthor")
	public String showDeleteAuthorsForm(Model model) {
	    model.addAttribute("authors", authorRepository.findAll());
	    return "/admin/formDeleteAuthor.html"; 
	}


	// Metodo POST che effettua la cancellazione
	@PostMapping("/admin/deleteAuthors")
	public String deleteSelectedAuthors(@RequestParam(value = "authorIds", required = false) List<Long> authorIds) {
	    if (authorIds != null) {
	        for (Long id : authorIds) {
	            authorRepository.deleteById(id);
	        }
	    }
	    return "redirect:/admin/indexAuthor";
	}





	// ====== AGGIUNTA LIBRI A UN AUTORE ======
	@GetMapping("/admin/formAddAuthorToBook")
	public String formAddAuthorToBook(@RequestParam(name="id", required=false) Long id, Model model) {
	    if (id == null) {
	        // Step 1: mostra lista autori per scegliere
	        model.addAttribute("authors", authorRepository.findAll());
	        model.addAttribute("step", 1);
	    } else {
	        // Step 2: mostra form per assegnare libro a autore selezionato
	        Author author = authorService.findById(id);
	        model.addAttribute("author", author);
	        model.addAttribute("books", bookRepository.findAll());
	        model.addAttribute("step", 2);
	    }
	    return "/admin/formAddAuthorToBook.html";
	}

	
	/*@PostMapping("/admin/formAddAuthorToBook")
	public String assignBookToAuthor(@RequestParam("id") Long authorId,
	                                 @RequestParam("bookId") Long bookId) {
	    Author author = authorService.findById(authorId);
	    Book book = bookService.findById(bookId);

	    if (author != null && book != null) {
	        author.getBooks().add(book);
	        authorRepository.save(author);
	    }

	    return "redirect:/admin/indexAuthor";
	}*/
	@PostMapping("/admin/formAddAuthorToBook")
	public String assignBookToAuthor(@RequestParam("id") Long authorId,
	                                 @RequestParam("bookId") Long bookId) {
	    Author author = authorService.findById(authorId);
	    Book book = bookService.findById(bookId);

	    if (author != null && book != null) {
	        author.getBooks().add(book);
	        book.getAuthors().add(author); // <- AGGIUNTA NECESSARIA PER RELAZIONE BIDIREZIONALE

	        // Salva entrambi per assicurarti che l'oggetto persistente venga aggiornato
	        authorRepository.save(author);
	        bookRepository.save(book);
	    }

	    return "redirect:/admin/indexAuthor";
	}




}
