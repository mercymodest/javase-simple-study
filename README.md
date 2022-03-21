## `JavaSE` 排序算法简要学习

> - 归并排序
> - 选择排序
> - 插入排序
> - (随机)快速排序

## 归并排序

> 归并排序（MERGE-SORT） 是利归并思想实现的排序方法.该算法采用典型的分治策略(divide-and-conquer)
>
> > 分治策略就是将问题(divide)分成一些小的问题然后递归求解，而治(conquer)就是将分阶段的结果合并成最终的结果

### 归并排序思想简要示意图

> 图片来源于互联网,其作者可能是[韩顺平老师](https://space.bilibili.com/651245581)

##### 算法梗概

![image-20220321075110094](https://img.mercymodest.com/public/image-20220321075110094.png)

##### 合并简要图示

> 图示展示的是最后一步合并操作

![image-20220321080155930](https://img.mercymodest.com/public/image-20220321080155930.png)

### 归并排序之 合并

```java
  /**
     * 归并排序之合并算法
     *
     * @param arr         {@code arr} 目标数组
     * @param start       {@code start} 合并起始索引
     * @param mid         {@code mid} 中间索引
     * @param end         {@code end} 合并终止索引
     * @param resultArray {@code resultArray} 存放排序后的数组元素,又称为临时数组.用于将排序后的结果合并到目标数组
     */
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
```

### 归并排序之分

```java
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
        int mid = len / 2 + from;
        // left
        spilt(targetArray, from, mid, resultArray);
        // right
        spilt(targetArray, mid, to, resultArray);

        // like debug log
        //System.out.println(Arrays.toString(Arrays.copyOfRange(targetArray, from, to)));
        merge(targetArray, from, mid, to, resultArray);
    }
```

### 算法测试

#### 生产数组的方法

```java
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
```

#### 归并排序测试

```java
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
```

#### 运行结果截图

![image-20220321080036180](https://img.mercymodest.com/public/image-20220321080036180.png)

