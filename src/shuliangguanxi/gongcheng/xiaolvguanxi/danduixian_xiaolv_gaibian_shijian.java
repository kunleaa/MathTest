package shuliangguanxi.gongcheng.xiaolvguanxi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 给了时间和总量关系，改变对象效率，求时间
 */
public class danduixian_xiaolv_gaibian_shijian {
    public static void main(String[] args) throws IOException {
        {
            while (true) {
                long cost = System.currentTimeMillis();

                // N天
                int tN = new Random().nextInt(2, 21);

                // 完成总量的分母
                int gDominator = new Random().nextInt(1, 10);

                // 剩余总量
                int gRemain = tN * (gDominator - 1);

                // 效率提升分母
                int eDomaintor = new Random().nextInt(1,10);

                // 则时间比计划提前几天
                double answer = gRemain - (gRemain * eDomaintor) / (eDomaintor + 1.0);

                System.out.printf("电商购物，前%s天囤积了1/%s，后来改进效率，比原来提升1/%s，则完成全部任务比原计划提前几天？\n",
                        tN,gDominator, eDomaintor);

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
