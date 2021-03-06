package jag.webapp.utils;

import org.zkoss.zul.Messagebox;

public class UserInterfaceUtils {

	public static void showNotification(String message, String title) {
		Messagebox.show(message, title, Messagebox.OK, Messagebox.INFORMATION);
	}
	
	public static void showError(String message, String title) {
		Messagebox.show(message, title, Messagebox.ABORT, Messagebox.ERROR);
	}
}
