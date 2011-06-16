package demo.callback;
interface Inf{
public void method();
}

class Impl1 implements Inf{
        public void method(){
                System.out.println("impl1 method calls");
        }
}

class Impl2 implements Inf{
        public void method(){
                System.out.println("impl2 method calls");
        }
}

class Invoker{
        Inf i;
        public Invoker(Inf i){
                this.i = i;
        }
        public void execute(){
                System.out.println("invoker execute calls");
                i.method();
        }
}


/**
 * JAVA的CALLBACK通过接口来实现。  
  例：  
  1.class   A,class   B  
  2.class   A实现接口operate  
  3.class   B拥有一个参数为operate接口类型的函数test(operate   o)  
  4.class   A运行时调用class   B中test函数,以自身传入参数  
  5.class   B已取得A，就可以随时回调A所实现的operate接口中的方法


来源：http://topic.csdn.net/t/20020210/19/527607.html中最后一个人的回帖


Callback机制通常用于两个类之间的协作，如下图

A-----将自己作为参数----->B
A<-----调用A的方法----->B
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2009-01-12 20:08:52 by Jxva
 */
public class Man{
        public static void main(String[] args){
                Invoker invoker1 = new Invoker(new Impl1());
                invoker1.execute();

                Invoker invoker2 = new Invoker(new Impl2());
                invoker2.execute();
        }
} 