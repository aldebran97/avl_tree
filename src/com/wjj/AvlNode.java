package com.wjj;

import java.util.ArrayList;
import java.util.List;

/**
 * Avl树节点
 *
 * @param <T> 泛型类型
 * @author Aldebran
 * @since 25/09/2020
 */
public class AvlNode<T> {

    public T elem;

    public AvlNode<T> left;

    public AvlNode<T> right;

    public AvlNode<T> parent;

    public int height;

    public AvlNode() {
        height = 1;
    }

    public AvlNode(T elem) {
        this();
        this.elem = elem;
    }

    /**
     * 计算任何节点深度(BFS遍历)，深度是从上到下算的，而高度是自底向上算的
     *
     * @return 深度
     */
    public static int deepBFS(AvlNode avlNode) {
        // 处理null
        if (avlNode == null) {
            return 0;
        }
        // 快速处理叶子结点
        if (avlNode.left == null && avlNode.right == null) {
            return 1;
        }
        List<AvlNode> avlNodeList = new ArrayList<>();
        avlNodeList.add(avlNode);
        int d = 0;
        while (!avlNodeList.isEmpty()) {
            List<AvlNode> cList = avlNodeList;
            final List<AvlNode> newAvlNodeList = new ArrayList<>();
            cList.forEach(node -> {
                AvlNode left = node.left;
                AvlNode right = node.right;
                if (left != null) {
                    newAvlNodeList.add(left);
                }
                if (right != null) {
                    newAvlNodeList.add(right);
                }
            });
            avlNodeList = newAvlNodeList;
            d++;
        }
        return d;
    }

    /**
     * 左子树高度
     *
     * @return 左子树高度
     */
    public int leftHeight() {
        return left == null ? 0 : left.height;
    }

    /**
     * 右子树高度
     *
     * @return 右子树高度
     */
    public int rightHeight() {
        return right == null ? 0 : right.height;
    }

    /**
     * 判断节点是否平衡
     *
     * @return bool
     */
    public boolean isBalanced() {
        int a = leftHeight() - rightHeight();
        return a >= -1 && a <= 1;
    }


    @Override
    public String toString() {
        return "AvlNode{" +
                "elem=" + elem +
                ", left=" + (left == null ? "null" : left.elem) +
                ", right=" + (right == null ? "null" : right.elem) +
                ", parent=" + (parent == null ? "null" : parent.elem) +
                ", height=" + height +
                '}';
    }
}
