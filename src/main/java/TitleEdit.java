
public class TitleEdit {
	String titlereplace (String newsTitle){
		newsTitle=newsTitle.replace("«","");
		newsTitle=newsTitle.replace("»","");
		newsTitle=newsTitle.replace(". Видео","");
		newsTitle=newsTitle.replace(". ВИДЕО","");
		//System.out.println("TITLE IS PREPARED");
		return newsTitle;
	}
	
	String newsNameGenerator(String title) {
		title = title.toLowerCase();
		title = title.replace(" ", "-");
		title = title.replace(".", "-");
		title = title.replace("–", "");
		title = title.replace(",", "-");
		title = title.replace("/", "-");
	
		char[] english = { 'a', 'b', 'v', 'g', 'd', 'e', 'e', 'j', 'z', 'i',
		'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'h', 'f', 'u',
		's', 's', 'q', 'u', 'a', 'i', 'c', 'j', 'c' };
	
		char[] russian = { 'а', 'б', 'в', 'г', 'д', 'е', 'э', 'ж', 'з', 'и',
		'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'х', 'ф', 'у',
		'ш', 'щ', 'ь', 'ю', 'я', 'ы', 'ч', 'й', 'ц' };
		for (int i = 0; i < title.length(); i++) {
		for (int j = 0; j < russian.length; j++) {
		if (title.charAt(i) == russian[j]) {
		title = title.replace(russian[j], english[j]);
	
		}
		}
		}
		// header=header.replaceAll("[0-9].+", "");
		return title;
	}
}