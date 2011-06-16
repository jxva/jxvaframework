package study.other;

import javax.activation.CommandInfo;
import javax.activation.MailcapCommandMap;

public class MailcapCommandMapDemo1 {
	public static void main(String[] args) {
		demo1();
		demo2();
	}

	private static void demo2() {
		MailcapCommandMap mailcapCommandMap = new MailcapCommandMap();
	    String mailcap = "text/plain;; " + "x-java-content-handler=beans.TextHandler;"
	        + "x-java-view=beans.TextViewer;" + "x-java-edit=beans.TextEditor";
	    mailcapCommandMap.addMailcap(mailcap);
	    // Get all MIME types
	    String[] mimeTypes = mailcapCommandMap.getMimeTypes();
	    for (String mimeType : mimeTypes) {
	      System.out.println(mimeType);
	      CommandInfo[] commandInfos = mailcapCommandMap.getAllCommands(mimeType);
	      for (CommandInfo info : commandInfos) {
	        System.out.println(" " + info.getCommandName() + " : "
	            + info.getCommandClass());
	      }
	    }
	}

	private static void demo1() {
		MailcapCommandMap mailcapCommandMap = new MailcapCommandMap();

		String[] mimeTypes = mailcapCommandMap.getMimeTypes();
		for (String mimeType : mimeTypes) {
			System.out.println(mimeType);
			CommandInfo[] commandInfos = mailcapCommandMap
					.getAllCommands(mimeType);
			for (CommandInfo info : commandInfos) {
				System.out.println(" " + info.getCommandName() + " : "
						+ info.getCommandClass());
			}
		}
	}
	
	
}