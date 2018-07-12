package it.unimi.dsi.sux4j.util;

import java.nio.ByteBuffer;

public class ByteBufferPool {

    public ByteBufferPool() {

    }

    public ByteBuffer acquire(int bytes) {
        return ByteBuffer.allocate(bytes);
    }

    public void release(final ByteBuffer buffer) {
        // Do nothing...
        // TODO: Fix ChunkedHashStore to reuse byte buffers.
    }
}