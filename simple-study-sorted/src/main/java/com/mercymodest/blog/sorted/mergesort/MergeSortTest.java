package com.mercymodest.blog.sorted.mergesort;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 */
public class MergeSortTest {


    @Test
    public void testMergeSort() {
        final int len = 20;
        final int maxValue = 100;
        int[] arr = getIntArray(len, maxValue);
        int[] resultArray = new int[arr.length];
        System.out.println(Arrays.toString(arr));
        spilt(arr, 0, arr.length, resultArray);
        System.out.println(Arrays.toString(resultArray));
    }

    /**
     * 获取一个指定的长度和指定数组元素最大的 {@link  int} 数组
     *
     * @param len      {@code  len} 数组长度
     * @param maxValue {@code maxValue} 数组元素最大值
     * @return {@code int[]}
     */
    private int[] getIntArray(Integer len, Integer maxValue) {
        Assert.notNull(len, () -> new IllegalArgumentException("len must not null"));
        Assert.notNull(maxValue, () -> new IllegalArgumentException("maxValue must not null"));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = RandomUtil.randomInt(maxValue);
        }
        return arr;
    }

    /**
     * 归并排序: 数组拆分
     *
     * @param targetArray {@code targetArray} 目标数组
     * @param from        {@code  from} 启始索引
     * @param to          {@code to}  终止索引
     * @param resultArray {@code resultArray } 结果数组 避免重复创建数组
     */
    private void spilt(int[] targetArray, int from, int to, int[] resultArray) {
        if (ArrayUtil.isEmpty(targetArray)) {
            // maybe should log warn logs
            return;
        }
        int len = to - from;
        if (len == 1) {
            // 当数组只要一个元素的时候,不需要再进行拆分
            // maybe should log trace logs
            return;
        }
        int mid = (len) / 2 + from;
        // left
        spilt(targetArray, from, mid, resultArray);
        // right
        spilt(targetArray, mid, to, resultArray);

        // like debug log
        //System.out.println(Arrays.toString(Arrays.copyOfRange(targetArray, from, to)));
        merge(targetArray, from, mid, to, resultArray);
    }

    private void merge(int[] arr, int start, int mid, int end, int[] resultArray) {
        if (ArrayUtil.isEmpty(arr)) {
            // maybe should log warn logs
            return;
        }
        int leftStart = start;
        int rightStart = mid;
        for (int i = start; i < end; i++) {
            if (leftStart == mid) {
                // 左边数据已经移动完毕
                resultArray[i] = arr[rightStart];
                rightStart++;
            } else if (rightStart == end) {
                // 右边数据已经全部移动完毕
                resultArray[i] = arr[leftStart];
                leftStart++;
            } else if (arr[leftStart] >= arr[rightStart]) {
                // 左边的数组数据大于等于右边数字
                resultArray[i] = arr[rightStart];
                rightStart++;
            } else {
                // 左边的数组数据小于于右边数字
                resultArray[i] = arr[leftStart];
                leftStart++;
            }
        }
        // maybe logger trance logs
//        System.out.println(Arrays.toString(Arrays.copyOfRange(resultArray, start, end)));

        // 将排好序的局部替换原数组
        System.arraycopy(resultArray, start, arr, start, end - start);
    }
}
