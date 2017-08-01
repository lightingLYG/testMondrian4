

import java.io.PrintWriter;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Query;
import mondrian.olap.Result;

/**
 *
 * @author Liuyg
 * @mail lyg210@msn.cn
 * @version
 * @time May 18, 2015
 *
 */
public class Test {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Connection connection = DriverManager
				.getConnection(
						"Provider=mondrian;Jdbc=jdbc:mysql://192.168.100.216:3306/foodmart?user=root&password=111111;Catalog=res:foodmart4.xml;",
						null);
		Query query = connection
				.parseQuery("SELECT NON EMPTY {Hierarchize({[Measures].[Store Sqft]})} ON COLUMNS,NON EMPTY {Hierarchize({[Store Type].Members})} ON ROWS FROM [Store]");
		Result result = connection.execute(query);
		PrintWriter pw = new PrintWriter(System.out);
		result.print(pw);
		pw.flush();
	}
}
