package refectored;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;

public class MessageHandler {
	private static MessageHandler instance = new MessageHandler();
	private static Console console = null;
	private boolean isConsole = false;
	
	private MessageHandler() {
		console = System.console();
		if (console != null) {
			isConsole = true;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static MessageHandler getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @param formatString
	 * @param args
	 */
	public void printf(String formatString, Object... args) {
		try {
			if (isConsole) {
				console.printf(formatString, args);
			} else {
				System.out.print(String.format(formatString, args));
			}
		} catch (IllegalFormatException e) {
			System.err.print("Error printing...\n");
		}

	}

	/**
	 * 
	 * @param s
	 */
	public void print(String s) {
		printf("%s", s);
	}

	/**
	 * 
	 * @param userPrompt
	 * @return
	 * @throws IOException
	 */
	public String readLine(String userPrompt) throws IOException {
		String line = "";
		if (isConsole) {
			line = console.readLine(userPrompt);
		} else {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			print(userPrompt);
			line = bufferedReader.readLine();
		}
		line.trim();
		return line;

	}
}
