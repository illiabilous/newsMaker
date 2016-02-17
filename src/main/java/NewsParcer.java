import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class NewsParcer {
	
	
	final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
	private static String imgRegexp = "src=\"(.*?)\"";
	//private static String videoRegexp = "href=\"(.*?)\"";

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
	List<Integer> getNewsTaxonomy(String header){
		List<Integer> taxonomies= new  ArrayList<>();
		HashMap<String, Integer> headerAndHref= new HashMap<>();
		int taxonomy  = 35;
		taxonomies.add(taxonomy);
		headerAndHref.put("Челси",40);
		headerAndHref.put("Арсенал",40);
		headerAndHref.put("Манчестер Юнайтед",40);
		headerAndHref.put("Манчестер Сити",40);
		headerAndHref.put("Ливерпуль",40);
		headerAndHref.put("Тоттенхэм",40);
		headerAndHref.put("Лестер",40);
		headerAndHref.put("Реал Мадрид",41);
		headerAndHref.put("Атлетико Мадрид",41);
		headerAndHref.put("Барселона",41);
		headerAndHref.put("Севилья",41);
		headerAndHref.put("Милан",43);
		headerAndHref.put("Интер",43);
		headerAndHref.put("Ювентус",43);
		headerAndHref.put("Бавария",42);
		headerAndHref.put("ПСЖ",44);
		headerAndHref.put("Боруссия Дортмунд",42);
		headerAndHref.put("Зенит",39);
		headerAndHref.put("Локомотив",39);
		headerAndHref.put("ЦСКА",39);
		headerAndHref.put("Динамо Москва",39);
		headerAndHref.put("Спартак",39);
		headerAndHref.put("Краснодар",39);
		headerAndHref.put("Кубан",39);
		headerAndHref.put("Шахтер",45);
		headerAndHref.put("Динамо Киев",45);
		headerAndHref.put("Днепр",45);
		headerAndHref.put("Англ",40);
		headerAndHref.put("Герм",42);
		headerAndHref.put("Испан",41);
		headerAndHref.put("Франц",44);
		headerAndHref.put("Росси",39);
		headerAndHref.put("Укра",45);
		headerAndHref.put("Чемпионов",37);
		headerAndHref.put("Лига чемпион",37);  
		headerAndHref.put("Лиги чемпион",37);  
		headerAndHref.put("ЛЧ",37);
		headerAndHref.put("ЛЕ",38);
		headerAndHref.put("Лига Европы",38);
		headerAndHref.put("ЧЕ",34);
		//headerAndHref.put("Чемпионат Европы",34);
		headerAndHref.put("Лиги Европы",38);
		headerAndHref.put("Евро",34);
		headerAndHref.put("Мира",34);
		headerAndHref.put("Сборн",34);
		
		
		for (Map.Entry<String,Integer> entry : headerAndHref.entrySet()) {
			if(header.contains(entry.getKey())){
				taxonomy=entry.getValue();
				taxonomies.add(taxonomy);
			}
		}
				return taxonomies;

		}

	
}