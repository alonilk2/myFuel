package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Request implements Serializable {
	public Request(String str) {
		parseStringIntoComponents(str);
	}
	public Request() {
	}

	private List<String> requestComponents = new ArrayList<String>();
	
	public String getRequestComponent(int index) {
		if(index >= requestComponents.size() || index < 0)
			return null;
		return requestComponents.get(index);
	}
	
	public List<String> getRequest(){
		return requestComponents;
	}
	
	public List<String> parseStringIntoComponents(String Sentence) {
		requestComponents.clear();
		String firstWord[] = Sentence.split(" ");
		for(String s : firstWord) {
			requestComponents.add(s);
		}
		return requestComponents;
	}
}
