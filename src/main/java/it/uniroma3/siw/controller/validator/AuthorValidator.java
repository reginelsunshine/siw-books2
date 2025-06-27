package it.uniroma3.siw.controller.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;
import it.uniroma3.siw.service.AuthorService;

@Component
public class AuthorValidator implements Validator {

    @Autowired
    private AuthorService authorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;

        /* Nome */
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            errors.rejectValue("name", "author.name.required");
        }

        /* Cognome */
        if (author.getSurname() == null || author.getSurname().trim().isEmpty()) {
            errors.rejectValue("surname", "author.surname.required");
        }

        /* Date */
        LocalDate dob = author.getDateOfBirth();
        LocalDate dod = author.getDateOfDeath();

        if (dob == null) {
            errors.rejectValue("dateOfBirth", "author.dob.required");
        } else {
            if (dob.isAfter(LocalDate.now())) {
                errors.rejectValue("dateOfBirth", "author.dob.future");
            }
            if (dod != null && dod.isBefore(dob)) {
                errors.rejectValue("dateOfDeath", "author.dod.beforeBirth");
            }
        }

        /* Duplicato → errore legato al campo surname  */
        if (!errors.hasErrors() && authorService.alreadyExists(author)) {
            errors.rejectValue("surname", "author.duplicate");
        }
    }
}



