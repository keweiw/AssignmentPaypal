package refectored;

import java.io.IOException;

public interface IOHandler {
	/**
	 * 
	 * @param formatString
	 * @param args
	 */
	public void printf(String formatString, Object... args);
	
	/**
	 * 
	 * @param s
	 */
	public void print(String s);
	
	/**
	 * 
	 * @param userPrompt
	 * @return
	 * @throws IOException
	 */
	public String readLine(String userPrompt) throws IOException;
}
