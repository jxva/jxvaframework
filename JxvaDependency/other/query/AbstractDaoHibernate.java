package test.dao.query;
# import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.management.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.Session;

import com.mysql.jdbc.Constants;
  
/** 
 * @author zouxuemo 
 * 
 */  
public class AbstractDaoHibernate extends HibernateDaoSupport implements Dao {  
    protected final Log logger = LogFactory.getLog(getClass());  
  
    /* (non-Javadoc) 
     * @see com.lily.dap.dao.BasetDao#get(java.lang.Class, java.io.Serializable) 
     */  
    public Object get(Class clazz, Serializable id)  
            throws ObjectRetrievalFailureException {  
        Object o = getHibernateTemplate().get(clazz, id);  
  
        if (o == null) {  
            logger.warn("给定id为[" + id + "]的" + clazz.getName() + "对象没找到...");  
            throw new ObjectRetrievalFailureException(clazz, id);  
        }  
  
        return o;  
    }  
  
    /* (non-Javadoc) 
     * @see com.lily.dap.dao.BasetDao#save(java.lang.Object) 
     */  
    public void save(Object o) {  
        getHibernateTemplate().saveOrUpdate(o);  
    }  
  
    /* (non-Javadoc) 
     * @see com.lily.dap.dao.BasetDao#remove(java.lang.Class, java.io.Serializable) 
     */  
    public void remove(Class clazz, Serializable id)  
            throws ObjectRetrievalFailureException {  
        remove(get(clazz, id));  
    }  
      
    public void remove(Object entity) {  
        getHibernateTemplate().delete(entity);  
    }  
  
    /* (non-Javadoc) 
     * @see com.lily.dap.dao.BasetDao#reload(java.lang.Object) 
     */  
    public void reload(Object object) {  
        getHibernateTemplate().refresh(object);  
    }  
  
    /* (non-Javadoc) 
     * @see com.lily.dap.dao.BasetDao#gets(java.lang.Class, com.lily.dap.model.QueryCondition) 
     */  
    public List gets(Class clazz, QueryCondition queryCondition) {  
        return gets(clazz, queryCondition, null);  
    }  
  
    /* (non-Javadoc) 
     * @see com.lily.dap.dao.BasetDao#gets(java.lang.Class, com.lily.dap.model.QueryCondition, com.lily.dap.model.QueryCallBack) 
     */  
    public List gets(Class clazz, QueryCondition queryCondition, QueryCallback callBack) {  
        String hql = parseQueryCondition(clazz, queryCondition, callBack);  
          
        if ( logger.isDebugEnabled() ) {  
            logger.debug("gets - [" + hql + "]...");  
        }  
          
        return executeFind(hql, queryCondition.getPageNo(), queryCondition.getPageSize());  
    }  
      
    public long count(Class clazz, QueryCondition queryCondition) {  
        return count(clazz, queryCondition, null);  
    }  
      
    public long count(Class clazz, QueryCondition queryCondition, QueryCallback callBack) {  
        String hql = parseQueryCondition(clazz, queryCondition, callBack);  
          
        int index = hql.indexOf("group by");  
        if (index < 0)  
            index = hql.indexOf("order by");  
          
        if (index >= 0)  
            hql = hql.substring(0, index);  
              
        hql = "select count(*) " + hql.substring(hql.indexOf("from"));  
          
        if ( logger.isDebugEnabled() ) {  
            logger.debug("count - [" + hql + "]...");  
        }  
          
        List result =  executeFind(hql);  
        long count = 0;  
        if (result.size() > 0)  
            count = ((Long)result.get(0)).longValue();  
          
        return count;  
    }  
      
    private String parseQueryCondition(Class clazz, QueryCondition queryCondition, QueryCallback callBack) {  
        StringBuffer buf = new StringBuffer();  
          
        //如果指定了要返回的字段，则构造选择字段的HQL语句  
        if (queryCondition.getSelects() != null && queryCondition.getSelects().length > 0) {  
            String[] selects = queryCondition.getSelects();  
            buf.append("select ").append(selects[0]);  
              
            for (int i = 1; i < selects.length; i++)  
                buf.append(",").append(selects[i]);  
              
            buf.append(" ");  
        }  
              
        buf.append("from ").append(clazz.getName()).append(" where ");  
          
        //从查询表达式列表读取查询表达式，检查表达式字段是否属于类中的属性值，检查表达式操作是否支持，构造HQL条件语句  
        queryCondition.beginFindCondition();  
        QueryExpression expression;  
        while ((expression = queryCondition.findNextCondition()) != null) {  
            if (callBack != null) {  
                String cond = callBack.parseCondition(expression);  
                if (cond != null && !"".equals(cond)){  
                    if (!QueryCallback.IGRONE.equals(cond))  
                        buf.append(cond).append(" and ");  
                      
                    continue;  
                }  
            }  
              
            String name = expression.getName();  
              
            Class type;  
            Method method = null;  
            try {  
                method = DaoHelper.getMethod(clazz, name);  
            } catch (SecurityException e) {  
                logger.warn("给定的查询字段[" + name + "]在[" + clazz.getName() + "]类中不允许操作！");  
                continue;  
            } catch (NoSuchMethodException e) {  
                logger.warn("给定的查询字段[" + name + "]在[" + clazz.getName() + "]类中不存在！");  
                continue;  
            }  
              
            type = method.getReturnType();  
            String expressionType = expression.getType();  
              
            boolean isSingleQuotesFlag = false;  
            if (expressionType != null) {  
                if(QueryExpression.TYPE_STRING.equals(expressionType) || QueryExpression.TYPE_DATE.equals(expressionType))  
                    isSingleQuotesFlag = true;  
            } else if (type == String.class || type == Date.class || type == Time.class || type == Timestamp.class) {  
                isSingleQuotesFlag = true;  
            }  
              
            String value = DaoHelper.expressionValue2String(expression.getValue());  
  
            //如果是数值型，则如果条件值为Constants.RETRIEVE_ALL时忽略该条件。如果是字符串型，则如果条件值为空字符串时忽略该条件  
            if ("long".equals(type.getName()) || "int".equals(type.getName()) || type == Long.class || type == Integer.class) {  
                if (value.equals(String.valueOf(Constants.RETRIEVE_ALL)))  
                    continue;  
            } else if (type == String.class) {  
                if ("".equals(value))  
                    continue;  
            }  
              
            //其他类型就要求提供查询值  
            if ("".equals(value)) {  
                logger.warn("给定的查询字段[" + name + ":" + type.getName() + "]必须输入查询条件值！");  
                continue;  
            }  
              
            String op = expression.getOp();   
            if (QueryExpression.OP_EQ.equals(op) ||   
                QueryExpression.OP_NE.equals(op) ||   
                QueryExpression.OP_GE.equals(op) ||   
                QueryExpression.OP_GT.equals(op) ||  
                QueryExpression.OP_LE.equals(op) ||   
                QueryExpression.OP_LT.equals(op)) {  
                buf.append(name).append(" ").append(op).append(" ");  
                if (isSingleQuotesFlag)  
                    buf.append("'").append(value).append("'");  
                else  
                    buf.append(value);  
                buf.append(" and ");  
            } else if (QueryExpression.OP_LIKE.equals(op)) {  
                buf.append(name).append(" like '%").append(value).append("%' and ");  
            } else if (QueryExpression.OP_LLIKE.equals(op)) {  
                buf.append(name).append(" like '%").append(value).append("' and ");  
            } else if (QueryExpression.OP_RLIKE.equals(op)) {  
                buf.append(name).append(" like '").append(value).append("%' and ");  
            } else if (QueryExpression.OP_IN.equals(op) || QueryExpression.OP_NOTIN.equals(op)) {  
                if (QueryExpression.OP_IN.equals(op))  
                    buf.append(name).append(" in (");  
                else  
                    buf.append(name).append(" not in (");  
                  
                StringTokenizer valuest = new StringTokenizer(value, QueryExpression.VALUE_SPLIT);  
                int c = 0;  
                while (valuest.hasMoreTokens()) {  
                    String val = valuest.nextToken();  
  
                    if (c++ > 0)  
                        buf.append(", ");  
                          
                    if (isSingleQuotesFlag)  
                        buf.append("'").append(val).append("'");  
                    else  
                        buf.append(val);  
                }  
                  
                buf.append(") and ");  
            } else if (QueryExpression.OP_INCLUDE.equals(op)) {  
                int[] ary = DaoHelper.parseNumExpression(value);  
                if (ary.length == 0) {  
                    logger.warn("给定的include查询操作值[" + value + "]格式错误！");  
                    continue;  
                }  
                  
                buf.append(name).append(" in (").append(ary[0]);  
                for (int i = 1; i < ary.length; i++)  
                    buf.append(", ").append(ary[0]);  
                buf.append(") and ");  
            } else if (QueryExpression.OP_INQUERY.equals(op)) {  
                buf.append(name).append(" in (").append(value).append(") and ");  
            } else {  
                logger.warn("对" + name + "的查询操作[" + op + "]不可识别！");  
                continue;  
            }  
        }  
          
        if (" and ".equals(buf.substring(buf.length() - 5)))  
            buf = buf.delete(buf.length() - 5, buf.length());  
        else  
            buf = buf.delete(buf.length() - 7, buf.length());  
          
        if (queryCondition.getGroupbys() != null && queryCondition.getGroupbys().length > 0) {  
            String[] groupbys = queryCondition.getGroupbys();  
            buf.append(" group by ").append(groupbys[0]);  
              
            for (int i = 1; i < groupbys.length; i++)  
                buf.append(",").append(groupbys[i]);  
              
            buf.append(" ");  
        }  
          
        //从排序列表读取排序字段，检查排序字段是否属于类中的属性值，检查排序方式是否支持，构造HQL排序语句  
        buf.append(" order by ");  
        queryCondition.beginFindOrder();  
        QueryOrder order;  
        while ((order = queryCondition.findNextOrder()) != null) {  
            String name = order.getName();  
            String dir = order.getDir();  
//          try {  
//              Method method = DaoHelper.getMethod(name, clazz);  
//          } catch (SecurityException e) {  
//              logger.warn("给定的排序字段[" + name + "]在[" + clazz.getName() + "]类中不允许操作！");  
//              continue;  
//          } catch (NoSuchMethodException e) {  
//              logger.warn("给定的排序字段[" + name + "]在[" + clazz.getName() + "]类中不存在！");  
//              continue;  
//          }  
              
            buf.append(name).append(" ").append(dir).append(", ");  
        }  
  
        if (", ".equals(buf.substring(buf.length() - 2)))  
            buf = buf.delete(buf.length() - 2, buf.length());  
        else  
            buf = buf.delete(buf.length() - 10, buf.length());  
          
        return buf.toString();  
    }  
      
    public List executeFind(final String hql) {  
        return executeFind(hql, null, null, 0, 0);  
    }  
      
    public List executeFind(final String hql, final Object[] args, final Type[] types) {  
        return executeFind(hql, args, types, 0, 0);  
    }  
      
    public List executeFind(final String hql, final int pageNo, final int pageSize) {  
        return executeFind(hql, null, null, pageNo, pageSize);  
    }  
      
    public List executeFind(final String hql, final Object[] args, final Type[] types, final int pageNo, final int pageSize) {  
        return getHibernateTemplate().executeFind(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException {  
                Query query = session.createQuery(hql);  
  
                if ( args != null ) {  
                    for ( int i = 0; i < args.length; i++ ) {  
                        Object arg = args[i];  
                        Type type = null;  
                        if ( types != null && i < types.length ) {  
                            type = types[i];  
                        }  
                        if ( type == null ) {  
                            query.setParameter(i, arg);  
                        } else {  
                            query.setParameter(i, arg, type);  
                        }  
                    }  
                }  
                  
                if (pageSize > 0) {  
                    int offset = (pageNo - 1) * pageSize;  
                    int limit = pageSize;  
                    query.setFirstResult(offset);  
                    query.setMaxResults(limit);  
                      
                    if ( logger.isDebugEnabled() ) {  
                        logger.debug("查询结果实现翻页，每页" + pageSize + "条记录，翻页到第" + pageNo + "页...");  
                    }  
                }  
  
                List list = query.list();  
                if (list == null)   list = new ArrayList();  
                  
                return list;  
            }  
        });  
    }  
  
    public Object findUniqueResult(final String hql) {  
        return findUniqueResult(hql, null, null);  
    }  
  
    public Object findUniqueResult(final String hql, final Object[] args, final Type[] types) {  
        return getHibernateTemplate().execute(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException {  
                Query query = session.createQuery(hql);  
                if ( args != null ) {  
                    for ( int i = 0; i < args.length; i++ ) {  
                        Object arg = args[i];  
                        Type type = null;  
                        if ( types != null && i < types.length ) {  
                            type = types[i];  
                        }  
                        if ( type == null ) {  
                            query.setParameter(i, arg);  
                        } else {  
                            query.setParameter(i, arg, type);  
                        }  
                    }  
                }  
                  
                query.setMaxResults(1);  
                return query.uniqueResult();  
            }  
        });  
    }  
      
    public void executeSQLUpdate(final String sql) throws Exception {  
        executeSQLUpdate(sql, null, null);  
    }  
      
    public void executeSQLUpdate(final String sql, final Object[] args, final String[] types) throws Exception {  
        Session session = getSession();  
        Connection connection = session.connection();  
        PreparedStatement pstmt = null;  
           try {  
               pstmt = connection.prepareStatement(sql);  
               if ( args != null ) {  
                   for ( int i = 0; i < args.length; i++ ) {  
                       Object arg = args[i];  
                       String type = null;  
                       if ( types != null && i < types.length ) {  
                           type = types[i];  
                       }  
                         
                       int index = i + 1;  
                       if ("String".equals(type)) {  
                           pstmt.setString(index, (String)arg);  
                       } else if ("long".equals(type) || "Long".equals(type)) {  
                           pstmt.setLong(index, ((Long)arg).longValue());  
                       } else if ("int".equals(type) || "Integer".equals(type)) {  
                           pstmt.setInt(index, ((Integer)arg).intValue());  
                       } else if ("double".equals(type) || "Double".equals(type)) {  
                           pstmt.setDouble(index, ((Double)arg).doubleValue());  
                       } else if ("float".equals(type) || "Float".equals(type)) {  
                           pstmt.setFloat(index, ((Float)arg).floatValue());  
                       } else if ("Date".equals(type)) {  
                           java.sql.Date date = null;  
                           if (arg instanceof java.util.Date) {  
                               date = new java.sql.Date(((java.util.Date)arg).getTime());  
                           } else if (arg instanceof java.sql.Date)  
                               date = (java.sql.Date)arg;  
                           else  
                               date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse((String)arg).getTime());  
                             
                           pstmt.setDate(index, date);  
                       } else if ("Time".equals(type)) {  
                           pstmt.setTime(index, (Time)arg);  
                       } else if ("Timestamp".equals(type)) {  
                           pstmt.setTimestamp(index, (Timestamp)arg);  
                       }  
                   }  
               }  
                 
               pstmt.executeUpdate();  
           } catch(Exception e) {  
               throw e;  
           } finally {  
               try {  
                   if (pstmt != null)   pstmt.close();  
               } catch (Exception e) { }  
           }  
          
    }  
  
    public ResultSet executeSQLQuery(String sql) {  
        return executeSQLQuery(sql, 0, 0);  
    }  
  
    public ResultSet executeSQLQuery(String sql, final int pageNo, final int pageSize) {  
        logger.debug("executeSQLQuery - [" + sql + "]...");  
        if (pageSize > 0)  
            logger.debug("每页" + pageSize + "条，翻页到第" + pageNo + "页");  
          
        Connection conn = this.getSession().connection();  
        PreparedStatement pstmt = null;  
        ResultSet rs = null;  
        try {  
            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );  
            if (pageSize > 0) {  
                int offset = (pageNo - 1) * pageSize;  
                int maxRows = offset + pageSize;  
                  
                pstmt.setMaxRows(maxRows);  
                rs = pstmt.executeQuery();  
                      
                rs.absolute(offset);  
            } else  
                rs = pstmt.executeQuery();  
        } catch (SQLException e) {  
            e.printStackTrace();  
            throw new RuntimeException("Execute retrieve sql " + sql + " failed");  
        }  
          
        return rs;  
    }  
  
    public long executeSQLCount(String sql) {  
        int index = sql.indexOf("order by");  
        if (index >= 0)  
            sql = sql.substring(0, index);  
          
        sql = "select count(0) from (" + sql + ") mytable";  
        if ( logger.isDebugEnabled() ) {  
            logger.debug("executeSQLCount - [" + sql + "]...");  
        }  
          
        Connection conn = this.getSession().connection();  
        PreparedStatement pstmt = null;  
        ResultSet rs = null;  
        long count = 0;  
        try {  
            pstmt = conn.prepareStatement(sql);  
            rs = pstmt.executeQuery();  
            if(rs.next())  
                count = rs.getLong(1);  
        } catch (SQLException e) {  
            e.printStackTrace();  
            throw new RuntimeException("Execute retrieve sql " + sql + " failed");  
        }  
          
        return count;  
    }  
      
    public List ResultSet2List(ResultSet rs, TransitionCallback callback) {  
        List list = new ArrayList();  
        try {  
            while (rs.next()) {  
                Object obj = callback.transition(rs);  
                list.add(obj);  
            }  
        } catch (SQLException e) {  
            throw new RuntimeException( "SQLException Exception encountered", e);  
        }  
          
        return list;  
    }  
      
    public List ResultSet2List(ResultSet rs) {  
        int columnCount = 0;  
        String[] columnNames = null;  
        try {  
            ResultSetMetaData resultSetMetaData = rs.getMetaData();  
            columnCount = resultSetMetaData.getColumnCount();  
            columnNames = new String[columnCount+1];  
            for (int i = 1; i <= columnCount; i++) {  
                columnNames[i] = resultSetMetaData.getColumnName(i);  
            }  
        } catch (SQLException e) {  
            throw new RuntimeException( "SQLException Exception encountered", e);  
        }  
          
        List list = new ArrayList();  
        try {  
            while (rs.next()) {  
                Map map = new HashMap();  
                  
                for (int i = 1; i <= columnCount; i++)  
                    map.put(columnNames[i], rs.getObject(i));  
                  
                list.add(map);  
            }  
        } catch (SQLException e2) {  
            throw new RuntimeException( "SQLException Exception encountered", e2);  
        }  
          
        return list;  
    }  
}  