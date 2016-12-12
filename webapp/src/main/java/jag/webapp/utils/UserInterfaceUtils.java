package jag.webapp.utils;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

public class UserInterfaceUtils {

	
	public static void showNotification(String message,String title) {
		Messagebox.show(message, title, Messagebox.OK, Messagebox.INFORMATION);
	    //Clients.showNotification(message, Messagebox.INFORMATION, null, null, 3000);
	}
}
