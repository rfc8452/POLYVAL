import java.util.*;

public class FieldElement64
{

    private long e0;
    private long e1;

    public FieldElement64(final String hexString)
    {
        byte[] bytes = new byte[16];
        for (int i=0; i<16; i++)
        {
            String chunk = hexString.substring(i*2, i*2 + 2);
            bytes[i] = (byte) new Scanner(chunk).nextInt(16);
        }
        fromBytes(bytes, 0);
    }

    public FieldElement64(final long e0, final long e1)
    {
        this.e0 = e0;
        this.e1 = e1;
    }

    public FieldElement64(final byte[] bytes)
    {
        fromBytes(bytes, 0);
    }

    public FieldElement64(final byte[] bytes, final int offset)
    {
        fromBytes(bytes, offset);
    }

    public void fromBytes(final byte[] bytes, final int offset)
    {
        e0 =      ((long) bytes[offset + 7] << 56)
                | ((long) bytes[offset + 6] & 0xff) << 48
                | ((long) bytes[offset + 5] & 0xff) << 40
                | ((long) bytes[offset + 4] & 0xff) << 32
                | ((long) bytes[offset + 3] & 0xff) << 24
                | ((long) bytes[offset + 2] & 0xff) << 16
                | ((long) bytes[offset + 1] & 0xff) << 8
                | ((long) bytes[offset    ] & 0xff);

        e1 =      ((long) bytes[offset + 15] << 56)
                | ((long) bytes[offset + 14] & 0xff) << 48
                | ((long) bytes[offset + 13] & 0xff) << 40
                | ((long) bytes[offset + 12] & 0xff) << 32
                | ((long) bytes[offset + 11] & 0xff) << 24
                | ((long) bytes[offset + 10] & 0xff) << 16
                | ((long) bytes[offset +  9] & 0xff) << 8
                | ((long) bytes[offset +  8] & 0xff);
    }

    public static FieldElement64 defaultElement()
    {
        return new FieldElement64(0L, 0L);
    }

    public FieldElement64 add(final FieldElement64 b)
    {
        final FieldElement64 a = this;
        return FieldElement64.add(a, b);
    }

    public FieldElement64 mul(final FieldElement64 b)
    {
        final FieldElement64 a = this;
        return FieldElement64.mul(a, b);
    }

    public static FieldElement64 add(final FieldElement64 a, final FieldElement64 b)
    {
        return new FieldElement64(a.e0 ^ b.e0, a.e1 ^ b.e1);
    }

    public static FieldElement64 mul(final FieldElement64 a, final FieldElement64 b)
    {
        final long a0 = a.e0;
        final long a1 = a.e1;
        final long a0Reversed = BitOperations.rev64(a0);
        final long a1Reversed = BitOperations.rev64(a1);

        final long b0 = b.e0;
        final long b1 = b.e1;
        final long b0Reversed = BitOperations.rev64(b0);
        final long b1Reversed = BitOperations.rev64(b1);

        final long m1 = BitOperations.bmul64(b1, a1);
        final long m2 = BitOperations.bmul64(b0, a0)
                        ^ BitOperations.bmul64(b0 ^ b1, a0 ^ a1)
                        ^ BitOperations.bmul64(b1, a1);

        final long k0 = BitOperations.multiplyReverseShift1(b0Reversed, a0Reversed);
        final long k1 = BitOperations.multiplyReverseShift1(b1Reversed, a1Reversed);
        final long k2 = BitOperations.multiplyReverseShift1(
                b0Reversed ^ b1Reversed, a0Reversed ^ a1Reversed) ^ (k0 ^ k1);

        final long r0 = BitOperations.bmul64(b0, a0);
        final long r1 = (m2 ^ k0) ^ (r0 << 63) ^ (r0 << 62) ^ (r0 << 57);
        final long r2 = (m1 ^ k2) ^ (r1 << 63) ^ (r1 << 62) ^ (r1 << 57) ^ r0 ^ (r0 >>> 1) ^ (r0 >>> 2) ^ (r0 >>> 7);
        final long r3 =       k1  ^                                        r1 ^ (r1 >>> 1) ^ (r1 >>> 2) ^ (r1 >>> 7);

        return new FieldElement64(r2, r3);
    }

    public byte[] toBytes()
    {
        final byte[] result = new byte[16];

        result[0]  = (byte) (e0);
        result[1]  = (byte) (e0 >> 8);
        result[2]  = (byte) (e0 >> 16);
        result[3]  = (byte) (e0 >> 24);
        result[4]  = (byte) (e0 >> 32);
        result[5]  = (byte) (e0 >> 40);
        result[6]  = (byte) (e0 >> 48);
        result[7]  = (byte) (e0 >> 56);

        result[8]  = (byte) (e1);
        result[9]  = (byte) (e1 >> 8);
        result[10] = (byte) (e1 >> 16);
        result[11] = (byte) (e1 >> 24);
        result[12] = (byte) (e1 >> 32);
        result[13] = (byte) (e1 >> 40);
        result[14] = (byte) (e1 >> 48);
        result[15] = (byte) (e1 >> 56);

        return result;
    }

    @Override
    public String toString() {
        return String.format("%016x%016x", Long.reverseBytes(e0), Long.reverseBytes(e1));
    }
}
