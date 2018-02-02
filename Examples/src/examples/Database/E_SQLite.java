package examples.Database;


import clib.database.SQLite;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class E_SQLite
{
    public static void main(String[] args)
    {
        try
        {
            String path = String.format("%s\\Desktop\\test.db", System.getProperty("user.home"));
            SQLite db = new SQLite(path);
            Statement st = db.getStatement();
            DatabaseMetaData metaData = db.getConnection().getMetaData();

            System.out.println("SQL Driver: " + metaData.getDriverName());

            st.execute("CREATE TABLE IF NOT EXISTS Conquerors (name TEXT UNIQUE, aka TEXT UNIQUE);");
            st.execute("INSERT OR IGNORE INTO Conquerors VALUES ('Mehmed II', 'Mehmed the Conqueror');");
            st.execute("INSERT OR IGNORE INTO Conquerors (name) VALUES ('Suleiman I');");
            st.execute("UPDATE Conquerors SET aka = 'Suleiman the Magnificent' WHERE name = 'Suleiman I';");

            ResultSet rs = st.executeQuery("SELECT rowid, * FROM Conquerors");

            while (rs.next())
            {
                System.out.println(String.format(
                        "Row %s: %s (%s) ", rs.getString("rowid"), rs.getString("aka"), rs.getString("name")));
            }

            db.closeConnection();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
