package com.antin.rec.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by jichangjin on 2017/9/20.
 */
public class DoubleFormat {
    static double f = 111231.5585;

    public static void m1() {
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }

    /**
     * DecimalFormat转换最简便
     */
    public static void m2() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(f));
    }

    /**
     * String.format打印最简便
     */
    public static void m3() {
        System.out.println(String.format("%.2f", f));
    }

    public static void m4() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(f));
    }

    public static void main(String[] args) {
        DoubleFormat.m1();
        DoubleFormat.m2();
        DoubleFormat.m3();
        DoubleFormat.m4();
    }
}
