package common;

import java.util.ArrayList;
import java.util.List;

public class utils {

    // 最大公约数
    public static int gcd(int a, int b) {
        int t=1;
        while (b!=0) {
            t = a%b;
            a=b;
            b=t;
        }
        return a;
    }

    // 找因数
    public static int[] findFactors(int a) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 1; i < a; i++) {
            if (a%i==0) {
                factors.add(i);
            }
        }
        return factors.stream().mapToInt(i->i).toArray();
    }


    // 转为最接近自己的偶数，3-》2，1-》0,5-》4
    public static int toEven(int a) {
        return (a/2)*2;
    }
}
