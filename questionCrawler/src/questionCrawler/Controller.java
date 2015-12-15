package questionCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

public class Controller {
	public static void main(String args[]) {
		
		String url = "http://stackoverflow.com/questions?pagesize=50&sort=frequent";
		String content = "";
		
		try{
			Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(10*1000).get();
			Elements temp = doc.select(".page-numbers");
			int count = Integer.parseInt(temp.get(temp.size()-2).text());
			System.out.println(count + " pages to crawl");
			
			int leng = 600;
			System.out.println(count/leng + " threads");
			// Create new instance of crawler
			for(int i=0;i<=count/leng;i++) {
				Crawler webCrawl = new Crawler(Integer.toString(i));
				webCrawl.start();
				break;
			}
			// Crawl and return list of visited sites
		} catch(IOException ioEx) {
		    ioEx.printStackTrace(); // or what ever you want to do with it
		}
	}
}
