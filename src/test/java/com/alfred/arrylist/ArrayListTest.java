package com.alfred.arrylist;

import com.alfred.arraylist.ExampleArrayList;

/**
 * ArrayList测试
 */
public class ArrayListTest {

    public static void main(String[] args) {

        ExampleArrayList<String> arrayList = new ExampleArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        arrayList.add(2,"7");

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }

        System.out.println("下标位置 "+arrayList.get(5));

        arrayList.remove(6);

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }
}
