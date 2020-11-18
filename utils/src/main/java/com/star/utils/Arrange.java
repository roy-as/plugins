package com.star.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排列组合
 */
public class Arrange {

    public static void main(String[] args) {
        // 全排列
        List<List<Integer>> fullArrange = new ArrayList<>();
        List<Integer> target = Arrays.asList(1, 2, 3, 4, 5);
        fullArrange(target, new ArrayList<>(), fullArrange);
        System.out.println(fullArrange);

        // 排列组合
        List<List<Integer>> arrange = new ArrayList<>();
        arrange(target, new ArrayList<>(), arrange, 2);
        System.out.println(arrange);

        //排列组合去重
        List<List<Integer>> arrangeFilter = new ArrayList<>();
        arrangeFilter(target, new ArrayList<>(), arrangeFilter, 2);
        System.out.println(arrangeFilter);
    }


    /**
     * 全排列
     * 回溯剪枝递归
     *
     * @param target
     * @param cap
     * @param result
     */
    public static void fullArrange(List<Integer> target, List<Integer> cap, List<List<Integer>> result) {
        if (cap.size() == target.size()) {
            result.add(new ArrayList<>(cap));
        }
        for (int i = 0; i < target.size(); i++) {
            Integer num = target.get(i);
            // 剪枝
            if (cap.contains(num)) {
                continue;
            }
            cap.add(target.get(i));
            // 递归
            fullArrange(target, cap, result);
            // 回溯
            cap.remove(num);

        }

    }

    /**
     * 排列组合
     *
     * @param target
     * @param cap
     * @param result
     * @param len
     */
    public static void arrange(List<Integer> target, List<Integer> cap, List<List<Integer>> result, int len) {
        if (cap.size() == len) {
            result.add(new ArrayList<>(cap));
        }
        for (int i = 0; i < target.size(); i++) {
            Integer num = target.get(i);
            // 剪枝
            if (cap.contains(num)) {
                continue;
            }
            cap.add(target.get(i));
            // 递归
            arrange(target, cap, result, len);
            // 回溯
            cap.remove(num);

        }

    }

    /**
     * 排列组合不包含重复元素，例如[1,2] 和[2,1]
     *
     * @param target
     * @param cap
     * @param result
     * @param len
     */
    public static void arrangeFilter(List<Integer> target, List<Integer> cap, List<List<Integer>> result, int len) {
        if (cap.size() == len) {
            result.add(new ArrayList<>(cap));
        }
        int size = (int) Math.ceil(target.size() / 2.0);
        for (int i = 0; i < size; i++) {
            Integer num = target.get(i);
            // 剪枝
            if (cap.contains(num)) {
                continue;
            }
            cap.add(target.get(i));
            // 递归
            arrange(target, cap, result, len);
            // 回溯
            cap.remove(num);

        }

    }

}

