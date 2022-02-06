package com.example.BookHomeWork;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{barcode}")
    public Book getBook(@PathVariable("barcode") String barcode) {
        return bookService.getBook(barcode);
    }

    @GetMapping("/{barcode}/totalPrice")
    public Double getPrice(@PathVariable("barcode") String barcode) {
        return bookService.getBook(barcode).getTotalPrice();
    }

    @PutMapping("/{barcode}/updateBook")
    public void updateBook(@RequestBody Book update, @PathVariable String barcode) {
        bookService.updateBook(update, barcode);
    }

    @PutMapping("/{barcode}/updateScienceJournal")
    public void updateScienceJournal(@RequestBody ScienceJournal update, @PathVariable String barcode) {
        bookService.updateBook(update, barcode);
    }

    @PutMapping("/{barcode}/updateAntiqueBook")
    public void updateAnitqueBook(@RequestBody AntiqueBook update, @PathVariable String barcode) {
        bookService.updateBook(update, barcode);
    }

    @GetMapping("/barcodes")
    public ArrayList<String[]> getBarcodes() {
        return (ArrayList<String[]>) bookService.getQuantityGroupsOfSortedBarcodes();
    }

    @PostMapping
    public void registerNewBook(@RequestBody Book book) {
        bookService.addNewBook(book);
    }
}
