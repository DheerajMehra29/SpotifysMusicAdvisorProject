package by.dheeraj.musicadvisor.factory;

import by.dheeraj.musicadvisor.factory.impl.GenericFactory;

public interface Factory<K, V> {

    void add(K k, V v);

    V get(K k);

    static <K, V> Factory<K, V> of() {

        return new GenericFactory<>();

    }

}
