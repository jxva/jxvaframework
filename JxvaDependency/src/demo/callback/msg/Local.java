package demo.callback.msg;
/**  
 * 
 * 执行Local类的main方法。

本文的目的并不是介绍使用的什么技术，而是重点阐述其实现原理。

 

一、 异步和同步

讲通俗点，异步就是不需要等当前执行的动作完成，就可以继续执行后面的动作。

 

通常一个程序执行的顺序是：从上到下，依次执行。后面的动作必须等前面动作执行完成以后方可执行。这就是和异步相对的一个概念——同步。

 

案例：

A、张三打电话给李四，让李四帮忙写份材料。

B、李四接到电话的时候，手上有自己的工作要处理，但他答应张三，忙完手上的工作后马上帮张三写好材料，并传真给张三。

C、通完电话后，张三外出办事。

 

说明：

张三给李四通完电话后，就出去办事了，他并不需要等李四把材料写好才外出。那么张三让李四写材料的消息就属于异步消息。

相反，如果张三必须等李四把材料写好才能外出办事的话，那么这个消息就属于同步消息了。

 

二、 异步的实现

传统的程序执行代码都是从上到下，一条一条执行的。

但生活中有很多情况并不是这样，以上的案例中，如果李四需要几个小时以后才能帮张三写好材料的话，那张三就必须等几个小时，这样张三可能会崩溃或者抓狂。

 

这种一条龙似的处理，显示不太合理。

 

可以使用以下办法来处理这种问题：

张三找王五去给李四打电话，等李四写好材料后，由王五转交给张三。这样张三就可以外出办其他的事情了。

 

问题得到了合理的解决，之前张三一条线的工作，由张三和王五两条线来完成了，两边同时进行，彼此不耽误。

 

三、 计算机语言的实现

办法有了，如何用程序来模拟实现呢？

 

A、以前由一个线程来处理的工作，可以通过新增一个线程来达到异步的目的。这也就是JAVA中的多线程技术。

B、最后李四写好的材料必须交给张三，以做他用。这就是回调。

 

回调你可以这样来理解：

A发送消息给B，B处理好A要求的事情后，将结果返回给A，A再对B返回的结果来做进一步的处理。

 

注意Local类中红色背景的那行：

remote.executeMessage(message, this);

executeMessage方法需要接收一个message参数，表示发送出去的消息，而CallBack参数是他自己，
也就是这里的this。表示发送消息后，由Local类自己来处理，调用自身的execute方法来处理消息结果。

如果这里不是用this，而是用其他的CallBack接口的实现类的话，那就不能称之为“回调”了，
在OO的世界里，那就属于“委派”。也就是说，“回调”必须是消息的发送者来处理消息结果，否则不能称之为回调。
这个概念必须明确。

 * 简单本地发送异步消息的类  
 * @author KOOK  
 *  
 */  
public class Local implements CallBack,Runnable{   
       
    /**  
     * 远程接收消息的类，模拟point-to-point  
     */  
    private Remote remote;   
       
    /**  
     * 发送出去的消息  
     */  
    private String message;   
       
    public Local(Remote remote, String message) {   
        super();   
        this.remote = remote;   
        this.message = message;   
    }   
  
    /**  
     * 发送消息  
     */  
    public void sendMessage()   
    {   
        /**当前线程的名称**/  
        System.out.println(Thread.currentThread().getName());   
        /**创建一个新的线程发送消息**/  
        Thread thread = new Thread(this);   
        thread.start();   
        /**当前线程继续执行**/  
        System.out.println("Message has been sent by Local~!");   
    }   
  
    /**  
     * 发送消息后的回调函数  
     */  
    public void execute(Object... objects ) {   
        /**打印返回的消息**/  
        System.out.println(objects[0]);   
        /**打印发送消息的线程名称**/  
        System.out.println(Thread.currentThread().getName());   
        /**中断发送消息的线程**/  
        Thread.interrupted();   
    }   
       
    public static void main(String[] args)   
    {   
        Local local = new Local(new Remote(),"Hello");   
           
        local.sendMessage();   
    }   
  
    public void run() {   
        remote.executeMessage(message, this);   
           
    }   
}  
