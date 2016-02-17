import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.ReplicationConnection;


public class Main {
	
	final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
	static ArrayList<String> hrefs = new ArrayList<>();
	static NewsParcer news = new NewsParcer();
	static Rewrite rewrite = new Rewrite();
	static ImageTreatment download = new ImageTreatment();
	static TitleEdit titlemod = new TitleEdit();
	
	
	public static void main(String[] args) throws IOException {	
		
		ArrayList<String> PAGES = new ArrayList<>();
		PAGES.add("http://sport.rbc.ru/news/football/");
		PAGES.add("http://sport.rbc.ru/article/football/");
		for(int page=0; page<PAGES.size(); page++){
			//getting news hrefs
			Document doc = null;
			doc = Jsoup.connect(PAGES.get(page)).timeout(10000).userAgent(USER_AGENT)
					.get();
			Elements elements = doc.select("a[class=announce__more]");
			Elements links=elements.select("a[href]");
	
			//let's do this
			for (Element link : links) {
				String newsHref=link.attr("abs:href"); 
				Document newsHtml=news.newsLoader(newsHref); 
				
				//taking html,title,picture from source
				String newsTitle=newsHtml.title(); // заголовок			
				String newsImage=news.getNewsImage(newsHtml);//картинка
				String newsText=news.getNewsText(newsHtml);//текст
				
				//TITLE EDIT - replacing \" and ". ВІДЕО"
				String posttitle = titlemod.titlereplace(newsTitle);
				System.out.println(posttitle);
				
				String postname=titlemod.newsNameGenerator(posttitle);
				
				//DOWNLOADING Image to PC
				/*if (newsImage!=null){
					download.loadImage(newsImage,posttitle);
				}*/
				
				//==== REWRITE - text content formation
				String postcontent=rewrite.rewrite(newsText, newsTitle, newsImage);
				System.out.println(postcontent);
				
				//==== SEO formation
				String seokeywords=posttitle;
				String seotitle=newsTitle;
				String seometa=rewrite.getmeta(newsText);
				//System.out.println("meta "+meta);
				
				
				//----DB--------
				//!!add news to DB;
				//!!add to DB seo;
				//!!add relationship to DB;
				
				
				
				System.err.println("===THE END OF THIS article===\n");
				}
			System.out.println("====="+page+"===THE END===");
		}
		System.out.println("========FULL END==========");
	}

}