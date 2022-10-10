package dev.tr7zw.fastergui.gpuBuffers;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL46;


public class GPUExpandingBuffer {

        public ShaderStorageBuffer internalBuffer;
        public ShaderStorageBuffer copyBuffer;
        final int bufferBase;
        public int filled;
        public int size;
        public DataType type;
        public int stride;
        public GPUExpandingBuffer(int bufferBase, DataType type, int hint)
        {
            copyBuffer = new ShaderStorageBuffer(bufferBase, hint);
            internalBuffer = new ShaderStorageBuffer(bufferBase,hint);
            this.bufferBase = bufferBase;
            this.type = type;
            stride = type.Dimensions*type.Size;
            size = 0;
            filled = 0;
        }
        /// <summary>
        /// replace or append, grows the buffer when needed.
        /// </summary>
        /// <param name="data"></param>
        /// <param name="start"></param>
        public void Send(ByteBuffer data, int start)
        {
            if (size == 0 && start == 0)
            {
                size = data.remaining();
                internalBuffer.bind();
                internalBuffer.setData(data);
                internalBuffer.bindTo(bufferBase);
                filled = size;
                return;
            }
            int end = data.remaining() + start;
            //if data spills, resize;
            if (end > size) Grow(end); 
            internalBuffer.subData(start, data);
            filled = filled < end ? end : filled;
        }
        /// <summary>
        /// grows the buffer to newSize's length
        /// </summary>
        /// <param name="newSize"></param>
        public void Grow(int newSize)
        {
            // no need to resize if it fits already
            if (newSize <= size) return;

            //TODO: acquire brain
            if (newSize >= size * 2) { newSize *= 2; }
            else { newSize = size * 2; }

            GL46.glBindBuffer(GL46.GL_COPY_READ_BUFFER, internalBuffer.Handle); //select original buffer as where to read from.
            GL46.glBindBuffer(GL46.GL_COPY_WRITE_BUFFER, copyBuffer.Handle); //where to copy data into.
            //fill copyBuffer with an empty buffer with the needed size
            GL46.glBufferData(GL46.GL_COPY_WRITE_BUFFER, stride * newSize, 
                GL46.GL_DYNAMIC_COPY);
            //this should copy it's data to another part of memory, with just a larger space
            GL46.glCopyBufferSubData(GL46.GL_COPY_READ_BUFFER,
                GL46.GL_COPY_WRITE_BUFFER, 
                0, 0, stride * size);
            
            size = newSize;

            //swap handles
            var temp = internalBuffer;
            internalBuffer = copyBuffer;
            copyBuffer = temp;

            //rebind the ssbo to use the now larger buffer.
            internalBuffer.bindTo(bufferBase);
        }
}