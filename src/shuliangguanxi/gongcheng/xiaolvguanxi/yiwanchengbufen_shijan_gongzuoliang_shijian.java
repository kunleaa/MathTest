package shuliangguanxi.gongcheng.xiaolvguanxi;

import common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 已经完成一部分，给了剩余工作量的时间、效率、工作量的关系，求耗时
 */
public class yiwanchengbufen_shijan_gongzuoliang_shijian {
    public static void main(String[] args) throws IOException {
        {
            while (true) {
                long cost = System.currentTimeMillis();


                // 已完成的量和总量
                int preGross = (new Random().nextInt(2, 10)/2)*2;

                // 灌满水需要的抽水机和时间
                int eN = new Random().nextInt(2, 8);
                int eT = (new Random().nextInt(preGross, 2*preGross)/2);
                // 剩余量
                int grossRemain =eT * eN;

                // 总量
                int gross = preGross + grossRemain;

                // 单台抽水机灌到一半需要的时间
                int tSingle = gross/2 - preGross;

                // 出题
                int[] factors = utils.findFactors(gross);
                // 几台抽水机
                int eP = factors[new Random().nextInt(1, factors.length)];
                //抽几个小时
                int answer = gross/eP;

                System.out.printf("池中有一定量水，一台抽水机灌水，%s小时灌至半满；%s台抽水机用%s小时可以灌满。如果池中水排空，用%s台抽水机几小时能灌满？\n",
                        tSingle,eN,eT,eP);

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
