package study.life;

/**
 * 运行结果是:
	feed cat
	feed cat
	这样的结果是因为重载是静态分派，在编译器执行的，取决于变量的声明类型，
	因为wc ,bc都是Cat所以调用的都是feed(Cat cat)的函数.
 * @author Administrator
 *
 */

class Cat{}
class WhiteCat extends Cat{}
class BlackCat extends Cat{}
public class Person {
    public void feed(Cat cat){
        System.out.println("feed cat");
    }
    public void feed(WhiteCat cat){
        System.out.println("feed WhiteCat");
    }
    public void feed(BlackCat cat){
        System.out.println("feed BlackCat");
    }
    public static void main(String[] args) {
        Cat wc = new WhiteCat();
        Cat bc = new BlackCat();
        Person p = new Person();
        p.feed(wc);
        p.feed(bc);
    }

}