package tagCrawler;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;



public class tagCrawler {
	public static void main(String[] args) throws IOException{	
		
		String url = "http://stackoverflow.com/tags";
		String content = "";
		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
		Elements temp = doc.select(".page-numbers");
		int count = Integer.parseInt(temp.get(temp.size()-2).text());
		System.out.println(count);
		
		long startTime = System.currentTimeMillis();

		
		for(int j=1;j<=count;j++) {
			doc = Jsoup.connect(url + "/?page=" + j).userAgent("Mozilla").timeout(10*1000).get();
			Elements tags = doc.select(".tag-cell .post-tag");
			for(int i=0;i<tags.size();i++) {
				//System.out.println(tags.get(i).text());
				content += "@DATAREC:" + tags.get(i).text() + "\n";
			}
		}
		//System.out.println(content);
		PrintWriter writer = new PrintWriter("./tags.rec", "UTF-8");
		writer.println(content);
		writer.close();
		
		long endTime = System.currentTimeMillis();
		long elapseTime = (endTime - startTime)/1000;
		System.out.println(elapseTime);
		System.out.println("Finish!");
	}
}
