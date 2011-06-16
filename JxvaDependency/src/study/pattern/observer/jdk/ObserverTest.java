package study.pattern.observer.jdk;

import java.util.Observable;
import java.util.Observer;

public class ObserverTest implements Observer{
	
	private int no;
	ObserverTest(int no){
		this.no=no;
	}

	/*
	 *  (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable,  java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		ObservableTest t=(ObservableTest)o;
		System.out.println(this.no+"==arg:"+arg+"     "+t.hasChanged());
	}
}
