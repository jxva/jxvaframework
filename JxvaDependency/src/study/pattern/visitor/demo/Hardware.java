package study.pattern.visitor.demo;

import java.util.ArrayList;
import java.util.List;

abstract class Hardware {
	protected double price;

	protected double getPrice() {
		return price;
	}

	abstract void accept(IComputerVisitor visitor);
}

class Mainboard extends Hardware {
	void accept(IComputerVisitor visitor) {
		visitor.visitMainboard(this);
	}
}

class Memory extends Hardware {
	void accept(IComputerVisitor visitor) {
		visitor.visitMemory(this);
	}
}

class Display extends Hardware {
	void accept(IComputerVisitor visitor) {
		visitor.visitDisplay(this);
	}
}

class NetworkAdapter extends Hardware {
	void accept(IComputerVisitor visitor) {
		visitor.visitNetworkAdapter(this);
	}
}

class Computer extends Hardware {
	private List<Hardware> parts = new ArrayList<Hardware>();

	public List<Hardware> add(Hardware hardware) {
		parts.add(hardware);
		return parts;
	}

	public void accept(IComputerVisitor visitor) {
		for (Hardware h : parts) {
			h.accept(visitor);
		}
	}
}

interface IComputerVisitor {
	public void visitMainboard(Mainboard mainboard);

	public void visitNetworkAdapter(NetworkAdapter adapter);

	public void visitDisplay(Display display);

	public void visitMemory(Memory memory);
}

// 遍历 computer 的每个部件,汇总价格
class PriceVisitor implements IComputerVisitor {
	// 总价格
	private double amountPrice;

	public void visitMainboard(Mainboard mainboard) {
		amountPrice += mainboard.getPrice();
	}

	public void visitNetworkAdapter(NetworkAdapter adapter) {
		amountPrice += adapter.getPrice();
	}

	public void visitDisplay(Display display) {
		amountPrice += display.getPrice();
	}

	public void visitMemory(Memory memory) {
		amountPrice += memory.getPrice();
	}
}