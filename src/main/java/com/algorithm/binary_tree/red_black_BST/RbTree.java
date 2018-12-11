package com.algorithm.binary_tree.red_black_BST;


/**
 * @author linxu
 * @version 1.0
 * 红黑树的实现
 */
public class RbTree<T extends Comparable<T>> {
    private RbNode<T> rootNode;
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private T t;
    public void setT(T value){
        t=value;
    }

    /**
     * Constructor
     */
    public RbTree() {
        rootNode = new RbNode<T>(t ,true,null,null,null);
    }


    public RbNode<T> init(T key, boolean color) {
        return new RbNode<T>(key, color, null, null, null);
    }

    private class RbNode<T extends Comparable<T>> {
        boolean color;
        T key;
        RbNode<T> leftNode;
        RbNode<T> rightNode;
        RbNode<T> parentNode;

        /**
         * Constructor
         *
         * @param key
         * @param color
         * @param parentNode
         * @param rightNode
         * @param leftNode
         */
        public RbNode(T key, boolean color, RbNode<T> parentNode, RbNode<T> rightNode, RbNode<T> leftNode) {
            this.key = key;
            this.color = color;
            this.parentNode = parentNode;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public T getKey() {
            return key;
        }

        @Override
        public String toString() {
            return " " + key + (this.color == RED ? "(R)" : "B");
        }
    }

    /**
     * 左旋方法；
     *
     * @param node 旋转结点；
     */
    private void leftNodeRorate(RbNode<T> node) {
        RbNode<T> rightNodeChild = node.rightNode;
        //当rightNodeChild的左孩子不为空时，将Y的的左孩子设置为NODE的右孩子；
        if (rightNodeChild.leftNode != null) {
            rightNodeChild.leftNode.parentNode = node;
        }
        //更换结点位置
        rightNodeChild.parentNode = node.parentNode;
        //如果单纯一个结点，那么转换之后父节点就是子右结点。
        if (node.parentNode == null) {
            this.rootNode = rightNodeChild;
        } else {
            //如果NODE是父结点的左孩子，那么rightNodeCHILD设为NODE父结点的左孩子；
            if (node.parentNode.leftNode == node) {
                node.parentNode.leftNode = rightNodeChild;
            } else {
                node.parentNode.rightNode = rightNodeChild;
            }
        }
        //实现旋转；
        rightNodeChild.leftNode = node;
        node.parentNode = rightNodeChild;
    }

    /**
     * 右旋方法
     *
     * @param node 旋转结点
     */
    private void rightNodeRorate(RbNode<T> node) {
        RbNode<T> leftNodeChild = node.leftNode;
        node.leftNode = leftNodeChild.rightNode;
        //如果左孩子的右孩子不为空，那么将左孩子的右孩子给NODE；
        if (leftNodeChild.rightNode != null) {
            leftNodeChild.rightNode.parentNode = node;
        }
        leftNodeChild.parentNode = node.parentNode;
        if (node.parentNode == null) {
            this.rootNode = leftNodeChild;
        } else {
            if (node == node.parentNode.rightNode) {
                node.parentNode.rightNode = leftNodeChild;
            } else {
                node.parentNode.leftNode = leftNodeChild;
            }
        }
        leftNodeChild.rightNode = node;
        node.parentNode = leftNodeChild;
    }

    /**
     * 找到父亲
     *
     * @param node 子节点
     * @return 父节点
     */
    private RbNode<T> getParentNode(RbNode<T> node) {
        return node != null ? node.parentNode : null;
    }

    /**
     * 空的默认黑色，不为空则返回颜色；
     *
     * @param node 结点
     * @return 颜色
     */
    private boolean colorOf(RbNode<T> node) {
        return node != null ? node.color : BLACK;
    }

    /**
     * 初始化颜色；
     *
     * @param node  结点
     * @param color 颜色
     */
    private void setColor(RbNode<T> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    /**
     * 是否是红色
     *
     * @param node 结点
     * @return
     */
    private boolean isRed(RbNode<T> node) {
        return (node != null) && (node.color) == RED;
    }

    /**
     * 设置父亲
     *
     * @param node
     * @param parentNode
     */
    private void setParent(RbNode<T> node, RbNode<T> parentNode) {
        if (node != null) {
            node.parentNode = parentNode;
        }
    }

    /**
     * 前序遍历红黑树
     *
     * @param rbTree 传入结点开始遍历
     */
    private void preThrough(RbNode<T> rbTree) {
        if (rbTree != null) {
            System.out.println(rbTree.key + "->");
            preThrough(rbTree.leftNode);
            preThrough(rbTree.rightNode);
        }
    }

    /**
     * 重载全前序遍历
     */
    public void preThrough() {
        preThrough(rootNode);
    }

    /**
     * 中序遍历红黑树
     *
     * @param rbTree 传入结点开始遍历
     */
    private void inThrough(RbNode<T> rbTree) {
        if (rbTree != null) {
            preThrough(rbTree.leftNode);
            System.out.println(rbTree.key + "->");
            preThrough(rbTree.rightNode);
        }
    }

    /**
     * 中序输出
     */
    public void inThrough() {
        inThrough(rootNode);
    }

    /**
     * 后序遍历
     *
     * @param rbTree 结点
     */
    private void postThrough(RbNode<T> rbTree) {
        if (rbTree != null) {
            preThrough(rbTree.leftNode);
            preThrough(rbTree.rightNode);
            System.out.println(rbTree.key + "->");
        }
    }

    public void postThrough() {
        preThrough(rootNode);
    }

    /**
     * 插入结点；
     *
     * @param node 新结点
     */
    private void insert(RbNode<T> node) {
        int cmp;
        RbNode<T> y = null;
        RbNode<T> x = this.rootNode;
        //首先将RBtree看作二叉查找树，插入结点；
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0) {
                x = x.leftNode;
            } else {
                x = x.rightNode;
            }
        }
        node.parentNode = y;
        if (y != null) {
            cmp = node.key.compareTo(x.key);
            if (cmp < 0) {
                y.leftNode = node;
            } else {
                y.rightNode = node;
            }
        }
        //设置结点的颜色
        node.color = RED;
        //修正为一棵二叉查找树
        insertFixUp(node);
    }

    /**
     * 创建新结点并且插入
     *
     * @param key
     */
    public void insert(T key) {
        RbNode<T> node = new RbNode<T>(key, BLACK, null, null, null);
        if (node!=null)
        insert(node);
    }

    /**
     * 修正方法；
     *
     * @param node 插入结点；
     */
    private void insertFixUp(RbNode<T> node) {
        RbNode<T> parent, gparent;

        // 若“父节点存在，并且父节点的颜色是红色”
        while (((parent = getParentNode(node)) != null) && isRed(parent)) {
            gparent = getParentNode(parent);

            //若“父节点”是“祖父节点的左孩子”
            if (parent == gparent.leftNode) {
                // Case 1条件：叔叔节点是红色
                RbNode<T> uncle = gparent.rightNode;
                if (isRed(uncle)) {
                    setColor(uncle, BLACK);
                    setColor(parent, BLACK);
                    setColor(gparent, RED);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if (parent.rightNode == node) {
                    RbNode<T> tmp;
                    leftNodeRorate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setColor(parent, BLACK);
                setColor(gparent, RED);
                rightNodeRorate(gparent);
            } else {    //若“z的父节点”是“z的祖父节点的右孩子”
                // Case 1条件：叔叔节点是红色
                RbNode<T> uncle = gparent.leftNode;
                if (isRed(uncle)) {
                    setColor(uncle, BLACK);
                    setColor(parent, BLACK);
                    setColor(gparent, RED);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.leftNode == node) {
                    RbNode<T> tmp;
                    rightNodeRorate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setColor(parent, BLACK);
                setColor(gparent, RED);
                leftNodeRorate(gparent);
            }
        }
        // 将根节点设为黑色
        setColor(this.rootNode, BLACK);
    }

    /**
     * 结点删除
     *
     * @param node
     */
    private void remove(RbNode<T> node) {
        RbNode<T> child, parent;
        boolean color;

        // 被删除节点的"左右孩子都不为空"的情况。
        if ((node.leftNode != null) && (node.rightNode != null)) {
            // 被删节点的后继节点。(称为"取代节点")
            // 用它来取代"被删节点"的位置，然后再将"被删节点"去掉。
            RbNode<T> replace = node;

            // 获取后继节点
            replace = replace.rightNode;
            while (replace.leftNode != null) {
                replace = replace.leftNode;
            }
            // "node节点"不是根节点(只有根节点不存在父节点)
            if (getParentNode(node) != null) {
                if (getParentNode(node).leftNode == node) {
                    getParentNode(node).leftNode = replace;
                } else {
                    getParentNode(node).rightNode = replace;
                }
            } else {
                // "node节点"是根节点，更新根节点。
                this.rootNode = replace;
            }

            // child是"取代节点"的右孩子，也是需要"调整的节点"。
            // "取代节点"肯定不存在左孩子！因为它是一个后继节点。
            child = replace.rightNode;
            parent = getParentNode(replace);
            // 保存"取代节点"的颜色
            color = colorOf(replace);

            // "被删除节点"是"它的后继节点的父节点"
            if (parent == node) {
                parent = replace;
            } else {
                // child不为空
                if (child != null) {
                    setParent(child, parent);
                }
                parent.leftNode = child;

                replace.rightNode = node.rightNode;
                setParent(node.rightNode, replace);
            }

            replace.parentNode = node.parentNode;
            replace.color = node.color;
            replace.leftNode = node.leftNode;
            node.leftNode.parentNode = replace;

            if (color == BLACK) {
                removeFixUp(child, parent);
            }

            node = null;
            return;
        }

        if (node.leftNode != null) {
            child = node.leftNode;
        } else {
            child = node.rightNode;
        }

        parent = node.parentNode;
        // 保存"取代节点"的颜色
        color = node.color;

        if (child != null) {
            child.parentNode = parent;
        }

        // "node节点"不是根节点
        if (parent != null) {
            if (parent.leftNode == node) {
                parent.leftNode = child;
            } else {
                parent.rightNode = child;
            }
        } else {
            this.rootNode = child;
        }

        if (color == BLACK) {
            removeFixUp(child, parent);
        }
        node = null;
    }

    /**
     * 查找并返回删除结点
     *
     * @param key VALUE
     */
    public void remove(T key) {
        RbNode<T> node;

        if ((node = search(rootNode, key)) != null) {
            remove(node);
        }
    }

    private void removeFixUp(RbNode<T> node, RbNode<T> parent) {
        RbNode<T> other;

        while ((!isRed(node)) && (node != this.rootNode)) {
            if (parent.leftNode == node) {
                other = parent.rightNode;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setColor(other, BLACK);
                    setColor(parent, RED);
                    leftNodeRorate(parent);
                    other = parent.rightNode;
                }

                if (!isRed(other.leftNode) && !isRed(other.rightNode)) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setColor(other, RED);
                    node = parent;
                    parent = getParentNode(node);
                } else {

                    if (!isRed(other.rightNode)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setColor(other.leftNode, BLACK);
                        setColor(other, RED);
                        rightNodeRorate(other);
                        other = parent.rightNode;
                    }
                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setColor(parent, BLACK);
                    setColor(other.rightNode, BLACK);
                    leftNodeRorate(parent);
                    node = this.rootNode;
                    break;
                }
            } else {

                other = parent.leftNode;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setColor(other, BLACK);
                    setColor(parent, RED);
                    rightNodeRorate(parent);
                    other = parent.leftNode;
                }

                if (!isRed(other.leftNode) && (!isRed(other.rightNode))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setColor(other, RED);
                    node = parent;
                    parent = getParentNode(node);
                } else {

                    if (!isRed(other.leftNode)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setColor(other.rightNode, BLACK);
                        setColor(other, RED);
                        leftNodeRorate(other);
                        other = parent.leftNode;
                    }

                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setColor(parent, BLACK);
                    setColor(other, RED);
                    rightNodeRorate(parent);
                    node = this.rootNode;
                    break;
                }
            }
        }
        if (node != null) {
            setColor(node, BLACK);
        }
    }

    /**
     * 删除节点；
     *
     * @param node 结点
     */
    private void destroy(RbNode<T> node) {
        if (node==null)
            return ;
        if (node.leftNode != null) {
            destroy(node.leftNode);
        }
        if (node.rightNode != null) {
            destroy(node.rightNode);
        }
    }

    /**
     * 整个树删除
     */
    public void destroyAllTree() {
        destroy(rootNode);
        rootNode = null;
    }

    private RbNode<T> search(RbNode<T> x, T key) {
        if (x == null) {
            return x;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return search(x.leftNode, key);
        } else if (cmp > 0) {
            return search(x.rightNode, key);
        } else {
            return x;
        }
    }

    public RbNode<T> search(T key) {
        return search(rootNode, key);
    }

    private RbNode<T> iterativeSearch(RbNode<T> x, T key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0) {
                x = x.leftNode;
            } else if (cmp > 0) {
                x = x.rightNode;
            } else {
                return x;
            }
        }

        return x;
    }

    public RbNode<T> iterativeSearch(T key) {
        return iterativeSearch(rootNode, key);
    }

    private RbNode<T> minimum(RbNode<T> tree) {
        if (tree == null) {
            return null;
        }
        while (tree.leftNode != null) {
            tree = tree.leftNode;
        }
        return tree;
    }

    public T minimum() {
        RbNode<T> p = minimum(rootNode);
        if (p != null) {
            return p.key;
        }
        return null;
    }

    private RbNode<T> maximum(RbNode<T> tree) {
        if (tree == null) {
            return null;
        }
        while (tree.rightNode != null) {
            tree = tree.rightNode;
        }
        return tree;
    }

    public T maximum() {
        RbNode<T> p = maximum(rootNode);
        if (p != null) {
            return p.key;
        }
        return null;
    }

    public RbNode<T> successor(RbNode<T> x) {
        // 如果x存在右孩子，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"。
        if (x.rightNode != null) {
            return minimum(x.rightNode);
        }

        // 如果x没有右孩子。则x有以下两种可能：
        // (01) x是"一个左孩子"，则"x的后继结点"为 "它的父结点"。
        // (02) x是"一个右孩子"，则查找"x的最低的父结点，并且该父结点要具有左孩子"，找到的这个"最低的父结点"就是"x的后继结点"。
        RbNode<T> y = x.parentNode;
        while ((y != null) && (x == y.rightNode)) {
            x = y;
            y = y.parentNode;
        }

        return y;
    }

    public RbNode<T> predecessor(RbNode<T> x) {
        // 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
        if (x.leftNode != null) {
            return maximum(x.leftNode);
        }

        // 如果x没有左孩子。则x有以下两种可能：
        // (01) x是"一个右孩子"，则"x的前驱结点"为 "它的父结点"。
        // (01) x是"一个左孩子"，则查找"x的最低的父结点，并且该父结点要具有右孩子"，找到的这个"最低的父结点"就是"x的前驱结点"。
        RbNode<T> y = x.parentNode;
        while ((y != null) && (x == y.leftNode)) {
            x = y;
            y = y.parentNode;
        }
        return y;
    }

    //输出
    private void print(RbNode<T> tree, T key, int direction) {

        if (tree != null) {

            if (direction == 0) {
                System.out.printf("%2d(B) is root\n", tree.key);
            } else {            // tree是分支节点
                System.out.printf("%2d(%s) is %2d's %6s child\n", tree.key, isRed(tree) ? "R" : "B", key, direction == 1 ? "right" : "left");
            }

            print(tree.leftNode, tree.key, -1);
            print(tree.rightNode, tree.key, 1);
        }
    }

    public void print() {
        if (rootNode != null) {
            print(rootNode, rootNode.key, 0);
        }
    }

}
