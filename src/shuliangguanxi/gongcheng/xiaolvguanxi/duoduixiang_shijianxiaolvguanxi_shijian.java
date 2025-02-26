package shuliangguanxi.gongcheng.xiaolvguanxi;

import common.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 多个施工队之间的完成时间关系，以及改变效率后的完成时间关系，求合作承接需要花费的时长
 */
public class duoduixiang_shijianxiaolvguanxi_shijian {
    public static void main(String[] args) throws IOException {
        {
            while (true) {
                long cost = System.currentTimeMillis();

                // 甲乙施工队的效率
                int ea = new Random().nextInt(2, 5);
                int eb = new Random().nextInt(ea, 10);

                // 效率迁移值
                int echange = new Random().nextInt(1, ea);
                // 丙队效率，确保丙队是可以与甲乙效率持平的
                int ec = ea + eb - 2 * echange;

                // 甲乙合作要比丙提前N天完成，这里约束n是echange的倍数，确保后边总工作量是个整数
                int n = new Random().nextInt(1, 10) * 2*echange;

                // g/(ea+eb)=g/ec - n;根据甲乙比丙提前N天完成工作，计算总工作量，设为g
                int g = (ea + eb) * ec * n / (2*echange);

                // 出题
                // 合作完成工作所需时间
                double answer = 1.0 * g/(ea + eb + ec);

                // 乙和甲、丙的公约数
                int gcd_achange = Utils.gcd(echange, ea);

                // 丙和甲、乙的公约数
                int gcd_bchange = Utils.gcd(echange, eb);

                System.out.printf("三个施工队，甲乙为一组，丙为一组，分别完成订单。甲、乙合作耗时比丙少%d天，如果将甲的%s/%s资源或者乙的%s/%s资源分给丙，则两组同时完成。问三队合作几天能完成订单？\n",
                        n, echange/gcd_achange, ea/gcd_achange,echange/gcd_bchange,eb/gcd_bchange);

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
