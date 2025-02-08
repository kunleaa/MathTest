public class Main {
    public static void main(String[] args) {
//        int[][] fruits = {{2, 8}, {6, 3}, {8, 6}};
//        int startPos = 5;
//        int k = 4;
//        int [][] fruits = {{200000,10000}};
//        int startPos = 5;
//        int k = 4;
//        int [][] fruits = {{200000,10000}};
//        int startPos = 200000;
//        int k = 200000;

//        int[][] fruits = {{0,9},{4,1},{5,7},{6,2},{7,4},{10,9}};
//        int startPos = 5;
//        int k = 4;

        int[][] fruits = {{0,10000}};
        int startPos = 20000;
        int k = 20000;


        int result = new Solution().maxTotalFruits(fruits, startPos, k);
        System.out.println(result);
    }

    public static class Solution {
        public int maxTotalFruits(int[][] fruits, int startPos, int k) {
            int max = 0;

            int[] sums = new int[fruits.length];
            sums[0] = fruits[0][1];
            for (int index = 1; index < fruits.length; index++) {
                sums[index] = sums[index - 1] + fruits[index][1];
            }


            for (int ls = 0; ls <= k; ls++) {
                //先向左，再向右 k = 2*ls + rs;
                int temp = k - 2 * ls;
                int rs = temp > 0 ? temp : 0;

                // 理论上的左右坐标值
                int left = startPos - ls;
                int right = startPos + rs;

                // 找到最左侧的下标
                int currentMax = getCurrentMax(fruits, left, right, sums);
                if (currentMax > max)
                    max = currentMax;


                // 反过来，先向右，再向左
                right = startPos + ls;
                left = startPos - rs;

                currentMax = getCurrentMax(fruits, left, right, sums);

                if (currentMax > max)
                    max = currentMax;
            }
            return max;
        }

        private int getCurrentMax(int[][] fruits, int left, int right, int[] sums) {
            int realRight;
            int realLeft;
            // 找到最左侧的下标
            realLeft = findRealLeft(fruits, left);

            // 找到最右侧的下标
            realRight = findRealRight(fruits, right);

            if (realLeft == -1 || realRight == -1) {
                return 0;
            }

            return sums[realRight] - sums[realLeft] + fruits[realLeft][1];
        }

        // 找到第一小于等于目标值的下标
        int findRealRight(int[][] fruits, int target) {
            int realRight = -1;

            int lTemp = 0;
            int rTemp = fruits.length - 1;
            int mid = 0;
            while (lTemp <= rTemp) {


                mid = (lTemp + rTemp) / 2;
                // 如果目标小于中间值，说明要找的值在左侧区间，移动右侧下表
                if (target < fruits[mid][0]) {
                    realRight = mid;
                    rTemp = mid - 1;
                }
                // 如果目标值大于中间值，说明要找的值在右侧区间，移动左侧下表
                else {
                    lTemp = mid + 1;
                }
            }
            return realRight;
        }

        // 找到第一个大于等于目标值的下标
        int findRealLeft(int[][] fruits, int target) {
            //  7   1 4 6 9 10
            //      0 1 2 3 4
            int realLeft = -1;

            int lTemp = 0;
            int rTemp = fruits.length - 1;
            int mid = 0;
            while (lTemp <= rTemp) {
                mid = (lTemp + rTemp) / 2;
                // 如果目标小于中间值，说明要找的值在左侧区间，移动右侧下表
                if (target <= fruits[mid][0]) {
                    realLeft = mid;
                    rTemp = mid - 1;
                }
                // 如果目标值大于中间值，说明要找的值在右侧区间，移动左侧下表
                else {
                    lTemp = mid + 1;
                }
            }
            return realLeft;
        }
    }
}