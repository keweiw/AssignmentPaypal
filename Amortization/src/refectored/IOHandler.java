package refectored;

import java.io.IOException;

public interface IOHandler {
	public void printf(String formatString, Object... args);
	public void print(String s);
	public String readLine(String userPrompt) throws IOException;
}
