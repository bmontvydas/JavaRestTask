package com.example.BookHomeWork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("SELECT b  FROM Book b WHERE b.Barcode = ?1")
    Optional<Book> findBooksByBarcode(String barcode);

    @Query("SELECT DISTINCT Quantity FROM Book")
    List<Integer> findDistinctQuantityInBooks();

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.Title = COALESCE(?1, b.Title),b.Author = COALESCE(?2, b.Author)," +
            "b.Barcode = COALESCE(?3, b.Barcode),b.Quantity = COALESCE(?4, b.Quantity)," +
            "b.PricePerUnit = ?5 WHERE b.Barcode = ?6 ")
    void updateBooksByBarcode
            (String title, String author, String newBarcode, Integer quantity, Double pricePerUnit, String oldBarcode);


    @Transactional
    @Modifying
    @Query("UPDATE ScienceJournal b SET b.Title = COALESCE(?1, b.Title),b.Author = COALESCE(?2, b.Author)," +
            "b.Barcode = COALESCE(?3, b.Barcode),b.Quantity = COALESCE(?4, b.Quantity)," +
            "b.PricePerUnit = ?5, b.ScienceIndex = COALESCE(?6, b.ScienceIndex) WHERE b.Barcode = ?7")
    void updateScientificJournalByBarcode
            (String title, String author, String newBarcode, Integer quantity, Double pricePerUnit, Integer scienceIndex, String oldBarcode);

    @Transactional
    @Modifying
    @Query("UPDATE AntiqueBook b SET b.Title = COALESCE(?1, b.Title),b.Author = COALESCE(?2, b.Author)," +
            "b.Barcode = COALESCE(?3, b.Barcode),b.Quantity = COALESCE(?4, b.Quantity)," +
            "b.PricePerUnit = ?5, b.ReleaseYear = COALESCE(?6, b.ReleaseYear) WHERE b.Barcode = ?7")
    void updateAntiqueBookByBarcode
            (String title, String author, String newBarcode, Integer quantity, Double pricePerUnit, Integer releaseYear, String oldBarcode);

    @Query("SELECT b FROM Book b WHERE b.Quantity = ?1")
    Book[] findBooksByQuantity(Integer quantity);
}
