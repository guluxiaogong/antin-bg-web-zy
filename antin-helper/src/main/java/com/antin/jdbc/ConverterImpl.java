package com.antin.jdbc;


import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/18.
 */
@Component
public class ConverterImpl implements Converter {

    @Autowired
    protected Validator validator;

    private static Logger log = LoggerFactory.getLogger(ConverterImpl.class);

    @Override
    public String toString(Object object) {//
        if (validator.isEmpty(object))
            return "";

        if (object.getClass().isArray()) {
            StringBuilder sb = new StringBuilder();
            for (int length = Array.getLength(object), i = 0; i < length; i++)
                sb.append(',').append(Array.get(object, i));

            return sb.substring(1);
        }

        if (object instanceof Iterable) {
            StringBuilder sb = new StringBuilder();
            ((Iterable) object).forEach(obj -> sb.append(',').append(toString(obj)));

            return sb.substring(1);
        }

        if (object instanceof Map) {
            StringBuilder sb = new StringBuilder();
            ((Map) object).forEach((key, value) -> sb.append(',').append(toString(key)).append('=').append(toString(value)));

            return sb.substring(1);
        }

        if (object instanceof java.sql.Date)
            return toString((java.sql.Date) object, getDateFormat());

        if (object instanceof Timestamp)
            return toString((Timestamp) object, getDateTimeFormat());

        return object.toString();
    }

    @Override
    public String toString(Date date, String format) {
        return date == null ? "" : getDateFormat(format).format(date);
    }

    protected String getDateFormat() {
//        Locale locale = context.getLocale();
//        String format = dateFormat.get(locale);
//        if (format == null) {
//            format = message.get("commons.format.date");
//            dateFormat.put(locale, format);
//        }
//
//        return format;
        return "yyyy-MM-dd";
    }

    protected FastDateFormat getDateFormat(String format) {
//        FastDateFormat fdf = dateFormatMap.get(format);
//        if (fdf == null) {
//            fdf = FastDateFormat.getInstance(format);
//            dateFormatMap.put(format, fdf);
//        }

        //      return fdf;
        FastDateFormat fdf = FastDateFormat.getInstance(format);
        return fdf;

    }

    protected String getDateTimeFormat() {
   /*     Locale locale = context.getLocale();
        String format = dateTimeFormat.get(locale);
        if (format == null) {
            format = message.get("commons.format.date-time");
            dateTimeFormat.put(locale, format);
        }

        return format;*/
        return "yyyy-MM-dd HH:mm:ss";
    }
}