package com.wjj;

import java.util.Comparator;

/**
 * Avl树
 *
 * @param <T>
 * @author Aldebran
 * @since 25/09/2020
 */
public class AvlTree<T> {

    public AvlNode<T> root;

    private Comparator<T> comparator;

    public AvlTree(final Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public AvlTree() {

    }

    private int compare(final T v1, final T v2) {
        if (comparator != null) {
            return comparator.compare(v1, v2);
        } else if (v1 instanceof Comparable && v2 instanceof Comparable) {
            Comparable comparable1 = (Comparable) v1;
            return comparable1.compareTo(v2);
        } else {
            throw new RuntimeException("fail to compare values! ");
        }
    }

    private void setRelationship(AvlNode<T> parent, AvlNode<T> child, boolean left) {
        if (child != null) {
            child.parent = parent;
        }
        if (parent != null) {
            if (left) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
    }

    public T get(final T elem) {
        AvlNode<T> current = root;
        while (current != null) {
            int cmpValue = compare(elem, current.elem);
            if (cmpValue == 0) {
                return current.elem;
            } else {
                if (cmpValue < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
        }
        return null;
    }

    public void insert(T elem) {
        AvlNode<T> newNode = new AvlNode<>(elem);
        // 插入根节点
        if (root == null) {
            root = newNode;
            return;
        }
        AvlNode<T> current = root;
        int cmpValue = 0;
        while (true) {
            cmpValue = compare(elem, current.elem);
            if (cmpValue == 0) {
                return;
            } else {
                if (cmpValue < 0) {
                    if (current.left == null) {
                        setRelationship(current, newNode, true);
                        current = newNode;
                        break;
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null) {
                        setRelationship(current, newNode, false);
                        current = newNode;
                        break;
                    } else {
                        current = current.right;
                    }
                }
            }
        }
        // 平衡操作， 向上回溯
        AvlNode<T> p = current;
        current = current.parent;
        AvlNode<T> pp = null;
        while (current != null) {
            int originH = current.height;
            int newH = Integer.max(current.leftHeight(), current.rightHeight()) + 1;
            if (newH > originH) {
                current.height = newH;
            } else {
                // 高度没变化，不再影响上级的平衡
                return;
            }
            if (!current.isBalanced()) {
                // 找到了不平衡节点
                break;
            }
            pp = p;
            p = current;
            current = current.parent;
        }
        // 没找到不平衡节点
        if (current == null) {
            return;
        }
        if (pp == null || p == null) {
            throw new RuntimeException("illegal AVL tree! ");
        }
        boolean L1 = (p == current.left);
        boolean L2 = (pp == p.left);
        // 执行对应的旋转操作
        if (L1) {
            if (L2) {
                LL(current, p, pp);
            } else {
                LR(current, p, pp);
            }
        } else {
            if (L2) {
                RL(current, p, pp);
            } else {
                RR(current, p, pp);
            }
        }
    }

    /**
     * LL右单旋
     */
    private void LL(AvlNode<T> current, AvlNode<T> p, AvlNode<T> pp) {
        AvlNode<T> pOriginRight = p.right;
        AvlNode<T> currentOriginParent = current.parent;
        boolean currentOriginParentLeft = current.parent == null ? false : (current == current.parent.left);
        setRelationship(p, current, false);
        setRelationship(current, pOriginRight, true);
        setRelationship(currentOriginParent, p, currentOriginParentLeft);
        if (currentOriginParent == null) {
            root = p;
        }
        // 更新高度
        current.height = Integer.max(current.leftHeight(), current.rightHeight()) + 1;
        p.height = Integer.max(p.leftHeight(), p.rightHeight()) + 1;
    }

    /**
     * RR左单旋
     */
    private void RR(AvlNode<T> current, AvlNode<T> p, AvlNode<T> pp) {
        AvlNode<T> pOriginLeft = p.left;
        AvlNode<T> currentOriginParent = current.parent;
        boolean currentOriginParentLeft = current.parent == null ? false : (current == current.parent.left);
        setRelationship(p, current, true);
        setRelationship(current, pOriginLeft, false);
        setRelationship(currentOriginParent, p, currentOriginParentLeft);
        if (currentOriginParent == null) {
            root = p;
        }
        // 更新高度
        current.height = Integer.max(current.leftHeight(), current.rightHeight()) + 1;
        p.height = Integer.max(p.leftHeight(), p.rightHeight()) + 1;
    }

    /**
     * LR，效果等同于左单旋+右单旋
     */
    private void LR(AvlNode<T> current, AvlNode<T> p, AvlNode<T> pp) {
        AvlNode<T> ppOriginLeft = pp.left;
        AvlNode<T> ppOriginRight = pp.right;
        AvlNode<T> currentOriginParent = current.parent;
        boolean currentOriginParentLeft = current.parent == null ? false : (current == current.parent.left);
        setRelationship(pp, current, false);
        setRelationship(pp, p, true);
        setRelationship(p, ppOriginLeft, false);
        setRelationship(current, ppOriginRight, true);
        setRelationship(currentOriginParent, pp, currentOriginParentLeft);
        if (currentOriginParent == null) {
            root = pp;
        }
        // 改变高度
        current.height = Integer.max(current.leftHeight(), current.rightHeight()) + 1;
        p.height = Integer.max(p.leftHeight(), p.rightHeight()) + 1;
        pp.height = Integer.max(pp.leftHeight(), pp.rightHeight()) + 1;
    }

    /**
     * RL，效果等同于右单旋+左单旋
     */
    private void RL(AvlNode<T> current, AvlNode<T> p, AvlNode<T> pp) {
        AvlNode<T> ppOriginRight = pp.right;
        AvlNode<T> ppOriginLeft = pp.left;
        AvlNode<T> currentOriginParent = current.parent;
        boolean currentOriginParentLeft = current.parent == null ? false : (current == current.parent.left);
        setRelationship(pp, current, true);
        setRelationship(pp, p, false);
        setRelationship(p, ppOriginRight, true);
        setRelationship(current, ppOriginLeft, false);
        setRelationship(currentOriginParent, pp, currentOriginParentLeft);
        if (currentOriginParent == null) {
            root = pp;
        }
        // 改变高度
        current.height = Integer.max(current.leftHeight(), current.rightHeight()) + 1;
        p.height = Integer.max(p.leftHeight(), p.rightHeight()) + 1;
        pp.height = Integer.max(pp.leftHeight(), pp.rightHeight()) + 1;
    }

}
