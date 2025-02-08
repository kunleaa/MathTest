package suanshu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 计算进位之后的余数，例如8+8 得 6
 */
public class SanJianSan {
    public static void main(String[] args) {
        while (true) {
            try {
                long cost = System.currentTimeMillis();
                int random1 = new Random().nextInt(100, 999);
                int random2 = new Random().nextInt(100, random1+1);
                System.out.printf("%s - %s%n", random1, random2);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String text = reader.readLine();
                int intput = Integer.parseInt(text);
                int answer = random1 - random2;
                String result = intput == answer ? "√" : "X";
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
