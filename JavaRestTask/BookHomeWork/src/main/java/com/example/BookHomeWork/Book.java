package com.example.BookHomeWork;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.Field;

@Entity
public class Book implements Comparable<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String Title;
    private String Author;
    private String Barcode;
    private Integer Quantity;
    private Double PricePerUnit;

    public Book() {
    }

    public Book(String title, String author, String barcode, Integer quantity, Double pricePerUnit) {
        Title = title;
        Author = author;
        Barcode = barcode;
        Quantity = quantity;
        PricePerUnit = pricePerUnit;
    }

    public double getTotalPrice() {
        return Quantity * PricePerUnit;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public double getPricePerUnit() {
        return PricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        PricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id=" + Id +
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", Barcode='" + Barcode + '\'' +
                ", Quantity=" + Quantity +
                ", PricePerUnit=" + PricePerUnit +
                '}';
    }

    public int compareTo(Book book) {
        if (getTotalPrice() < book.getTotalPrice())
            return 1;
        else if (getTotalPrice() > book.getTotalPrice())
            return -1;
        else
            return 0;
    }

    public boolean checkNull() {
        for (Field f : getClass().getDeclaredFields()) {
            try {
                if ((f.get(this) == null) && (f.getName() != "Id"))
                    return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean checkIfDoubleEmpty() {
        try {
            Field f = getClass().getField("PricePerUnit");
        } catch (NoSuchFieldException e) {
            return true;
        }
        return false;
    }
}
