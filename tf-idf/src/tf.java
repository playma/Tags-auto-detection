import java.util.Hashtable;
import java.util.ArrayList;
import java.util.*;

public class tf {
	public tf(String data, Hashtable hashtable) {
		int start = data.indexOf("@QREC");
		int count = 0;
		
		while(true) {	
			int startPosition = data.indexOf("@CONTENT:", start);
			int endPosition = data.indexOf("@CODE:", start);
			String content = data.substring(startPosition + 9, endPosition -1);
			content = content.replaceAll("[\\pP‘’“”]", "");
			content = content.toLowerCase();
			content = content.replaceAll( "\\s", " " );
			//System.out.print(content);
			
			String[] words = content.split(" ");
			
			for(String word : words) {
				if(hashtable.containsKey(word)) {
					hashtable.put(word, (int)hashtable.get(word)+1);
				}
				else
					hashtable.put(word, 1);
				//System.out.println(word);
			}
			
			start = data.indexOf("QREC",start+1);
			count ++;
			if(start<0) break;
		}
		System.out.println(count);
		/*
		Enumeration e = hashtable.keys();
		while(e.hasMoreElements()){
			String s= e.nextElement().toString();
			String s2 = hashtable.get(s).toString();
			System.out.println(s+": " +s2);
			//System.out.println(s2);
		}*/
		/*
		List<String> v = new ArrayList<String>(hashtable.keySet());
		Collections.sort(v);
		for (String str : v) {
		      System.out.println(str + " " + hashtable.get(str));
		}*/
	}	
}
