package com.mercymodest.blog.sorted.quick;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 快速排序
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 */
public class QuickSortTest {


    @Test
    public void test() {
        final int len = 10;
        final int maxValue = 100;
        int[] targetArray = getIntArray(len, maxValue);
        System.out.println(Arrays.toString(targetArray));
//        quickSort(targetArray, 0, targetArray.length - 1);
        System.out.println(Arrays.toString(Arrays.stream(targetArray).sorted().toArray()));
        randomQuickSort(targetArray, 0, targetArray.length - 1);
        System.out.println(Arrays.toString(targetArray));
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
     * 快速排序
     *
     * @param targetArray {@code targetArray} 目标数组
     * @param left        {@code left} 左边起始
     * @param right       {@code right} 右边起始
     */
    private void quickSort(int[] targetArray, final int left, final int right) {
        if (left >= right) {
            // 指针重叠 排序完成
            return;
        }
        if (ArrayUtil.isEmpty(targetArray)) {
            // maybe logger warn logs
            return;
        }
        int currentLeft = left;
        int currentRight = right;
        //快排基数
        final int pivot = targetArray[currentLeft];
        // 默认右边为空
        boolean emptyLeft = true;
        while (currentLeft != currentRight) {
            if (emptyLeft) {
                // 左边为空
                if (pivot > targetArray[currentRight]) {
                    targetArray[currentLeft] = targetArray[currentRight];
                    emptyLeft = false;
                    currentLeft++;
                } else {
                    currentRight--;
                }
            } else {
                // 右边为空
                if (pivot < targetArray[currentLeft]) {
                    targetArray[currentRight] = targetArray[currentLeft];
                    emptyLeft = true;
                    currentRight--;
                } else {
                    currentLeft++;
                }
            }
        }
        targetArray[currentLeft] = pivot;
        //判断是否需要再次进行排序
        // now  right==left
        // 判断左边是否需要进行排序
        // 快排基数左边
        quickSort(targetArray, left, currentLeft - 1);
        // 快排基数右边
        quickSort(targetArray, currentRight + 1, right);
    }

    /**
     * 随机快速排序
     *
     * @param targetArray {@code targetArray} 目标数组
     * @param left        {@code left} 左边起始
     * @param right       {@code right} 右边起始
     */
    private void randomQuickSort(int[] targetArray, final int left, final int right) {
        if (left >= right) {
            // 指针重叠 排序完成
            return;
        }
        if (ArrayUtil.isEmpty(targetArray)) {
            // maybe logger warn logs
            return;
        }
        int currentLeft = left;
        int currentRight = right;
        //快排基数
        int pivotIndex = ThreadLocalRandom.current().nextInt(currentLeft, currentRight);
        final int pivot = targetArray[pivotIndex];
        //将  pivot 和 最左(右)边的值进行交换
        targetArray[pivotIndex] = targetArray[currentLeft];
        targetArray[currentLeft] = pivot;
        // 默认左(右)边为空
        boolean emptyLeft = true;
        while (currentLeft != currentRight) {
            if (emptyLeft) {
                // 左边为空
                if (pivot > targetArray[currentRight]) {
                    targetArray[currentLeft] = targetArray[currentRight];
                    emptyLeft = false;
                    currentLeft++;
                } else {
                    currentRight--;
                }
            } else {
                // 右边为空
                if (pivot < targetArray[currentLeft]) {
                    targetArray[currentRight] = targetArray[currentLeft];
                    emptyLeft = true;
                    currentRight--;
                } else {
                    currentLeft++;
                }
            }
        }
        targetArray[currentLeft] = pivot;
        //判断是否需要再次进行排序
        // now  right==left
        // 判断左边是否需要进行排序
        // 快排基数左边
        quickSort(targetArray, left, currentLeft - 1);
        // 快排基数右边
        quickSort(targetArray, currentRight + 1, right);
    }

}
