package shuliangguanxi.gongcheng.xiaolvguanxi;

import common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 多对象，时间分段，量分段，两两合作完成
 */
public class duoduixiang_shijian_liang_liang {
    public static void main(String[] args) throws IOException {
            while (true) {
                long cost = System.currentTimeMillis();

                // 甲乙丙工作效率
                int ea = new Random().nextInt(1, 5);
                int eb = new Random().nextInt(1, 5);
                int ec = new Random().nextInt(1, 5);

                // 时间分三段
                int tavg = new Random().nextInt(1, 5);
                int tall = tavg*3;

                // 总工作量
                int grossa = (ea + eb + ec) * 2 * tavg;

                // 甲丙tavg的工作量
                int gross1 = (ea + ec) * tavg;

                // 乙丙tavg的工作量
                int gross2 = (eb + ec) * tavg;

                // 甲全部工作量
                int answer = ea*2*tavg;

                // 第一阶段完成了的占比
                int gcd1 = utils.gcd(gross1,grossa);

                // 第二阶段，完成量/剩余全部工作量
                int gcd2 = utils.gcd(gross2,grossa - gross1);

                System.out.printf("甲乙丙合作完成任务，甲、丙合作%s天，完成全部任务的%s/%s；接着乙、丙合作%s天，完成剩下任务的%s/%s；最后甲、乙合作%s天，恰好完成剩余任务。问甲完成部分占全部任务的多少？\n",
                        tavg,gross1/gcd1, grossa/gcd1,
                        tavg,gross2/gcd2, grossa - gross1/gcd2,
                        tavg);

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
