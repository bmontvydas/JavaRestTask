package edu.ktu.ds.lab3.utils;

import edu.ktu.ds.lab3.demo.CarsGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ParsableHashMap<K, V extends Parsable<V>> extends HashMap<K, V> implements ParsableMap<K, V> {

    private final Function<String, K> keyCreateFunction;   // funkcija bazinio rakto objekto kūrimui
    private final Function<String, V> valueCreateFunction; // funkcija bazinio reikšmės objekto kūrimui

    /**
     * Konstruktorius su funkcija bazinių rakto ir reikšmės objektų kūrimui
     *
     * @param keyCreateFunction
     * @param valueCreateFunction
     */
    public ParsableHashMap(Function<String, K> keyCreateFunction,
                           Function<String, V> valueCreateFunction) {

        this(keyCreateFunction, valueCreateFunction, DEFAULT_HASH_TYPE);
    }

    /**
     * Konstruktorius su funkcija bazinių rakto ir reikšmės objektų kūrimui
     *
     * @param keyCreateFunction
     * @param valueCreateFunction
     * @param ht
     */
    public ParsableHashMap(Function<String, K> keyCreateFunction,
                           Function<String, V> valueCreateFunction,
                           HashType ht) {

        this(keyCreateFunction, valueCreateFunction, DEFAULT_INITIAL_CAPACITY, ht);
    }

    /**
     * Konstruktorius su funkcija bazinių rakto ir reikšmės objektų kūrimui
     *
     * @param keyCreateFunction
     * @param valueCreateFunction
     * @param initialCapacity
     * @param ht
     */
    public ParsableHashMap(Function<String, K> keyCreateFunction,
                           Function<String, V> valueCreateFunction,
                           int initialCapacity,
                           HashType ht) {

        this(keyCreateFunction, valueCreateFunction, initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    /**
     * Konstruktorius su funkcija bazinių rakto ir reikšmės objektų kūrimui
     *
     * @param keyCreateFunction
     * @param valueCreateFunction
     * @param initialCapacity
     * @param loadFactor
     * @param ht
     */
    public ParsableHashMap(Function<String, K> keyCreateFunction,
                           Function<String, V> valueCreateFunction,
                           int initialCapacity,
                           float loadFactor,
                           HashType ht) {

        super(initialCapacity, loadFactor, ht);
        this.keyCreateFunction = keyCreateFunction;
        this.valueCreateFunction = valueCreateFunction;
    }

    @Override
    public V put(String key, String value) {
        return super.put(
                create(keyCreateFunction, key, "Nenustatyta raktų kūrimo funkcija"),
                create(valueCreateFunction, value, "Nenustatyta reikšmių kūrimo funkcija")
        );
    }

    /**
     * Suformuoja atvaizį iš filePath failo
     *
     * @param filePath
     */
    @Override
    public void load(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return;
        }
        clear();
        try (BufferedReader fReader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset())) {
            fReader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .forEach(line -> put(CarsGenerator.generateId(), line));
        } catch (FileNotFoundException e) {
            Ks.ern("Tinkamas duomenų failas nerastas: " + e.getLocalizedMessage());
        } catch (IOException | UncheckedIOException e) {
            Ks.ern("Failo skaitymo klaida: " + e.getLocalizedMessage());
        }
    }

    /**
     * Išsaugoja sąrašą faile fName tekstiniu formatu tinkamu vėlesniam
     * skaitymui
     *
     * @param filePath
     */
    @Override
    public void save(String filePath) {
        throw new UnsupportedOperationException("Atvaizdžio išsaugojimas.. šiuo metu nepalaikomas");
    }

    /**
     * Atvaizdis spausdinamas į Ks.ouf("")
     *
     * @param delimiter Atvaizdžio poros toString() eilutės kirtiklis
     */
    @Override
    public void println(String delimiter) {
        if (super.isEmpty()) {
            Ks.oun("Atvaizdis yra tuščias");
            return;
        }

        String[][] mapModel = getMapModel(delimiter);

        int mappingMaxLength = Arrays.stream(mapModel)
                .flatMap(Arrays::stream)
                .filter(d -> !d.equals("-->") && !d.startsWith("[") && !d.endsWith("]"))
                .mapToInt(String::length)
                .max()
                .orElse(0);
        int indexMaxLength = Arrays.stream(mapModel).map(row -> row[0]).mapToInt(String::length).max().orElse(0);

        Ks.oufln("****** Atvaizdis ******");
        printMapModel(mapModel, mappingMaxLength, indexMaxLength);
        Ks.oufln("****** Bendras porų kiekis yra " + super.size());
    }

    private void printMapModel(String[][] mapModel, int maxKeyLength, int maxIndexLength) {
        for (String[] row : mapModel) {
            for (int j = 0; j < row.length; j++) {
                String format;
                if (j == 0) { // pirmas stulpelis, pvz: [ 0 ]
                    format = "%" + (maxIndexLength + 1) + "s";
                } else { // likę stulpeliai
                    format = j % 2 == 1 ? "%-4s" : "%-" + (maxKeyLength + 1) + "s";
                }
                String value = row[j];
                Ks.ouf(format, (value == null ? "" : value + " "));
            }
            Ks.oufln("");
        }
    }

    @Override
    public String[][] getMapModel(String delimiter) {
        String[][] result = new String[table.length][];
        int count = 0;
        for (Node<K, V> n : table) {
            List<String> list = new ArrayList<>();
            list.add("[ " + count + " ]");
            while (n != null) {
                list.add("-->");
                list.add(split(n.toString(), delimiter));
                n = n.next;
            }
            result[count] = list.toArray(new String[0]);
            count++;
        }
        return result;
    }

    private String split(String s, String delimiter) {
        int k = s.indexOf(delimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    private static <T, R> R create(Function<T, R> function, T data, String errorMessage) {
        return Optional.ofNullable(function)
                .map(f -> f.apply(data))
                .orElseThrow(() -> new IllegalStateException(errorMessage));
    }
}
