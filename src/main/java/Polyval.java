public class Polyval implements Authenticator
{

    private static final int BLOCK_SIZE = 16;

    private FieldElement64 h;
    private FieldElement64 s;

    Polyval(final long h0, final long h1)
    {
        this.h = new FieldElement64(h0, h1);
        this.s = FieldElement64.defaultElement();
    }

    Polyval(final long h0, final long h1, final long s0, final long s1)
    {
        this.h = new FieldElement64(h0, h1);
        this.s = new FieldElement64(s0, s1);
    }

    Polyval(final byte[] h)
    {
        this.h = new FieldElement64(h);
        this.s = FieldElement64.defaultElement();
    }

    Polyval(final byte[] h, final byte[] s)
    {
        this.h = new FieldElement64(h);
        this.s = new FieldElement64(s);
    }

    Polyval(final String hexString)
    {
        h = new FieldElement64(hexString);
        s = FieldElement64.defaultElement();
    }

    Polyval(final String hHexString, final String sHexString)
    {
        h = new FieldElement64(hHexString);
        s = new FieldElement64(sHexString);
    }

    public Polyval updateBlock(final byte[] v) {
        FieldElement64 update = new FieldElement64(v);
        return updateBlock(update);
    }

    public Polyval updateBlock(final String hexString)
    {
        FieldElement64 update = new FieldElement64(hexString);
        return updateBlock(update);
    }

    public Polyval updateBlock(final FieldElement64 update)
    {
        s = s.add(update).mul(h);
        return this;
    }

    public Polyval reset() {
        s = FieldElement64.defaultElement();
        return this;
    }

    public Polyval update(final byte[] b)
    {
        final int remainder = b.length % BLOCK_SIZE;

        for (int i = 0; i < b.length - remainder; i += BLOCK_SIZE)
        {
            final FieldElement64 blockUpdate = new FieldElement64(b, i);
            updateBlock(blockUpdate);
        }

        if (remainder != 0)
        {
            final byte[] block = new byte[BLOCK_SIZE];
            System.arraycopy(b, b.length - remainder, block, 0, remainder);
            final FieldElement64 blockUpdate = new FieldElement64(block);
            updateBlock(blockUpdate);
        }
        return this;
    }

    public Polyval update(final String hexString)
    {
        final int remainder = hexString.length() % (BLOCK_SIZE * 2);

        for (int i = 0; i < hexString.length() - remainder; i += (BLOCK_SIZE * 2))
        {
            final FieldElement64 blockUpdate = new FieldElement64(hexString.substring(i, i + (BLOCK_SIZE * 2)));
            updateBlock(blockUpdate);
        }

        if (remainder != 0)
        {
            final FieldElement64 blockUpdate = new FieldElement64(
                    hexString.substring(hexString.length() - remainder)
                            + "0".repeat(BLOCK_SIZE * 2 - remainder)
            );
            updateBlock(blockUpdate);
        }
        return this;
    }

    public FieldElement64 getH()
    {
        return h;
    }

    public FieldElement64 getS()
    {
        return s;
    }

    public byte[] digest()
    {
        return s.toBytes();
    }

    @Override
    public String toString()
    {
        return s.toString();
    }

}
