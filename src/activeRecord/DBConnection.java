package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    // variables de connection
    private String userName;
    private String password;
    private String serverName;
    private String portNumber;
    private String tableName;
    private String dbName;
    private Connection connection;
    private static DBConnection instance;


    public DBConnection(String userName, String password, String serverName, String portNumber, String dbName) throws SQLException {
        this.userName = userName;
        this.password = password;
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.dbName = dbName;
        try {
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

    private DBConnection() throws SQLException {
        this("root", "", "127.0.0.1", "3306", "testpersonne");
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
