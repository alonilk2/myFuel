package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * This entity class intended to give an option to send "Complex" messages between Client and Server,
 * Containing important methods to make it easier for the server and client to communicate.
 */
public class Request implements Serializable {
	private List<String> requestComponents = new ArrayList<String>();

	public Request(String str) {
		parseStringIntoComponents(str);
	}
	public Request() {}
	/**
	 * This method returns a specific component of a request by it's index.
	 * @param index The index of the component to receive.
	 * @return Returns the component requested as a String object.
	 */
	public String getRequestComponent(int index) {
		if(index >= requestComponents.size() || index < 0)
			return null;
		return requestComponents.get(index);
	}
	/**
	 * This method returns the Request as List of Strings.
	 * @return Returns the Request as a List of Strings.
	 */
	public List<String> getRequest(){
		return requestComponents;
	}
	/**
	 * This method gets a String and cut's it into components.
	 * @return Returns the Request as a List of Strings.
	 */
	public List<String> parseStringIntoComponents(String Sentence) {
		requestComponents.clear();
		String firstWord[] = Sentence.split(" ");
		for(String s : firstWord) {
			requestComponents.add(s);
		}
		return requestComponents;
	}
}
