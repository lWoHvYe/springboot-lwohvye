package com.lwohvye.springboot.otherpart.local.sort;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Hongyan Wang
 * @packageName com.lwohvye.springboot.otherpart.local.sort
 * @className bubbleSort
 * @description 排序算法
 * @date 2020/3/20 13:06
 */
public class SortInteger {
    public static void main(String[] args) {
        int[] array = new int[]{2, 1, 5, 8, 3, 5, 3, 7, 1, 0, 6, 0, 2};
        ArrayList<Integer> arrayList = new ArrayList<>() {
            {
                add(2);
                add(1);
                add(5);
                add(8);
                add(3);
                add(5);
                add(3);
                add(7);
                add(1);
                add(0);
                add(6);
                add(0);
                add(2);
            }
        };
//        int[] bubbleSort = bubbleSort(array);
//        int[] selectionSort = selectionSort(array);
//        int[] insertionSort = insertionSort(array);
//        int[] selectionSort = selectionSort(array);
//        int[] mergeSort = mergeSort(array);
//        int[] quickSort = quickSort(array, 0, array.length - 1);
//        int[] quickSort1 = quickSort1(array, 0, array.length - 1);
//        ArrayList<Integer> bucketSort = bucketSort(arrayList, 3);
        int[] radixSort = radixSort(array);
    }
//----------------------------------------------------------------------

    /**
     * @return int[]
     * @description 冒泡排序，
     * 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * 针对所有的元素重复以上的步骤，除了最后一个；
     * 重复步骤1~3，直到排序完成。
     * 总结：通过相邻比较，将最大的放在最后面
     * 时间复杂度：最佳情况：T(n) = O(n)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(n2)
     * @params [array]
     * @author Hongyan Wang
     * @date 2020/3/20 13:08
     */
    public static int[] bubbleSort(int[] array) {
        if (array.length == 0)
            return array;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                //前面
                int before = array[j];
                //后面
                int after = array[j + 1];
//                后面比前面小，换位
                if (before > after) {
                    array[j] = after;
                    array[j + 1] = before;
                }
            }
        }
        return array;
    }
//----------------------------------------------------------------------

    /**
     * @return int[]
     * @description 选择排序
     * n个记录的直接选择排序可经过n-1趟直接选择排序得到有序结果。具体算法描述如下：
     * 初始状态：无序区为R[1..n]，有序区为空；
     * 第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录 R[k]，将它与无序区的第1个记录R交换，使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
     * n-1趟结束，数组有序化了。
     * 总结：在待排序的数组里找最小的，放在前面
     * 时间复杂度：最佳情况：T(n) = O(n2)  最差情况：T(n) = O(n2)  平均情况：T(n) = O(n2)
     * @params [array]
     * @author Hongyan Wang
     * @date 2020/3/20 13:59
     */
    public static int[] selectionSort(int[] array) {
        if (array.length == 0)
            return array;
        for (int i = 0; i < array.length; i++) {
//            最小值对应的索引
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex])
                    minIndex = j;
            }
            //把第一个与最小的换位
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
        return array;
    }
//----------------------------------------------------------------------

    /**
     * @return int[]
     * @description 插入排序
     * 一般来说，插入排序都采用in-place在数组上实现。具体算法描述如下：
     * 从第一个元素开始，该元素可以认为已经被排序；
     * 取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * 如果该元素（已排序）大于新元素，将该元素移到下一位置；
     * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     * 将新元素插入到该位置后；
     * 重复步骤2~5。
     * 总结：每个数与前面排好的做比较，找到前面比自己小，后面比自己大的位置
     * 时间复杂度：最佳情况：T(n) = O(n)   最坏情况：T(n) = O(n2)   平均情况：T(n) = O(n2)
     * @params [array]
     * @author Hongyan Wang
     * @date 2020/3/20 14:48
     */
    public static int[] insertionSort(int[] array) {
        if (array.length == 0)
            return array;
        int current;
        for (int i = 0; i < array.length - 1; i++) {
            //要找位置的
            current = array[i + 1];
            //参与比较的，current要与前面所有的比较找位置
            int preIndex = i;
            //前面的一定是有序的，所以在比较到头，或找到比current小的元素时，停止
            while (preIndex >= 0 && current < array[preIndex]) {
                //前面比current大的，后移一位，空出的可以简单理解为放的current
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            //将current放在对应的位置，preIndex处是比current小的
            array[preIndex + 1] = current;
        }
        return array;
    }
//----------------------------------------------------------------------

    /**
     * @return int[]
     * @description 希尔排序
     * 我们来看下希尔排序的基本步骤，在此我们选择增量gap=length/2，缩小增量继续以gap = gap/2的方式，这种增量选择我们可以用一个序列来表示，{n/2,(n/2)/2...1}，称为增量序列。希尔排序的增量序列的选择与证明是个数学难题，我们选择的这个增量序列是比较常用的，也是希尔建议的增量，称为希尔增量，但其实这个增量序列不是最优的。此处我们做示例使用希尔增量。
     * 先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，具体算法描述：
     * 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
     * 按增量序列个数k，对序列进行k 趟排序；
     * 每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     * 总结：优化插入排序,不断的分组，并组内插入排序，比如index是i和j的在一组，排序后，可能i和j在大数组中位置互换，
     * 时间复杂度：最佳情况：T(n) = O(nlog2 n)  最坏情况：T(n) = O(nlog2 n)  平均情况：T(n) =O(nlog2n)
     * @params [array]
     * @author Hongyan Wang
     * @date 2020/3/20 15:34
     */
    public static int[] shellSort(int[] array) {
        int len = array.length;
        //初始gap和temp都是一半
        int temp, gap = len >> 1;
        //gap每次走完循环减一半
        while (gap > 0) {
            //这个可以理解为初始两个一组时，以后一个的下标为准，即gap
            //接下来就是组内的插入排序，需要注意组间距变成了gap了
            for (int i = gap; i < len; i++) {
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && temp < array[preIndex]) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
            }
            gap >>= 1;
        }
        return array;
    }
//----------------------------------------------------------------------

    /**
     * @return int[]
     * @description 归并排序，分治法的一个应用
     * 把长度为n的输入序列分成两个长度为n/2的子序列；
     * 对这两个子序列分别采用归并排序；
     * 将两个排序好的子序列合并成一个最终的排序序列。
     * 总结：先把数组分成一个一组，然后相邻两组排序，（使用递归分组），然后两组并一组再排序，依此类推
     * 时间复杂度：最佳情况：T(n) = O(n)  最差情况：T(n) = O(nlogn)  平均情况：T(n) = O(nlogn)
     * @params [array]
     * @author Hongyan Wang
     * @date 2020/3/20 16:33
     */
    public static int[] mergeSort(int[] array) {
        int len = array.length;
        if (len < 2)
            return array;
        int mid = len >> 1;
        //分成两半
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, len);
        //归并排序代码
        return merge(mergeSort(left), mergeSort(right));
    }

    /**
     * @return int[]
     * @description 归并排序，将两段排序好的数组结合成一个排序数组
     * @params [left, right]
     * @author Hongyan Wang
     * @date 2020/3/20 17:23
     */
    private static int[] merge(int[] left, int[] right) {
        int leftLen = left.length;
        int rightLen = right.length;
        int[] result = new int[leftLen + rightLen];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= leftLen) {
                //i先放完的话，只放j的
                result[index] = right[j++];
            } else if (j >= rightLen) {
                //同上
                result[index] = left[i++];
            } else if (left[i] > right[j]) {
                //i大，则把j放入结果
                result[index] = right[j++];
            } else {
                //j大，则把i放入结果，保证小的在前
                result[index] = left[i++];
            }
        }
        return result;
    }
//----------------------------------------------------------------------

    /**
     * @return int[]
     * @description 快速排序法
     * 快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：
     * 从数列中挑出一个元素，称为 “基准”（pivot）；
     * 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
     * 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
     * 总结：随机选一个基准，然后比较使得左侧比基准小，右侧比基准大，然后递归
     * 时间复杂度：最佳情况：T(n) = O(nlogn)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(nlogn)
     * @params [array, start, end]
     * @author Hongyan Wang
     * @date 2020/3/20 17:50
     */
    public static int[] quickSort(int[] array, int start, int end) {
        if (start < 0 || end >= array.length || start > end)
            return null;
        int smallIndex = partition(array, start, end);
        if (smallIndex > start)
            quickSort(array, start, smallIndex - 1);
        if (smallIndex < end)
            quickSort(array, smallIndex + 1, end);
        return array;
    }

    /**
     * @return int
     * @description 快排算法-partition   虽不太明白，但执行后，基准左侧均小于基准，基准右侧均大于基准
     * @params [array, start, end]
     * @author Hongyan Wang
     * @date 2020/3/20 18:00
     */
    private static int partition(int[] array, int start, int end) {
        //基准在范围内随机选择
        int pivot = (int) (start + Math.random() * (end - start + 1));
        //因为下面比较前先进行了自增，所以这里先减一
        int smallIndex = start - 1;
        //把基准放到最后，最后会将基准挪到smallIndex对应的位置
        swap(array, pivot, end);
        for (int i = start; i <= end; i++) {
            //用于比较的要比基准小，
            //如果出现比基准大的，当再出现小的时，smallIndex对应的应是大的，所以与i位置对应的小的换位
            //再此后，保持遇到小的就与大的换位，最后一次将end与smallIndex处的换位
            //首先smallIndex前的都是比end小的，之后遇到小的就与smallIndex处的大的换位
            //触发换位，显然smallIndex小于i，且所在位置比end要大
            if (array[i] <= array[end]) {
                smallIndex++;
                //i比smallIndex大，证明出现了end不是最大的情况，
                if (i > smallIndex)
                    swap(array, i, smallIndex);
            }
        }
        return smallIndex;
    }

    /**
     * @return void
     * @description 交互数组内两个元素的位置
     * @params [array, i, j]
     * @author Hongyan Wang
     * @date 2020/3/20 18:04
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
//------------------------------------------------------------------------

    /**
     * @param array 带排序数组
     * @param lo    开始位置
     * @param hi    结束位置
     * @description 快速排序，这种更易理解一些
     */
    static int[] quickSort1(int[] array, int lo, int hi) {
        if (lo >= hi) {
            return array;
        }
        int index = partition1(array, lo, hi);
        quickSort1(array, lo, index - 1);
        quickSort1(array, index + 1, hi);
        return array;
    }

    /**
     * @param array 待排序的数组
     * @param lo    开始位置
     * @param hi    结束位置
     * @return 基准值所在位置
     */
    static int partition1(int[] array, int lo, int hi) {
        //以lo位置为基准
        int key = array[lo];
        while (lo < hi) {
            //寻找右侧比lo小的
            while (array[hi] >= key && hi > lo) {
                hi--;
            }
            //将其值赋给lo
            array[lo] = array[hi];
            //寻找左侧比lo大的
            while (array[lo] <= key && hi > lo) {
                lo++;
            }
            //将其值赋给hi
            array[hi] = array[lo];
            //一次循环新hi换到原lo，新lo赋到新hi，初始lo被赋到key，新lo存在两个位置（hi,lo）
            //再一次循环hi赋到多余的lo上（lo），lo被赋到hi上，新lo存在两个位置(hi,lo)
        }
        //最后将hi位置的lo换成基准的
        array[hi] = key;
        return hi;
    }
//------------------------------------------------------------------

    //声明全局变量，用于记录数组array的长度；
    static int len;

    /**
     * 堆排序算法
     * 将初始待排序关键字序列(R1,R2….Rn)构建成大顶堆，此堆为初始的无序区；
     * 将堆顶元素R[1]与最后一个元素R[n]交换，此时得到新的无序区(R1,R2,……Rn-1)和新的有序区(Rn),且满足R[1,2…n-1]<=R[n]；
     * 由于交换后新的堆顶R[1]可能违反堆的性质，因此需要对当前无序区(R1,R2,……Rn-1)调整为新堆，然后再次将R[1]与无序区最后一个元素交换，得到新的无序区(R1,R2….Rn-2)和新的有序区(Rn-1,Rn)。不断重复此过程直到有序区的元素个数为n-1，则整个排序过程完成。
     * 总结：根据二叉树的性质，调整父节点为最大值，把最大的放尾部，然后新构建不含尾部的堆
     * 时间复杂度：最佳情况：T(n) = O(nlogn) 最差情况：T(n) = O(nlogn) 平均情况：T(n) = O(nlogn)
     *
     * @param array
     * @return
     */
    public static int[] HeapSort(int[] array) {
        len = array.length;
        if (len < 1) return array;
        //1.构建一个最大堆
        buildMaxHeap(array);
        //2.循环将堆首位（最大值）与末位交换，然后在重新调整最大堆
        while (len > 0) {
            swap(array, 0, len - 1);
            len--;
            adjustHeap(array, 0);
        }
        return array;
    }

    /**
     * 建立最大堆
     *
     * @param array
     */
    public static void buildMaxHeap(int[] array) {
        //从最后一个非叶子节点开始向上构造最大堆
        for (int i = (len / 2 - 1); i >= 0; i--) { //感谢 @让我发会呆 网友的提醒，此处应该为 i = (len/2 - 1)
            adjustHeap(array, i);
        }
    }

    /**
     * 调整使之成为最大堆
     *
     * @param array
     * @param i
     */
    public static void adjustHeap(int[] array, int i) {
        int maxIndex = i;
        //如果有左子树，且左子树大于父节点，则将最大指针指向左子树
        if (i * 2 < len && array[i * 2] > array[maxIndex])
            maxIndex = i * 2;
        //如果有右子树，且右子树大于父节点，则将最大指针指向右子树
        if (i * 2 + 1 < len && array[i * 2 + 1] > array[maxIndex])
            maxIndex = i * 2 + 1;
        //如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。
        if (maxIndex != i) {
            swap(array, maxIndex, i);
            adjustHeap(array, maxIndex);
        }
    }
//--------------------------------------------------------------------

    /**
     * @return int[]
     * @description 计数排序
     * 找出待排序的数组中最大和最小的元素；
     * 统计数组中每个值为i的元素出现的次数，存入数组C的第i项；
     * 对所有的计数累加（从C中的第一个元素开始，每一项和前一项相加）；
     * 反向填充目标数组：将每个元素i放在新数组的第C(i)项，每放一个元素就将C(i)减去1
     * 总结：比较简单，新建数组，值对应位置数值加1，最后从数组取出来
     * 时间复杂度：最佳情况：T(n) = O(n+k)  最差情况：T(n) = O(n+k)  平均情况：T(n) = O(n+k)
     * @params [array]
     * @author Hongyan Wang
     * @date 2020/3/20 21:28
     */
    public static int[] countingSort(int[] array) {
        if (array.length == 0)
            return array;
        int bias, min = array[0], max = array[0];
        for (int i = 0; i < array.length; i++) {
            //先确定最值
            if (array[i] > max)
                max = array[i];
            if (array[i] < min)
                min = array[i];
        }
        bias = -min;
        //定义数组，这里长度为这个，所以放的时候，需要减去min
        int[] bucket = new int[max - min + 1];
        //先填充0
        Arrays.fill(bucket, 0);
        for (int i = 0; i < array.length; i++) {
            bucket[array[i] + bias]++;
        }
        int index = 0, i = 0;
        while (index < array.length) {
            if (bucket[i] != 0) {
                //这里array当成定长的新数组用了
                array[index] = i - bias;
                bucket[i]--;
                index++;
            } else {
                i++;
            }
        }
        return array;
    }
//-------------------------------------------------------------------

    /**
     * @return java.util.ArrayList<java.lang.Integer>
     * @description 桶排序
     * 人为设置一个BucketSize，作为每个桶所能放置多少个不同数值（例如当BucketSize==5时，该桶可以存放｛1,2,3,4,5｝这几种数字，但是容量不限，即可以存放100个3）；
     * 遍历输入数据，并且把数据一个一个放到对应的桶里去；
     * 对每个不是空的桶进行排序，可以使用其它排序方法，也可以递归使用桶排序；
     * 从不是空的桶里把排好序的数据拼接起来
     * 如果递归使用桶排序为各个桶排序，则当桶数量为1时要手动减小BucketSize增加下一循环桶的数量，否则会陷入死循环，导致内存溢出
     * 总结：递归调用，最后是一个数字（可重复多个），放在一个桶里，然后顺序取，达到排序
     * 时间复杂度：最佳情况：T(n) = O(n+k)   最差情况：T(n) = O(n+k)   平均情况：T(n) = O(n2)
     * @params [array, bucketSize]
     * @author Hongyan Wang
     * @date 2020/3/21 9:31
     */
    public static ArrayList<Integer> bucketSort(ArrayList<Integer> array, int bucketSize) {
        if (array == null || array.size() < 2)
            return array;
        int max = array.get(0), min = array.get(0);
        for (int i = 0; i < array.size(); i++) {
            //先确定最值
            if (array.get(i) > max)
                max = array.get(i);
            if (array.get(i) < min)
                min = array.get(i);
        }
        //bucketSize是每个桶放的不同数字数,bucketCount是总共要多少桶
        int bucketCount = (max - min) / bucketSize + 1;
        //所有桶的数据放这里
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketCount);
        ArrayList<Integer> resultArr = new ArrayList<>();
        //初始化
        for (int i = 0; i < bucketCount; i++)
            bucketArr.add(new ArrayList<>());
        //get部分确定哪个桶，add把数字放进对应桶内
        for (int i = 0; i < array.size(); i++)
            bucketArr.get((array.get(i) - min) / bucketSize).add(array.get(i));
        //向各个桶
        for (int i = 0; i < bucketCount; i++) {
            //每个桶只放一个数字
            if (bucketSize == 1) {
                for (int j = 0; j < bucketArr.get(i).size(); j++)
                    resultArr.add(bucketArr.get(i).get(j));
            } else {
                //如果只有一个桶，就把桶的大小减一，保证不止一个桶
                if (bucketCount == 1)
                    bucketSize--;
                ArrayList<Integer> temp = bucketSort(bucketArr.get(i), bucketSize);
                for (int j = 0; j < temp.size(); j++)
                    resultArr.add(temp.get(j));
            }
        }
        return resultArr;
    }
//--------------------------------------------------------------------------------

    /**
     * @return int[]
     * @description 基数排序
     * 取得数组中的最大数，并取得位数；
     * arr为原始数组，从最低位开始取每个位组成radix数组；
     * 对radix进行计数排序（利用计数排序适用于小范围数的特点）;
     * 总结：从低位到高位，先每个位按照对应的数字分桶并取出
     * 时间复杂度：最佳情况：T(n) = O(n * k)   最差情况：T(n) = O(n * k)   平均情况：T(n) = O(n * k)
     * @params [array]
     * @author Hongyan Wang
     * @date 2020/3/21 10:29
     */
    public static int[] radixSort(int[] array) {
        if (array == null || array.length < 2)
            return array;
        //算最值
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        //算有多少位
        int maxDigit = 0;
        while (max != 0) {
            max /= 10;
            maxDigit++;
        }
        int mod = 10, div = 1;
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<>();
        //初始化结果集
        for (int i = 0; i < mod; i++)
            bucketList.add(new ArrayList<>());
        //乘10升一位
        for (int i = 0; i < maxDigit; i++, mod *= 10, div *= 10) {
            for (int j = 0; j < array.length; j++) {
                //低位确定初始集合
                int num = (array[j] % mod) / div;
                bucketList.get(num).add(array[j]);
            }
            int index = 0;
            for (int j = 0; j < bucketList.size(); j++) {
                for (int k = 0; k < bucketList.get(j).size(); k++)
                    //把根据低位分桶的数据有序放入array中
                    array[index++] = bucketList.get(j).get(k);
                //清空桶
                bucketList.get(j).clear();
            }
            //然后根据高一位进行二次分桶
        }
        return array;
    }
//-------------------------------------------------------------------------
}
