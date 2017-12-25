package com.antin.rec.process;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/22.
 * 时间匹配
 */
public abstract class CalculateTime {

    public abstract long calculate(Date sourceStart, Date sourceEnd, Date targetStart, Date targetEnd);


}
