package com.example.BookHomeWork;

import javax.persistence.Entity;
import java.util.Calendar;

@Entity
public class AntiqueBook extends Book {
    private Integer ReleaseYear;

    public AntiqueBook(String title, String author, String barcode, Integer quantity, Double pricePerUnit, Integer releaseYear) {
        super(title, author, barcode, quantity, pricePerUnit);
        ReleaseYear = releaseYear;
    }

    public AntiqueBook() {
    }

    @Override
    public double getTotalPrice() {
        return getQuantity() * getPricePerUnit() * (Calendar.getInstance().get(Calendar.YEAR) - ReleaseYear) / 10;
    }

    public Integer getReleaseYear() {
        return ReleaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        ReleaseYear = releaseYear;
    }
}
