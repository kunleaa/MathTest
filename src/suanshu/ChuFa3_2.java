package suanshu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * 3位数除以2位数
 * 假设是 A/B
 * 经过几个步骤
 * 步骤1：1位数 x 两位数：试值1 x B，得到“数1”
 * 步骤2：两个三位数对比大小：A vs ”数1“（可以当做两个三位数做差，需要记住“数1”）
 * 步骤3：判断：如果A < ”数1“，需要返回步骤1；否则，继续往下
 * 步骤4：两个三位数做差： A - “数1”，得到“数4”（2位数）
 *
 * 步骤5：1位数 x 两位数：试值2 x B，得到“数5”
 * 步骤6：两个三位数比较大小：“数4（补0）” vs “数5”
 * 步骤7：判断：“数4（补0）” < “数5”，需要返回步骤6；否则，继续往下
 * 步骤8：两个三位数做差：”数4（补0）“ - ”数5“，得到“数8”（2位数）
 *
 * 步骤9：1位数 x 两位数：试值3 x B，得到“数9”
 * 步骤10：两个三位数比较大小：“数8（补0）” vs “数9”
 * 步骤11：判断：“数8（补0）” < “数9”，需要返回步骤10；否则继续往下
 * 结束：试值1，试值2，试值3 组成结果
 * 核心能力：1 三位数做差（心算）；2 一位数乘两位数（心算）；3 临时记忆：在心算乘法时，需要准确记住减法得到的临时结果
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
