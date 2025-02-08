package shuliangguanxi.gongcheng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 给定完工时间，求一定合作时间之后，单独完成时间
 * 最小公倍数得总量，相除的效率
 */
public class wangongshijian {
    public static void main(String[] args) throws IOException {
        while (true) {
            long cost = System.currentTimeMillis();

            // 三个人的效率
            double ea = new Random().nextInt(1, 10);
            double eb = new Random().nextInt(1, 10);
            double ec = new Random().nextInt(1, 10);

            // 总工作量设为g,为保证各自完成所需时间为整数，这里将总工作值设为各自效率的乘积的n倍
            double n = new Random().nextInt(1, 3);
            double g = (ea * eb * ec) * n;

            // 各自完成需要的总工时
            double ta = g/ea;
            double tb = g/eb;
            double tc = g/ec;

            // 出题
            // ABC先合作tq时间，剩余由第三人单独完成
            // 共同合作完成所需时间
            double tHezuo = g/(ea + eb + ec);
            double tXian = new Random().nextInt(1, (int)(tHezuo + 1));
            double answer = (g - tXian * (ea + eb + ec))/ec;

            System.out.printf("某项工程，A单独完成需要%s小时，B单独完成需要%s小时，C单独完成需要%s小时。三人合作%s小时之后，剩下由C单独完成，则C需要多少小时？\n", ta, tb, tc, tXian);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String text = reader.readLine();
            double input = Double.parseDouble(text);

            cost = System.currentTimeMillis() - cost;
            cost = cost / 1000;

            boolean beyond = Math.abs((answer-input)/answer) <= 0.03;
            String result = beyond ? "√" : "X";
            System.out.printf("------------%s，%s秒 [%s, %s]%n", result, cost, answer, input);
            System.out.println();
        }
    }
}
