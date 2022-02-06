package com.example.BookHomeWork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addNewBook(Book book) {
        Optional<Book> bookByBarcode = bookRepository
                .findBooksByBarcode(book.getBarcode());
        if (book.checkNull()) {
            throw new IllegalStateException("Wrong parameters");
        }
        if (bookByBarcode.isPresent()) {
            throw new IllegalStateException("Barcode taken");
        }
        bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(String barcode) {
        return bookRepository.findBooksByBarcode(barcode).orElseThrow(() -> new BookNotFoundException(barcode));
    }

    public List<String[]> getQuantityGroupsOfSortedBarcodes() {
        List<String[]> grouped = new ArrayList<String[]>();
        List<Integer> uniqValues = bookRepository.findDistinctQuantityInBooks();
        for (var value : uniqValues) {
            Book[] group = bookRepository.findBooksByQuantity(value);
            Arrays.sort(group);
            grouped.add(getBarcodesFromBooks(group));
        }
        return grouped;
    }

    public String[] getBarcodesFromBooks(Book[] books) {
        String[] barcodes = new String[books.length];

        for (int i = 0; i < books.length; i++) {
            barcodes[i] = books[i].getBarcode();
        }

        return barcodes;
    }

    public void updateBook(Book update, String oldBarcode) {
        if (update.checkIfDoubleEmpty()) {
            update.setPricePerUnit(getBook(oldBarcode).getPricePerUnit());
        }
        if (update instanceof ScienceJournal) {
            bookRepository.updateScientificJournalByBarcode(update.getTitle(), update.getAuthor(), update.getBarcode(),
                    update.getQuantity(), update.getPricePerUnit(), ((ScienceJournal) update).getScienceIndex(), oldBarcode);
            return;
        }
        if (update instanceof AntiqueBook) {
            bookRepository.updateAntiqueBookByBarcode(update.getTitle(), update.getAuthor(), update.getBarcode(),
                    update.getQuantity(), update.getPricePerUnit(), ((AntiqueBook) update).getReleaseYear(), oldBarcode);
            return;
        }
        bookRepository.updateBooksByBarcode(update.getTitle(), update.getAuthor(), update.getBarcode(),
                update.getQuantity(), update.getPricePerUnit(), oldBarcode);
    }

}
