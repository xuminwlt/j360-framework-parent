package me.j360.framework.boot.mybatis;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明：
 *
 * 另外一种写法,in和其他组合的时候使用
 * return CollectionUtils.isEmpty(ids) ? Collections.EMPTY_LIST : playMapper.findIn(new InList<>(ids));
 * in $(ids) 来实现
 *
 */

public class SimpleSelectInLangDriver  extends XMLLanguageDriver implements LanguageDriver {

    private static final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll("<foreach collection=\"$1\" item=\"_item\" open=\"(\" " +
                    "separator=\",\" close=\")\" >#{_item}</foreach>");
        }
        script = "<script>" + script + "</script>";
        return super.createSqlSource(configuration, script, parameterType);
    }
}