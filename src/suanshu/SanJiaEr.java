package suanshu;

import common.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 三个数加两个数，用于听力运算
 * 听力心算，第一步：心中清晰的写出数字，两个数写两行。第二步：从到到尾逐个作差
 */
public class SanJiaEr {

    static boolean isLook = false;
    static boolean isListen = true;

    public static void main(String[] args) {
        while (true) {
            try {
                long cost = System.currentTimeMillis();
                int random1 = new Random().nextInt(500, 900);
                int random2 = new Random().nextInt(11, 99);

                if (isLook) {
                    System.out.printf("%s + %s%n", random1, random2);
                }

                if (isListen) {
                    System.out.printf("请作答%n");
                    String speakContent = String.format("%s 加 %s", random1, random2);
                    Utils.speak(speakContent);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String text = reader.readLine();
                int intput = Integer.parseInt(text);
                int answer = random1 + random2;
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
