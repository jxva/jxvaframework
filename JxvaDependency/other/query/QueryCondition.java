package test.dao.query;
# import java.util.ArrayList;  
# import java.util.Iterator;  
# import java.util.List;  
#   
# import org.apache.commons.lang.builder.EqualsBuilder;  
# import org.apache.commons.lang.builder.HashCodeBuilder;  
# import org.apache.commons.lang.builder.ToStringBuilder;  
#   
# /** 
 * 查询条件实体类，用于传递查询条件内容</p> 
 * 使用方式：</p> 
 *      QueryCondition queryCondition = new QueryCondition();</p> 
 *      queryCondition.setSelects(new String[]{"class_id", "count(name)"}); //查询给定分类的记录条数 
 *      queryCondition.setGroupbys(new String[]{"class_id"});               //GroupBy分组 
 *      queryCondition.putCondition("id", "-1");        //这个查询条件将忽略</p> 
 *      queryCondition.putCondition("username", "admin");   </p> 
 *      queryCondition.putCondition("name", QueryExpression.OP_IN, "张三,李四,王五");</p> 
 *      queryCondition.putCondition("birthday", QueryExpression.OP_GE, "2005-12-31");</p> 
 *      queryCondition.putCondition("birthday", QueryExpression.OP_LE, "2006-12-31");</p> 
 *      queryCondition.addOrder("username");        //设置查询进行排序</p> 
 *      queryCondition.setPageSize(10);             //设置查询结果实现翻页，每页10条记录</p> 
 *      queryCondition.setPageNo(2);                //设置查询结果实现翻页，显示第二页记录</p> 
 *      List list = dao.gets(Register.class, queryCondition);</p> 
 * 将生成查询条件如下：</p> 
 * from com.lily.dap.model.Register where username = 'admin' and name in ('张三', '李四', '王五') and</p>  
 *                                  birthday >= '2005-12-31' and birthday <= '2006-12-31' order by username asc</p> 
 *  
 *      queryCondition.putCondition("cond0", null); 
 *      assertEquals("", queryCondition.getCondition("cond0").getValue()); 
 *       
 *      queryCondition.putCondition("cond1", "value1"); 
 *      assertEquals("value1", queryCondition.getCondition("cond1").getValue()); 
 *       
 *      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
 *      queryCondition.putCondition("cond2", sdf.parse("2006-12-31")); 
 *      assertEquals("2006-12-31 00:00:00", queryCondition.getCondition("cond2").getValue()); 
 *       
 *      queryCondition.putCondition("cond3", new long[]{1,2,3}); 
 *      assertEquals("1,2,3", queryCondition.getCondition("cond3").getValue()); 
 *       
 *      queryCondition.putCondition("cond4", new int[]{100,200,300}); 
 *      assertEquals("100,200,300", queryCondition.getCondition("cond4").getValue()); 
 *       
 *      queryCondition.putCondition("cond5", new String[]{"aa", "bb", "cc"}); 
 *      assertEquals("aa,bb,cc", queryCondition.getCondition("cond5").getValue()); 
 *       
 *      List list = new ArrayList(); 
 *      list.add("aaa"); 
 *      list.add(new Long(1234)); 
 *      queryCondition.putCondition("cond6", list); 
 *      assertEquals("aaa,1234", queryCondition.getCondition("cond6").getValue()); 
 *       
 *      queryCondition.putCondition("cond7", new Long(123)); 
 *      assertEquals("123", queryCondition.getCondition("cond7").getValue()); 
 *       
 *      queryCondition.putCondition("cond8", new Boolean(true)); 
 *      assertEquals("true", queryCondition.getCondition("cond8").getValue()); 
 *       
 *      queryCondition.putCondition("cond9", new Float(33.3333f)); 
 *      assertEquals("33.3333", queryCondition.getCondition("cond9").getValue()); 
 *  
 * @author zouxuemo 
 */  
public class QueryCondition extends BaseObject {  
    /** Comment for <code>serialVersionUID</code> */  
    private static final long serialVersionUID = -5970921549911182011L;  
  
    /** 选择列数组 */  
    protected String[] selects = null;  
      
    /** Group By 数组 */  
    protected String[] groupbys = null;  
      
    /** 
     * 查询条件列表，存放QueryExpression对象 
     *  
     * <code>conditions</code> 
     */  
    protected List conditions = new ArrayList();  
  
    /** 
     * 排序字段列表，存放QueryOrder对象 
     *  
     * <code>orders</code> 
     */  
    protected List orders = new ArrayList();  
      
    /** 
     * 从查询结果返回的页号，默认为0表示不分页 
     *  
     * <code>pageNo</code> 
     */  
    protected int pageNo = 0;  
      
    /** 
     * 从查询结果返回的每页记录条数，默认0：表示不分页 
     *  
     * <code>pageSize</code> 
     */  
    protected int pageSize = 0;  
      
    private Iterator itCondition = null;  
      
    private Iterator itOrder = null;  
      
    /** @return Returns the selects. 
     */  
    public String[] getSelects() {  
        return selects;  
    }  
  
    /** @param selects The selects to set. 
     */  
    public QueryCondition setSelects(String[] selects) {  
        this.selects = selects;  
          
        return this;  
    }  
  
    /** @return Returns the groupbys. 
     */  
    public String[] getGroupbys() {  
        return groupbys;  
    }  
  
    /** @param groupbys The groupbys to set. 
     */  
    public QueryCondition setGroupbys(String[] groupbys) {  
        this.groupbys = groupbys;  
          
        return this;  
    }  
  
    /** 
     * @return the conditions 
     */  
    public List getConditions() {  
        return conditions;  
    }  
  
    /** 
     * @param conditions the conditions to set 
     */  
    public void setConditions(List conditions) {  
        this.conditions = conditions;  
    }  
  
    /** 
     * 添加等于操作的条件表达式，指定条件名称、条件值，默认查询操作为"="，并且不覆盖有同样名的查询条件 
     *  
     * @param condition_name 
     * @param condition_value 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, Object condition_value) {  
        return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, false);  
    }  
  
    /** 
     * 添加等于操作的条件表达式，指定条件名称、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件 
     *  
     * @param condition_name 
     * @param condition_value 
     * @param isReplaceRepeat 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, Object condition_value, boolean isReplaceRepeat) {  
        return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, isReplaceRepeat);  
    }  
  
    /** 
     * 添加条件表达式，指定条件名称、条件操作、条件值，默认不覆盖有同样名的查询条件 
     *  
     * @param condition_name 
     * @param condition_op 
     * @param condition_value 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, String condition_op, Object condition_value) {  
        return putCondition(condition_name, condition_op, condition_value, false);  
    }  
  
    /** 
     * 添加条件表达式，指定条件名称、条件操作、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件 
     *  
     * @param condition_name 
     * @param condition_op 
     * @param condition_value 
     * @param isReplaceRepeat 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, String condition_op, Object condition_value, boolean isReplaceRepeat) {  
        if (isReplaceRepeat)  
            removeCondition(condition_name);   
          
        if (condition_value == null)  
            condition_value = "";  
          
        conditions.add(new QueryExpression(condition_name, condition_op, condition_value));  
        return this;  
    }  
  
    /** 
     * 添加条件表达式，指定条件名称、条件操作、条件值、条件值类型，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件 
     *  
     * @param condition_name 
     * @param condition_op 
     * @param condition_value 
     * @param conditin_type 
     * @param isReplaceRepeat 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, String condition_op, Object condition_value, String conditin_type, boolean isReplaceRepeat) {  
        if (isReplaceRepeat)  
            removeCondition(condition_name);   
          
        if (condition_value == null)  
            condition_value = "";  
          
        conditions.add(new QueryExpression(condition_name, condition_op, condition_value, conditin_type));  
        return this;  
    }  
  
    /** 
     * 添加等于操作的条件表达式，指定条件名称、条件值，默认查询操作为"="，并且不覆盖有同样名的查询条件 
     *  
     * @param condition_name 
     * @param condition_value 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, long condition_value) {  
        return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, false);  
    }  
  
    /** 
     * 添加等于操作的条件表达式，指定条件名称、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件 
     *  
     * @param condition_name 
     * @param condition_value 
     * @param isReplaceRepeat 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, long condition_value, boolean isReplaceRepeat) {  
        return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, isReplaceRepeat);  
    }  
  
    /** 
     * 添加条件表达式，指定条件名称、条件操作、条件值，默认不覆盖有同样名的查询条件 
     *  
     * @param condition_name 
     * @param condition_op 
     * @param condition_value 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, String condition_op, long condition_value) {  
        return putCondition(condition_name, condition_op, condition_value, false);  
    }  
  
    /** 
     * 添加条件表达式，指定条件名称、条件操作、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件 
     *  
     * @param condition_name 
     * @param condition_op 
     * @param condition_value 
     * @param isReplaceRepeat 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, String condition_op, long condition_value, boolean isReplaceRepeat) {  
        if (isReplaceRepeat)  
            removeCondition(condition_name);   
  
        conditions.add(new QueryExpression(condition_name, condition_op, new Long(condition_value)));  
        return this;  
    }  
  
    /** 
     * 添加等于操作的条件表达式，指定条件名称、条件值，默认查询操作为"="，并且不覆盖有同样名的查询条件 
     *  
     * @param condition_name 
     * @param condition_value 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, int condition_value) {  
        return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, false);  
    }  
  
    /** 
     * 添加等于操作的条件表达式，指定条件名称、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件 
     *  
     * @param condition_name 
     * @param condition_value 
     * @param isReplaceRepeat 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, int condition_value, boolean isReplaceRepeat) {  
        return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, isReplaceRepeat);  
    }  
  
    /** 
     * 添加条件表达式，指定条件名称、条件操作、条件值，默认不覆盖有同样名的查询条件 
     *  
     * @param condition_name 
     * @param condition_op 
     * @param condition_value 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, String condition_op, int condition_value) {  
        return putCondition(condition_name, condition_op, condition_value, false);  
    }  
  
    /** 
     * 添加条件表达式，指定条件名称、条件操作、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件 
     *  
     * @param condition_name 
     * @param condition_op 
     * @param condition_value 
     * @param isReplaceRepeat 
     * @return 
     */  
    public QueryCondition putCondition(String condition_name, String condition_op, int condition_value, boolean isReplaceRepeat) {  
        if (isReplaceRepeat)  
            removeCondition(condition_name);   
  
        conditions.add(new QueryExpression(condition_name, condition_op, new Integer(condition_value)));  
        return this;  
    }  
  
/* ******************** 
 *  
 * 暂时屏蔽 
 *  把浮点数作为查询对象的情况很少见，这里就不对这个特例专门编写方法了 
 *  如果非要用，可以调用上面提供的方法同样可以实现这种查询 
 *  
 * ********************/  
  
//  /**  
//   * 添加等于操作的条件表达式，指定条件名称、条件值，默认查询操作为"="，并且不覆盖有同样名的查询条件  
//   *   
//   * @param condition_name  
//   * @param condition_value  
//   * @return  
//   */  
//  public QueryCondition putCondition(String condition_name, double condition_value) {  
//      return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, false);  
//  }  
//  
//  /**  
//   * 添加等于操作的条件表达式，指定条件名称、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件  
//   *   
//   * @param condition_name  
//   * @param condition_value  
//   * @param isReplaceRepeat  
//   * @return  
//   */  
//  public QueryCondition putCondition(String condition_name, double condition_value, boolean isReplaceRepeat) {  
//      return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, isReplaceRepeat);  
//  }  
//  
//  /**  
//   * 添加条件表达式，指定条件名称、条件操作、条件值，默认不覆盖有同样名的查询条件  
//   *   
//   * @param condition_name  
//   * @param condition_op  
//   * @param condition_value  
//   * @return  
//   */  
//  public QueryCondition putCondition(String condition_name, String condition_op, double condition_value) {  
//      return putCondition(condition_name, condition_op, condition_value, false);  
//  }  
//  
//  /**  
//   * 添加条件表达式，指定条件名称、条件操作、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件  
//   *   
//   * @param condition_name  
//   * @param condition_op  
//   * @param condition_value  
//   * @param isReplaceRepeat  
//   * @return  
//   */  
//  public QueryCondition putCondition(String condition_name, String condition_op, double condition_value, boolean isReplaceRepeat) {  
//      if (isReplaceRepeat)  
//          removeCondition(condition_name);   
//  
//      conditions.add(new QueryExpression(condition_name, condition_op, new Double(condition_value)));  
//      return this;  
//  }  
  
/* ******************** 
 *  
 * 暂时屏蔽 
 *  下面的传入布尔型的方法会和 
 *  putCondition(String condition_name, Object condition_value, boolean isReplaceRepeat)冲突 
 *  为了防止出现使用错误，先屏蔽该方法 
 *  
 * ********************/  
      
//  /**  
//   * 添加等于操作的条件表达式，指定条件名称、条件值，默认查询操作为"="，并且不覆盖有同样名的查询条件  
//   *   
//   * @param condition_name  
//   * @param condition_value  
//   * @return  
//   */  
//  public QueryCondition putCondition(String condition_name, boolean condition_value) {  
//      return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, false);  
//  }  
//  
//  /**  
//   * 添加等于操作的条件表达式，指定条件名称、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件  
//   *   
//   * @param condition_name  
//   * @param condition_value  
//   * @param isReplaceRepeat  
//   * @return  
//   */  
//  public QueryCondition putCondition(String condition_name, boolean condition_value, boolean isReplaceRepeat) {  
//      return putCondition(condition_name, QueryExpression.OP_EQ, condition_value, isReplaceRepeat);  
//  }  
//  
//  /**  
//   * 添加条件表达式，指定条件名称、条件操作、条件值，默认不覆盖有同样名的查询条件  
//   *   
//   * @param condition_name  
//   * @param condition_op  
//   * @param condition_value  
//   * @return  
//   */  
//  public QueryCondition putCondition(String condition_name, String condition_op, boolean condition_value) {  
//      return putCondition(condition_name, condition_op, condition_value, false);  
//  }  
//  
//  /**  
//   * 添加条件表达式，指定条件名称、条件操作、条件值，指定是否覆盖有同样名的查询条件，指定覆盖，则如果条件名称已经存在，覆盖原有条件  
//   *   
//   * @param condition_name  
//   * @param condition_op  
//   * @param condition_value  
//   * @param isReplaceRepeat  
//   * @return  
//   */  
//  public QueryCondition putCondition(String condition_name, String condition_op, boolean condition_value, boolean isReplaceRepeat) {  
//      if (isReplaceRepeat)  
//          removeCondition(condition_name);   
//  
//      conditions.add(new QueryExpression(condition_name, condition_op, new Boolean(condition_value)));  
//      return this;  
//  }  
      
    /** 
     * 去除给定名称的条件表达式，如果有多个同名的条件表达式，则删除第一个找到的条件表达式 
     *  
     * @param condition_name 
     */  
    public void removeCondition(String condition_name) {  
        QueryExpression queryExpression = getCondition(condition_name);  
        if (queryExpression != null)  
            conditions.remove(queryExpression);  
    }  
      
    /** 
     * 清除所有的查询条件 
     */  
    public void clearCondition() {  
        while (conditions.size() > 0)  
            conditions.remove(0);  
    }  
  
    /** 
     * 检索给定表达式名称找到的第一个表达式，如果没有找到满足条件的表达式，返回null 
     *  
     * @param condition_name 
     * @return 
     */  
    public QueryExpression getCondition(String condition_name) {  
        Iterator it = conditions.iterator();  
        while (it.hasNext()) {  
            QueryExpression queryExpression = (QueryExpression)it.next();  
            if (condition_name.equals(queryExpression.getName()))  
                return queryExpression;  
        }  
          
        return null;  
    }  
      
    /** 
     * 开始进行查询条件遍历 
     *  
     */  
    public void beginFindCondition() {  
        itCondition = conditions.iterator();  
    }  
      
    /** 
     * 遍历搜索下一个查询条件，如果已经遍历完成，返回null 
     *  
     * @return 
     */  
    public QueryExpression findNextCondition() {  
        if (itCondition == null)  
            beginFindCondition();  
          
        if (itCondition.hasNext())  
            return (QueryExpression)itCondition.next();  
        else  
            return null;  
    }  
  
    /** 
     * @return the orders 
     */  
    public List getOrders() {  
        return orders;  
    }  
  
    /** 
     * @param orders the orders to set 
     */  
    public void setOrders(List orders) {  
        this.orders = orders;  
    }  
      
    /** 
     * 添加排序字段，指定排序字段和排序顺序，如果有同名的排序字段，则覆盖排序方向 
     *  
     * @param order_name 
     * @param order_dir 
     * @return 
     */  
    public QueryCondition addOrder(String order_name, String order_dir) {  
        QueryOrder queryOrder = getOrder(order_name);  
          
        if (!QueryOrder.DIR_ASC.equals(order_dir) && !QueryOrder.DIR_DESC.equals(order_dir))   
            order_dir = QueryOrder.DIR_ASC;  
          
        if (queryOrder != null)  
            queryOrder.setDir(order_dir);  
        else  
            orders.add(new QueryOrder(order_name, order_dir));  
          
        return this;  
    }  
      
    /** 
     * 添加排序字段，默认为正序排序 
     *  
     * @param order_name 
     * @return 
     */  
    public QueryCondition addOrder(String order_name) {  
        return addOrder(order_name, QueryOrder.DIR_ASC);  
    }  
      
    /** 
     * 删除指定名称的排序字段 
     *  
     * @param order_name 
     */  
    public void removeOrder(String order_name) {  
        QueryOrder queryOrder = getOrder(order_name);  
        if (queryOrder != null)  
            orders.remove(queryOrder);  
    }  
      
    /** 
     * 清除所有的排序字段 
     */  
    public void clearOrder() {  
        while (orders.size() > 0)  
            orders.remove(0);  
    }  
      
    /** 
     * 返回指定名称的排序信息 
     *  
     * @param order_name 
     * @return 
     */  
    public QueryOrder getOrder(String order_name) {  
        Iterator it = orders.iterator();  
        while (it.hasNext()) {  
            QueryOrder queryOrder = (QueryOrder)it.next();  
            if (order_name.equals(queryOrder.getName()))  
                return queryOrder;  
        }  
          
        return null;  
    }  
      
    /** 
     * 开始遍历排序条件 
     */  
    public void beginFindOrder() {  
        itOrder = orders.iterator();  
    }  
      
    /** 
     * 遍历下一个排序条件，如果已经遍历完成，则返回null 
     * @return 
     */  
    public QueryOrder findNextOrder() {  
        if (itOrder == null)  
            beginFindOrder();  
          
        if (itOrder.hasNext())  
            return (QueryOrder)itOrder.next();  
        else  
            return null;  
    }  
  
    /** 
     * 返回当前查询的分页页号 
     *  
     * @return the pageNo 
     */  
    public int getPageNo() {  
        return pageNo;  
    }  
  
    /** 
     * 设置当前查询的分页页号 
     *  
     * @param pageNo the pageNo to set 
     */  
    public QueryCondition setPageNo(int pageNo) {  
        this.pageNo = pageNo;  
          
        return this;  
    }  
  
    /** 
     * 返回当前查询的分页每页记录数 
     *  
     * @return the pageSize 
     */  
    public int getPageSize() {  
        return pageSize;  
    }  
  
    /** 
     * 设置当前查询的分页每页记录数 
     *  
     * @param pageSize the pageSize to set 
     */  
    public QueryCondition setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
          
        return this;  
    }  
  
    /** 
     * @see java.lang.Object#equals(Object) 
     */  
    public boolean equals(Object object) {  
        if (!(object instanceof QueryCondition)) {  
            return false;  
        }  
        QueryCondition rhs = (QueryCondition) object;  
        return new EqualsBuilder().append(  
                this.selects, rhs.selects).append(this.groupbys, rhs.groupbys)  
                .append(this.orders, rhs.orders).append(this.pageSize, rhs.pageSize)  
                .append(this.conditions, rhs.conditions).append(this.pageNo, rhs.pageNo)  
                .isEquals();  
    }  
  
    /** 
     * @see java.lang.Object#hashCode() 
     */  
    public int hashCode() {  
        return new HashCodeBuilder(-2021626145, -489366831)  
                .append(this.selects)  
                .append(this.conditions)  
                .append(this.groupbys)  
                .append(this.orders)  
                .append(this.pageSize)  
                .append(this.pageNo)  
                .toHashCode();  
    }  
  
    /** 
     * @see java.lang.Object#toString() 
     */  
    public String toString() {  
        return new ToStringBuilder(this)  
                .append("selects", this.selects)  
                .append("conditions", this.conditions)  
                .append("groupbys", this.groupbys)  
                .append("orders", this.orders)  
                .append("pageSize", this.pageSize)  
                .append("pageNo", this.pageNo)  
                .toString();  
    }  
}  