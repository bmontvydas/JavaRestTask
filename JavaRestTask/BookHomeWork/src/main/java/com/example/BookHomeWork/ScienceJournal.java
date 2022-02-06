package com.example.BookHomeWork;

import javax.persistence.Entity;

@Entity
public class ScienceJournal extends Book {
    private Integer ScienceIndex;

    public ScienceJournal(String name, String author, String barcode, Integer quantity, Double pricePerUnit, Integer scienceIndex) {
        super(name, author, barcode, quantity, pricePerUnit);
        ScienceIndex = scienceIndex;
    }

    public ScienceJournal() {
    }

    @Override
    public double getTotalPrice() {
        return getQuantity() * getPricePerUnit() * ScienceIndex;
    }

    public Integer getScienceIndex() {
        return ScienceIndex;
    }

    public void setScienceIndex(Integer scienceIndex) {
        if (scienceIndex < 1 || scienceIndex > 10)
            throw new IllegalStateException("Wrong, science index has to be between 1 and 10");
        ScienceIndex = scienceIndex;
    }
}
