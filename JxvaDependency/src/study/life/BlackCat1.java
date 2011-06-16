package study.life;

/**
 * 动态分派定义：发生在运行期，动态分派，动态的置换掉某个方法。
 * 这个时候的结果是:
	black cat eat
	这样的结果是因为在执行期发生了向下转型，就是动态分派了。
 * @author Administrator
 *
 */
class Cat1{
    public void eat(){
        System.out.println("cat eat");
    }
}
public class BlackCat1 extends Cat1{
    public void eat(){
        System.out.println("black cat eat");
    }
    public static void main(String[] args){
        Cat1 cat = new BlackCat1();
        cat.eat();
    }
}