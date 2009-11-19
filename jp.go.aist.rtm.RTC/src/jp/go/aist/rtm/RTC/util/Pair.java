package jp.go.aist.rtm.RTC.util;

public class Pair<K, V> {

    private K key;
    private V value;
    
    public Pair(final K key, final V value) {
        
        this.key = key;
        this.value = value;
    }
    
    public K getKey() {
        
        return this.key;
    }
    
    public V getValue() {
        
        return this.value;
    }
}
