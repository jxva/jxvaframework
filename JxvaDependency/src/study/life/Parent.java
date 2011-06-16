package study.life;

public class Parent 
{ 
    public void test(){
    }
 
    public Parent(){
        test();
    }
 
    public static void main(String[] args){
        new Child();
    }
}
 
class Child extends Parent 
{
    private int instanceValue = 20;
    public void test() {
        System.out.println("instance value is: " + instanceValue);
    }
}
