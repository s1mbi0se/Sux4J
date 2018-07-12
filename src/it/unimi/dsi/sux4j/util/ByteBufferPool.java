package it.unimi.dsi.sux4j.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ByteBufferPool {

    private final List<ByteBuffer> buffers;
    private final AtomicInteger borrowed = new AtomicInteger(0);

    public ByteBufferPool() {
        buffers = new ArrayList<>();
    }

    private static ByteBuffer malloc(int bytes) {
        return ByteBuffer.allocate(bytes);
    }

    public ByteBuffer acquire(int bytes) {
        List<ByteBuffer> list = buffers;

        ByteBuffer bb = list.isEmpty() ? malloc(bytes) : list.remove(list.size() - 1);
        bb.position(0).limit(bytes);

        // fill with zeroes to ensure deterministic behavior upon handling 'uninitialized' data
        for (int i = 0, n = bb.remaining(); i < n; i++) {
            bb.put(i, (byte) 0);
        }

        borrowed.incrementAndGet();
        return bb;
    }

    public void release(ByteBuffer buffer) {
        buffers.add(buffer);
        borrowed.decrementAndGet();
    }

    public int borrowed() {
        return borrowed.get();
    }

    public List<ByteBuffer> buffers() {
        return buffers;
    }
}