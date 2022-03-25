package com.mercymodest.blog.datastructure.queue;

import cn.hutool.core.util.ArrayUtil;

import java.util.Objects;
import java.util.Scanner;

/**
 * 使用数组实现简单的队列
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 */
public class ArrayQueueTest {

    public static void main(String[] args) {
        char key;
        try (Scanner scanner = new Scanner(System.in)) {
            ArrayQueue queue = new ArrayQueue(3);
            boolean loop = true;
            while (loop) {
                System.out.println("s(show): 显示队列");
                System.out.println("e(exit): 退出程序");
                System.out.println("a(add): 添加数据到队列");
                System.out.println("g(get): 从队列取出数据");
                System.out.println("h(head): 查看队列头的数据");
                key = scanner.next().charAt(0);
                switch (key) {
                    // 打印队列
                    case 's':
                        queue.printQueue();
                        break;
                    case 'a':
                        System.out.println("输出一个数");
                        int value = scanner.nextInt();
                        queue.add(value);
                        break;
                    //取出数据
                    case 'g':
                        try {
                            int res = queue.get();
                            System.out.printf("取出的数据是%d\n", res);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    //查看队列头的数据
                    case 'h':
                        try {
                            int res = queue.head();
                            System.out.printf("队列头的数据是%d\n", res);
                        } catch (Exception e) {

                            System.out.println(e.getMessage());
                        }
                        break;
                    //退出
                    case 'e':
                        scanner.close();
                        loop = false;
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * 使用 数组形式简单模拟的队列
     */
    static class ArrayQueue {

        /**
         * 队列最大值
         */
        private final Integer maxSize;

        /**
         * 头部指针
         */
        private Integer front;

        /**
         * 尾部指针
         */
        private Integer rear;

        /**
         * 数组队列
         */
        private final Integer[] queue;

        public ArrayQueue(Integer maxSize) {
            this.maxSize = maxSize;
            this.front = -1;
            this.rear = -1;
            this.queue = new Integer[maxSize];
        }

        /**
         * 队列是否为空
         *
         * @return {@code boolean} 如果队列为空,则返回 true,否则返回false
         */
        public boolean isEmpty() {
            return Objects.equals(front, rear);
        }

        /**
         * 队列是否已经满
         *
         * @return {@code  boolean}  如果队列为空,则返回 true,否则返回 false
         */
        public boolean isFull() {
            return Objects.equals(rear, maxSize - 1);
        }

        /**
         * 向队列中添加元素
         *
         * @param element {需要添加的元素}
         * @throws RuntimeException 队列满了的话
         */
        public void add(Integer element) throws RuntimeException {
            if (isFull()) {
                System.out.println("队列已满,无法添加元素哦");
                return;
            }
            queue[++rear] = element;
        }

        /**
         * 从队列中获取元素
         *
         * @return {@code Integer} 队列元素
         */
        public Integer get() {
            if (isEmpty()) {
                // may be logger ingo log.
                System.out.println("队列为空哦,可以向队列中添加你想要的数据");
            }
            front++;
            Integer result = queue[front];
            queue[front] = null;
            return result;
        }

        /**
         * 打印队列元素
         */
        public void printQueue() {
            if (ArrayUtil.isEmpty(queue)) {
                System.out.println("队列为空哦,可以向队列中添加你想要的数据");
                return;
            }
            int emptyCount = 1;
            for (int i = 0; i < queue.length; i++) {
                if (Objects.isNull(queue[i])) {
                    emptyCount++;
                    continue;
                }
                System.out.printf("queue[%d]=%d   ", i, queue[i]);
            }
            if (emptyCount == maxSize) {
                System.out.println("队列为空哦,可以向队列中添加你想要的数据");
            } else {
                System.out.println();
            }
        }

        /**
         * 展现头部元素
         *
         * @return {@code Integer} 展示头部元素
         */
        public Integer head() {
            if (isEmpty()) {
                // may be logger ingo log.
                return null;
            }
            return queue[front + 1];
        }
    }
}
