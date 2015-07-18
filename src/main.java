import gui.SwingFrame;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import javax.swing.SwingUtilities;


public class main {

	public static void main(String args[]) throws InvocationTargetException,
	InterruptedException, ParseException {



		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				SwingFrame st = new SwingFrame();
			}
		});
	}
}
