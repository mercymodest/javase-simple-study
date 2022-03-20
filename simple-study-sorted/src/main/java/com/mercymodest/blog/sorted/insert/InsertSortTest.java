package com.mercymodest.blog.sorted.insert;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 03-20 21:22
 */
public class InsertSortTest {

    @Test
    public void test() {
        final int len = 10;
        final int maxValue = 100;
        int[] targetArray = getIntArray(len, maxValue);
        System.out.println(Arrays.toString(targetArray));
        insertSort(targetArray);
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
     * 插入排序
     *
     * @param targetArray {@code targetArray}
     */
    private void insertSort(int[] targetArray) {
        if (ArrayUtil.isEmpty(targetArray)) {
            // maybe logger warn logs
            return;
        }
        // 当前值
        int current;
        // 上一个索引
        int preIndex;
        for (int i = 1; i < targetArray.length; i++) {
            current = targetArray[i];
            preIndex = i - 1;
            // 10, 49, 74, 17, 22, 55, 56, 46, 66, 58
            while (preIndex >= 0 && current < targetArray[preIndex]) {
                // 将较大的值往后置换
                targetArray[preIndex + 1] = targetArray[preIndex];
                preIndex--;
            }
            //
            // 当前元素应该方式 preIndex
            targetArray[preIndex + 1] = current;
        }
    }
}
