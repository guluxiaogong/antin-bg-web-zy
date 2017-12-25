package com.antin.jdbc;

import java.util.Date;

/**
 * 转换器。提供常用转换操作。
 */
public interface Converter {
    /**
     * 将对象转化为字符串。如果为null或空集则返回空字符串；如果是数组则使用逗号分割后拼接；其它返回object.toString()结果。
     *
     * @param object 对象。
     * @return 字符串。
     */
    String toString(Object object);//

    /**
     * 按指定格式将日期值格式化为字符串。
     *
     * @param date   要进行格式化的日期值。
     * @param format 目标格式。
     * @return 格式化后的日期值字符串。
     */
    String toString(Date date, String format);
}
