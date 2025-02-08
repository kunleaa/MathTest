package suanshu.jichu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 计算减法之后的余数，例如9-8 得 1
 */
public class BuShu {
    public static void main(String[] args) {
        while (true) {
            try {
                long cost = System.currentTimeMillis();
                int random1 = new Random().nextInt(1, 10);
                System.out.printf("%s%n", random1);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String text = reader.readLine();
                int intput = Integer.parseInt(text);
                String result = intput == 10 - random1 ? "√" : "X";
                cost = System.currentTimeMillis() - cost;
                cost = cost / 1000;
                System.out.printf("------------%s，%s秒 %n", result, cost);
                System.out.println();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
