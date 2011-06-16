package test.dao.query;
import org.apache.commons.lang.builder.HashCodeBuilder;  
import org.apache.commons.lang.builder.EqualsBuilder;  
import org.apache.commons.lang.builder.ToStringBuilder;  
  
/** 
 * 查询条件表达式，保存查询条件的一个表达式 
 *  
 * @author zouxuemo 
 * 
 */  
public class QueryExpression {  
    public final static String OP_EQ = "=";             //"field = value"  
    public final static String OP_GE = ">=";             //"field >= value"  
    public final static String OP_GT = ">";              //"field > value"  
    public final static String OP_LE = "<=";             //"field <= value"  
    public final static String OP_LT = "<";              //"field < value"  
    public final static String OP_NE = "<>";              //"field <> value"  
    public final static String OP_LIKE = "like";            //"field like '%value%'"  
    public final static String OP_LLIKE = "llike";      //"field like '%value'"  
    public final static String OP_RLIKE = "rlike";      //"field like 'value%'"  
    public final static String OP_IN = "in";                //"field in (value1, value2, value3, ...)"  
    public final static String OP_NOTIN = "notin";      //"field not in (value1, value2, value3, ...)"  
    public final static String OP_INCLUDE = "include";  //支持查询传入"1-3,5,7-10"这样的查询数据  
    public final static String OP_INQUERY = "inquery";  //支持IN的子查询（例如：in (select parentId from Item)）  
  
    public final static String TYPE_STRING = "string";  //参数类型－字符串  
    public final static String TYPE_NUMBER = "number";  //参数类型－数值  
    public final static String TYPE_DATE = "date";      //参数类型－日期  
      
    public final static String VALUE_SPLIT = ",";  
      
    /** 
     * 查询条件名称 
     */  
    private String name = null;  
      
    /** 
     * 查询操作 
     */  
    private String op = null;  
      
    /** 
     * 查询值 
     */  
    private Object value = null;  
      
    /** 
     * 查询值类型 
     */  
    private String type = null;  
  
    /** 
     * 构造函数（给定条件名称和条件值，默认查询操作为"="） 
     *  
     * @param name 
     * @param value 
     */  
    public QueryExpression(String name, Object value) {  
        super();  
        this.name = name;  
        this.op = OP_EQ;  
        this.value = value;  
    }  
  
    /** 
     * 构造函数（给定条件名称，操作，条件值） 
     *  
     * @param name 
     * @param op 
     * @param value 
     */  
    public QueryExpression(String name, String op, Object value) {  
        super();  
        this.name = name;  
        if (op == null || "".equals(op))  
            op = OP_EQ;  
        this.op = op;  
        this.value = value;  
    }  
  
    /** 
     * 构造函数（给定条件名称，操作，条件值，值类型） 
     *  
     * @param name 
     * @param op 
     * @param value 
     * @param type 
     */  
    public QueryExpression(String name, String op, Object value, String type) {  
        super();  
        this.name = name;  
        if (op == null || "".equals(op))  
            op = OP_EQ;  
        this.op = op;  
        this.value = value;  
        this.type = type;  
    }  
  
    /** 
     * @return the name 
     */  
    public String getName() {  
        return name;  
    }  
  
    /** 
     * @param name the name to set 
     */  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    /** 
     * @return the op 
     */  
    public String getOp() {  
        return op;  
    }  
  
    /** 
     * @param op the op to set 
     */  
    public void setOp(String op) {  
        this.op = op;  
    }  
  
    /** 
     * @return the value 
     */  
    public Object getValue() {  
        return value;  
    }  
  
    /** 
     * @param value the value to set 
     */  
    public void setValue(Object value) {  
        this.value = value;  
    }  
  
    /** @return Returns the type. 
     */  
    public String getType() {  
        return type;  
    }  
  
    /** @param type The type to set. 
     */  
    public void setType(String type) {  
        this.type = type;  
    }  
  
    /** 
     * @see java.lang.Object#hashCode() 
     */  
    public int hashCode() {  
        return new HashCodeBuilder(-159050485, 144990391).appendSuper(  
                super.hashCode()).append(this.op).append(this.value).append(  
                this.type).append(this.name).toHashCode();  
    }  
  
    /** 
     * @see java.lang.Object#equals(Object) 
     */  
    public boolean equals(Object object) {  
        if (!(object instanceof QueryExpression)) {  
            return false;  
        }  
        QueryExpression rhs = (QueryExpression) object;  
        return new EqualsBuilder().appendSuper(super.equals(object)).append(  
                this.op, rhs.op).append(this.value, rhs.value).append(  
                this.type, rhs.type).append(this.name, rhs.name).isEquals();  
    }  
  
    /** 
     * @see java.lang.Object#toString() 
     */  
    public String toString() {  
        return new ToStringBuilder(this).append("name", this.name).append(  
                "value", this.value).append("op", this.op).append("type",  
                this.type).toString();  
    }  
}  