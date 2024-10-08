/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author xuan
 */
public interface MapInterface<K, V> {

    public V put(K key, V value);

    public V get(K key);

    public V remove(K key);

    public boolean containsKey(K key);

    public boolean isEmpty();

    public int size();
    
    public int capacity();
    
    public K getKey(int index);
    
    public V getOrDefault(K key, V defaultValue);
    
    public void clear();
    
    public K[] getKeys(); // Returns an array or collection of keys
}

