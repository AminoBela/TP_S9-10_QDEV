package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrincipaleJDBC {
    public static void main(String[] args) {
        try {
            // Obtenir l'instance unique de DBConnection
            DBConnection dbConnection = DBConnection.getInstance();
            Connection connect = dbConnection.getConnection();

            // Exemple : Vérification de connexion
            System.out.println("Connexion réussie avec la base : " + connect.getCatalog());

            // Effectuer d'autres opérations SQL ici
            String SQLPrep = "SELECT 1;";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                System.out.println("Test SQL réussi : " + rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("*** ERREUR SQL ***");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("*** ERREUR ***");
            e.printStackTrace();
        }
    }
}
