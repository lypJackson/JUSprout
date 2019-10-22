package com.alfred.hashmap;

public class HashMapTest {

    public static void main(String[] args) {
        ExampleHashMap hashMap = new ExampleHashMap();
        hashMap.put("1号", "1号");// 0
        hashMap.put("2号", "1号");// 1
        hashMap.put("3号", "1号");// 2
        hashMap.put("4号", "1号");// 3
        hashMap.put("6号", "1号");// 4
        hashMap.put("7号", "1号");
        hashMap.put("14号", "1号");

        hashMap.put("22号", "1号");
        hashMap.put("26号", "1号");
        hashMap.put("27号", "1号");
        hashMap.put("28号", "1号");
        hashMap.put("66号", "66");
        hashMap.put("30号", "1号");
        System.out.println("扩容前数据....");
        hashMap.print();

        hashMap.put("31号", "1号");
        hashMap.put("66号", "123466666");

        System.out.println("扩容后数据....");
        hashMap.print();

    }

}
