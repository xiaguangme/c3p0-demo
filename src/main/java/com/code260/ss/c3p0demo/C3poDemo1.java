/**
 * 2021年7月31日  copy right
 */
package com.code260.ss.c3p0demo;

import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.impl.NewProxyConnection;

/**
 * @author code260@qq.com
 *
 */
public class C3poDemo1 {
	
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/account_00";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";
    
    private ComboPooledDataSource dataSource;
    
    public C3poDemo1() throws PropertyVetoException {
    	dataSource = new ComboPooledDataSource();
    	dataSource.setDriverClass("com.mysql.jdbc.Driver");
    	dataSource.setMinPoolSize(5);
    	dataSource.setMaxPoolSize(20);
    	dataSource.setInitialPoolSize(5);
    	dataSource.setMaxIdleTime(30);
    	dataSource.setMaxStatements(100);
    	dataSource.setIdleConnectionTestPeriod(15);
    	dataSource.setUser(USER);
    	dataSource.setPassword(PASSWORD);
    	dataSource.setJdbcUrl(URL);
    }

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws PropertyVetoException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, PropertyVetoException {
		C3poDemo1 c3poDemo1 = new C3poDemo1();
		for (int i = 0; i < 10; i++) {
			c3poDemo1.testQuery();
		}
		System.out.println("end");
				

	}
	
	public void testQuery() throws SQLException, ClassNotFoundException {
		//1.加载驱动程序
//        Class.forName("com.mysql.jdbc.Driver");
//        //2. 获得数据库连接
//        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
		
		// inner
		System.out.println(getFieldValue(NewProxyConnection.class, "inner", conn));
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery("SELECT * FROM account_00.t_user_00");
        System.out.println(rs);
        //如果有数据，rs.next()返回true
        while(rs.next()){
            System.out.println("phone：" + rs.getString("phone")+ ",email:" +rs.getString("email"));
        }
        conn.close();
	}
	
	public Object getFieldValue(Class<?> clazz, String fieldName, Object instance) {
		try {
			Field declaredField = clazz.getDeclaredField(fieldName);
			declaredField.setAccessible(true);
			return declaredField.get(instance);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
