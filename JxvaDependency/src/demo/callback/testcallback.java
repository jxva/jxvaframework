package demo.callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class testcallback {
   /**
    * 回调函数 
    * @param  str   参数，也可以直接传递数组
    */
    public void echo(String str)
    {
        System.out.println(str);
    }
    public static void main(String[] args) throws Exception{
        //动态装载类
        Class cls =Class.forName("demo.callback.testcallback");
        //取函数对象
        Method mtd =cls.getMethod("echo",new Class[]{String.class});
        //定义一个回调函数所在的类的实例
        Object cb =new testcallback();
        cls2  c2=new cls2();
        //call回调
        c2.Call(mtd,cb,new String[]{"call back function"});      
    }
}
class cls2
{
    /**
     *   回调函数
     *   @param mtd   函数
     *   @param obj   函数所关联对象
     *   @param param 函数参数
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public void Call(Method mtd, Object  obj, Object[] param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        mtd.invoke(obj,param);
    }
    public void method2(){}
}