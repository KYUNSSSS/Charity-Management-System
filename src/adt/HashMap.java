/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author xuan
 */
public class HashMap<K, V> implements MapInterface<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    private MapEntry<K, V>[] table;
    private int size;

    public HashMap() {
        this.table = new MapEntry[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = getIndex(key);
        for (MapEntry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }

        table[index] = new MapEntry<>(key, value, table[index]);
        size++;
        if (size >= LOAD_FACTOR_THRESHOLD * table.length) {
            resize();
        }
        return null;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        for (MapEntry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = getIndex(key);
        MapEntry<K, V> prev = null;
        for (MapEntry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return table.length;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    private void resize() {
        MapEntry<K, V>[] oldTable = table;
        table = new MapEntry[oldTable.length * 2];
        size = 0;
        for (MapEntry<K, V> entry : oldTable) {
            for (; entry != null; entry = entry.next) {
                put(entry.key, entry.value);
            }
        }
    }

    public MapEntry<K, V> getEntry(int index) {
        if (index >= 0 && index < table.length) {
            return table[index];
        }
        return null;
    }

    @Override
    public K getKey(int index) {
        MapEntry<K, V> entry = getEntry(index);
        return (entry != null) ? entry.key : null;
    }

    public static class MapEntry<K, V> {

        private final K key;
        private V value;
        private MapEntry<K, V> next;

        public MapEntry(K key, V value, MapEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
