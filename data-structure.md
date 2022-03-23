# 简单学习`Java` 数据结构

> - 稀疏数组

## 稀疏数组

### 从一个简单需求出发

> 图片来源于互联网,其作者可能是[韩顺平老师](https://space.bilibili.com/651245581)

![image-20220323082026690](https://img.mercymodest.com/public/image-20220323082026690.png)

### 稀疏数组处理方式

```text
稀疏数组是一个3*(n+1)二维数组构成,其中 n 表示原数组中的个数.其构造规则如下
第一行依次记录着原数组的 行信息,列信息,有值元素个数,从第二行开始(包含第二行)
依次分别记录原数组有值元素的行信息,列信息,以及元素取值
```

图片来源于互联网,其作者可能是[韩顺平老师](https://space.bilibili.com/651245581)

![image-20220323082153691](https://img.mercymodest.com/public/image-20220323082153691.png)


### 简单实现

```java
package com.mercymodest.blog.datastructure.sparsearray;

import cn.hutool.core.util.ArrayUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 数据结构之稀疏数组
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 03-23 7:43
 */
public class SparseArrayTest {
    /*
       稀疏数组是一个3*(n+1)二维数组构成,其中 n 表示原数组中的个数.其构造规则如下
       第一行依次记录着原数组的 行信息,列信息,有值元素个数,从第二行开始(包含第二行)
       依次分别记录原数组有值元素的行信息,列信息,以及元素取值
     */

    @Test
    public void test() {
        /*
           模拟简要 11*11 五子棋棋盘
           0: 表示 没有棋子
           1: 表示 白子
           2: 表示 黑子
         */
        int[][] chessboard = new int[11][11];
        chessboard[2][3] = 1;
        chessboard[4][3] = 1;
        chessboard[6][5] = 2;
        chessboard[8][5] = 2;
        printIntDoubleDimensionalArray(chessboard);

        // 获取二维数组元素个数
        int elementCount = Stream.of(chessboard)
                .mapToInt(doubleDimensionalArray -> {
                    if (ArrayUtil.isEmpty(doubleDimensionalArray)) {
                        return 0;
                    }
                    return Arrays.stream(doubleDimensionalArray)
                            .filter(e -> e > 0)
                            .map(e -> 1)
                            .sum();
                })
                .sum();
        System.out.println(elementCount);

        int[][] sparseArray = new int[elementCount + 1][3];
        sparseArray[0][0] = chessboard.length;
        sparseArray[0][1] = chessboard[0].length;
        sparseArray[0][2] = elementCount;
        int index = 0;
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                if (chessboard[i][j] > 0) {
                    index++;
                    sparseArray[index][0] = i;
                    sparseArray[index][1] = j;
                    sparseArray[index][2] = chessboard[i][j];
                }
            }
        }
        System.out.println("====================================");
        printIntDoubleDimensionalArray(sparseArray);
        // 从稀疏数组中恢复到原二维数组
        int[][] oldChessboard = new int[sparseArray[0][0]][sparseArray[0][1]];
        for (int i = 1; i < sparseArray.length; i++) {
            oldChessboard[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        System.out.println("====================================");
        printIntDoubleDimensionalArray(oldChessboard);
    }


    /**
     * 以简要二维表的形式打印 int 二维数组
     *
     * @param doubleDimensionalArray {@code  int[][]}
     */
    private void printIntDoubleDimensionalArray(int[][] doubleDimensionalArray) {
        if (ArrayUtil.isEmpty(doubleDimensionalArray)) {
            // maybe logger warn logs
            return;
        }
        for (int[] columns : doubleDimensionalArray) {
            if (ArrayUtil.isEmpty(columns)) {
                continue;
            }
            for (int column : columns) {
                System.out.printf("\t%d", column);
            }
            System.out.println();
        }
    }
}
```

### 运行结果测试

![image-20220323082428080](https://img.mercymodest.com/public/image-20220323082428080.png)

