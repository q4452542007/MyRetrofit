package mapsoft.com.myretrofit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

public abstract class BZip2Utils {

    public static final int BUFFER = 1024;
    public static final CharSequence EXT = ".bz2";

    /**
     * 数据压缩
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 压缩
        compress(bais, baos);

        byte[] output = baos.toByteArray();

        baos.flush();
        baos.close();

        bais.close();

        return output;
    }

    /**
     * 文件压缩
     *
     * @param file
     * @throws IOException
     */
    public static void compress(File file) throws IOException {
        compress(file, true);
    }

    /**
     * 文件压缩
     *
     * @param file
     * @param delete 是否删除原始文件
     * @throws IOException
     */
    public static void compress(File file, boolean delete) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

        compress(fis, fos);

        fis.close();
        fos.flush();
        fos.close();

        if (delete) {
            file.delete();
        }
    }

    /**
     * 数据压缩
     *
     * @param is
     * @param os
     * @throws IOException
     */
    public static void compress(InputStream is, OutputStream os)
            throws IOException {

        BZip2CompressorOutputStream gos = new BZip2CompressorOutputStream(os);

        int count;
        byte data[] = new byte[BUFFER];
        while ((count = is.read(data, 0, BUFFER)) != -1) {
            gos.write(data, 0, count);
        }

        gos.finish();

        gos.flush();
        gos.close();
    }

    /**
     * 文件压缩
     *
     * @param path
     * @throws IOException
     */
    public static void compress(String path) throws IOException {
        compress(path, true);
    }

    /**
     * 文件压缩
     *
     * @param path
     * @param delete 是否删除原始文件
     * @throws IOException
     */
    public static void compress(String path, boolean delete) throws IOException {
        File file = new File(path);
        compress(file, delete);
    }

    /**
     * 数据解压缩
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] decompress(byte[] data) throws IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 解压缩
        decompress(bais, baos);
        data = baos.toByteArray();

        baos.flush();
        baos.close();

        bais.close();

        return data;
    }

    /**
     * 文件解压缩
     *
     * @param file
     * @throws IOException
     */
    public static void decompress(File file) throws IOException {
        decompress(file, true);
    }

    /**
     * 文件解压缩
     *
     * @param file
     * @param delete 是否删除原始文件
     * @throws IOException
     */
    public static void decompress(File file, boolean delete) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT, ""));
        decompress(fis, fos);
        fis.close();
        fos.flush();
        fos.close();

        if (delete) {
            file.delete();
        }
    }

    /**
     * 数据解压缩
     *
     * @param is
     * @param os
     * @throws IOException
     * @throws IOException
     */
    public static void decompress(InputStream is, OutputStream os) throws IOException {
        BZip2CompressorInputStream gis = new BZip2CompressorInputStream(is);
        int count;
        byte data[] = new byte[BUFFER];
        while ((count = gis.read(data, 0, BUFFER)) != -1) {
            os.write(data, 0, count);
        }
        gis.close();

    }

    /**
     * 文件解压缩
     *
     * @param path
     * @throws IOException
     */
    public static void decompress(String path) throws IOException {
        decompress(path, true);
    }

    /**
     * 文件解压缩
     *
     * @param path
     * @param delete 是否删除原始文件
     * @throws IOException
     */
    public static void decompress(String path, boolean delete) throws IOException {
        File file = new File(path);
        decompress(file, delete);
    }

}
