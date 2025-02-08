package suanshu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * 3位数除以2位数
 */
public class ChuFa3_2 {
    public static void main(String[] args) {
        while (true) {
            try {
                long cost = System.currentTimeMillis();
                double random1 = new Random().nextInt(100, 1000);
                double random2 = new Random().nextInt(11, 100);

                System.out.printf("%s / %s%n", random1, random2);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String text = reader.readLine();
                double intput = Double.parseDouble(text);

                double answer = random1 / random2;
                BigDecimal bd = new BigDecimal(answer);
                bd = bd.setScale(3, RoundingMode.HALF_UP);
                answer = bd.doubleValue();

                boolean beyond = Math.abs((answer-intput)/answer) <= 0.03;

                String result = beyond ? "√" : "X";
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
