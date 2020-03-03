package iii.aihub.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InputParameterUtils {

    public static Logger logger = LoggerFactory.getLogger("InputParameterUtils");

    public static Date checkDateParameter(String dateString) throws Exception {
        if(dateString == null || dateString.isEmpty()){
            logger.error("start date is null.");
            throw new Exception("start date is null.");
        }else{
            return new java.sql.Date(DateTimeUtils.parseToyyyyMMddDate(dateString).getTime());
        }
    }

    public static DateTime getDateTimeParameter(Map data, String name){
        DateTime parameter = null;
        if (data.get(name) != null) {
            if (data.get(name) != null && (data.get(name) instanceof String)) {
                String s = (String)data.get(name);
                if (!s.isEmpty()) {
                    try {
                        parameter = DateTimeFormat.forPattern(DateTimeUtils.DateFormatString).parseDateTime(s);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return parameter;
    }

    public static Date getDateParameter(Map data, String name){
        Date parameter = null;
        if (data.get(name) != null) {
            if (data.get(name) != null && (data.get(name) instanceof String)) {
                String s = (String)data.get(name);
                if (!s.isEmpty()) {
                    try {
                        parameter = DateTimeUtils.parseToyyyyMMddDate(s);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return parameter;
    }

    public static String getStringParameter(Map data, String name){
        String parameter = null;
        if (data.get(name) != null) {
            if (data.get(name) instanceof Integer) {
                parameter = String.valueOf(data.get(name));
            } else if (data.get(name) != null && (data.get(name) instanceof String)) {
                parameter = (String) data.get(name);
                if (parameter.isEmpty()){
                    return null;
                }
            }
        }
        return parameter;
    }

    public static Integer getIntegerParameter(Map data, String name){
        Integer parameter = null;
        if( data.get(name) != null && (data.get(name) instanceof Integer) ){
            parameter = (Integer)data.get(name);
        }else if( data.get(name) != null && (data.get(name) instanceof String) ){
            String s = (String)data.get(name);
            if (s.isEmpty()){
                return null;
            }
            if (StringUtils.isNumeric(s)) {
                parameter = Integer.parseInt(s);
            }
        }
        return parameter;
    }

    public static Double getDoubleParameter(Map data, String name){
        Double parameter = null;
        if( data.get(name) != null && (data.get(name) instanceof Double) ){
            parameter = (Double)data.get(name);
        }else if( data.get(name) != null && (data.get(name) instanceof String) ){
            String s = (String)data.get(name);
            if (s.isEmpty()){
                return null;
            }
            if (StringUtils.isNumeric(s)) {
                parameter = Double.parseDouble(s);
            }
        }
        return parameter;
    }

    public static BigDecimal getBigDecimalParameter(Map data, String name){
        BigDecimal parameter = null;
        if( data.get(name) != null && (data.get(name) instanceof Integer) ){
            parameter = new BigDecimal( (Integer)data.get(name) );
        }else if( data.get(name) != null && (data.get(name) instanceof String) ){
            if ( ((String) data.get(name)).isEmpty() ){
                parameter = null;
            }else {
                parameter = new BigDecimal((String)data.get(name));
            }

        }
        return parameter;
    }

    public static Map<String, Object> getMapParameter(Map data, String name){
        Map parameter = null;
        if (data.get(name) != null) {
            if (data.get(name) instanceof Map) {
                parameter = (Map) data.get(name);
            }
        }
        return parameter;
    }

    public static List getListParameter(Map data, String name){
        List parameter = null;
        if (data.get(name) != null) {
            if (data.get(name) instanceof List) {
                parameter = (List) data.get(name);
            }
        }
        return parameter;
    }

    public static Long getLongParameter(Map data, String name){
        Long parameter = null;
        if (data == null){
            return null;
        }
        if (data.get(name) == null){
            return null;
        }
        if( data.get(name) != null && (data.get(name) instanceof Integer) ){
            parameter = new Long( (Integer)data.get(name) );
        }else if( data.get(name) != null && (data.get(name) instanceof String) ){
            parameter = new Long((String)data.get(name));
        }
        return parameter;
    }

    public static Integer validateIntegerParameter(Map<String, Object> data, String parameterName, Boolean defaultValue) throws Exception{
        if( Optional.ofNullable(data.get(parameterName)).isPresent() ) {
            return Integer.parseInt((String) data.get(parameterName));
        }else if(defaultValue){
            return 0;
        }else{
            throw new Exception("validate integer parameter name["+parameterName+"] fail: "+data);
        }
    }
}
