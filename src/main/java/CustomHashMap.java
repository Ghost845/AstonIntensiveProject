import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomHashMap<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] array = new Entry[INITIAL_CAPACITY];
    private int size = 0;

    void put(K key, V value) {
        if (size >= (array.length * LOAD_FACTOR)) {
            resize();
        }
        boolean put = put(key, value, array);
        if (put) {
            size++;
        }
    }

    private boolean put(K key, V value, Entry<K, V>[] destinationArray) {
        int position = getElementPosition(key, destinationArray.length);
        Entry<K, V> currentElement = destinationArray[position];
        if (currentElement == null) {
            Entry<K, V> entry = new Entry(key, value, null);
            destinationArray[position] = entry;
            return true;
        } else {
            while (true) {
                if (currentElement.key.equals(key)) {
                    currentElement.value = value;
                    return false;
                }
                if (currentElement.next == null) {
                    currentElement.next = new Entry(key, value, null);
                    return true;
                }
                currentElement = currentElement.next;
            }
        }
    }

    V get(K key) {
        int position = getElementPosition(key, array.length);
        Entry<K, V> currentElement = array[position];
        while (currentElement != null) {
            if (currentElement.key.equals(key)) {
                return currentElement.value;
            }
            currentElement = currentElement.next;
        }
        return null;
    }

    Set<K> keySet() {
        Set<K> keys = new HashSet<>();

        for (Entry<K, V> entry: array) {
            Entry<K, V> currentElement = entry;
            while (currentElement != null) {
                keys.add(currentElement.key);
                currentElement = currentElement.next;
            }
        }
        return keys;
    }

    List<V> values() {
        List<V> values = new ArrayList<>();

        for (Entry<K, V> entry: array) {
            Entry<K, V> currentElement = entry;
            while (currentElement != null) {
                values.add(currentElement.value);
                currentElement = currentElement.next;
            }
        }
        return values;
    }

    boolean remove(K key) {
        int position = getElementPosition(key, array.length);
        Entry<K, V> currentElement = array[position];
        if (currentElement != null && currentElement.key.equals(key)) {
            array[position] = currentElement.next;
            size--;
            return true;
        } else {
            while (currentElement != null) {
                Entry<K, V> nextElement = currentElement.next;
                if (nextElement == null) {
                    return false;
                }
                if (nextElement.key.equals(key)) {
                    currentElement.next = nextElement.next;
                    size--;
                    return true;
                }
                currentElement = currentElement.next;
            }
        }
        return false;
    }

    int size() {
        return size;
    }

    void clear() {
        array = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    private void resize() {
        Entry<K, V>[] newArray = new Entry[array.length * 2];
        for (Entry<K, V> entry: array) {
            Entry<K, V> currentElement = entry;
            while (currentElement != null) {
                put(currentElement.key, currentElement.value, newArray);
                currentElement = currentElement.next;
            }
        }
        array = newArray;
    }

    private int getElementPosition(K key, int arrayLength) {
        return Math.abs(key.hashCode() % arrayLength);
    }

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}