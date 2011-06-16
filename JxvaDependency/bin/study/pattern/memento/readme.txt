 Java中的模式 --- 双重接口的实现,备忘录模式
一、定义：备忘录(memento)模式又叫快照(snapshot)模式或者token模式，主要功能：
备忘录模式是用一个对象来存储另外一个对象的内部状态的快照，实现备忘录模式的关键点是在不破坏封装的
情况下，将一个对象的状态捕捉住，并外部化，存储起来，从而可以在合适的时候，把这个对象还原。
说明：备忘录模式适模式中比较好理解的一个，这里就不举例子，但是备忘录模式是模式中实现比较难，或者说
实现比较巧的，这里主要说说。
二、备忘录模式的实现
1,备忘录模式中的角色
发起人：创建含有内部状态的备忘录对象，并使用备忘录对象存储状态
负责人：负责人保存备忘录对象，但不检查备忘录对象的内容
备忘录：备忘录对象将发起人对象的内部状态存起来，并保正其内容不被发起人对象之外的对象像读取
注意：在备忘录的角色中，定义了他必须对不同的人提供不同的接口，对发起人提供宽接口，对其它任何人提供窄
接口。也许你说我都提供宽接口得了。对这也是备忘录的一种实现，叫做白箱备忘录，不过这种方法的封装没有设计
好，安全性不够好。
2,白箱备忘录的实现：
 public class Originator{
     private String state;
     public Memento createMemento(){
         return new Memento(state);
     }
     public void restoreMemento(Memento memento){
         this.state = memento.getState();
     }
     public String getState(){
        return this.state;
    }
    public void setState(String state){
        this.state=state;
        System.out.println("Current state = " + this.state);
    }
}
public class Memento{
    private String state;
    public Memento(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setState(String state){
        this.state = state;
    }
}
public class Caretaker{
    private Memento memento;
    public Memento retrieveMemento(){
        return this.memento;
    }
    public void saveMemento(Memento memento){
        this.memento = memento;
    }
}
public class Client{
    private static Originator o = new Originator();
    private static Caretaker c = new Caretaker();
    public static void main(Sting[] args){
        o.setState("ON");
        c.saveMemento(o.createMemento());
        o.setState("OFF");
        o.restoreMemento(c.retrieveMemento());
    }
}
白箱的优点：实现简单
白箱的缺点：上边说了，破坏了封装，安全性有些问题。
说明：这里白箱的实现只保存了一个状态，其实是可以保存多个状态的。
3，双接口的实现，宽窄接口（黑箱)
如何实现宽窄接口呢，内部类也许是个好方法。我们把备忘录类设计"成发起人"的内部类，但这样还有的问题是同一
package中的其它类也能访问到，为了解决这个问题，我们可以把"备忘录"的方法设计成私有的方法，这样就
可以保正封装，又保正发起人能访问到。实现如下：
定义窄接口.
 public interface NarrowMemento{
     public void narrowMethod();
 }
 class Originator {
     private String state;
     private NarrowMemento memento;
     public Originator(){
     }
     public NarrowMemento createMemento(){
        memento = new Memento(this.state);
        return memento;
    }
    public void restoreMemento(NarrowMemento memento){
        Memento aMemento = (Memento)memento;
        this.setState(aMemento.getState());
    }
    public String getState(){
        return this.state;
    }
    public void setState(String state){
        this.state = state;
    }
    //内部类
    protected class Memento implements NarrowMemento{
        private String savedState;
        private Memento(String someState){
            saveState = someState;
        }
        private void setState(String someState){
            saveState = someState;
        }
        private String getState(){
            return saveState;
        }
        public void narrowMethod(){
            System.out.println("this is narrow method");
        }
        
    }
    public NarrowMemento getNarrowMemento(){
        return memento;
    }
}
public class Caretaker{
    private NarrowMemento memento;
    public NarrowMemento retrieveMemento(){
        return this.memento;
    }
    public void saveMemento(NarrowMemento memento){
        this.memento = memento;
    }
}
public class Client{
    private static Originator o = new Originator();
    private static Caretaker c = new Caretaker();
    public static void main(String[] args){
        //use wide interface
        o.setState("On");
        c.saveMemento(o.createMemento());
        o.setState("Off");
        o.restoreMemento(c.retrieveMemento());
        //use narrow interface
        NarrowMemento memento = o.getNarrowMemento();
        memento.narrowMethod();
    }
}
ok，实现了对大多数人实现比较窄的接口，对Originator实现了宽接口.
三,最后的一些说明：
1，前边两个例子都是记录了单个状态(单check点)，要实现多个状态点很容易，只须要把记录state的字符串换
成一个list，然後添加，取得。如果须要随机须得状态点，也可以用map来存放.这样多个check点就实现了。
2，一般情况下可以扩展负责人的功能，让负责人的功能更强大，从而让客户端的操做更少些。解放客户端。
3，自述历史模式，这个就是把发起人，负责人写在一个类中，平时的应用中这种方法比较常见。 