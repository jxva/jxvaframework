package test.dao.query;
# import java.lang.reflect.Method;  
# import java.text.SimpleDateFormat;  
# import java.util.Arrays;  
# import java.util.Collection;  
# import java.util.Date;  
# import java.util.Iterator;  
#   
# import org.apache.commons.lang.StringUtils;  
#   
# import com.lily.dap.model.QueryExpression;  
#   
# /** 
#  * @author zouxuemo 
 * 
 */  
public class DaoHelper {  
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
      
    public static String expressionValue2String(Object value) {  
        String v = null;  
        if (value == null)  
            v = "";  
        else if (value instanceof String)  
            v = (String)value;  
        else if (value instanceof Date)  
            v = sdf.format((Date)value);  
        else if (value instanceof long[]) {  
            long[] ary = (long[])value;  
            if (ary.length > 0) {  
                StringBuffer sb = new StringBuffer().append(ary[0]);  
                  
                for (int i = 1; i < ary.length; i++)  
                    sb.append(QueryExpression.VALUE_SPLIT).append(ary[i]);  
                  
                v = sb.toString();  
            }  
        } else if (value instanceof int[]) {  
            int[] ary = (int[])value;  
            if (ary.length > 0) {  
                StringBuffer sb = new StringBuffer().append(ary[0]);  
                  
                for (int i = 1; i < ary.length; i++)  
                    sb.append(QueryExpression.VALUE_SPLIT).append(ary[i]);  
                  
                v = sb.toString();  
            }  
        } else if (value instanceof Object[]) {  
            v = StringUtils.join((Object[])value, QueryExpression.VALUE_SPLIT);  
        } else if (value instanceof Collection) {  
            Iterator it = ((Collection)value).iterator();  
            v = StringUtils.join(it, QueryExpression.VALUE_SPLIT);  
        } else  //Long Integer Double Float Boolean  
            v = value.toString();  
          
        return v;  
    }  
      
    /**  
     * 分析数字表达式，并返回其中定义的数字数组<p> 
     * 数字表达式格式为："1-4","1,3,5,7","1-3,5,7-10",其中，1-4表示：1至4，1,3,5,7表示：1 3 5 7 
     * 
     * @param expression 要分析的表达式 
     * @return 返回按照大小排序的整数数组 
     */  
    public static int[] parseNumExpression(String expression) {  
        if (expression == null || "".equals(expression))  
            return new int[0];  
          
        int[] temp = new int[1000];  
        int ptr = 0;  
        String[] tokens = expression.split(",");  
        for (int i = 0; i < tokens.length; i++) {  
            try {  
                int i_val = Integer.parseInt(tokens[i].trim());  
                temp[ptr++] = i_val;  
            } catch (NumberFormatException e) {  
                String[] tmp = tokens[i].split("-");  
                if (tmp.length == 2) {  
                    int bg = 0, ed = 0;  
                    try {  
                        bg = Integer.parseInt(tmp[0].trim());  
                        ed = Integer.parseInt(tmp[1].trim());  
                    } catch (NumberFormatException e1) {  
                        continue;  
                    }  
                      
                    for (int ii = bg; ii <= ed; ii++) {  
                        temp[ptr++] = ii;  
                    }  
                }  
            }  
        }  
          
        if (ptr == 0)  
            return new int[0];  
          
        Arrays.sort(temp, 0, ptr);  
        int length = 1, old = temp[0];  
        for (int i = 1; i < ptr; i++) {  
            if (old != temp[i]) {  
                length++;  
                old = temp[i];  
            }  
        }  
          
        int[] ret = new int[length];  
        int index = 0;  
        ret[index] = temp[0];  
        for (int i = 1; i < ptr; i++) {  
            if (ret[index] != temp[i])   
                ret[++index] = temp[i];  
        }  
          
        return ret;  
    }  
      
    public static Method getMethod(Class clazz, String name) throws SecurityException, NoSuchMethodException {  
        //如果是boolean的属性，必须用is方法来调用，所以先判断是否有get方法，如果没有，再判断有没有is方法  
        Method method = null;  
        try {  
            method = clazz.getMethod(buildGetMethodName(name), new Class[0]);  
        } catch (NoSuchMethodException e) {  
            method = clazz.getMethod(buildIsMethodName(name), new Class[0]);  
        }  
          
        return method;  
    }  
      
    public static String buildGetMethodName(String str){  
        StringBuffer out = new StringBuffer("get");  
        out.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));  
        return out.toString();  
    }  
      
    public static String buildIsMethodName(String str){  
        StringBuffer out = new StringBuffer("is");  
        out.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));  
        return out.toString();  
    }  
