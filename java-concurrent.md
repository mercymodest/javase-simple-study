# ThreadLocal

## 基本特性

> 不同的线程使用同一个`ThreadLocal`存储的数据是相互隔离的呢

### 代码示例

```java
    @Test
    public void testFirst() {
        final  ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();
        integerThreadLocal.set(1);
        ThreadUtil.newSingleExecutor()
                .execute(() -> printThreadLocalValue(integerThreadLocal));
        printThreadLocalValue(integerThreadLocal);
        // 确保所有线程都可以执行完成
        ThreadUtil.safeSleep(200);
    }

    /**
     * 打印线程在{@link  ThreadLocal} 上存储的值
     *
     * @param threadLocal {@code threadLocal}
     */
    private void printThreadLocalValue(ThreadLocal<?> threadLocal) {
        if (Objects.isNull(threadLocal)) {
            // maybe logger warn logs
            return;
        }
        System.out.printf("%s --- threadLocal: %s value:%s %n", Thread.currentThread().getName(), threadLocal, threadLocal.get());
    }
```

#### 运行结果示例

![image-20220411075503700](https://img.mercymodest.com/public/image-20220411075503700.png)

## 核心原理

>  图片来源于互联网,其作者可能是[鲁班大叔](http://coderead.cn/)

![image-20220411075634068](https://img.mercymodest.com/public/image-20220411075634068.png)

### 我们先来断点看看  `ThreadLocal` 其对象结构

![image-20220411080108472](https://img.mercymodest.com/public/image-20220411080108472.png)

### 我们再通过`IntelliJ IDEA`的`Evaluate` 看看当前线程的结构

![image-20220411080650039](https://img.mercymodest.com/public/image-20220411080650039.png)

### 那么`ThreadLocal`是何时进行初始化的呢

![image-20220411080924427](https://img.mercymodest.com/public/image-20220411080924427.png)

#### 我们修改一下代码，在线程中使用`ThreadLocal` 设置一个值试试

![image-20220411081211236](https://img.mercymodest.com/public/image-20220411081211236.png)

## ThreadLocal 内存泄露风险

 图片来源于互联网,其作者可能是[鲁班大叔](http://coderead.cn/)

![image-20220411081330728](https://img.mercymodest.com/public/image-20220411081330728.png)

> 通过上面的交流，我们了解到，当线程通过`ThreadLcoal`进行值存储的时候，ThreadLocal本身不存储任何值，通过`ThreadLocal`设置的值是存着在线程本身,`ThreadLocal`其只是作为一个引用,用于通过其设置的值的呢

![image-20220411082613039](https://img.mercymodest.com/public/image-20220411082613039.png)

### 我们所能想到，作为`ThreadLocal`的作者肯定是会考虑到的呢，下面我们通过相关源码的阅读来一窥强者的风采

#### 带着实现理论去看源码

> 上文我们交流`ThreadLocal` 其存储特特性的时候，每个线程所持有的`ThreadLocal`的变量值，是通过`ThreadLocal`对象的引用构建关联关系的呢，这里作者(`ThreaLocal`)作者使用的Java的弱引用,根据弱引用的关系，一旦弱引用对象被`JVM`垃圾回收器回收，则其对应的弱引用实例会被设置为 `null`.既在线程内部的`thradLocals`对应的`key`会被自动设置为`null`(#get()返回的是`null`).当我们再次访问线程的`threadLocals`时，如果其`key`为`null`,则其值会被清空,从而实现规避`OOM`的风险

![image-20220425075755695](https://img.mercymodest.com/public/image-20220425075755695.png)

![image-20220425075829470](https://img.mercymodest.com/public/image-20220425075829470.png)

![image-20220425075935097](https://img.mercymodest.com/public/image-20220425075935097.png)

#### 当然啦,如果线程结束了生命周期,其对应的`threaLocals`自然会被清空呢

![image-20220425080216820](https://img.mercymodest.com/public/image-20220425080216820.png)

## ThreadLocalMap 底层结构小析

 图片来源于互联网,其作者可能是[鲁班大叔](http://coderead.cn/)

![image-20220425080414419](https://img.mercymodest.com/public/image-20220425080414419.png)

![image-20220425080618532](https://img.mercymodest.com/public/image-20220425080618532.png)

![image-20220425080847382](https://img.mercymodest.com/public/image-20220425080847382.png)

![image-20220425081046851](https://img.mercymodest.com/public/image-20220425081046851.png)

### `table`索引的计算

![image-20220425081611810](https://img.mercymodest.com/public/image-20220425081611810.png)

### 那么出现`Hash`冲突的时候，`ThreadLocalMap`是如何处理的呢

> 上文我们交流的索引，其实可以看做是起始索引，如果起始索引的已经存在了元素，会以我们计算的起始索引为起点，依次向下寻找没有元素的位置，如果寻找到数组的最后仍然没有找到的话，会从头开始寻找，直到寻找到为止呢，我们大致可以认为`table`是一个有界的循环数组呢,有没有可能整个table会被填满呢，答案肯定的呢--- 不会
>
> ![image-20220425082221155](https://img.mercymodest.com/public/image-20220425082221155.png)
>
> ![image-20220425084037358](https://img.mercymodest.com/public/image-20220425084037358.png)

![image-20220425083719264](https://img.mercymodest.com/public/image-20220425083719264.png)

![image-20220425083753694](https://img.mercymodest.com/public/image-20220425083753694.png)

### `ThreadLocalMap`清空旧条目的实现

> ```java
> replaceStaleEntry(ThreadLocal<?> key, Object value,int staleSlot)
> ```
>
> 
>
> ![image-20220425084345721](https://img.mercymodest.com/public/image-20220425084345721.png)
