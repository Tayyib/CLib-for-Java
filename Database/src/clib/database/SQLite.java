package clib.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLite
{
    private Connection connection;
    private Statement statement;


    /***
     * Add <b>sqlite-jdbc</b> dependency to project first.
     */
    public SQLite(String path) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");

        connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
        statement = connection.createStatement();
    }

    public void closeConnection() throws SQLException
    {
        statement.close();
        connection.close();
    }

    // GETTERS

    public Connection getConnection()
    {
        return connection;
    }

    public Statement getStatement()
    {
        return statement;
    }
}
