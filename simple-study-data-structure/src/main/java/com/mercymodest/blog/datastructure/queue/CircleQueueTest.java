package com.mercymodest.blog.datastructure.queue;

import cn.hutool.core.util.ArrayUtil;

import java.util.Objects;
import java.util.Scanner;

/**
 * 使用数组实现简单的环形队列
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 */
public class CircleQueueTest {

    public static void main(String[] args) {
        char key;
        try (Scanner scanner = new Scanner(System.in)) {
            CircleQueue circleQueue = new CircleQueue(4);
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
                        circleQueue.printQueue();
                        break;
                    case 'a':
                        System.out.println("输出一个数");
                        int value = scanner.nextInt();
                        circleQueue.add(value);
                        break;
                    //取出数据
                    case 'g':
                        try {
                            int res = circleQueue.get();
                            System.out.printf("取出的数据是%d\n", res);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    //查看队列头的数据
                    case 'h':
                        try {
                            int res = circleQueue.head();
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
    static class CircleQueue {

        /**
         * 队列最大值 预留一个位置作为约定位置以通过取模实现环形算法
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

        public CircleQueue(Integer maxSize) {
            this.maxSize = maxSize;
            this.front = 0;
            this.rear = 0;
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
            return Objects.equals(front, (rear + 1) % maxSize);
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
            queue[rear] = element;
            rear = (rear + 1) % maxSize;
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
            Integer result = queue[front];
            queue[front] = null;
            front = (front + 1) % maxSize;
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
            for (int i = front; i < front + size(); i++) {
                int index = i % maxSize;
                if (Objects.isNull(queue[index])) {
                    emptyCount++;
                    continue;
                }
                System.out.printf("queue[%d]=%d   ", index, queue[index]);
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
            return queue[front];
        }

        /**
         * 获取环形队列的实际长度
         *
         * @return {@code  int} size
         */
        public int size() {
            return (rear - front + maxSize) % maxSize;
        }
    }
}
