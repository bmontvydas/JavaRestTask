package com.example.BookHomeWork;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BookConfig {
    @Bean
    CommandLineRunner commandLineRunner(BookRepository repository) {
        return args ->
        {
            Book book1 = new Book("Nusikaltimas ir bausmė", "Fiodoras Dostojevskis",
                    "ABC-abc-0001", 62, 22.5);
            Book book2 = new Book("Idiotas", "Fiodoras Dostojevskis",
                    "ABC-abc-0002", 52, 10.5);
            Book book3 = new Book("Pažemintieji ir nuskriaustieji", "Fiodoras Dostojevskis",
                    "ABC-abc-0003", 20, 12.65);
            Book book4 = new Book("Ketvertas", "Scoot galloway",
                    "ABC-abc-0004", 20, 10.55);
            Book book5 = new Book("Kaip įsigyti darugų ir daryti įtaką žmonėms skaitmeniniame amžiuje",
                    "Dale Carnegie & associates",
                    "ABC-abc-0005", 12, 19.5);
            Book book6 = new Book("Ketvertas", "Scoot Galloway",
                    "ABC-abc-0006", 20, 20.0);
            Book book7 = new Book("Apie peles ir žmones",
                    "John Steinbeck",
                    "ABC-abc-0007", 20, 16.65);
            Book book8 = new AntiqueBook("Old Withcraft Secrets",
                    "Unknown",
                    "ABC-abc-0008", 20, 16.65, 1995);
            Book book9 = new AntiqueBook("Black magick",
                    "Unknown",
                    "ABC-abc-0009", 20, 16.65, 1995);
            Book book10 = new ScienceJournal("Neribota atmintis",
                    "Kevin Horsley",
                    "ABC-abc-0010", 20, 9.0, 5);
            Book book11 = new ScienceJournal("Logikos menas",
                    "Eugenia Chang",
                    "ABC-abc-0011", 20, 16.65, 7);
            repository.saveAll(List.of(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10, book11));
        };

    }
}
