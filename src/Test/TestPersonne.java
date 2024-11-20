package Test;

import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestPersonne {

    @BeforeEach
    void Ini() throws SQLException {
        Personne.createTable();
        new Personne("Durand", "Paul").save();
        new Personne("Martin", "Anne").save();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    void testFindAll() throws SQLException {
        assertEquals(2, Personne.findAll().size());
    }

    @Test
    void testFindById() throws SQLException {
        Personne p = Personne.findById(1);
        assertNotNull(p);
        assertEquals("Durand", p.nom);
    }

    @Test
    void testFindByName() throws SQLException {
        assertEquals(1, Personne.findByName("Martin").size());
    }

    @Test
    void testSaveNew() throws SQLException {
        Personne p = new Personne("Dupont", "Claire");
        p.save();
        assertNotEquals(-1, p.id);
        assertEquals(3, Personne.findAll().size());
    }

    @Test
    void testUpdate() throws SQLException {
        Personne p = Personne.findById(1);
        p.nom = "Lemoine";
        p.save();
        assertEquals("Lemoine", Personne.findById(1).nom);
    }

    @Test
    void testDelete() throws SQLException {
        Personne p = Personne.findById(1);
        p.delete();
        assertNull(Personne.findById(1));
    }
}
