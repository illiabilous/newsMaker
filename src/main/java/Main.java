import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
	static TitleEdit titleedit = new TitleEdit();
	static MysqlDAO mysql=new MysqlDAO();
	static 	ArrayList<String> PAGES = new ArrayList<>();
	static List<String> currentNewsTitles = new ArrayList<>();
	static List<Integer> titleTaxonomies = new ArrayList<>();
	public static void main(String[] args) throws IOException, ParseException, InterruptedException, SQLException {
		int counterNews=0;
		int maxId=mysql.selectMaxID();
		currentNewsTitles=mysql.selectTranslationQuery(maxId);
		System.err.println(currentNewsTitles);
		
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
				String postTitle = titleedit.titlereplace(newsTitle);
				System.out.println(postTitle);
				
				String postName=titleedit.newsNameGenerator(postTitle);
				
				//DOWNLOADING Image to PC
				/*if (newsImage!=null){
					download.loadImage(newsImage,posttitle);
				}*/
				
				//==== REWRITE - text content formation
				String postContent=rewrite.rewrite(newsText, newsTitle, newsImage);
				//System.out.println(postContent);
				System.out.println(maxId);
				
				//====== add post to DB;
				
				if(!currentNewsTitles.contains(postTitle)){
					
					
					System.out.println("POST TITLE(in Main) "+postTitle);
					mysql.insertTranslationQuery(postContent, postTitle, postName);
					long newsId=mysql.newsId(postTitle);
					
					titleTaxonomies=news.getNewsTaxonomy(postTitle);
					
					//System.out.println("news id"+newsId);
					
					for (int i = 0; i < titleTaxonomies.size(); i++) {
						System.out.println("news taxon "+titleTaxonomies.get(i));
						mysql.insertTerm(newsId, titleTaxonomies.get(i));
					}
					
				mysql.insertSeo(newsId,postTitle,postContent);
				}
				
				
				System.err.println("===THE END OF THIS article===\n");
				}
			System.out.println("====="+page+"===THE END===");
		}
		System.out.println("========FULL END==========");
	}

}