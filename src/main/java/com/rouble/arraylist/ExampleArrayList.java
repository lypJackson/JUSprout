package com.rouble.arraylist;

import java.util.Arrays;

/**
 * 简单实现ArrayList
 * @param <E>
 */
public class ExampleArrayList<E> implements ExampleList<E> {

    //用来存放元素
    private Object[] elementData;

    //元素实际个数
    private int size;

    //默认元素长度
    private static final int DEFAULT_CAPACITY = 10;


    //无参构造，默认初始化长度为10
    public ExampleArrayList() {
        this(DEFAULT_CAPACITY);
    }

    //有参构造，长度小于0时抛出异常
    public ExampleArrayList(int minCapacity) {
        if (minCapacity < 0) {
            throw new IllegalArgumentException("初始化容量不能小于0");
        }
        elementData = new Object[minCapacity];
    }

    @Override
    public void add(E e) {
        //首先进行扩容校验,size+1的原因是必须保证始终要比ArrayList实际元素量大1
        ensureCapactity(size + 1);
        int sizes = size++;
        elementData[sizes] = e;
    }

    @Override
    public void add(int index, E e) {
        //首先进行扩容校验,size+1的原因是必须保证始终要比ArrayList实际元素量大1
        ensureCapactity(size + 1);
        // 重新排列位置复制数组,由于数据下标是从0开始，长度是从1开始的，所以这里计算挪动后的位置只需要长度size-下标index就能知道往后面挪动多少了
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = e;
        size++;
    }

    public void ensureCapactity(int minCapactity) {
        //实际数量等于当前ArrayList的数量时，需要扩容
        if (size == elementData.length) {
            int oldCapactity = elementData.length;
            //通过移位算法扩容至原来的1.5倍
            int newCapactity = oldCapactity + (oldCapactity >> 1);
            //第一次执行到这里的时候，oldCapactity=1,oldCapactity >> 1移位后没有扩容newCapactity还是1，所以至少保证和minCapactity一致大小
            if (newCapactity - oldCapactity < 0) {
                newCapactity = minCapactity;
            }
            //将老数组复制到因扩容的数组里边去
            elementData = Arrays.copyOf(elementData, newCapactity);

        }
    }


    private E elementData(int index) {
        return (E) elementData[index];
    }

    @Override
    public E get(int index) {
        return elementData(index);
    }

    /**
     * 根据下标删除元素，例如删除元素4也就是下标为index=3
     * 【1，2，3，4，5，6】
     */
    @Override
    public E remove(int index) {
        //获取当前位置是否存在
        E e = get(index);
        //删除过后，后面所剩的位置
        int afterNum = size - index - 1;
        if (afterNum > 0) {
            //由于是要删除元素4，所以源数据下标从5开始复制，也就是index+1，目标数据从index=3位置开始覆盖，删除掉元素4后，后面长度为2
            //最终效果为【1，2，3，5，6，6】
            System.arraycopy(elementData, index + 1, elementData, index, afterNum);
        }
        elementData[--size] = null;//将最后一位置为null
        return e;
    }

    @Override
    public int size() {
        return this.size;
    }

}
