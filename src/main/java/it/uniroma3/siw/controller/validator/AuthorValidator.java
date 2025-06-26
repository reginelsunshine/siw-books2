package it.uniroma3.siw.controller.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;

@Component
public class AuthorValidator implements Validator{
	@Autowired
    private AuthorService authorService;
	
	@Override
    public boolean supports(Class<?> classVal) {
        return Author.class.equals(classVal);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;

        /* ---------- Nome ---------- */
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            errors.rejectValue("name", "required", "Il nome è obbligatorio");
        }

        /* ---------- Cognome ---------- */
        if (author.getSurname() == null || author.getSurname().trim().isEmpty()) {
            errors.rejectValue("surname", "required", "Il cognome è obbligatorio");
        }

        /* ---------- Date ---------- */
        LocalDate dob = author.getDateOfBirth();
        LocalDate dod = author.getDateOfDeath();

        if (dob == null) {
            errors.rejectValue("dateOfBirth", "required", "La data di nascita è obbligatoria");
        } else {
            if (dob.isAfter(LocalDate.now())) {
                errors.rejectValue("dateOfBirth", "future", "La data di nascita non può essere nel futuro");
            }
            if (dod != null && dod.isBefore(dob)) {
                errors.rejectValue("dateOfDeath", "beforeBirth", "La data di morte non può precedere la nascita");
            }
        }

        /* ---------- Duplicati ---------- */
        if (!errors.hasErrors() && authorService.alreadyExists(author)) {
            errors.reject("duplicate", "Esiste già un autore con stesso nome, cognome e data di nascita");
        }
    }
}
