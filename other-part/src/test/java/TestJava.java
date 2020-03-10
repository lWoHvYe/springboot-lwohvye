import com.lwohvye.springboot.dubbointerface.common.util.TreeNode;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Hongyan Wang
 * @packageName PACKAGE_NAME
 * @className Test
 * @description
 * @date 2020/2/23 11:32
 */
public class TestJava {

    @Test
    public void Test() throws InterruptedException {
//        创建对象并赋参数值
        Demo demo1 = new Demo();
//        开启新线程
        demo1.setZdl("字段1");
        new Thread(demo1).start();
        Thread.sleep(2000);
//        Demo demo2 = new Demo("字段2");
        demo1.setZdl("字段2");
        new Thread(demo1).start();
//        这部分是为了等其他线程执行结束 ，可以试着去掉这部分，看看效果
        Thread.sleep(5000);

        System.out.println("执行结束");
    }

    @Getter
    @Setter
    class Demo implements Runnable {
        private String zdl;

//        Demo(String zdl) {
//            this.zdl = zdl;
//        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + zdl);
        }
    }

    //输出等腰三角形
    public static void main(String[] args) {
        while (true) {
            //接收输入参数
            Scanner scanner = new Scanner(System.in);
//        int n = Integer.parseInt(scanner.nextLine());
            int n = scanner.nextInt();

//        int n = 4;
            StringBuffer sb = new StringBuffer();
//        i控制行数,j和k控制列输出的内容
            for (int i = 0; i < n; i++) {
//                i递增，j递减,在j<i时输出#
                for (int j = n; j >= 0; j--) {
                    if (j > i) {
                        sb.append(" ");
                    } else {
                        sb.append("#");
                    }
                }
                for (int k = 0; k <= n; k++) {
                    if (k < i) {
                        sb.append("#");
                    } else {
                        sb.append(" ");
                    }
                }
                sb.append("\n");
            }
            System.out.print(sb);
        }
    }

    class Solution {
        public List<Integer> selfDividingNumbers(int left, int right) {
            List<Integer> list = new ArrayList<>();
            if (left > right) {
                int temp = left;
                left = right;
                right = temp;
            }
            for (int i = left; i <= right; i++) {
                boolean flag = true;
                String value = String.valueOf(i);
                if (value.contains("0"))
                    continue;
                char[] chars = value.toCharArray();
                for (char c : chars) {
                    int ys = i % (c - '0');
                    if (ys != 0) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    list.add(i);
            }
            return list;
        }
    }

    //计算二叉树最大直径
    class Solution01 {
        int ans;

        public int diameterOfBinaryTree(TreeNode root) {
            ans = 1;
            depth(root);
            return ans - 1;
        }

        public int depth(TreeNode node) {
            if (node == null) return 0; // 访问到空节点了，返回0
            int L = depth(node.left); // 左儿子为根的子树的深度
            int R = depth(node.right); // 右儿子为根的子树的深度
            ans = Math.max(ans, L + R + 1); // 计算d_node即L+R+1 并更新ans
            return Math.max(L, R) + 1; // 返回该节点为根的子树的深度
        }

    }

    //模拟计算器，除法使用/，假设所传字符串可运算且不会造成溢出
    class solution227 {
        int calculate(String str) {
//            字符串转char数组
            char[] s = str.toCharArray();
//            初始化栈
            Stack<Integer> stk = new Stack<>();
            // 记录算式中的数字
            int num = 0;
            // 记录 num 前的符号，初始化为 +
            char sign = '+';
            for (int i = 0; i < s.length; i++) {
                char c = s[i];
                // 如果是数字，连续读取到 num，用于获取当个数值
                //Character.isDigit用于判断字符是否为数字
                if (Character.isDigit(c))
                    //(c - '0')的这个括号不能省略，否则可能造成整型溢出。
                    num = 10 * num + (c - '0');
                //对括号的处理是，碰到
                // 如果不是数字，就是遇到了下一个符号，要考虑不是数字且不是空格的情况
                // 之前的数字和符号就要存进栈中
                if ((!Character.isDigit(c) && c != ' ') || i == s.length - 1) {
                    int pre;
                    switch (sign) {
                        case '+':
                            stk.push(num);
                            break;
                        case '-':
                            stk.push(-num);
                            break;
                        // 只要拿出前一个数字做对应运算即可
                        case '*':
//                            peek()用于获取栈顶元素
//                            pre = stk.peek();
//                            pop()用于弹出栈顶元素，并返回栈顶的值
                            pre = stk.pop();
//                            入栈
                            stk.push(pre * num);
                            break;
                        case '/':
//                            pre = stk.peek();
                            pre = stk.pop();
                            stk.push(pre / num);
                            break;

                    }
                    // 更新符号为当前符号，数字清零
                    sign = c;
                    num = 0;
                }
            }
            // 将栈中所有结果求和就是答案
            int res = 0;
            while (!stk.empty()) {
//                res += stk.peek();
                res += stk.pop();
            }
            return res;
        }

    }
}