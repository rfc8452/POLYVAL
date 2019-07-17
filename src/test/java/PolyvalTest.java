import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PolyvalTest {

    private final Polyval polyval0 = new Polyval(0L, 0L, 0L, 0L);
    private final Polyval polyvalA = new Polyval(1L, 1L, 1L, 1L);

    private final byte[] bytesA = new byte[] {
            1, 0, 0 ,0,
            0, 0, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 0
    };

    @Test
    void shouldConstructPolyvalFromHLongs() {
        final Polyval constructed = new Polyval(0, 0);
        Assertions.assertEquals(polyval0.toString(), constructed.toString());
    }

    @Test
    void shouldConstructPolyvalFromHAndSLongs() {
        final Polyval constructed = new Polyval(0, 0, 0, 0);
        Assertions.assertEquals(polyval0.toString(), constructed.toString());
    }

    @Test
    void shouldConstructSFromItsOwnDigest() {
        final Polyval constructed = new Polyval(bytesA, polyvalA.digest());
        Assertions.assertEquals(polyvalA.toString(), constructed.toString());
    }

    @Test
    void shouldConstructPolyvalFromHBytes() {
        final Polyval constructed = new Polyval(bytesA);
        Assertions.assertEquals(polyval0.toString(), constructed.toString());
    }

    @Test
    void shouldConstructPolyvalFromHAndSBytes() {
        final Polyval constructed = new Polyval(bytesA, bytesA);
        Assertions.assertEquals(polyvalA.toString(), constructed.toString());
    }

    @Test
    void shouldConstructPolyvalFromHString() {
        final Polyval constructed = new Polyval("00000000000000000000000000000000");
        Assertions.assertEquals(polyval0.toString(), constructed.toString());
    }

    @Test
    void shouldConstructPolyvalFromHAndSStrings() {
        final Polyval constructed = new Polyval(
                "00000000000000000000000000000000", "00000000000000000000000000000000");
        Assertions.assertEquals(polyval0.toString(), constructed.toString());
    }

    @Test
    void shouldUpdateBlock0()
    {
        final Polyval polyval = new Polyval("25629347589242761d31f826ba4b757b");
        polyval.updateBlock("00000000000000000000000000000000");
        Assertions.assertEquals("00000000000000000000000000000000", polyval.toString());
    }

    @Test
    void shouldUpdateBlock()
    {
        final Polyval p0 = new Polyval("25629347589242761d31f826ba4b757b");

        p0.updateBlock("4f4f95668c83dfb6401762bb2d01a262");
        Assertions.assertEquals("cedac64537ff50989c16011551086d77", p0.toString());

        p0.updateBlock("d1a24ddd2721d006bbe45f20d3c9f362");
        Assertions.assertEquals("f7a3b47b846119fae5b7866cf5e5b77e", p0.toString());
    }

    @Test
    void shouldUpdateNotDependingOnZeroBlock()
    {
        final Polyval p0 = new Polyval("25629347589242761d31f826ba4b757b");
        p0.update("000000000000000000000000000000004f4f95668c83dfb6401762bb2d01a262d1a24ddd2721d006bbe45f20d3c9f362");
        Assertions.assertEquals("f7a3b47b846119fae5b7866cf5e5b77e", p0.toString());
    }

    @Test
    void shouldUpdate()
    {
        final Polyval p0 = new Polyval("25629347589242761d31f826ba4b757b");
        p0.update("4f4f95668c83dfb6401762bb2d01a262d1a24ddd2721d006bbe45f20d3c9f362");
        Assertions.assertEquals("f7a3b47b846119fae5b7866cf5e5b77e", p0.toString());
    }

    @Test
    void shouldPadWithZeros()
    {
        final Polyval p0 = new Polyval("25629347589242761d31f826ba4b757b")
                .update("d1a24ddd2721d006bbe45f20d3c9f36200");
        final Polyval p1 = new Polyval("25629347589242761d31f826ba4b757b")
                .update("d1a24ddd2721d006bbe45f20d3c9f36200000000000000000000000000000000");
        Assertions.assertEquals(p0.toString(), p1.toString());
    }

    @Test
    void shouldResetSToZero()
    {
        final Polyval p0 = new Polyval("25629347589242761d31f826ba4b757b");
        p0.update("4f4f95668c83dfb6401762bb2d01a262d1a24ddd2721d006bbe45f20d3c9f362");
        Assertions.assertEquals("f7a3b47b846119fae5b7866cf5e5b77e", p0.toString());
        p0.reset();
        Assertions.assertEquals("00000000000000000000000000000000", p0.toString());
    }

}