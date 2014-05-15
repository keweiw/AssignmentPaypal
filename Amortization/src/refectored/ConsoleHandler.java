package refectored;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;

/**
 * Message handle class for input and output to console.
 * We would define a interface 
 * @author kewewang
 *
 */
public class ConsoleHandler implements IOHandler{
	private static ConsoleHandler instance = new ConsoleHandler();
	private static Console console = null;
	private boolean isConsole = false;
	
	private ConsoleHandler() {
		console = System.console();
		if (console != null) {
			isConsole = true;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static ConsoleHandler getInstance() {
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
			print(userPrompt + "\n");
			line = bufferedReader.readLine();
		}
		line.trim();
		return line;

	}
}
