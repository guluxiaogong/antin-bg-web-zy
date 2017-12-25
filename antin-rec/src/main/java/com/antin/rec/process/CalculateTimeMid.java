package com.antin.rec.process;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/29.
 */
public class CalculateTimeMid extends CalculateTime {

    @Override
    public long calculate(Date sourceStart, Date sourceEnd, Date targetStart, Date targetEnd) {
        return calculateAsMid(sourceStart, sourceEnd, targetStart, targetEnd);
    }

    /**
     * 计算两个时间段的差值
     *
     * @param sourceStart 原始开始时间
     * @param sourceEnd   原始结束时间
     * @param targetStart 比较开始时间
     * @param targetEnd   比较结束时间
     * @return 两时间差值
     */
    public long calculateAsMid(Date sourceStart, Date sourceEnd, Date targetStart, Date targetEnd) {
        try {
            //TODO sourceTime时间其实只要计算一次就可以了,待优化
            long sourceTime = convertToCurrent(sourceStart) + (convertToCurrent(sourceEnd) - convertToCurrent(sourceStart));
            long targetTime = convertToCurrent(targetStart) + (convertToCurrent(targetEnd) - convertToCurrent(targetStart));
            return Math.abs(sourceTime - targetTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 将日期转化用当天日期，保留原时、分、秒
     *
     * @param date 日期时间
     * @return 毫秒数
     */
    private long convertToCurrent(Date date) {
        Calendar source = Calendar.getInstance();
        source.setTime(date);
        Calendar target = Calendar.getInstance();
        target.set(Calendar.HOUR, source.get(Calendar.HOUR));
        target.set(Calendar.MINUTE, source.get(Calendar.MINUTE));
        target.set(Calendar.MILLISECOND, source.get(Calendar.MILLISECOND));
        return target.getTimeInMillis();
    }
}
