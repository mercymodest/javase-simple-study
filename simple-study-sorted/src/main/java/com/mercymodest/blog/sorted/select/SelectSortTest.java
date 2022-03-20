package com.mercymodest.blog.sorted.select;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 */
public class SelectSortTest {

    @Test
    public void test() {
        final int len = 10;
        final int maxValue = 100;
        int[] targetArray = getIntArray(len, maxValue);
        System.out.println(Arrays.toString(targetArray));
        selectSort(targetArray);
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
     * 选择排序
     *
     * @param targetArray {@code  targetArray }
     */
    private void selectSort(int[] targetArray) {
        if (ArrayUtil.isEmpty(targetArray)) {
            //  maybe logger warn logs
            return;
        }
        int minIndex;
        int currentMinValue;
        // 最后一个元素不需要再进行比较
        for (int i = 0; i < targetArray.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < targetArray.length; j++) {
                if (targetArray[minIndex] > targetArray[j]) {
                    minIndex = j;
                }
            }
            if (minIndex == i) {
                // 当前值就是最小值无需进行元素位置置换
                continue;
            }
            currentMinValue = targetArray[minIndex];
            targetArray[minIndex] = targetArray[i];
            targetArray[i] = currentMinValue;
        }
    }
}
