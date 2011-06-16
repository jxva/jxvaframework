package net.jxva.sql;
import java.util.ArrayList;
import java.util.List;

/** *//**
 * SQL语句整形工具类
 * @author 何杨（heyang78@gmail.com）
 *
 * @since 2009-2-5 上午11:42:03
 * @version 1.00
 */
public class sqlformatter2Util{
    public static void main(String[] args){
        List<String> ls=new ArrayList<String>();
        
        
        ls.add("select ( * )  from dual");    
        ls.add("SELECT * frOm dual");
        ls.add("Select C1,c2 From tb");
        ls.add("selecT c1,c2 from tb");
        ls.add("select count(*) from t1");
        ls.add("select c1,c2,c3 from t1 where condi1=1 ");
        ls.add("Select c1,c2,c3 From t1 Where condi1=1 ");
        ls.add("select c1,c2,c3 from t1,t2 where ( condi3=3 or condi4=5 ) order   by o1,o2");
        ls.add("select f1,(select f2 from t01) from t02 where 1=1");
        ls.add("select f1,( select a from b ) from ( select f1,f2 from ( select f1,f2,f3 from tb ) ),t4 where 1=1 ");
        ls.add("select f1,( select * from tb2,( select * from ( select * from ( select * from tb5 ) ) ) ) from tabl1 where 1=1");
        ls.add("");
        ls.add("Select c1 1,c2,c3 from t1 3,t2 4 Where condi3=3 and condi4=5 Order   by o1,o2");
        ls.add("select c1,c2,c3 from    t1,t2,  t3 where condi1=5 and condi6=6 or condi7=7 group  by g1,g2");
        ls.add("Select c1,c2,c3 From t1,t2,t3 Where condi1=5 and condi6=6 or condi7=7 Group  by g1,g2");
        ls.add("Select c1,c2,c3 From t1,t2,t3 Where condi1=5 and ( condi6=6 or condi7=7 ) Group  by g1,g2,g3 order  by g2,g3");
        ls.add("select c1,c2,c3 from t1 left join t2 on condi3=3 or condi4=5 order   by o1,o2");
        ls.add("select c1,c2,c3 from t1 right join t2 on condi3=3 or condi4=5 order   by o1,o2");
        ls.add("select c1,c2,c3 from t1 inner join t2 on condi3=3 or condi4=5 order   by o1,o2");
        ls.add("select c1,c2,c3 from t1 left join t2 having condi3=3 or condi4=5 group by g1,g3,g5 order   by o1,o2");
        
        
        ls.add("delete from table");
        ls.add("delete from table where 1=1");
        ls.add("delete from table where c1=1 and c2=2 or c3=3");
        
        ls.add("update checktable set ID='' where 1=1 ");
        ls.add("update checktable set ID='', NAME='' where 1=1 and 2=2");
        ls.add("update checktable set ID='', NAME='', count='', remark='' where 1=1 and 2=2 or 3=3 ");
        
        
        ls.add(" insert into checktable ( ID ) values ( '1' ) ");
        ls.add(" insert into checktable ( ID,r ) values ( '1','' ) ");
        ls.add(" insert into checktable ( ID, NAME, count, remark ) values ( '1', '2', '3', '4' ) ");
        
        
        ls.add("insert into checktable select c1,c2,c3 from t1 where condi1=1 ");
        ls.add("insert into checktable Select c1,c2,c3 From t1 Where condi1=1 ");
        ls.add("insert into checktable select c1,c2,c3 from t1,t2 where condi3=3 or condi4=5 order   by o1,o2");
        ls.add("insert into checktable Select c1 1,c2,c3 from t1 3,t2 4 Where condi3=3 and condi4=5 Order   by o1,o2");
        ls.add("insert into checktable select c1,c2,c3 from    t1,t2,  t3 where condi1=5 and condi6=6 or condi7=7 group  by g1,g2");
        ls.add("insert into checktable Select c1,c2,c3 From t1,t2,t3 Where condi1=5 and condi6=6 or condi7=7 Group  by g1,g2");
        ls.add("insert into checktable Select c1,c2,c3 From t1,t2,t3 Where condi1=5 and condi6=6 or condi7=7 Group  by g1,g2,g3 order  by g2,g3");
        ls.add("insert into checktable select c1,c2,c3 from t1 left join t2 on condi3=3 or condi4=5 order   by o1,o2");
        ls.add("insert into checktable select c1,c2,c3 from t1 right join t2 on condi3=3 or condi4=5 order   by o1,o2");
        ls.add("insert into checktable select c1,c2,c3 from t1 inner join t2 on condi3=3 or condi4=5 order   by o1,o2");
        ls.add("insert into checktable select c1,c2,c3 from t1 left join t2 having condi3=3 or condi4=5 group by g1,g3,g5 order   by o1,o2");
        
        ls.add("select (select * from dual)  from dual");    
        ls.add("select (*)  from dual");    
        ls.add("select count(*)  from dual");    
        ls.add("select  id,name from (select  id,name from (select id,name from customer) t1 ) t2");    
        for(String sql:ls){
            System.out.println("原始的Sql为:\n"+(sql));
            System.out.println("解析后的的Sql为:\n"+getFormatedSql(sql));
        }
    }
    
    /** *//**
     * 取得整形完毕的Sql语句
     * Entry Point：这个包的入口点
     * @param sql
     * @return
     */
    public static String getFormatedSql(String sql){
        // 去除前后空白
        sql=sql.trim();
        // 将Sql语句中原有的回车换行替换成空白
        sql=sql.replaceAll("(\\n+|\\r+)", " ");
        
        return (new Fomatter(sql).getFomattedSql());
    }
}