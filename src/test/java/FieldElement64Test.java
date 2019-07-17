import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FieldElement64Test
{

    private final FieldElement64 elementA = new FieldElement64(1L, 1L);
    private final FieldElement64 elementB = new FieldElement64(2L, 2L);

    private final byte[] bytesA = new byte[] {
            1, 0, 0 ,0,
            0, 0, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 0
    };

    private final byte[] bytes = new byte[] {
            0, 0, 0, 0,
            1, 0, 0 ,0,
            0, 0, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 0
    };

    @Test
    void shouldConstructElementFromString()
    {
        final FieldElement64 constructed = new FieldElement64("01000000000000000100000000000000");
        Assertions.assertEquals(elementA.toString(), constructed.toString());
    }

    @Test
    void shouldConstructElementFromBytes()
    {
        final FieldElement64 constructed = new FieldElement64(bytesA);
        Assertions.assertEquals(elementA.toString(), constructed.toString());
    }

    @Test
    void shouldConstructElementFromBytesWithOffset()
    {
        final FieldElement64 constructed = new FieldElement64(bytes, 4);
        Assertions.assertEquals(elementA.toString(), constructed.toString());
    }

    @Test
    void shouldConstructElementFromLongs()
    {
        final FieldElement64 constructed = new FieldElement64(0x1L, 0x1L);
        Assertions.assertEquals(elementA.toString(), constructed.toString());
    }

    @Test
    void shouldReturnValidStringRepresentation()
    {
        Assertions.assertEquals("01000000000000000100000000000000", elementA.toString());
    }

    @Test
    void shouldReturnValidBytesRepresentation()
    {
        Assertions.assertArrayEquals(bytesA, elementA.toBytes());
    }

    @Test
    void shouldAddTwoElements()
    {
        Assertions.assertEquals("03000000000000000300000000000000", elementA.add(elementB).toString());
    }

    @Test
    void shouldMultiplyTwoElements()
    {
        Assertions.assertEquals("010000000000000000000000000008e6", elementA.mul(elementB).toString());
    }

}