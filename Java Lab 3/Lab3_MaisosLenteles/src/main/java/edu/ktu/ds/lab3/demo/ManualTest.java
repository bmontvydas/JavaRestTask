package edu.ktu.ds.lab3.demo;

import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;

import java.util.Locale;

public class ManualTest {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car c3 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        Car c4 = new Car("Renault Laguna 2001 115900 7500");
        Car c5 = new Car.Builder().buildRandom();
        Car c6 = new Car("Honda   Civic  2007  36400 8500.3");
        Car c7 = new Car("Renault Laguna 2001 115900 7500");
        Car c8 = new Car.Builder().buildRandom();
        Car c9 = new Car.Builder().buildRandom();
        Car c10 = new Car.Builder().buildRandom();
        // Atvaizdžio raktų masyvas
        String[] carsIds = {"TA156", "TA102", "TA178", "TA171", "TA105", "TA106", "TA107", "TA108"};
        int id = 0;
        ParsableMap<String, Car> carsMap
                = new ParsableHashMap<>(String::new, Car::new, HashType.DIVISION);

        // Atvaizdžio reikšmių masyvas
        Car[] cars = {c1, c2, c3, c4, c5, c6, c7};
        for (Car c : cars) {
            carsMap.put(carsIds[id++], c);
        }

        Ks.oun("Porų išsidėstymas atvaizdyje pagal raktus");
        carsMap.println("");
        Ks.oun("Ar egzistuoja pora atvaizdyje?");
        Ks.oun(carsMap.contains(carsIds[6]));
        Ks.oun(carsMap.contains(carsIds[7]));
        Ks.oun("Pašalinamos poros iš atvaizdžio:");
        Ks.oun(carsMap.remove(carsIds[1]));
        Ks.oun(carsMap.remove(carsIds[7]));
        Ks.oun("carsMap ilgis: " + carsMap.size());
        Ks.oun("Porų išsidėstymas atvaizdyje pagal raktus. Vaizduojami tik raktai");
        carsMap.println("=");

        Ks.oun("Atliekame porų paiešką atvaizdyje:");
        Ks.oun(carsMap.get(carsIds[2]));
        Ks.oun(carsMap.get(carsIds[7]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        Ks.ounn(carsMap);

        Ks.oun("");
        Ks.oun("Testavimas");
        Ks.oun("");
        Ks.oun("Testuojame remove metodą");

        ParsableMap<String, Car> carsMapTesting
                = new ParsableHashMap<>(String::new, Car::new, HashType.DIVISION);
        int temp = 0;
        // Atvaizdžio reikšmių masyvas
        for (Car c : cars) {
            carsMapTesting.put(carsIds[temp++], c);
        }

        Ks.oun("Prieš trynima vaizdavimas:");
        carsMapTesting.println("=");
        Ks.oun("Po trynimo:");
        carsMapTesting.remove(carsIds[2]);
        carsMapTesting.remove(carsIds[6]);
        carsMapTesting.println("=");
        Ks.oun("");

        Ks.oun("Patikriname ar egzistuoja containsValue");
        Ks.oun("Kaip nariai yra:");
        Ks.ounn(carsMapTesting);
        Ks.oun("True testas, kai ieškome " + c4);
        Ks.oun("Atsakymas: " + carsMapTesting.containsValue(c4));
        Ks.oun("False testas, kai ieškome " + c3);
        Ks.oun("Atsakymas: " + carsMapTesting.containsValue(c3));
        Ks.oun("");

        Ks.oun("Testuojame numberOfEmpties");
        Ks.oun("Atvaizdas atrodo šitaip: ");
        carsMapTesting.println("=");
        Ks.oun("Number of empties " + carsMapTesting.numberOfEmpties());
        Ks.oun("");

        Ks.oun("Testuojame Put all");
        Ks.oun("Pradinis map");
        carsMapTesting.println("=");
        ParsableMap<String, Car> carsMapTesting2
                = new ParsableHashMap<>(String::new, Car::new, HashType.DIVISION);
        int temp2 = 0;
        String[] carsIds2 = {"TA106", "TA107", "TA108", "TA109", "TA110"};
        Car[] cars2 = {c5, c6, c7,c8,c9};
        // Atvaizdžio reikšmių masyvas
        for (Car c : cars2) {
            carsMapTesting2.put(carsIds2[temp2++], c);
        }
        Ks.oun("Naujas map:");
        carsMapTesting2.println("=");
        Ks.oun("Po Put all");
        carsMapTesting.putAll(carsMapTesting2);
        carsMapTesting.println("=");
        Ks.oun("");

        Ks.oun("Replace metodas");
        Ks.oun("Pradinis map");
        Ks.ounn(carsMapTesting);
        Ks.oun("Replace TA107 " + c6 + " į " + c10);
        carsMapTesting.replace("TA107",c6,c10);
        Ks.oun("Po replace");
        Ks.ounn(carsMapTesting);

        Ks.oun("Get number of collisions");
        carsMap.println("=");
        Ks.oun(carsMap.getNumberOfCollisions());
        carsMapTesting.println("=");
        Ks.oun(carsMapTesting.getNumberOfCollisions());
    }
}
