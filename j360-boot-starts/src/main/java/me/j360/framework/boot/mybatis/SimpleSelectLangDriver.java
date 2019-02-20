package me.j360.framework.boot.mybatis;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明：
 * @Select("SELECT id,user_name,password,phone,address,email FROM users (#{user})")
 @Lang(SimpleSelectLangDriver.class)
 */
public class SimpleSelectLangDriver  extends XMLLanguageDriver implements LanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<where>");
            for (Field field : parameterType.getDeclaredFields()) {
                String tmp = "<if test=\"_field != null\">AND _column=#{_field}</if>";
                sb.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column",
                        CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
            }
            sb.append("</where>");

            script = matcher.replaceAll(sb.toString());
            script = "<script>" + script + "</script>";
        }

        return super.createSqlSource(configuration, script, parameterType);
    }
}