package com.algorithm.stack.sqstack;

import java.util.Scanner;

/**
 * @Author:Rp
 * @Description:利用栈进行符号匹配
 * @Data:2018/12/6_21:26
 */
public class BracketMatching {
    /**
     * 括号匹配问题 1、输入：含多种括号的表达式 2、输出:正确时显示匹配成功，错误显示匹配失败，并指出匹配错误 原因和位置
     * 3、可以对源文件进行成对出现的符号进行匹配（包括括号，引号）
     */
    private final int left = 0;
    private final int right = 1;
    private final int other = 2;

    // 判断为哪种字符串，返回int值
    public int very1(String str) {
        if ("(".equals(str) || "{".equals(str) || "[".equals(str) || "/*".equals(str))
            return left;
        else if (")".equals(str) || "]".equals(str) || "}".equals(str) || "*/".equals(str))
            return right;
        else
            return other;
    }

    // 判断括号是否匹配，boolean
    public boolean very2(String str1, String str2) {
        if ("(".equals(str1) && ")".equals(str2) || "[".equals(str1) && "]".equals(str2)
                || "{".equals(str1) && "}".equals(str2) || "/*".equals(str1) && "*/".equals(str2))
            return true;
        else
            return false;
    }

    /*
     * 主要思想：从左向右扫描Java语句，每次需要读取一个字符， 1、如果发现其为左分隔符，将其压入栈中；
     * 2、当从输入中读到右分隔符时，则弹出栈顶的左分隔符，并且看他是否有右分隔符匹配，如果他们不匹配，则匹配失败， 3、若栈空或者栈中存在左分隔符，则匹配失败
     * 4、若所有的字符读入结束后，栈为空（所有的左分隔符都被匹配完了），则匹配成功
     */
    private boolean isLegal(String str) throws Exception {
        if (!"".equals(str) && str != null) {
            // 使用栈解决
            SqStack s = new SqStack(100); // 新建最大空间为100的顺序栈
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char c = str.charAt(i);
                String t = String.valueOf(c); // 字符转换成字符串型
                if (i != length) // c不是最后一个字符
                {
                    if ('/' == c && '*' == str.charAt(i + 1) || '*' == c && '/' == str.charAt(i + 1)) {
                        t = t.concat(String.valueOf(str.charAt(i + 1)));// 将注释符号/*连接成字符串
                        ++i; // 跳过一个字符
                    }
                }
                String temp;
                if (t.equals("{")) {    // 用于判断{}是否被()嵌套着
                    if (!s.isEmpty()) {
                        temp = s.pop().toString();
                        if (temp.equals("(")) {
                            return false;
                        } else {
                            s.push(temp);    // 如果没有被嵌套着则，把出栈的元素在入进去
                        }
                    }
                }

                if (left == very1(t)) // t为左分隔符
                {
                    s.push(t);    // 进栈
                } else if (right == very1(t)) {
                    if (s.isEmpty() || !very2(s.pop().toString(), t)) {    //栈为空或者出栈进行匹配判断
                        throw new Exception("错误！");
                    }
                }

            }
            if (!s.isEmpty()) {    // 整个表达式遍历完还不是空的，那就说明有问题
                System.out.println("错误！Java语句不合法！");
                return false;
            }
            return true;    // 没有前面问题的，则返回True
        } else
            throw new Exception("错误！Java语句为空！无法匹配！");
    }

    public static void main(String[] args) {
        BracketMatching d = new BracketMatching();
        System.out.println("请输入表达式：");
        Scanner sc = new Scanner(System.in);
        try {
            if (d.isLegal(sc.nextLine()))
                System.out.println("合法语句");
            else
                System.out.println("不合法语句");
        } catch (Exception e) {
            System.out.println("不合法语句");
        }
    }
}

