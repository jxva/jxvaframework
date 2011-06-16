
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
package study.license;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LicenseGUI extends Shell {

	private Text text_6;
	private Text text_5;
	private CCombo combo_2;
	private Text text_4;
	private Text text_3;
	private Text text_2;
	private Text text;
	private CCombo Combo;
	protected Shell shell;
	
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			LicenseGUI shell = new LicenseGUI(display, SWT.SHELL_TRIM);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell
	 * @param display
	 * @param style
	 */
	public LicenseGUI(Display display, int style) {
		super(display, style);
		createContents();
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		setText("JLicense Creator Version1.0");
		setSize(389, 384);

		final Group jlicenseGroup = new Group(this, SWT.NONE);
		jlicenseGroup.setBounds(0, 0, 381, 357);

		Combo = new CCombo(jlicenseGroup, SWT.BORDER);
		Combo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		Combo.setEditable(false);
		Combo.setItems(new String[] {"Enterprise", "Development", "Evaluation", "Personal", "Unlimited"});
		Combo.select(1);
		Combo.setBounds(135, 16, 161, 19);

		final Label editionNameLabel = new Label(jlicenseGroup, SWT.NONE);
		editionNameLabel.setText("Edition Name:");
		editionNameLabel.setBounds(40, 25, 70, 15);

		final Label companyNameLabel = new Label(jlicenseGroup, SWT.NONE);
		companyNameLabel.setText("Company Name:");
		companyNameLabel.setBounds(30, 60, 80, 15);

		text = new Text(jlicenseGroup, SWT.BORDER);
		text.setBounds(135, 51, 161, 19);

		final Label productidLabel = new Label(jlicenseGroup, SWT.NONE);
		productidLabel.setText("ProductID:");
		productidLabel.setBounds(55, 90, 55, 15);

		text_2 = new Text(jlicenseGroup, SWT.BORDER);
		text_2.setBounds(135, 83, 161, 19);

		final Label hostnameLabel = new Label(jlicenseGroup, SWT.NONE);
		hostnameLabel.setText("Computer name:");
		hostnameLabel.setBounds(30, 126, 80, 15);

		text_3 = new Text(jlicenseGroup, SWT.BORDER);
		text_3.setText(LicenseCreate.getComputername());
		text_3.setBounds(135, 119, 161, 19);

		final Label ipAddressLabel = new Label(jlicenseGroup, SWT.NONE);
		ipAddressLabel.setText("IP Address:");
		ipAddressLabel.setBounds(50, 160, 60, 15);

		text_4 = new Text(jlicenseGroup, SWT.BORDER);
		text_4.setText(LicenseCreate.getHostname());
		text_4.setBounds(135, 153, 161, 19);

		final Label plateformNameLabel = new Label(jlicenseGroup, SWT.NONE);
		plateformNameLabel.setText("Plateform name:");
		plateformNameLabel.setBounds(30, 196, 80, 15);

		final Label plateformVersionLabel = new Label(jlicenseGroup, SWT.NONE);
		plateformVersionLabel.setText("Plateform Version:");
		plateformVersionLabel.setBounds(20, 229, 90, 15);

		combo_2 = new CCombo(jlicenseGroup, SWT.BORDER);
		combo_2.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		combo_2.setEditable(false);
		combo_2.setItems(new String[] {"JxvaFramework","JDAO","JSSO","JHmac","JMVC","JScript","JLicense","JTree"});
		combo_2.select(0);
		combo_2.setBounds(135, 188, 161, 19);

		text_5 = new Text(jlicenseGroup, SWT.BORDER);
		text_5.setText("V1.0");
		text_5.setBounds(135, 222, 161, 19);
		
		final Button generateButton = new Button(jlicenseGroup, SWT.NONE);
		generateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if(text.getText().equals(""))
				{
					MessageBox msgbox=new MessageBox(shell,SWT.OK|SWT.CANCEL);
					msgbox.setMessage("Company name is null!");
					if(msgbox.open()==SWT.OK)
					{
						text.setFocus();
						return;
					}
				}
				else if(text_2.getText().equals(""))
				{
					MessageBox msgbox=new MessageBox(shell,SWT.OK|SWT.CANCEL);
					msgbox.setMessage("ProductID is null!");
					if(msgbox.open()==SWT.OK)
					{
						text_2.setFocus();
						return;
					}
				}
				else if(text_3.getText().equals(""))
				{
					MessageBox msgbox=new MessageBox(shell,SWT.OK|SWT.CANCEL);
					msgbox.setMessage("Computer name is null!");
					if(msgbox.open()==SWT.OK)
					{
						text_3.setFocus();
						return;
					}
				}
				else if(text_4.getText().equals(""))
				{
					MessageBox msgbox=new MessageBox(shell,SWT.OK|SWT.CANCEL);
					msgbox.setMessage("IP Address is null!");
					if(msgbox.open()==SWT.OK)
					{
						text_4.setFocus();
						return;
					}
				}
				else if(text_5.getText().equals(""))
				{
					MessageBox msgbox=new MessageBox(shell,SWT.OK|SWT.CANCEL);
					msgbox.setMessage("Plateform Version is null!");
					if(msgbox.open()==SWT.OK)
					{
						text_5.setFocus();
						return;
					}
				}
				else if(text_6.getText().equals(""))
				{
					MessageBox msgbox=new MessageBox(shell,SWT.OK|SWT.CANCEL);
					msgbox.setMessage("Please select one directory for saving license!");
					if(msgbox.open()==SWT.OK)
					{
						text_6.setFocus();
						return;
					}
				}
				else
				{
					String License		=Combo.getText();
					String Company		=text.getText();
					String ProductID	=text_2.getText();
					//String Hostname		=text_3.getText();
					//String Computername	=text_4.getText();
					String Plateform	=combo_2.getText();
					String Version		=text_5.getText();
					String SavePath		=text_6.getText();
					
					LicenseCreate.setLicense(License);
					LicenseCreate.setCompany(Company);
					LicenseCreate.setProductID(ProductID);
					LicenseCreate.setPlateform(Plateform);
					LicenseCreate.setVersion(Version);
					
					//文件路径的最后一个字符为\或/,去掉这个字符
					if(SavePath.endsWith("/")||SavePath.endsWith("\\")){
						SavePath=SavePath.substring(0,SavePath.length()-1);
					}//文件路径的最后一个字符为\或/,去掉这个字符 ends
					
					//将文件路径中的\替换成/
					StringBuffer sb = new StringBuffer(SavePath);
					for (int i = 0; i < sb.length(); i++) {
						if (sb.charAt(i) == '\\') {
							sb.setCharAt(i, '/');
						}
					}
					SavePath = sb.toString();
					if(SavePath.charAt(SavePath.length()-1)!='/'){
						SavePath+="/";
					}
					//将文件路径中的\替换成/ ends
					if(LicenseCreate.saveLicense(SavePath+"jlicense.lic"))
					{
						System.out.println("JLicense file Generate Success!");
						System.out.println(SavePath+"jlicense.lic");
						MessageBox msgbox=new MessageBox(shell,SWT.OK);
						msgbox.setMessage("JLicense file Generate Success!");
						if(msgbox.open()==SWT.OK)
						{
							return;
						}
					}

				}
			}
		});
		generateButton.setText("Generate");
		generateButton.setBounds(100, 310, 80, 23);

		final Button resetButton = new Button(jlicenseGroup, SWT.NONE);
		resetButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox msgbox=new MessageBox(shell,SWT.OK|SWT.CANCEL);
				msgbox.setMessage("Are you sure to exit JLicenseGUI!");
				if(msgbox.open()==SWT.OK)
				{
					System.exit(0);
					return;
				}
			}
		});
		resetButton.setText("Cancel");
		resetButton.setBounds(222, 309, 80, 23);

		final Link eclipseorgLink = new Link(jlicenseGroup, SWT.NONE);
		eclipseorgLink.setText("<a href=\"http://www.jxva.com/\" target=\"_blank\">Www.Jxva.Com</a>");
		eclipseorgLink.setBounds(10, 0, 80, 15);

		final Label savePathLabel = new Label(jlicenseGroup, SWT.NONE);
		savePathLabel.setText("Save Path:");
		savePathLabel.setBounds(55, 260, 55, 15);

		text_6 = new Text(jlicenseGroup, SWT.BORDER);
		text_6.setEditable(false);
		text_6.setBounds(135, 260, 130, 19);

		final Button browserButton = new Button(jlicenseGroup, SWT.NONE);
		browserButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog d	=new DirectoryDialog(shell, SWT.OPEN);
				d.setFilterPath("C:/");
				String selected =d.open();
	            if (selected!=null)
	            {
	            	text_6.setText(selected);
	            }
	            else
	            {
	                return;
	            }
			}
		});
		browserButton.setText("Browser");
		browserButton.setBounds(275, 260, 55, 19);
		//
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
