package test.dao.query;

  
/** 
 * 查询条件分析回调函数接口，实现对特殊查询条件的处理操作，如果设置了回调接口，则系统在解析回调表达式前先调用回调接口进行解析 
 *  
 * @author zouxuemo 
 * 
 */  
public interface QueryCallback {  
    public final static String IGRONE = "[igrone]";  
      
    /** 
     * 分析传入的条件表达式，返回构造出的表达式，否则返回null 
     * 如果希望查询分析器忽略表达式，则返回 IGRONE 
     *  
     * @param queryExpression 
     * @return 
     */  
    public String parseCondition(QueryExpression queryExpression);  
}  