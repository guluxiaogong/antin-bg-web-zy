package com.antin.rec.test.comme;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */
public class Tesk1 {
    @Test
    public void Test() {
        List<String> list = Arrays.asList("123", "45634", "7892", "abch", "sdfhrthj", "mvkd");



        list.stream().forEach(e -> {
            if (e.length() >= 5) {
                return;
            }
            System.out.println(e);
        });
    }
}
