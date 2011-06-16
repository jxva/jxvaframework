
/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
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
 *
 */
package study.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class View extends Shell {

	private Model model;
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	//在视图层使用控制器,是为了绑定事件
	//将控制器做为全局变量,可以绑定更多事件
	private Controller controller;
	
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			View view = new View(display, SWT.SHELL_TRIM);
			view.open();
			view.layout();
			while (!view.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public View(Display display, int style) {
		super(display, style);
		setSize(222, 100);
		createUI();
		pack();	
	}
	
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	protected void createUI() {
		model=new Model();
		controller=new Controller(this);
		
		model.setInfo(new Text(this, SWT.BORDER));
		model.getInfo().setBounds(30, 10, 80, 25);
		
		final Button btnOk = new Button(this, SWT.NONE);
		btnOk.setText("Ok");
		btnOk.setBounds(124, 10, 80, 23);
		//将事件绑定控制层
		btnOk.addSelectionListener(controller.okListener);
	}
}
