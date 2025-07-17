package com.practica.library.service;

import com.practica.library.model.ActionLog;
import com.practica.library.model.Book;
import com.practica.library.model.BookDTO;
import com.practica.library.repository.ActionLogRepository;
import com.practica.library.repository.BookRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@Service
public class BookServiceJPA {

    private final BookRepository bookRepository;
    private final ActionLogRepository actionLogRepository;
    private final ModelMapper modelMapper;

    public BookServiceJPA(BookRepository bookRepository, ActionLogRepository actionLogRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.actionLogRepository = actionLogRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookDTO> getBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : books) {
            bookDTOs.add(modelMapper.map(book, BookDTO.class));
        }
        return bookDTOs;
    }

    public BookDTO saveBook(Book book) {
        Book saved = bookRepository.save(book);
        return modelMapper.map(saved, BookDTO.class);
    }

    public String randomAction() {
        long count = bookRepository.count();
        String result;

        if (count == 0) {
            result = "No books available.";
            log.info("randomAction: no books in database.");
        } else {
            int randomIndex = new Random().nextInt((int) count);
            List<Book> allBooks = bookRepository.findAll();
            Book selected = allBooks.get(randomIndex);
            result = "Random book: " + selected.getTitle();
            log.info("randomAction: selected book - {}", selected.getTitle());
        }

        // save action to DB
        ActionLog logEntry = new ActionLog(result, LocalDateTime.now());
        actionLogRepository.save(logEntry);

        return result;
    }

    public List<ActionLog> getAllActions() {
        log.info("Fetching all action logs.");
        return actionLogRepository.findAll();
    }
}
