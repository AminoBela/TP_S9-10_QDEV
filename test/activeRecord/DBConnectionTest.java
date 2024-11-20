package activeRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void getConnection() {
        try {
            // Obtenir l'instance unique de DBConnection
            DBConnection dbConnection = DBConnection.getInstance();
            assertNotNull(dbConnection.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getInstance() {
        try {
            // Obtenir l'instance unique de DBConnection
            DBConnection dbConnection = DBConnection.getInstance();
            assertNotNull(dbConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSingleton() {
        try {
            // Obtenir l'instance unique de DBConnection
            DBConnection dbConnection = DBConnection.getInstance();
            DBConnection dbConnection2 = DBConnection.getInstance();
            assertEquals(dbConnection, dbConnection2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deuxiemeBase() {
        try {
            DBConnection dbConnection1 = DBConnection.getInstance();
        Connection connection1 = dbConnection1.getConnection();
        assertNotNull(connection1, "conexion nulle");

        //deuxieme bd
        DBConnection dbConnection2 = new DBConnection("root", "", "127.0.0.1", "3306", "base2");
        Connection connection2 = dbConnection2.getConnection();
        assertNotNull(connection2, "conexion nulle");

        assertNotSame(connection1, connection2);
        } catch(Exception e) {
        e.printStackTrace();
        }
    }

}