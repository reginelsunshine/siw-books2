package it.uniroma3.siw.controller.validator;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;


@Component
public class BookValidator implements Validator{
	@Autowired
	BookService bookService;
	
	 /* Indichiamo al framework che questo validator lavora su istanze Book */
	//chiamo classVal perche' non posso usare di nuovo "class"
    @Override
    public boolean supports(Class<?> classVal) {
        return Book.class.equals(classVal);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        /* ---------- Titolo ---------- */
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            errors.rejectValue("title", "required",
                    "Il titolo non può essere vuoto");
        }

        /* ---------- Anno di pubblicazione ---------- */
        Integer year = book.getYearOfPublication();
        if (year == null) {
            errors.rejectValue("yearOfPublication", "required",
                    "L’anno di pubblicazione è obbligatorio");
        } else {
            int currentYear = Year.now().getValue();
            if (year < 1450 || year > currentYear) {
                errors.rejectValue("yearOfPublication", "range",
                        "L’anno deve essere compreso fra 1450 e " + currentYear);
            }
        }

        /* ---------- Duplicati ---------- */
        /* Effettuiamo la ricerca solo se non ci sono già altri errori bloccanti */
        if (!errors.hasErrors() && bookService.alreadyExists(book)) {
            // NB: implementa alreadyExists(Book) in BookService / BookRepository
            errors.reject("duplicate",
                    "Esiste già un libro con lo stesso titolo e anno");
        }
    }
	
}
