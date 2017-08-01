package testMondrian;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.junit.Test;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Query;
import mondrian.olap.Result;
import mondrian.olap.Util;

/**
 *
 * @author Liuyg
 * @mail lyg210@msn.cn
 * @version
 * @time May 18, 2015
 *
 */
public class Testaaa {

	public static void main(String[] args) {
		
		Connection connection = DriverManager.getConnection(
				"Provider=mondrian;Jdbc=jdbc:mysql://192.168.60.160/footmart?user=root&password=111111;Catalog=N:/works/workspace/mine/java/workspace-test/myTest/testMondrian/src/main/resources/foodmart4.xml;",
				null);
		Query query = connection.parseQuery(
				"SELECT NON EMPTY {Hierarchize({[Measures].[Store Sqft]})} ON COLUMNS,NON EMPTY {Hierarchize({[Store Type].Members})} ON ROWS FROM [Store] ");

		Result result = connection.execute(query);
		PrintWriter pw = new PrintWriter(System.out);
		result.print(pw);
		pw.flush();
	}
	
	
}
