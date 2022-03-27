package com.mercymodest.blog.datastructure.linkedlist;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * 单向链表测试
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 03-27 22:35
 */
public class SingleLinkedListTest {

    @Test
    public void test() {
        HeroNode node1 = new HeroNode(1, "晁盖", "呼保义");
        HeroNode node2 = new HeroNode(2, "宋江", "及时雨");
        HeroNode node3 = new HeroNode(3, "卢俊义", "玉麒麟");
        HeroNode node4 = new HeroNode(4, "吴用", "智多星");
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addByNoSort(node1);
        singleLinkedList.addByNoSort(node3);
        singleLinkedList.addByNoSort(node2);
        singleLinkedList.addByNoSort(node4);
        singleLinkedList.listNodes();
        System.out.println("更新 node2");
        HeroNode updateNode = BeanUtil.copyProperties(node2, HeroNode.class)
                .setName("小宋")
                .setNickName("哈哈哈~");
        singleLinkedList.update(updateNode);
        singleLinkedList.listNodes();
        System.out.println("删除节点数");
        singleLinkedList.del(1);
        singleLinkedList.del(4);
        singleLinkedList.del(3);
        singleLinkedList.del(2);
        singleLinkedList.listNodes();
    }


    /**
     * 单向链表简单实现
     */
    static class SingleLinkedList {

        /**
         * the linkedList head
         */
        private final HeroNode head = new HeroNode(null, null, null);

        /**
         * 向链表中添加元素(按照添加顺序添加)
         *
         * @param node {@link  HeroNode}
         */
        public void add(HeroNode node) {
            if (Objects.isNull(node)) {
                // maybe logger warn logs
                return;
            }
            HeroNode beforeAddNode = head;
            while (Objects.nonNull(beforeAddNode.next)) {
                beforeAddNode = beforeAddNode.next;
            }
            beforeAddNode.next = node;
            // maybe logger trance logs
        }

        /**
         * 以 {@link  HeroNode#getNo()} 顺序排序插入节点,如果对应{@link HeroNode#getNo()} 的节点已经存在,则提示错误信息
         *
         * @param node {@link HeroNode}
         */
        public void addByNoSort(HeroNode node) {
            if (Objects.isNull(node)) {
                // maybe logger warn logs
                return;
            }
            HeroNode beforeAddNode = head;
            boolean noExisted = false;
            while (true) {
                if (Objects.isNull(beforeAddNode.next)) {
                    break;
                } else if (Objects.equals(beforeAddNode.next.no, node.no)) {
                    noExisted = true;
                    break;
                } else if (beforeAddNode.next.no > node.no) {
                    break;
                }
                beforeAddNode = beforeAddNode.next;
            }
            if (noExisted) {
                System.out.printf("抱歉亲,no: %d 已经存在了哦%n", node.no);
            } else {
                node.next = beforeAddNode.next;
                beforeAddNode.next = node;
                // maybe logger trance logs
            }
        }

        /**
         * 列表{@link SingleLinkedList} 节点数据
         */
        public void listNodes() {
            if (isEmpty()) {
                System.out.println("呀,当前链表没有任何节点数据呢");
                return;
            }
            HeroNode current = head;
            while (Objects.nonNull(current.next)) {
                System.out.println(current.next);
                current = current.next;
            }
        }

        /**
         * 判断当前链表是否没有任何节点数据
         *
         * @return 如果当前链表没有任何节点数据的话, 则返回 {@code  true} 否则 返回 {@code false}
         */
        public boolean isEmpty() {
            return Objects.isNull(head.next);
        }

        /**
         * 通过{@link HeroNode#getNo()} 删除节点数据，如果不存在的话,会提示相关错误信息
         *
         * @param delNo {@code delNo} 需要删除的节点{@link  HeroNode#getNo()}
         */
        public void del(Integer delNo) {
            if (Objects.isNull(delNo)) {
                // maybe logger warn logs
                return;
            }
            if (isEmpty()) {
                // maybe logger warn logs
                System.out.println("呀,当前链表没有任何节点数据呢,无法执行删除操作呢");
                return;
            }
            HeroNode current = head;
            boolean isExisted = false;
            while (Objects.nonNull(current.next)) {
                if (Objects.equals(current.next.no, delNo)) {
                    isExisted = true;
                    break;
                }
                current = current.next;
            }
            if (isExisted) {
                current.next = current.next.next;
            } else {
                System.out.printf("抱歉亲, no:%d 节点不存在呢,无法执行删除操作呢 %n", delNo);
            }
        }

        /**
         * 通过 {@link  HeroNode#getNo()} 为条件对 {@link  HeroNode} 非 {@link HeroNode#getNo()} 进行更新
         *
         * @param node {@code node} 需要更新的节点数据
         */
        public void update(HeroNode node) {
            if (Objects.isNull(node)) {
                // maybe logger warn logs
                return;
            }
            if (Objects.isNull(node.no)) {
                throw new IllegalArgumentException(StrUtil.format("node' no must not null. node: {}", node));
            }
            HeroNode current = head;
            boolean isExisted = false;
            while (true) {
                if (Objects.isNull(current.next)) {
                    // may be logger warn logs
                    break;
                }
                if (Objects.equals(current.next.no, node.no)) {
                    isExisted = true;
                    break;
                }
                current = current.next;
            }
            if (isExisted) {
                // node'no can't be updated.
                BeanUtil.copyProperties(node, current.next, "no");
            } else {
                System.out.printf("抱歉,未找到 no 为 %d 的节点数据呢%n", node.no);
            }

        }

    }


    @Setter
    @Getter
    @Accessors(chain = true)
    static class HeroNode {

        private Integer no;

        private String name;

        private String nickName;

        private HeroNode next;

        public HeroNode(Integer no, String name, String nickName) {
            this.no = no;
            this.name = name;
            this.nickName = nickName;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickName='" + nickName + '\'' +
                    '}';
        }
    }
}
