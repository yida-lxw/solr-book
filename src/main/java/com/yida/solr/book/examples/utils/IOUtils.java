package com.yida.solr.book.examples.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

/**
 * @ClassName: IOUtils
 * @Description: IO工具类
 * @author Lanxiaowei
 * @date 2013-4-24 下午12:18:35
 */
public class IOUtils {

    /**
     * @Title: object2ByteArray
     * @Description: Object对象转换成字节数组
     * @param @param obj
     * @param @return
     * @param @throws IOException
     * @return byte[]
     * @throws
     */
    public static byte[] object2ByteArray(Object obj) throws IOException {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(obj);
            out.flush();
            byte[] bytes = bout.toByteArray();
            bout.close();
            out.close();
            return bytes;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @Title: byteArray2Object
     * @Description: 字节数组转换成Object对象
     * @param @param bytes
     * @param @return
     * @param @throws IOException
     * @param @throws ClassNotFoundException
     * @return Object
     * @throws
     */
    public static Object byteArray2Object(byte[] bytes) {
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            Object obj = oi.readObject();
            bi.close();
            oi.close();
            return obj;
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Object对象转换成NIO中的ByteBuffer
     * @param obj
     * @return
     * @throws IOException
     */
    public static ByteBuffer object2ByteBuffer(Object obj) throws IOException {
        byte[] bytes = object2ByteArray(obj);
        ByteBuffer buff = ByteBuffer.wrap(bytes);
        return buff;
    }

    /**
     * 输入流内容Copy到输出流<br/>
     * 字节复制
     *
     * @param input
     * @param output
     * @param buffer
     * @return
     * @throws IOException
     */
    public static long byteCopy(InputStream input, OutputStream output, byte buffer[]) throws IOException {
        long count = 0L;
        for (int n = 0; -1 != (n = input.read(buffer));) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 输入流内容Copy到输出流
     *
     * @param input
     * @param output
     * @return
     * @throws IOException
     */
    public static long byteCopy(InputStream input, OutputStream output) throws IOException {
        return byteCopy(input, output, new byte[4096]);
    }

    /**
     * 输入流内容Copy到输出流<br/>
     * 字符复制
     *
     * @param reader
     * @param writer
     * @param buffer
     * @return
     * @throws IOException
     */
    public static long charCopy(Reader reader, Writer writer, char buffer[]) throws IOException {
        long count = 0L;
        for (int n = 0; -1 != (n = reader.read(buffer));) {
            writer.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 输入流内容Copy到输出流<br/>
     * 字符复制
     *
     * @param reader
     * @param writer
     * @return
     * @throws IOException
     */
    public static long charCopy(Reader reader, Writer writer) throws IOException {
        return charCopy(reader, writer, new char[4096]);
    }

    /**
     * 字节复制
     *
     * @param input
     * @param output
     * @return  返回copy的字节总数
     * @throws IOException
     */
    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = byteCopy(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int) count;
    }

    /**
     * 字符复制
     * @param reader
     * @param writer
     * @return
     * @throws IOException
     */
    public static long copy(Reader reader, Writer writer) throws IOException {
        long count = charCopy(reader, writer);
        return count > 9223372036854775807L? -1 : count;
    }

    /**
     * IO流复制
     * @param reader
     * @param output
     * @throws IOException
     */
    public static void copy(Reader reader, OutputStream output) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output);
        charCopy(reader, ((Writer) (out)));
        out.flush();
    }

    /**
     * IO流复制
     * @param reader
     * @param output
     * @param encoding     写入编码
     * @throws IOException
     */
    public static void copy(Reader reader, OutputStream output, String encoding) throws IOException {
        if (encoding == null) {
            copy(reader, output);
        } else {
            OutputStreamWriter out = new OutputStreamWriter(output, encoding);
            charCopy(reader, ((Writer) (out)));
            out.flush();
        }
    }

    /**
     * 字节数组写到输出流
     *
     * @param data
     *            字节数组
     * @param output
     *            输出流
     * @param encoding
     *            字节数组数据原始编码
     * @throws IOException
     */
    public static void write(byte data[], OutputStream output, String encoding) throws IOException {
        if (data != null) {
            if (null != encoding && encoding.length() > 0) {
                output.write(new String(data, encoding).getBytes());
            } else {
                output.write(data);
            }
        }
    }

    /***************关闭IO资源************************/
    public static void closeSilence(Reader input) {
        closeSilence(((Closeable) (input)));
    }

    public static void closeSilence(Writer output) {
        closeSilence(((Closeable) (output)));
    }

    public static void closeSilence(InputStream input) {
        closeSilence(((Closeable) (input)));
    }

    public static void closeSilence(OutputStream output) {
        closeSilence(((Closeable) (output)));
    }

    public static void closeSilence(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException io) {
        }
    }

    public static void closeSilence(Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException io) {
            }
        }
    }

    public static void closeSilence(Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException io) {
            }
        }
    }

    public static void closeSilence(ServerSocket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException io) {
            }
        }
    }
    /***************关闭IO资源 End************************/
}
