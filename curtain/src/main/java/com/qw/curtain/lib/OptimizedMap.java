package com.qw.curtain.lib;

import android.os.Build;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OptimizedMap<K, V> implements Map<K, V> {
    private Map<K, V> inner;

    OptimizedMap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            inner = new ArrayMap<>(4);
        } else {
            inner = new HashMap<>();
        }
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return inner.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return inner.containsValue(value);
    }

    @Nullable
    @Override
    public V get(@Nullable Object key) {
        return inner.get(key);
    }

    @Nullable
    @Override
    public V put(@NonNull K key, @NonNull V value) {
        return inner.put(key, value);
    }

    @Nullable
    @Override
    public V remove(@Nullable Object key) {
        return inner.remove(key);
    }

    @Override
    public void putAll(@NonNull Map<? extends K, ? extends V> m) {
        inner.putAll(m);
    }

    @Override
    public void clear() {
        inner.clear();
    }

    @NonNull
    @Override
    public Set<K> keySet() {
        return inner.keySet();
    }

    @NonNull
    @Override
    public Collection<V> values() {
        return inner.values();
    }

    @NonNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return inner.entrySet();
    }
}
