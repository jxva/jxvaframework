package study.pattern.observer.jdk;

import java.util.Observable;

/**
 * Observer是观察者角色,Observable是被观察目标(subject)角色
 * 
 * Observable是一个封装subject基本功能的类，比如注册observer（attach功能），注销observer（detatch功能）等。
 * 这些功能是任何一个扮演observerable角色的类都需要实现的，从这一点上来讲，JDK里将这些通用功能专门封装在一个类里，
 * 显得合情合理。通常情况下，我们的类只要从Observerable类派生就可以称为observerable角色类，使用非常简单。
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-16 15:40:27 by Jxva
 */
public class ObservableTest extends Observable{

	public static void main(String[] args){
		ObservableTest test=new ObservableTest();
		test.addObserver(new ObserverTest(1));
		test.addObserver(new ObserverTest(2));
		test.setChanged();
		test.notifyObservers("xxy");
	}
}
