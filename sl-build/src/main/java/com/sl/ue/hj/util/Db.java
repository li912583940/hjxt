package com.sl.ue.hj.util;

import java.sql.*;

public class Db {

	//这里可以设置数据库名称
    private final static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=TeleJY_HJ";
    private static final String USER="sa";
    private static final String PASSWORD="123456";
    private static final String DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    private static Connection conn=null;
    //静态代码块（将加载驱动、连接数据库放入静态块中）
    static{
        try {
            //1.加载驱动程序
            Class.forName(DRIVER);
            //2.获得数据库的连接
            conn=(Connection)DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //对外提供一个方法来获取数据库连接
    public static Connection getConnection(){
        return conn;
    }
}
