/*
 * Copyright @ 2006-2008 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package study.pattern.visitor.demo;

/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2008-12-16 16:53:14 by Jxva
 */
public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Computer computer = new Computer();
		Mainboard mainboard = new Mainboard();
		NetworkAdapter networkAdapter = new NetworkAdapter();
		Display display = new Display();
		Memory memory = new Memory();
		computer.add(mainboard);
		computer.add(networkAdapter);
		computer.add(display);
		computer.add(memory);
		IComputerVisitor visitor = new PriceVisitor();
		computer.accept(visitor);
		System.out.println(computer.getPrice());

	}

}
