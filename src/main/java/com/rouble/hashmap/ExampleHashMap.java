package com.rouble.hashmap;

/**
 * 简单实现HashMap
 * @param <K>
 * @param <V>
 */
public class ExampleHashMap<K, V> implements ExampleMap<K, V> {

    //1、定义table，存放HashMap数组元素，默认没有初始化容器，懒加载
    Node<K, V>[] table = null;
    //2、实际储存容量
    int size;
    //2、加载因子，扩容使用，加载因子越小，hash冲突越少
    float DEFAULT_LOAD_FACTOR = 0.75f;
    //4、table默认初始化大小 16
    static int DEFAULT_INIT_CAPACTITY = 1 << 4;

    @Override
    public V put(K k, V v) {
        //table如果为空，先初始化
        if (table == null) {
            table = new Node[DEFAULT_INIT_CAPACTITY];
        }
        if (size > (DEFAULT_LOAD_FACTOR * DEFAULT_INIT_CAPACTITY)) {
            //开始对table进行数组扩容
            reSize();
        }
        //计算hash值，指定下标位置
        int index = getIndex(k, DEFAULT_INIT_CAPACTITY);
        Node<K, V> node = table[index];
        if (node == null) {//没有出现hash冲突
            node = new Node<>(k, v, null);
            size++;
        } else {//hash冲突了
            Node<K, V> newNode = node;
            while (newNode!=null){
                if (newNode.getKey().equals(k) || newNode.getKey()==k){
                    //走到此处说明key相同，覆盖value值
                    return newNode.setValue(v);
                }else {
                    /**
                     * 发生hash冲突且key不相同，也就是要以链表的方式解决，当newNode.next == null的时候，把新put的值放在最前面，之前的值挤到后边去。
                     */
                    if (newNode.next==null){
                        // 说明遍历到最后一个node节点、添加元素
                        node=new Node<>(k,v,node);
                        size++;
                    }
                }

                newNode=newNode.next;//将newNode的next节点赋值给newNode，如果有值继续while循环，如果为null，退出循环
            }
        }

        table[index]=node;
        return null;
    }



    @Override
    public V get(K k) {
        // 使用取模算法定位数据链表
        Node<K, V> node = getNode(table[getIndex(k, DEFAULT_INIT_CAPACTITY)], k);
        return node == null ? null : node.value;
    }


    @Override
    public int size() {
        return size;
    }

    /**
     * 扩容
     */
    public void reSize(){
        //定义新的table大小，是之前两倍的大小
        Node<K,V>[] newTable = new Node[DEFAULT_INIT_CAPACTITY << 1];
        //重新计算index索引位置，存放到新的table里边
        for (int i = 0; i <table.length ; i++) {
            Node<K, V> oldNode = table[i];
            while (oldNode!=null){
                //table[i]=null;// 赋值为null -- 让垃圾回收机制进行回收 将之前的node删除
                K oldKey = oldNode.key;
                //重新计算index
                int index = getIndex(oldKey, newTable.length);
                Node<K, V> oldNext = oldNode.next;

                // 如果当前【index】下标在newTable发生相同的【index】时候、以链表进行存储
                //没扩容前链表存储结构放在新的node里边顺序将会发生变化,例如没扩容前结构【key22号,value：1号】【key66号,value：123466666】
                //扩容后的效果是【key66号,value：123466666】【key22号,value：1号】
                /**
                 * 流程：例如 oldNode结果为【key22号,value：1号】【key66号,value：123466666】，新的index=6，oldNext=【key66号,value：123466666】
                 * 当执行oldNode.next = newTable[index];代码时，newTable[index=6]=null,即oldNode.next=null，
                 * 此时oldNode结果为【key22号,value：1号】null，紧接着执行newTable[index] = oldNode;即newTable[index=6] = 【key22号,value：1号】null;
                 * ，执行到oldNode = oldNext;即oldNode = 【key66号,value：123466666】，由于oldNde不为null继续循环，newTable[index=6];结果为【key22号,value：1号】null;
                 * 当执行完oldNode.next = newTable[index];句后oldNode结果为【key66号,value：123466666】，【key22号,value：1号】，null，顺序也就发生了变化，就这样将值放入了
                 * 新的容器里边。
                 */
                oldNode.next = newTable[index];

                // 将之前的node赋值给newTable[index]
                newTable[index] = oldNode;
                // 判断是否继续循环遍历
                oldNode = oldNext;
            }
        }
        // 3.将newTable赋值给旧的table
        table = newTable;
        DEFAULT_INIT_CAPACTITY = newTable.length;
        newTable = null; // 让垃圾回收机制进行回收

    }





    /**
     * 获取指定元素
     *
     * @param node
     * @param k
     * @return
     */
    public Node<K, V> getNode(Node<K, V> node, K k) {
        while (node != null) {
            if (node.getKey().equals(k)) {
                return node;
            } else {
                node = node.next;
            }
        }
        return null;
    }



    //定义节点
    static class Node<K, V> implements Entry<K, V> {

        private K key;

        private V value;

        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value=value;
            return oldValue;
        }
    }


    /**
     * 打印所有的链表元素
     */
    void print() {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            System.out.print("下标位置【" + i + "】");
            while (node != null) {
                System.out.print("【key" + node.getKey() + ",value：" + node.getValue() + "】");
                node = node.next;
            }
            System.out.println();
        }
    }


    public int getIndex(K k, int length) {
        int hashCode = k.hashCode();
        int index = hashCode % length;
        return index;
    }


}
