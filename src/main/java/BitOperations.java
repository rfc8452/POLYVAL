public class BitOperations {

    public static long rev64(final long a)
    {
        long x = a;

        x = ((x >>> 16) & 0x0000_ffff_0000_ffffL) | ((x & 0x0000_ffff_0000_ffffL) << 16);
        x = ((x >>> 8)  & 0x00ff_00ff_00ff_00ffL) | ((x & 0x00ff_00ff_00ff_00ffL) << 8);
        x = ((x >>> 4)  & 0x0f0f_0f0f_0f0f_0f0fL) | ((x & 0x0f0f_0f0f_0f0f_0f0fL) << 4);
        x = ((x >>> 2)  & 0x3333_3333_3333_3333L) | ((x & 0x3333_3333_3333_3333L) << 2);
        x = ((x >>> 1)  & 0x5555_5555_5555_5555L) | ((x & 0x5555_5555_5555_5555L) << 1);

        return (x << 32) | (x >>> 32);
    }

    public static long bmul64(final long a, final long b)
    {

        final long a0 = a & 0x1111_1111_1111_1111L;
        final long a1 = a & 0x2222_2222_2222_2222L;
        final long a2 = a & 0x4444_4444_4444_4444L;
        final long a3 = a & 0x8888_8888_8888_8888L;

        final long b0 = b & 0x1111_1111_1111_1111L;
        final long b1 = b & 0x2222_2222_2222_2222L;
        final long b2 = b & 0x4444_4444_4444_4444L;
        final long b3 = b & 0x8888_8888_8888_8888L;

        final long r0 = ((a0 * b0) ^ (a1 * b3) ^ (a2 * b2) ^ (a3 * b1)) & 0x1111_1111_1111_1111L;
        final long r1 = ((a0 * b1) ^ (a1 * b0) ^ (a2 * b3) ^ (a3 * b2)) & 0x2222_2222_2222_2222L;
        final long r2 = ((a0 * b2) ^ (a1 * b1) ^ (a2 * b0) ^ (a3 * b3)) & 0x4444_4444_4444_4444L;
        final long r3 = ((a0 * b3) ^ (a1 * b2) ^ (a2 * b1) ^ (a3 * b0)) & 0x8888_8888_8888_8888L;

        return r0 | r1 | r2 | r3;
    }

    public static long multiplyReverseShift1(final long a, final long b)
    {
        return rev64(bmul64(a, b)) >>> 1;
    }

}
