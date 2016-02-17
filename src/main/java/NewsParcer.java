import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class NewsParcer {
	
	
	final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
	private static String imgRegexp = "src=\"(.*?)\"";
	private static String videoRegexp = "href=\"(.*?)\"";

	Document newsLoader(String http) throws IOException{
		Document doc = null;

		doc = Jsoup.connect(http).timeout(10000).userAgent(USER_AGENT)
				.get();
		return doc;
	}

	String getNewsText(Document doc) throws IOException{
	
		Elements elements = doc.select("div[class=article__body]");
		Elements titleText=elements.select("p");
		
		return titleText.toString();
	}
	String getNewsImage(Document doc) throws IOException{
		
		Elements elements = doc.select("div[class=article__body]");
		String imageBlock = elements.select("img[src]").toString();
		Pattern patt = Pattern.compile(imgRegexp);
		Matcher match = patt.matcher(imageBlock);
		String imgHref = "";
		if (match.find()) {
			imgHref = match.group(1);
		}
			return imgHref;
	}
	/*
	static String getNewsVideo(Document doc) throws IOException{
		
		Elements elements = doc.select("div[class=article__body]");
		elements = elements.select("div[class=js-video-wrapper]");
		String videoBlock = elements.select("a[href]").toString();

		Pattern patt = Pattern.compile(videoRegexp);
		Matcher match = patt.matcher(videoBlock);
		String videoHref = "";
		while (match.find()) {
			videoHref = match.group(1);
		}
			return videoHref;
	}
	*/

	
}