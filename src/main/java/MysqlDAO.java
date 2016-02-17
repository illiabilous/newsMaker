
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlDAO {
	static Logger logger = Logger.getLogger(MysqlDAO.class.getName());
	private static Connection  connection;
	private static final String URL = "jdbc:mysql://hostx.mysql.ukraine.com.ua:3306/hostx_footlivehd?useUnicode=true&characterEncoding=UTF-8";
	private static final String USERNAME = "hostx_footlivehd";
	private static final String PASSWORD = "udxz56xp";
	final private static String INSERT_NEWS_WITHOUT_MINIATURE = "INSERT INTO wp_posts (post_author, post_date, post_date_gmt, post_content,"
			+ " post_excerpt,post_title, post_status, comment_status, ping_status,post_name,to_ping,pinged,"
			+ "post_modified, post_modified_gmt,post_content_filtered, guid,post_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	final private static String EASY_SEO_HEADER_MAKER = "INSERT into wp_postmeta(post_id,meta_key,meta_value) VALUES (?,?,?);";
	MysqlDAO() {
		
		
		try {
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger.logp(Level.INFO, "DbAccess", "DbWorker()", "З'єднання з БД встановлено");
		}
		}

		public Connection getConnection() {
			return connection;
		}
			void insertTranslationQuery( String postContent,
			String postTitle, String postName) throws ParseException {
				
				
			try {
				PreparedStatement preparedStatement = null;
				String currentDate = DateWorker.currentDateWithTime();
				String currentGmtDate = DateWorker.currentDateGrinvichTime().toString();
				preparedStatement = getConnection().prepareStatement(INSERT_NEWS_WITHOUT_MINIATURE);
				preparedStatement.setInt(1, 2);
				preparedStatement.setString(2, currentDate);
				preparedStatement.setString(3, currentGmtDate);
				preparedStatement.setString(4, postContent);
				preparedStatement.setString(5, "");
				preparedStatement.setString(6, postTitle);
				preparedStatement.setString(7, "publish");
				preparedStatement.setString(8, "open");
				preparedStatement.setString(9, "open");
				preparedStatement.setString(10, postName);
				preparedStatement.setString(11, "");
				preparedStatement.setString(12, "");
				preparedStatement.setString(13, currentDate);
				preparedStatement.setString(14, currentGmtDate);
				preparedStatement.setString(15, "");
				preparedStatement.setString(16, "");
				preparedStatement.setString(17, "post");
				preparedStatement.execute();
				logger.logp(Level.INFO, "DbAccess", "insertTranslationQuery", "Трансляцію додано");
			} catch (SQLException e) {
				logger.logp(Level.WARNING, "DbAccess", "insertTranslationQuery", "Не вдалось вставити дані в БД");
				e.printStackTrace();
			}
			}
		
			
			List<String> selectTranslationQuery(int maxId) {
				int idMin;
				if(maxId>500){
					 idMin=maxId-500;
				}else{
					idMin=0;
				}
			String SELECT_CURRENT_BD_TRANSLATIONS = "SELECT post_title FROM wp_posts WHERE ID>"+idMin;
			ArrayList<String> list = new ArrayList<String>();
			String title = null;
			try {
			PreparedStatement preparedStatement = null;
			preparedStatement = getConnection().prepareStatement(SELECT_CURRENT_BD_TRANSLATIONS);
			ResultSet set = preparedStatement.executeQuery();
			while (set.next()) {
				title = set.getString("post_title");
				list.add(title);
			}
			preparedStatement.execute();
			logger.logp(Level.INFO, "DbAccess", "selectTranslationQuery", "Вибірка завершена");
			} catch (SQLException e) {
			logger.logp(Level.WARNING, "DbAccess", "selectTranslationQuery", "Не вдалось вибрати дані з БД");
			System.out.println();
			e.printStackTrace();
			}
			return list;
			}
				
				
			int selectMaxID() {
				String maxQuery="SELECT MAX(id) FROM wp_posts";
				int maxId = 0;
				try {
					PreparedStatement preparedStatement = null;
					preparedStatement = getConnection().prepareStatement(maxQuery);
					ResultSet set = preparedStatement.executeQuery();
					while (set.next()) {
						maxId = set.getInt(1);
					}
					preparedStatement.execute();
					logger.logp(Level.INFO, "DbAccess", "selectMaxID", "Максимальне id вибрано");
					} catch (SQLException e) {
					logger.logp(Level.WARNING, "DbAccess", "selectMaxID", "не вдалось вибрати максимальне id");
					e.printStackTrace();
				}
				return maxId;
				}
			
			long newsId(String title) throws SQLException {
				String maxQuery="SELECT ID, post_title FROM wp_posts WHERE post_title='"+title+"'";
				long id=-1;
				PreparedStatement preparedStatement = null;
				preparedStatement = getConnection().prepareStatement(maxQuery);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
				 id= rs.getInt(1);
				
				}
				preparedStatement.execute();
				return id;
				}
			
			void insertTerm(long objectId, int termTaxonomyId)throws ParseException {
			try {
			String TERM_POST = "INSERT into wp_term_relationships(object_id,term_taxonomy_id) VALUES (?,?);";
			PreparedStatement preparedStatement = null;
			preparedStatement = getConnection().prepareStatement(TERM_POST);
			preparedStatement.setLong(1, objectId);
			preparedStatement.setInt(2, termTaxonomyId);
			preparedStatement.execute();
			logger.logp(Level.INFO, "DbAccess", "insertTerml","рубрику додано");

			} catch (SQLException e) {
			logger.logp(Level.WARNING, "DbAccess", "insertTerml","рубрику не додано");
			e.printStackTrace();
			}
			}
			
			
			// SEO
			void insertSeo(long postId, String newsTitle, String newsContent)
			throws ParseException {
			try {
				PreparedStatement preparedFocusKeyword = null;
				preparedFocusKeyword = getConnection().prepareStatement(EASY_SEO_HEADER_MAKER);
				preparedFocusKeyword.setLong(1, postId);
				preparedFocusKeyword.setString(2, "_yoast_wpseo_title");
				preparedFocusKeyword.setString(3, newsTitle + "-footlivehd.com");
				preparedFocusKeyword.execute();
				logger.logp(Level.INFO, "DbAccess", "insertSeo","Сео заголовок додано");
				
				preparedFocusKeyword = getConnection().prepareStatement(EASY_SEO_HEADER_MAKER);
				preparedFocusKeyword.setLong(1, postId);
				preparedFocusKeyword.setString(2, "_yoast_wpseo_metadesc");
				preparedFocusKeyword.setString(3, Rewrite.getmeta(newsContent));
				preparedFocusKeyword.execute();
				logger.logp(Level.INFO, "DbAccess", "insertSeo","Сео мету додано");
				
				preparedFocusKeyword = getConnection().prepareStatement(EASY_SEO_HEADER_MAKER);
				preparedFocusKeyword.setLong(1, postId);
				preparedFocusKeyword.setString(2, "_yoast_wpseo_focuskw");
				preparedFocusKeyword.setString(3, newsTitle);
				preparedFocusKeyword.execute();
				logger.logp(Level.INFO, "DbAccess", "insertSeo","Сео ключові слова додано");
		}catch (SQLException e) {
				System.out.println();
				logger.logp(Level.WARNING, "DbAccess", "insertSeo","Сео не зроблено.");
				e.printStackTrace();
		}
			}

			
}