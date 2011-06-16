package study.pattern.memento;

public class Client {
	private static Originator o = new Originator();
	private static Caretaker c = new Caretaker();

	public static void main(String[] args) {
		o.setState("ON");
		c.saveMemento(o.createMemento());
		o.setState("OFF");
		o.restoreMemento(c.retrieveMemento());
	}
}