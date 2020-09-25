package com.wjj;

import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * 测试AvlTree、TreeMap、HashMap和线性查找效率
 */
public class Main {

    // 随机数数量
    private static int total = 10000000;

    // 随机种子
    private static int seed = 1;

    public static void main(String[] args) {
        AvlTree<Integer> avlTree = new AvlTree<>();
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        Random random = new Random(seed);
        int[] randomNumbers = new int[total];
        for (int i = 0; i < total; i++) {
            randomNumbers[i] = random.nextInt(total);
        }
        // 插入到AVL树
        System.out.println("avl tree: put elements");
        int count = 0;
        long st = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            avlTree.insert(randomNumbers[i]);
            count++;
            if (count % (total / 10) == 0) {
                System.out.println((System.currentTimeMillis() - st));
                st = System.currentTimeMillis();
            }
        }
        // 插入到散列表
        System.out.println("hash map: put elements");
        count = 0;
        st = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            hashMap.put(randomNumbers[i], 0);
            count++;
            if (count % (total / 10) == 0) {
                System.out.println((System.currentTimeMillis() - st));
                st = System.currentTimeMillis();
            }
        }
        // 插入到tree map
        System.out.println("tree map: put elements");
        count = 0;
        st = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            treeMap.put(randomNumbers[i], 0);
            count++;
            if (count % (total / 10) == 0) {
                System.out.println((System.currentTimeMillis() - st));
                st = System.currentTimeMillis();
            }
        }
        // AVL查询
        System.out.println("avl tree: get elements");
        st = System.currentTimeMillis();
        count = 0;
        for (int i = 0; i < total; i++) {
            Integer j = avlTree.get(randomNumbers[i]);
            if (j == null) {
                throw new RuntimeException("fail to get " + randomNumbers[i]);
            }
            count++;
            if (count % (total / 10) == 0) {
                System.out.println((System.currentTimeMillis() - st));
                st = System.currentTimeMillis();
            }
        }
        // 散列表查询
        System.out.println("hash map: get elements");
        st = System.currentTimeMillis();
        count = 0;
        for (int i = 0; i < total; i++) {
            Integer j = hashMap.get(randomNumbers[i]);
            if (j == null) {
                throw new RuntimeException("fail to get " + randomNumbers[i]);
            }
            count++;
            if (count % (total / 10) == 0) {
                System.out.println((System.currentTimeMillis() - st));
                st = System.currentTimeMillis();
            }
        }
        // treeMap查询
        System.out.println("tree map: get elements");
        st = System.currentTimeMillis();
        count = 0;
        for (int i = 0; i < total; i++) {
            Integer j = treeMap.get(randomNumbers[i]);
            if (j == null) {
                throw new RuntimeException("fail to get " + randomNumbers[i]);
            }
            count++;
            if (count % (total / 10) == 0) {
                System.out.println((System.currentTimeMillis() - st));
                st = System.currentTimeMillis();
            }
        }
        // 一般的查找
        System.out.println("normal find");
        st = System.currentTimeMillis();
        count = 0;
        for (int num : randomNumbers) {
            for (int j = randomNumbers.length - 1; j >= 0; j--) {
                if (randomNumbers[j] == num) {
                    count++;
                    if (count % (total / 10) == 0) {
                        System.out.println((System.currentTimeMillis() - st));
                        st = System.currentTimeMillis();
                        break;
                    }
                }
            }
        }

    }
}
