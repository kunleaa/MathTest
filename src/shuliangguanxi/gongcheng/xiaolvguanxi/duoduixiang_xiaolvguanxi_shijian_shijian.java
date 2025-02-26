package shuliangguanxi.gongcheng.xiaolvguanxi;

import common.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 多个施工队之间的效率关系，工作总时长，求单个施工队承接需要花费的时长
 */
public class duoduixiang_xiaolvguanxi_shijian_shijian {
    public static void main(String[] args) throws IOException {
        {
            while (true) {
                long cost = System.currentTimeMillis();

                // 三个施工队的效率
                int ea = new Random().nextInt(1, 10);
                int eb = new Random().nextInt(1, 10);
                int ec = new Random().nextInt(1, 10);

                // 合作需用时
                int n = new Random().nextInt(1, 20);

                // 总工作量设为g
                int g = (ea + eb + ec) * n;

                // 出题
                // a完成所需时间
                int answer = g/ea;

                // 乙和甲、丙的公约数
                int gcdac = Utils.gcd(eb, (ea + ec));

                // 丙和甲、乙的公约数
                int gcdab = Utils.gcd(ec, (ea + eb));

                System.out.printf("三个施工队，乙工效为甲、丙的%s/%s，丙工效为甲、乙的%s/%s，三队合作%s天完成总工程。如果由甲单独做，需要多少天？\n",
                        (eb/gcdac), ((ea + ec)/gcdac),(ec/gcdab),((ea + eb)/gcdab), n);

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
}
