import com.lwohvye.springboot.dubbointerface.common.util.ListNode;
import com.lwohvye.springboot.dubbointerface.common.util.TreeNode;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.*;

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

    @Test
    public void Test227() {

        Solution876 solution227 = new Solution876();
        var calculate = solution227.middleNode2(ListNode.makeNode(new int[]{0, 4, 6, 5, 6, 4, 5, 7, 2, 14, 4}));
        System.out.println(calculate);
    }

    class solution227 {
        public int calculate(String str) {
//            字符串转char数组
            char[] s = str.toCharArray();
//            初始化栈
            Stack<Integer> stk = new Stack<>();
            // 记录算式中的数字
            int num = 0;
            // 记录 num 前的符号，初始化为 +
            char sign = '+';
//            标识是否拼接字符串
            boolean flag = false;
//            遇到的括号数
            int count = 0;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < s.length; i++) {
                char c = s[i];
                // 如果是数字，连续读取到 num，用于获取当个数值
                //Character.isDigit用于判断字符是否为数字
                if (Character.isDigit(c))
                //(c - '0')的这个括号不能省略，否则可能造成整型溢出。
                {
                    num = 10 * num + (c - '0');
                }
//                当碰到左括号，设置拼接
                if ("(".equals(c + "")) {
//                    设置
                    flag = true;
//                    非第一个左括号，进行拼接
                    if (count != 0) {
                        sb.append(c);
                    }
//                    记录遇到的左括号数
                    count++;
                    continue;
                }
//                遇到右括号，进行判断
                if (")".equals(c + "")) {
//                    遇到右括号，数量减一
                    count--;
//                    数量减到0，进行循环调用
                    if (count == 0) {
//                        记录调用结果
                        num = calculate(sb.toString());
//                        设置不在进行拼接
                        flag = false;
//                        清空sb
                        sb = new StringBuffer();
                    }
                }
                if (flag) {
//                    进行拼接
                    sb.append(c);
                    continue;
                }

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

        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];
                if (map.containsKey(complement)) {
                    return new int[]{map.get(complement), i};
                }
                map.put(nums[i], i);
            }
            throw new IllegalArgumentException("No two sum solution");
        }

        public int reverse(int x) {
            if (x > 0) {
                StringBuilder builder = new StringBuilder(x + "").reverse();
                if (builder.length() > 8) {
                    int anInt = Integer.parseInt(builder.substring(0, builder.length() - 1));
                    if (Integer.MAX_VALUE / 10 < anInt)
                        return 0;
                }
                return Integer.parseInt(builder.toString());
            } else {
                if (x == Integer.MIN_VALUE)
                    return 0;
                StringBuilder builder = new StringBuilder(-x + "").reverse();
                if (builder.length() > 8) {
                    int anInt = Integer.parseInt(builder.substring(0, builder.length() - 1));
                    if (Integer.MAX_VALUE / 10 < anInt)
                        return 0;
                }
                return -Integer.parseInt(builder.toString());
            }
        }

        public int reverse1(int x) {
            int rev = 0;
            while (x != 0) {
//                算的最后一位，也是反转后的首位
                int pop = x % 10;
//                算的其他位
                x /= 10;
                if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
                if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
//                一位一位的往上加，直到出结果
                rev = rev * 10 + pop;
            }
            return rev;
        }

        public boolean isPalindrome(int x) {
            return (x + "").equals(new StringBuilder(x + "").reverse().toString());
        }

        public boolean isPalindrome1(int x) {
            // 特殊情况：
            // 如上所述，当 x < 0 时，x 不是回文数。
            // 同样地，如果数字的最后一位是 0，为了使该数字为回文，
            // 则其第一位数字也应该是 0
            // 只有 0 满足这一属性
            if (x < 0 || (x % 10 == 0 && x != 0)) {
                return false;
            }

            int revertedNumber = 0;
            //我们将原始数字除以 10，然后给反转后的数字乘上 10，所以，当原始数字小于反转后的数字时，就意味着我们已经处理了一半位数的数字。
            while (x > revertedNumber) {
                revertedNumber = revertedNumber * 10 + x % 10;
                x /= 10;
            }

            // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
            // 例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，revertedNumber = 123，
            // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
            return x == revertedNumber || x == revertedNumber / 10;
        }
    }

    class Solution13 {
        public int romanToInt(String s) {
            int sum = 0;
//            表示前一位
            int preNum = getValue(s.charAt(0));
            for (int i = 1; i < s.length(); i++) {
//                获取对应的值
                int num = getValue(s.charAt(i));
                if (preNum < num) {
//                    前一位比后一位小则减
                    sum -= preNum;
                } else {
//                    否则加
                    sum += preNum;
                }
//                后一位变为前一位，开始下一个循环
                preNum = num;
            }
            sum += preNum;
            return sum;
        }

        private int getValue(char ch) {
            switch (ch) {
                case 'I':
                    return 1;
                case 'V':
                    return 5;
                case 'X':
                    return 10;
                case 'L':
                    return 50;
                case 'C':
                    return 100;
                case 'D':
                    return 500;
                case 'M':
                    return 1000;
                default:
                    return 0;
            }
        }
    }

    class Solution14 {
        public String longestCommonPrefix(String[] strs) {
            String prox = "";
            if (strs.length == 0)
                return prox;
            String str = strs[0];
            if (strs.length == 1)
                return str;

//            前缀
//            是否继续遍历
            boolean flag = true;
            for (int i = 0; i < str.length(); i++) {
                if (!flag) {
                    break;
                }
                String proxs = str.substring(0, i + 1);

                for (int j = 1; j < strs.length; j++) {
                    String s = strs[j];
                    if (!s.startsWith(proxs)) {
//                        存在不匹配的，结束循环
                        flag = false;
//                        如果第一次就未匹配，则重置前缀值
                        if (i == 0)
                            prox = "";
                        break;
                    }

                }
                if (flag)
                    prox = proxs;
            }
            return prox;
        }
    }

    class Solution169 {


        public Integer majorityElement(int[] nums) {
            int n = nums.length >> 1;
            if (n == 0)
                return nums[0];
            Map<Integer, Integer> map = new HashMap<>();
            for (int num : nums) {
                if (!map.containsKey(num)) {
                    map.put(num, 1);
                } else {
                    int integer = map.get(num) + 1;
                    if (integer > n)
                        return num;
                    map.put(num, integer);
                }
            }
            return null;
        }

        public int majorityElement1(int[] nums) {
//            根据要求，元素排序后，下标是n/2的元素，其出现的次数一定大于n/2
            Arrays.sort(nums);
            return nums[nums.length / 2];
        }

        //摩尔投票法
        public Integer majorityElement2(int[] nums) {
            int count = 0;
            Integer candidate = null;
            for (int num : nums) {
//                得票为0,换人
                if (count == 0)
                    candidate = num;
                count += num == candidate ? 1 : -1;
            }
            return candidate;
        }
    }

    class Solution40 {
        //直接取最小
        public int[] getLeastNumbers(int[] arr, int k) {
            List<Integer> list = new ArrayList<>();
            int[] result = new int[k];
            for (int i : arr) {
                list.add(i);
            }
            for (int i = 0; i < k; i++) {
                Integer min = Collections.min(list);
                list.remove(min);
                result[i] = min;
            }
            return result;
        }

        //先排再取
        public int[] getLeastNumbers1(int[] arr, int k) {
            List<Integer> list = new ArrayList<>();
            int[] result = new int[k];
            for (int i : arr) {
                list.add(i);
            }
            list.sort(Integer::compareTo);

            for (int i = 0; i < k; i++) {
                result[i] = list.get(i);
            }
            return result;
        }

        //使用堆 大根堆(前K小) / 小根堆（前K大),Java中有现成的PriorityQueue，实现起来最简单：O(NlogK)
        public int[] getLeastNumbers2(int[] arr, int k) {
            // 保持堆的大小为K，然后遍历数组中的数字，遍历的时候做如下判断：
            // 1. 若目前堆的大小小于K，将当前数字放入堆中。
            // 2. 否则判断当前数字与大根堆堆顶元素的大小关系，如果当前数字比大根堆堆顶还大，这个数就直接跳过；
            //    反之如果当前数字比大根堆堆顶小，先poll掉堆顶，再将该数字放入堆中。
            if (k == 0 || arr.length == 0)
                return new int[0];
            // 默认是小根堆，实现大根堆需要重写一下比较器
            Queue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
            for (int num : arr) {
                if (queue.size() < k) {
                    //offer()用于放元素
                    queue.offer(num);
                    //peek()用于从队列头部取元素的值
                } else if (num < queue.peek()) {
                    //poll()用于删除头部元素
                    queue.poll();
                    queue.offer(num);
                }
            }
            int[] res = new int[queue.size()];
            int idx = 0;
            //将queue转成数组，因为是int类型，不能使用toArray转(支持String)，
            for (Integer num : queue) {
                res[idx++] = num;
            }
            return res;
        }

        //使用二叉树
        public int[] getLeastNumbers3(int[] arr, int k) {
            if (k == 0 || arr.length == 0) {
                return new int[0];
            }
            // TreeMap的key是数字, value是该数字的个数。数字个数这个属性主要是因为给定的数组可能出现重复的情况
            // cnt表示当前map总共存了多少个数字。
            TreeMap<Integer, Integer> map = new TreeMap<>();
            int cnt = 0;
            for (int num : arr) {
                // 1. 遍历数组，若当前map中的数字个数小于k，则map中当前数字对应个数+1
                if (cnt < k) {
                    map.put(num, map.getOrDefault(num, 0) + 1);
                    cnt++;
                    //结束此轮for循环
                    continue;
                }
                // 2. 否则，取出map中最大的Key（即最大的数字), 判断当前数字与map中最大数字的大小关系：lastEntity()获取最大值
                //    若当前数字比map中最大的数字还大，就直接忽略；
                //    若当前数字比map中最大的数字小，则将当前数字加入map中，并将map中的最大数字的个数-1。
                Map.Entry<Integer, Integer> entry = map.lastEntry();
                if (entry.getKey() > num) {
                    //将小的放进去
                    map.put(num, map.getOrDefault(num, 0) + 1);
                    //如果大的那个个数大于1则减去1，否则移除
                    if (entry.getValue() == 1) {
                        map.pollLastEntry();
                    } else {
                        map.put(entry.getKey(), entry.getValue() - 1);
                    }
                }

            }
            // 最后返回map中的元素
            int[] res = new int[k];
            int idx = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int freq = entry.getValue();
                while (freq-- > 0) {
                    res[idx++] = entry.getKey();
                }
            }
            return res;
        }
    }

    class Solution365 {
        //裴蜀定理（或贝祖定理）得名于法国数学家艾蒂安·裴蜀，说明了对任何整数a、b和它们的最大公约数d，关于未知数x和y的线性不定方程（称为裴蜀等式）：若a,b是整数,且gcd(a,b)=d，那么对于任意的整数x,y,ax+by都一定是d的倍数，特别地，一定存在整数x,y，使ax+by=d成立。
        //它的一个重要推论是：a,b互质的充要条件是存在整数x,y使ax+by=1.
        //关于最大公约数gcd()，可以自写，也可以使用现有的方法
        public boolean canMeasureWater(int x, int y, int z) {
            if (x == 0 && y == 0)
                return z == 0;
            return z == 0 || (z % gcd(x, y) == 0 && x + y >= z);
        }

        private int gcd(int x, int y) {
            if (y == 0)
                return x;
            int r = x % y;
            return gcd(y, r);
        }
    }

    class Solution876 {
        //链表转为数组，然后取中间值即可
        public ListNode middleNode(ListNode head) {
            ListNode[] A = new ListNode[100];
            int t = 0;
            while (head != null) {
                //把链表放入数组中
                A[t++] = head;
                head = head.next;
            }
            return A[t / 2];
        }

        //单指针法
        public ListNode middleNode1(ListNode head) {
            int n = 0;
            ListNode cur = head;
            while (cur != null) {
                ++n;
                cur = cur.next;
            }
            int k = 0;
            cur = head;
            while (k < n / 2) {
                ++k;
                cur = cur.next;
            }
            return cur;
        }

        //快慢指针，快指针一次两步，慢指针一次一步，快指针到头时，慢指针在中间
        public ListNode middleNode2(ListNode head) {
            ListNode slow = head, fast = head;
            while (fast!=null&&fast.next!=null){
                slow=slow.next;
                fast=fast.next.next;
            }
            return slow;
        }

    }

}