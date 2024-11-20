package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestPersonne {

    @BeforeEach
    void Ini() throws SQLException {
        Personne.createTable();
        new Personne("Lambert", "Valentino").save();
        new Personne("Bella", "Amin").save();
    }

    @AfterEach
    void Reini() throws SQLException {
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
        assertEquals("Lambert", p.getNom());
        assertEquals("Valentino", p.getPrenom());
    }

    @Test
    void testFindByName() throws SQLException {
        assertEquals(1, Personne.findByName("Bella").size());
        Personne p = Personne.findByName("Bella").get(0);
        assertEquals("Bella", p.getNom());
        assertEquals("Amin", p.getPrenom());
    }

    @Test
    void testSaveNew() throws SQLException {
        Personne p = new Personne("Haha", "Hehe");
        p.save();
        assertNotEquals(-1, p.getId());
        assertEquals(3, Personne.findAll().size());
    }

    @Test
    void testUpdate() throws SQLException {
        Personne p = Personne.findById(1);
        p.setNom("Aled");
        p.setPrenom("Moi");
        p.save();
        Personne PersonneMaj = Personne.findById(1);
        assertEquals("Aled", PersonneMaj.getNom());
        assertEquals("Moi", PersonneMaj.getPrenom());
    }

    @Test
    void testDelete() throws SQLException {
        Personne p = Personne.findById(1);
        p.delete();
        assertNull(Personne.findById(1));
        assertEquals(1, Personne.findAll().size());
    }
}
