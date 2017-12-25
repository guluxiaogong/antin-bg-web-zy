package com.antin.rec.util.helper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SqlKeyWord
 */
public class SqlKeyWord {

    private static Logger logger = LoggerFactory.getLogger(SqlKeyWord.class);

    public static final String update = "update ";
    public static final String select = "select ";
    public static final String count = "count(*) c ";
    public static final String between = "between ";
    public static final String delete = "delete ";
    public static final String insertInto = "insert  into ";
    public static final String insert = "insert ";
    public static final String and = "and ";
    public static final String or = "or ";
    public static final String limit = "limit ";
    public static final String set = "set ";
    public static final String orderBy = "order by ";
    public static final String groupBy = "group by ";
    public static final String by = "by ";
    public static final String leftJoin = "left join ";
    public static final String rightJoin = "right join ";
    public static final String period = ".";
    public static final String comma = ",";
    public static final String equal = "=";
    public static final String from = "from ";
    public static final String quotation = "'";
    public static final String where = "where ";
    public static final String desc = "desc ";
    public static final String asc = "asc ";
    public static final String semicolon = ";";
    public static final String asterisk = "* ";
    public static final String into = "into ";
    public static final String values = "values ";

    public static final String leftParentheses = "(";
    public static final String rightParentheses = ")";

    public static String wordFilter(String word) {
        if (word != null) {
            if (word.contains(SqlKeyWord.update) || word.contains(SqlKeyWord.select) || word.contains(SqlKeyWord.delete)
                    || word.contains(SqlKeyWord.insert)) {
                if (logger.isDebugEnabled())
                    logger.debug("发现关键字：" + word);
                return "";
            }
        }
        return word;
    }

}
