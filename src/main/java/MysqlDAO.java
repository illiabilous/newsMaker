
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;

public class MysqlDAO {
	static Logger logger = Logger.getLogger(MysqlDAO.class.getName());
	private static Connection  connection;
	private static final String URL = "jdbc:mysql://hostx.mysql.ukraine.com.ua:3306/hostx_footlivehd?useUnicode=true&characterEncoding=UTF-8";
	private static final String USERNAME = "hostx_footlivehd";
	private static final String PASSWORD = "udxz56xp";
	final private static String INSERT_NEWS_WITHOUT_MINIATURE = "INSERT INTO wp_posts (post_author, post_date, post_date_gmt, post_content,"
			+ " post_excerpt,post_title, post_status, comment_status, ping_status,post_name,to_ping,pinged,"
			+ "post_modified, post_modified_gmt,post_content_filtered, guid,post_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
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
			void insertTranslationQuery(MysqlDAO dbWorker, String postContent,
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
				preparedStatement.setString(5, "footlivehd.com");
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
}