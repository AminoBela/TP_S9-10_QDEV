package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    // variables de connection
    private String userName = "root";
    private String password = "";
    private String serverName = "127.0.0.1";
    private String portNumber = "3306";
    private String tableName = "personne";
    private String dbName = "testpersonne";
    private Connection connection;
    private static DBConnection instance;


    private DBConnection() throws SQLException {
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            String urlDB = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName;
            Properties connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", password);
            this.connection = DriverManager.getConnection(urlDB, connectionProps);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erreur lors du chargement du driver JDBC.", e);
        }
    }

    //methode getConnection, il doit encapsuler dans des attributs les parametres de connection
    public Connection getConnection() throws SQLException {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }



}
