package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.AuthorRepository;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;

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
	public String addAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("authors", this.authorRepository.findAll());
			return "/admin/indexAuthor.html";
		}
		this.authorRepository.save(author);
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
	public String updateAuthor(@ModelAttribute("author") @Valid Author updatedAuthor, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("authors", this.authorRepository.findAll());
			return "/admin/formUpdateAuthor.html";
		}
		this.authorRepository.save(updatedAuthor);
		return "redirect:/admin/indexAuthor";
	}


	// ====== ELIMINA AUTORE ======
	@GetMapping("/admin/formDeleteAuthor")
	public String deleteAuthor(@RequestParam("id") Long id) {
		this.authorRepository.deleteById(id);
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

	
	@PostMapping("/admin/formAddAuthorToBook")
	public String assignBookToAuthor(@RequestParam("id") Long authorId,
	                                 @RequestParam("bookId") Long bookId) {
	    Author author = authorService.findById(authorId);
	    Book book = bookService.findById(bookId);

	    if (author != null && book != null) {
	        author.getBooks().add(book);
	        authorRepository.save(author);
	    }

	    return "redirect:/admin/indexAuthor";
	}



}
