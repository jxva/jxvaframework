package study.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现观察者模式有很多形式，比较直观的一种是使用一种“注册——通知——撤销注册”的形式。下面的三个图详细的描述了这样一种过程：
　　1：观察者（Observer）将自己注册到被观察对象（Subject）中，被观察对象将观察者存放在一个容器（Container）里。
　　2：被观察对象发生了某种变化（如图中的SomeChange），从容器中得到所有注册过的观察者，将变化通知观察者。
　　3：观察者告诉被观察者要撤销观察，被观察者从容器中将观察者去除。 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-16 15:08:19 by Jxva
 */

//主题,这里是快餐店 ,被观察对象 
class Subject {
	
	private List<Observer> observers = new ArrayList<Observer>();

	public void add(Observer o) {
		observers.add(o);
	}

	// 通知,对订阅主题的客户发布通知,比如“外卖已好”
	public void _notify() {
		for (Observer c : observers) {
			c.getFood();
		}
	}
	
	public static void main(String[] args){
		Subject subject = new Subject();  
		Observer zhangSan = new Observer(subject);  
		subject._notify(); 
	}
}

// 订阅者,这里是客户,观察者
class Observer {
	Observer(Subject shop) {
		// 将客户加入到快餐店列表
		shop.add(this);
	}

	// 回调函数,当接到通知后,客户的动作
	public void getFood() {
		// 取得外卖
		System.out.println("取得外卖");
	}
}