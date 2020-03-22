package com.lwohvye.springboot.otherpart.local.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

/**
 * @author Hongyan Wang
 * @description 计算器，主要是对栈的应用
 * 定义数值栈和符号栈，如果数字直接入对应栈，如果符号先判断优先级，低优先级会取数值栈顶部俩和符号栈顶部一个进行运算
 * @date 2020/3/10 12:26
 */
public class FormulaUtil {

    private int scale; // 进行除法出现无线循环小数时保留的精度

    /**
     * 数字栈：用于存储表达式中的各个数字
     */
    private Stack<BigDecimal> numberStack = null;
    /**
     * 符号栈：用于存储运算符和括号
     */
    private Stack<Character> symbolStack = null;

    public FormulaUtil(int scale) {
        super();
        this.scale = scale;
    }

    public FormulaUtil() {
        this(32);
    }

    /**
     * 解析并计算四则运算表达式(含括号优先级)，返回计算结果
     *
     * @param numStr 算术表达式(含括号)
     */
    public BigDecimal caculate(String numStr) {
        numStr = removeStrSpace(numStr); // 去除空格
        // 如果算术表达式尾部没有‘=’号，则在尾部添加‘=’，表示结束符
        if (numStr.length() > 1 && !"=".equals(numStr.charAt(numStr.length() - 1) + "")) {
            numStr += "=";
        }
        // 检查表达式是否合法
        if (!isStandard(numStr)) {
            System.err.println("错误：算术表达式有误！");
            return null;
        }
        // 初始化栈
        if (numberStack == null) {
            numberStack = new Stack<>();
        }
        numberStack.clear();
        if (symbolStack == null) {
            symbolStack = new Stack<>();
        }
        symbolStack.clear();
        // 用于缓存数字，因为数字可能是多位的
        StringBuffer temp = new StringBuffer();
        // 从表达式的第一个字符开始处理
        for (int i = 0; i < numStr.length(); i++) {
            char ch = numStr.charAt(i); // 获取一个字符
            if (isNumber(ch)) { // 若当前字符是数字
                temp.append(ch); // 加入到数字缓存中
            } else { // 非数字的情况
                String tempStr = temp.toString(); // 将数字缓存转为字符串
                if (!tempStr.isEmpty()) {
                    // long num = Long.parseLong(tempStr); // 将数字字符串转为长整型数
                    BigDecimal num = new BigDecimal(tempStr);
                    numberStack.push(num); // 将数字压栈
                    temp = new StringBuffer(); // 重置数字缓存
                }
                // 判断运算符的优先级，若当前优先级低于栈顶的优先级，则先把计算前面计算出来
                while (!comparePri(ch) && !symbolStack.empty()) {
                    BigDecimal b = numberStack.pop(); // 出栈，取出数字，后进先出
                    BigDecimal a = numberStack.pop();
                    // 取出运算符进行相应运算，并把结果压栈进行下一次运算
                    switch (symbolStack.pop()) {
                        case '+':
                            numberStack.push(a.add(b));
                            break;
                        case '-':
                            numberStack.push(a.subtract(b));
                            break;
                        case '*':
                            numberStack.push(a.multiply(b));
                            break;
                        case '/':
                            try {
                                numberStack.push(a.divide(b));
                            } catch (java.lang.ArithmeticException e) {
                                // 进行除法出现无限循环小数时，就会抛异常，此处设置精度重新计算
                                numberStack.push(a.divide(b, this.scale,
                                        RoundingMode.HALF_EVEN));
                            }
                            break;
                        default:
                            break;
                    }
                } // while循环结束
                if (ch != '=') {
                    symbolStack.push(ch); // 符号入栈
                    if (ch == ')') { // 去括号
//                        弹出刚压入的)
                        symbolStack.pop();
//                        弹出栈顶的(
                        symbolStack.pop();
                    }
                }
            }
        } // for循环结束

        return numberStack.pop(); // 返回计算结果
    }

    /**
     * 去除字符串中的所有空格
     */
    private String removeStrSpace(String str) {
        return str != null ? str.replaceAll(" ", "") : "";
    }

    /**
     * 检查算术表达式的基本合法性，符合返回true，否则false
     */
    private boolean isStandard(String numStr) {
        if (numStr == null || numStr.isEmpty()) // 表达式不能为空
            return false;
        // 用来保存括号，检查左右括号是否匹配，遇到( 压入栈，遇到) 弹出栈顶，如果最后栈不为空，或无可弹项，即为不匹配
        Stack<Character> stack = new Stack<>();
        boolean b = false; // 用来标记'='符号是否存在多个
        for (int i = 0; i < numStr.length(); i++) {
            char n = numStr.charAt(i);
            // 判断字符是否合法，只可包含数字 . + - * / ( )
            if (!(isNumber(n) || "(".equals(n + "") || ")".equals(n + "")
                  || "+".equals(n + "") || "-".equals(n + "")
                  || "*".equals(n + "") || "/".equals(n + "")
                  || "=".equals(n + ""))) {
                return false;
            }
            // 将左括号压栈，用来给后面的右括号进行匹配
            if ("(".equals(n + "")) {
                stack.push(n);
            }
            if (")".equals(n + "")) { // 匹配括号
                if (stack.isEmpty() || !"(".equals((char) stack.pop() + "")) // 括号是否匹配
                    return false;
            }
            // 检查是否有多个'='号
            if ("=".equals(n + "")) {
                if (b)
                    return false;
                b = true;
            }
        }
        // 可能会有缺少右括号的情况
        if (!stack.isEmpty())
            return false;
        // 检查'='号是否不在末尾
        if (!("=".equals(numStr.charAt(numStr.length() - 1) + "")))
            return false;
        return true;
    }

    /**
     * 判断字符是否是0-9的数字
     */
    private boolean isNumber(char num) {
        //Charactor.isDigit()用于判断是否为数字
        return Character.isDigit(num) || num == '.';
    }

    /**
     * 比较优先级：如果当前运算符比栈顶元素运算符优先级高则返回true，否则返回false
     */
    private boolean comparePri(char symbol) {
        if (symbolStack.empty()) { // 空栈返回ture
            return true;
        }

        // 符号优先级说明（从高到低）:
        // 第1级: (
        // 第2级: * /
        // 第3级: + -
        // 第4级: )

        char top = symbolStack.peek(); // 查看堆栈顶部的对象，注意不是出栈
        if (top == '(') {
            return true;
        }
        // 比较优先级
        switch (symbol) {
            case '(': // 优先级最高
                return true;
            case '*':
            case '/': {
                // 优先级比+和-高
                return top == '+' || top == '-';
            }
            case '+':
            case '-':
                return false;
            case ')': // 优先级最低
                return false;
            case '=': // 结束符
                return false;
            default:
                break;
        }
        return true;
    }

    // 测试
    public static void main(String args[]) {
        String numStr = "((1+2)*3)/9+(3+2)*4 "; // 默认的算式
        numStr="(1+(4+5+2)-3)+(6+8)";
        BigDecimal result = new FormulaUtil().caculate(numStr); // 计算算式的结果
        System.out.println(numStr + "=");
        System.out.println(result);
    }
}