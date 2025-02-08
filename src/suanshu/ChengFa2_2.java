package suanshu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 两位数乘两位数
 *
 */
public class ChengFa2_2 {
    public static void main(String[] args) {
        while (true) {
            try {
                long cost = System.currentTimeMillis();
                int random1 = new Random().nextInt(61, 100);
                int random2 = new Random().nextInt(61, 100);

                System.out.printf("%s * %s%n", random1, random2);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String text = reader.readLine();
                int intput = Integer.parseInt(text);
                int answer = random1 * random2;
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
