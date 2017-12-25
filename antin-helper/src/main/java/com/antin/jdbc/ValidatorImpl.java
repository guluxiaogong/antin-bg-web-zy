package com.antin.jdbc;

import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;


@Component
public class ValidatorImpl implements Validator {
    private static final String EMAIL = "^(?:\\w+\\.?-?)*\\w+@(?:\\w+\\.?-?)*\\w+$";

    protected Map<String, Pattern> patterns = new ConcurrentHashMap<>();

    @Override
    public boolean isEmpty(Object object) {
        if (object == null)
            return true;

        if (object instanceof String)
            return ((String) object).trim().length() == 0;

        if (object.getClass().isArray())
            return Array.getLength(object) == 0;

        if (object instanceof Collection)
            return ((Collection<?>) object).isEmpty();

        if (object instanceof Map)
            return ((Map<?, ?>) object).isEmpty();

        return false;
    }

    @Override
    public boolean isEmail(String email) {
        return isEmpty(email) ? false : isMatchRegex(EMAIL, email);
    }

    @Override
    public boolean isMatchRegex(String regex, String string) {
        if (regex == null || string == null)
            return false;

        return getPattern(regex).matcher(string).matches();
    }
    @Override
    public boolean isDateString(String dateString) {
        if (dateString == null)
            return false;
        return getPattern("^[1-9]\\d{3}\\-(0?[1-9]|1[0-2])\\-(0?[1-9]|[12]\\d|3[01])\\s*(0?[1-9]|1\\d|2[0-3])(\\:(0?[1-9]|[1-5]\\d)){2}$").matcher(dateString).matches();
    }

    protected Pattern getPattern(String regex) {
        Pattern pattern = patterns.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            patterns.put(regex, pattern);
        }

        return pattern;
    }
}
