import java.io.*;
import java.util.*; 

public class main {
	
	
	public static void main(String args[]) {
		String rec="";
		Hashtable hashtable = new Hashtable(); 
		
		for(int i=0; i<=75;i++) {
			try {
				File file = new File("./rec/question/questionsID" + i + ".rec");
				FileInputStream fs = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fs.read(data);
				rec = new String(data, "UTF-8");
				tf go = new tf(rec, hashtable);
				//System.out.println(str);
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		//tf go = new tf(rec, hashtable);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("./hashtable.txt", "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
		  // report
		} 
		
		List<String> v = new ArrayList<String>(hashtable.keySet());
		Collections.sort(v);
		for (String str : v) {
			writer.println(str + " " + hashtable.get(str));
		     //System.out.println(str + " " + hashtable.get(str));
		}
		
		
		
		writer.println("The second line");
		writer.close();
	}


}
