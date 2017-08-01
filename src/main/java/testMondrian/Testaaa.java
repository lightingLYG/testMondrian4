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
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
//		Connection connection = DriverManager.getConnection(
//				"Provider=mondrian;Jdbc=jdbc:mysql://192.168.60.160/footmart?user=root&password=111111;Catalog=N:/works/workspace/mine/java/workspace-test/myTest/testMondrian/src/main/resources/foodmart4.xml;",
//				null);
//		Query query = connection.parseQuery(
//				"SELECT NON EMPTY {Hierarchize({[Measures].[Store Sqft]})} ON COLUMNS,NON EMPTY {Hierarchize({[Store Type].Members})} ON ROWS FROM [Store] ");
		Connection connection = DriverManager.getConnection(
				"Provider=mondrian;Jdbc=jdbc:mysql://192.168.100.217:3307/zuhu?user=root&password=root;Catalog=C:/Users/lighting/Desktop/report.xml;",
				null);
		Query query = connection.parseQuery(
				"SELECT NON EMPTY {Hierarchize({[Measures].[calls]})} ON COLUMNS,NON EMPTY {Hierarchize({[times].[day].Members})} ON ROWS FROM [call_data] ");

		Result result = connection.execute(query);
		PrintWriter pw = new PrintWriter(System.out);
		result.print(pw);
		pw.flush();
	}
	
	@Test
	public void testtt() throws SQLException{
		ConnectionFactory connectionFactory=new DriverManagerConnectionFactory("jdbc:mysql://192.168.100.217:3307/zuhu?user=root&password=root",new Properties());
		
		ObjectPool connectionPool = getPool(connectionFactory, true);
		DataSource dataSource= new PoolingDataSource(connectionPool);
		java.sql.Connection conn=dataSource.getConnection("root","111111");
		System.out.println(conn.isValid(20));
		conn.close();
		
	}
	
	public void test2() throws SQLException{
		BasicDataSource ds=new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://192.168.60.160:3306/cti");
		ds.setUsername("root");
		ds.setPassword("111111");
		java.sql.Connection conn=ds.getConnection();
		System.out.println(conn.isClosed());
		conn.close();
	}
	
	private synchronized ObjectPool getPool(
	        ConnectionFactory connectionFactory,
	        boolean mysql)
	    {
	        ObjectPool connectionPool  = new GenericObjectPool(
	                null, // PoolableObjectFactory, can be null
	                5000, // max active
	                GenericObjectPool.WHEN_EXHAUSTED_GROW, // action when exhausted
	                3000, // max wait (milli seconds)
	                10, // max idle
	                mysql, // test on borrow
	                false, // test on return
	                60000, // time between eviction runs (millis)
	                5, // number to test on eviction run
	                30000, // min evictable idle time (millis)
	                true); // test while idle

	            // create a PoolableConnectionFactory
	            AbandonedConfig abandonedConfig = new AbandonedConfig();
	            // flag to remove abandoned connections from pool
	            abandonedConfig.setRemoveAbandoned(true);
	            // timeout (seconds) before removing abandoned connections
	            abandonedConfig.setRemoveAbandonedTimeout(300);
	            // Flag to log stack traces for application code which abandoned a
	            // Statement or Connection
	            abandonedConfig.setLogAbandoned(true);
	            PoolableConnectionFactory poolableConnectionFactory =
	                new PoolableConnectionFactory(
	                    // the connection factory
	                    connectionFactory,
	                    // the object pool
	                    connectionPool,
	                    // statement pool factory for pooling prepared statements,
	                    // or null for no pooling
	                    null,
	                    // validation query (must return at least 1 row e.g. Oracle:
	                    // select count(*) from dual) to test connection, can be
	                    // null
	                    mysql ? "SELECT 1" : null,
	                    // default "read only" setting for borrowed connections
	                    false,
	                    // default "auto commit" setting for returned connections
	                    true,
	                    // AbandonedConfig object configures how to handle abandoned
	                    // connections
	                    abandonedConfig);

	            // "poolableConnectionFactory" has registered itself with
	            // "connectionPool", somehow, so we don't need the value any more.
	            Util.discard(poolableConnectionFactory);
	        return connectionPool;
	    }
}
