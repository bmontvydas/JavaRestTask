package edu.ktu.ds.lab3.utils;

import java.util.Set;

/**
 * Interfeisu aprašomas Atvaizdžio ADT.
 *
 * @param <K> Atvaizdžio poros raktas
 * @param <V> Atvaizdžio poros reikšmė
 */
public interface Map<K, V> {

    /**
     * Patikrinama ar atvaizdis yra tuščias.
     *
     * @return true, jei tuščias
     */
    boolean isEmpty();

    /**
     * Grąžinamas atvaizdyje esančių porų kiekis.
     *
     * @return Grąžinamas atvaizdyje esančių porų kiekis.
     */
    int size();

    /**
     * Išvalomas atvaizdis.
     */
    void clear();

    /**
     * Grąžinamas porų masyvas.
     *
     * @return Grąžinamas porų masyvas.
     */
    String[][] toArray();

    /**
     * Atvaizdis papildomas nauja pora.
     *
     * @param key   raktas,
     * @param value reikšmė.
     * @return Grąžinama atvaizdžio poros reikšmė.
     */
    V put(K key, V value);

    /**
     * Grąžinama atvaizdžio poros reikšmė.
     *
     * @param key raktas.
     * @return Grąžinama atvaizdžio poros reikšmė.
     */
    V get(K key);

    /**
     * Iš atvaizdžio pašalinama pora.
     *
     * @param key raktas.
     * @return Grąžinama pašalinta atvaizdžio poros reikšmė.
     */
    V remove(K key);

    /**
     * Patikrinama ar atvaizdyje egzistuoja pora su raktu key.
     *
     * @param key raktas.
     * @return true, jei atvaizdyje egzistuoja pora su raktu key, kitu atveju - false
     */
    boolean contains(K key);

    /**
     * Pakeičia atvaizdyje egzistuojantį raktą ir jį atitinkančią reikšmę naują reikšme ir grąžina true.
     * Jei raktas neegzistuoja atvaizdyje, ar jo reikšmė neatitinka metodo argumente nurodytos senosios reikšmės,
     * pakeitimas nevykdomas ir gražinama false.
     *
     * @param key      raktas.
     * @param oldValue sena reikšmė.
     * @param newValue nauja reikšmė.
     * @return true, jei pakeitimas įvyko
     */
    boolean replace(K key, V oldValue, V newValue);

    /**
     * Patikrinama ar atvaizdyje egzistuoja vienas ar daugiau raktų metodo argumente nurodytai reikšmei
     *
     * @param value reikšmė.
     * @return true, jei atvaizdyje egzistuoja vienas ar daugiau raktų metodo argumente nurodytai reikšmei
     */
    boolean containsValue(Object value);

    /**
     * Kopijuoja visas raktų-reikšmių poras argumente nurodyto atvaizdžio į šį atvaizdį.
     *
     * @param map atvaizdis
     */
    void putAll(Map<K, V> map);

    /**
     * Jei argumente nurodytas raktas neturi reikšmės šiame atvaizdyje,
     * argumente nurodyta raktas - reikšmė pora įrašoma ir grąžinama null.
     * Kitu atveju grąžinama atvaizdyje jau egzistuojanti raktą atitinkanti reikšmė
     *
     * @param key   raktas.
     * @param value reikšmė.
     * @return Esama reikšmė, jei raktas jau egzistuoja atvaizdyje, arba null - jei pora įrašoma į atvaizdį
     */
    V putIfAbsent(K key, V value);

    Set<K> keySet();

    int getNumberOfCollisions();
}
