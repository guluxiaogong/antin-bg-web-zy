package com.antin.rec.test.utils.date;

import org.junit.Test;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/8/24.
 */
public class TestDate {
    @Test
    public void testCalendar() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 20);
        c.set(Calendar.MINUTE, 5);
        c.set(Calendar.MILLISECOND, 3);

        System.out.println(c.get(Calendar.HOUR));
        System.out.println(c.get(Calendar.HOUR_OF_DAY));
        System.out.println(c.getTimeInMillis());
        System.out.println(c.getTime());
    }
}
