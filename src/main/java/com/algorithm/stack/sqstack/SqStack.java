package com.algorithm.stack.sqstack;
/**
 *@Author:Rp
 *@Description:栈的实现
 *@Data:2018/12/6_21:26
 */
public class SqStack {
    private Object[] stackElem;
    private int top;

    public SqStack(int maxSize)        // 栈初始化
    {
        top = 0;    // 栈顶没有元素
        stackElem = new Object[maxSize];    // 指针指向新申请的空间
    }

    public void clear() //空栈状态
    {
        top = 0;
    }

    public int length() //栈中元素个数
    {
        return top;
    }

    public boolean isEmpty() //判栈空
    {
        if (top == 0) {
            return true;
        } else
            return false;
    }

    public Object peek() //取栈顶元素位置
    {
        if (!isEmpty())
            return stackElem[top - 1];
        else
            return null;
    }

    //将元素x压入栈中
    public void push(Object x) throws Exception {
        if (top == stackElem.length)
            throw new Exception("栈已满");    // 栈内存不够用
        else
            stackElem[top++] = x;    //进栈，然后top向上移
    }

    //删除并返回栈顶元素
    public Object pop() {
        if (isEmpty())
            return null;
        else
            return stackElem[--top];    // top向下移，然后把指向的元素返回回去
    }

}
