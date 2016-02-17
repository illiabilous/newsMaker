import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

public class Rewrite {
	String rewrite (String newsText, String newsTitle, String newsImage){
		newsText=cleanedText(newsText);
		//System.out.println(newsText);
		newsText=similarWordReplace(newsText);
		ArrayList<String> sameWords = new ArrayList<>();
		sameWords=wordMaxSimilarList(newsText);
		//System.out.println(sameWords);
		String content=autochangedText(newsText, newsTitle, sameWords);
		//System.err.println(content);
		content=addImage(content, newsImage, newsTitle);
		//System.out.println("CONTENT IS PREPARED");
		return content;
	}
	
	 String addImage (String content, String image, String title){
		if(image.contains("http")){
			String imageblock="<img class=\"aligncenter\" src=\""+image+"\" border=\"2\" alt=\""+title+"\" >";
			return imageblock+content;
		}
		else return content;
	}
	static ArrayList<String> wordMaxSimilarList (String text)
	{
		//зберігаємо слова в list
		ArrayList<String> list=new ArrayList<>();
		Pattern patt = Pattern.compile("([А-Яа-я]+)");
		Matcher match = patt.matcher(text);
		String piece="";
		
		while(match.find()){	
			piece = match.group(1);
			if(piece.length()>2)
			list.add(piece);
		}
		int reg=0;//регулювальна змінна
		//рахуємо кількість повторень слів
		ArrayList<Integer> quantity = new ArrayList<>();	
		for(int i=0;i<list.size();i++){
			int counter=1;
			for (int j=i+1;j<list.size();j+=(1-reg)){
				//перевірка слів на однаковість (відкидаєтсья 1 символ з кінця)
				int k=list.get(i).length()-list.get(j).length();
				if((k>=0)&&(k<=3)){
					if(list.get(i).substring(0, list.get(i).length()-1-k).equalsIgnoreCase(list.get(j).substring(0, list.get(j).length()-1))){
						counter++;
						list.remove(j);
					}
				}else if((k<0)&&(k>=-3)){
					if(list.get(i).substring(0, list.get(i).length()-1).equalsIgnoreCase(list.get(j).substring(0, list.get(j).length()-1+k))){
						counter++;
						list.remove(j);	
				
					}
				}
			}
			quantity.add(counter);
		}
		//відкидаємо слова розміром менше 1 i 2

		for(int i=0;i<list.size();i+=(1-reg)){
			reg=0;
			if(list.get(i).length()<3){
				list.remove(i);
				quantity.remove(i);
				reg=1;
			}
		}
		//getting wordlist of title & text: titlelist & textlist
		ArrayList<String> maxList = new ArrayList<>();
		ArrayList<Integer> maxQuan = new ArrayList<>();
		int numOfSameWORDs=6;
		for(int k=0;k<numOfSameWORDs;k++){
			int max=quantity.get(0);
			int maxindex=0;
			for(int i=1;i<quantity.size(); i++){
				if (quantity.get(i)>max){
					max = quantity.get(i);
					maxindex=i;
				}
			}
			
			maxQuan.add(max);
			maxList.add(list.get(maxindex));
			quantity.remove(maxindex);
			list.remove(maxindex);
		}
		
		return maxList;
	}	
	
	static String cleanedText(String text){
		String newText=text;
		newText=newText.replace("&laquo;", "");
		newText=newText.replace("&nbsp;", " ");
		newText=newText.replace("&raquo;", "");
		newText=newText.replace(".", ". ");
		newText=newText.replace("<nobr>", "");
		newText=newText.replace("</nobr>", "");
		newText=newText.replace("\n", "");
		
		return newText;
	}
	
	static String similarWordReplace(String text){
		if(text.contains("Манчестер Юнайтед"))
			text=text.replace("Манчестер Юнайтед", "ФК Манчестер Юнайтед");
		
		return text;
	}
	
	
	static String autochangedText(String text, String title, ArrayList<String> list){
		String newText=text;
		Pattern patt = Pattern.compile("([А-Яа-я]+)");
		Matcher match = patt.matcher(newText);

		while (match.find()){
			String word=match.group(1);
			if(!title.contains(word)&&!list.contains(word)){
				newText=newText.replace(word,replacer(word));	
			}			
		}
		return newText;
	}
	static String replacer (String toReplace){
		//lower
		if(toReplace.contains("у")){
			toReplace=toReplace.replace("у","y");
		}
		if(toReplace.contains("е")){
			toReplace=toReplace.replace("е","e");
		}
		if(toReplace.contains("о")){
			toReplace=toReplace.replace("о","o");
		}
		if(toReplace.contains("р")){
			toReplace=toReplace.replace("р","p");
		}
		if(toReplace.contains("а")){
			toReplace=toReplace.replace("а","a");
		}
		if(toReplace.contains("с")){
			toReplace=toReplace.replace("с","c");
		}
		if(toReplace.contains("х")){
			toReplace=toReplace.replace("х","x");
		}
		//upper
		if(toReplace.contains("Е")){
			toReplace=toReplace.replace("Е","E");
		}
		if(toReplace.contains("О")){
			toReplace=toReplace.replace("О","O");
		}
		if(toReplace.contains("Р")){
			toReplace=toReplace.replace("Р","P");
		}
		if(toReplace.contains("А")){
			toReplace=toReplace.replace("А","A");
		}
		if(toReplace.contains("С")){
			toReplace=toReplace.replace("С","C");
		}
		if(toReplace.contains("Х")){
			toReplace=toReplace.replace("Х","X");
		}
		
		if(toReplace.contains("М")){
			toReplace=toReplace.replace("М","M");
		}
		if(toReplace.contains("Н")){
			toReplace=toReplace.replace("Н","H");
		}
		if(toReplace.contains("Т")){
			toReplace=toReplace.replace("Т","T");
		}
		if(toReplace.contains("К")){
			toReplace=toReplace.replace("К","K");
		}
		
		return toReplace;
	}
	
	static String getmeta(String text) { //SEO meta
		Pattern patt = Pattern.compile("(<p>)(.*?)(</p)");
		Matcher match = patt.matcher(text);
		if (match.find()) {
				text = match.group(2);
		}
		if(text.length()<140)
			return text;
		else return text.replace(text, text.substring(0,140));
		
	}
}