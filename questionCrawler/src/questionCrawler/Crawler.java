package questionCrawler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class Crawler extends Thread{
	public Crawler(String Name) {
		super(Name);
	}
	
	public void run() {		
			long startTime = System.currentTimeMillis();
			String ThreadName=Thread.currentThread().getName();
			int index = Integer.parseInt(ThreadName);
			int leng = 600;
			int start = index*leng + 1;
			int end = (index+1)*leng;
			System.out.println("Page from " + start  + " to " + end + " start.");
			
			String content = "";
			for(int j= start ;j<= end ;j++) {
				content = "";
				//System.out.println("page:" + j + " start crawling") ;
				long pageStartTime = System.currentTimeMillis();
				String url = "http://stackoverflow.com/questions?pagesize=50&sort=frequent";
				Document doc = null;
				try {
					doc = Jsoup.connect(url + "&page=" + j).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").timeout(0).ignoreHttpErrors(true).get();		
				} catch (NullPointerException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			        System.out.println( "Not Found Page: " + url + "&page=" + j);
			        continue;
			    } catch (HttpStatusException e) {
			    	e.printStackTrace();
			        System.out.println( "Not Found Page: " + url + "&page=" + j);
			        continue;
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        //e.printStackTrace();
			    	System.out.println( "Not Found Page: " + url + "&page=" + j);
			        continue;
			    }
				Elements questions = doc.select(".question-summary");
				for(int i=0;i<questions.size();i++) {
					//get question id
					String id = questions.get(i).attr("id").split("-")[2];
					//get question data
					String questionURL = "http://stackoverflow.com/questions/" + id;
					Document questionPage = null;
					try {
						questionPage = Jsoup.connect(questionURL).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").timeout(0).get();	
					} catch (NullPointerException e) {
				        // TODO Auto-generated catch block
				        //e.printStackTrace();
				        System.out.println( "Not Found Question: " + questionURL);
				        continue;
				    } catch (HttpStatusException e) {
				    	//e.printStackTrace();
				        System.out.println( "Not Found Question: " + questionURL);
				        continue;
				    } catch (IOException e) {
				        // TODO Auto-generated catch block
				        //e.printStackTrace();
				    	System.out.println( "Not Found Question: " + questionURL);
				        continue;
				    }
					String Qlink = questionPage.select("#question-header .question-hyperlink").attr("href");
					String Qtitle = questionPage.select("#question-header .question-hyperlink").text();
					String Qcode = questionPage.select(".question .post-text code").text();
					String Qscore = questionPage.select(".question .vote-count-post").text();
					String Qtags = questionPage.select(".post-taglist a").text();
					questionPage.select("code").remove();
					String Qcontent = questionPage.select(".question .post-text").text();
					//Document docc = Jsoup.connect("https://api.stackexchange.com/2.2/questions/" + id + "?order=desc&sort=activity&site=stackoverflow").userAgent("Mozilla").timeout(10*1000).get();
					//System.out.println(docc.data());
					//JSONObject json = readJsonFromUrl("https://api.stackexchange.com/2.2/questions/" + id + "?order=desc&sort=activity&site=stackoverflow");
					//System.out.println(json.toString());
					content += "@QREC:" + id + "\n";
					content += "@LINK:" + Qlink + "\n";
					content += "@TITLE:" + Qtitle + "\n";
					content += "@CONTENT:" + Qcontent + "\n";
					content += "@CODE:" + Qcode + "\n";
					content += "@SCORE:" + Qscore + "\n";
					content += "@TAGS:" + Qtags + "\n";
					break;
				}
				try {
					//PrintWriter writer = new PrintWriter("./questionsID" + ThreadName +  ".rec", "UTF-8");
					PrintWriter writer = new PrintWriter(new FileOutputStream( new File("./questionsID" + ThreadName +  ".rec"),  true /* append = true */)); 
					writer.println(content);
					writer.close();	
				} catch (IOException ex) {
	                System.err.println("An IOException was caught!");
	                ex.printStackTrace();
	            }
				long pageEndTime = System.currentTimeMillis();
				long pageElapseTime = (pageEndTime - pageStartTime)/1000;
				System.out.println( "Page:" + j + " is crawled use " + pageElapseTime + "s" );
			}
			//System.out.println(content);
			
				
			long endTime = System.currentTimeMillis();
			long elapseTime = (endTime - startTime)/1000;
			System.out.println("Finish : questionsID" + ThreadName +  ".rec");
			System.out.println(elapseTime);
	}
	
}
