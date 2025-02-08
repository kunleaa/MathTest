package suanshu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * 方法一
 * 3位数成1位数，结果是4位的数字A1A2A3A4
 * 432 * 7
 * 1.乘数的前两位乘积的余数 + 乘数后两位乘积的进位 得到结果的中间2位数字 A2A3； A2A3为 81 + 21 = 102
 * 2.乘数最后一位乘积的余数作为A4； A4为4
 * 3.乘数的第一位乘积的进位作为A1；A1为2
 * 则结果为3024
 *
 * 方法二
 * 从第一位开始依次按位往后计算，后一位的进位加到当前位
 */
public class ChengFa3_1 {
    public static void main(String[] args) {
        while (true) {
            try {
                long cost = System.currentTimeMillis();
                double random1 = new Random().nextInt(100, 1000);
                double random2 = new Random().nextInt(6, 10);

                System.out.printf("%s * %s%n", random1, random2);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String text = reader.readLine();
                double intput = Double.parseDouble(text);
                double answer = random1 * random2;
                String result = answer == intput ? "√" : "X";
                cost = System.currentTimeMillis() - cost;
                cost = cost / 1000;
                System.out.printf("------------%s，%s秒 [%s, %s]%n", result, cost, answer, intput);
                System.out.println();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
