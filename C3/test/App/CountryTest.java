package App;

import org.junit.jupiter.api.Test;

import SAE.Country;

import static org.junit.jupiter.api.Assertions.*;

public class CountryTest {
    @Test
    public void testGetType() {
        assertEquals("FRANCE", Country.FRANCE.getPays());
        assertEquals("ITALY", Country.ITALY.getPays());
        assertEquals("GERMANY", Country.GERMANY.getPays());
        assertEquals("SPAIN", Country.SPAIN.getPays());
    }
}
