import java.util.ArrayList;
import java.util.List;

/**
 * @author Hongyan Wang
 * @packageName PACKAGE_NAME
 * @className TestTXJava
 * @description 腾讯
 * @date 2020/3/23 9:22
 */
public class TestTXJava {
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            List<Integer> list = new ArrayList<>();
            for (int num : nums) {
                list.add(num);
            }
            for (int i = 0; i < list.size(); i++) {
                Integer integer = list.get(i);
                int temp = target - integer;
                int j = list.indexOf(temp);
                if (j >= 0 && j != i)
                    return new int[]{i, j};
            }
            return new int[2];
        }
    }
}
