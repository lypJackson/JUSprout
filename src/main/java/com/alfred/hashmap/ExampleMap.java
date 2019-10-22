package com.alfred.hashmap;

public interface ExampleMap<K, V> {
    /**
     * 添加元素
     *
     * @param k
     * @param v
     * @return
     */
    V put(K k, V v);

    /**
     * 查询元素
     *
     * @param k
     * @return
     */
    V get(K k);

    /**
     * 获取元素个数
     *
     * @return
     */
    int size();

    /**
     * Entr作用-Node节点
     * @param <K>
     * @param <V>
     */
    interface Entry<K, V> {
        K getKey();

        V getValue();

        V setValue(V value);
    }
}
