package test.dao.query;
# import org.apache.commons.lang.builder.EqualsBuilder;  
# import org.apache.commons.lang.builder.HashCodeBuilder;  
# import org.apache.commons.lang.builder.ToStringBuilder;  
#   
# /** 
#  * 查询排序类，存放查询排序字段 
#  *  
 * @author zouxuemo 
 * 
 */  
public class QueryOrder {  
    public final static String DIR_ASC = "asc";     //按照正序排列  
    public final static String DIR_DESC = "desc";       //按照倒序排列  
      
    /** 
     * 排序字段 
     */  
    private String name;  
      
    /** 
     * 排序方向 
     */  
    private String dir;  
  
    /** 
     * 构造函数（给定排序字段和排序方向） 
     *  
     * @param name 
     * @param dir 
     */  
    public QueryOrder(String name, String dir) {  
        super();  
        this.name = name;  
        this.dir = dir;  
    }  
  
    /** 
     * 构造函数（给定排序名称，默认正序排序） 
     *  
     * @param name 
     */  
    public QueryOrder(String name) {  
        super();  
        this.name = name;  
        this.dir = DIR_ASC;  
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
     * @return the dir 
     */  
    public String getDir() {  
        return dir;  
    }  
  
    /** 
     * @param dir the dir to set 
     */  
    public void setDir(String dir) {  
        this.dir = dir;  
    }  
  
    /** 
     * @see java.lang.Object#equals(Object) 
     */  
    public boolean equals(Object object) {  
        if (!(object instanceof QueryOrder)) {  
            return false;  
        }  
        QueryOrder rhs = (QueryOrder) object;  
        return new EqualsBuilder().appendSuper(super.equals(object)).append(  
                this.name, rhs.name).append(this.dir, rhs.dir).isEquals();  
    }  
  
    /** 
     * @see java.lang.Object#hashCode() 
     */  
    public int hashCode() {  
        return new HashCodeBuilder(-745339319, 670215467).appendSuper(  
                super.hashCode()).append(this.name).append(this.dir)  
                .toHashCode();  
    }  
  
    /** 
     * @see java.lang.Object#toString() 
     */  
    public String toString() {  
        return new ToStringBuilder(this).append("name", this.name).append(  
                "dir", this.dir).toString();  
    }  
}  