package com.alfred.arraylist;

public interface ExampleList<E> {

    /**
     * 单个元素添加
     *
     * @param e
     */
    public void add(E e);

    /**
     * 指定位置添加元素
     * @param index
     * @param e
     */
    public void add(int index, E e);

    /**
     * 实际元素个数
     *
     * @return
     */
    public int size();

    /**
     * 根据下标获取元素
     * @param index
     * @return
     */
    public E get(int index);

    /**
     * 根据下标删除指定元素
     * @param index
     * @return
     */
    public E remove(int index);

}
