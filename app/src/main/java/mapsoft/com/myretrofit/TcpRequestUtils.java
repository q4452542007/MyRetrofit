package mapsoft.com.myretrofit;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TcpRequestUtils {

    public static byte charToByte(char paramChar) {
        return (byte) "0123456789ABCDEF".indexOf(paramChar);
    }

    /**
     * 创建套接字
     * @param paramString
     * @param paramInt
     * @return
     * @throws IOException
     */
    public static Socket startSocket(String paramString, int paramInt)
            throws IOException {
        return new Socket(paramString, paramInt);
    }

    /**
     * 关闭socket
     * @param paramSocket
     * @throws IOException
     */
    public static void closeSocket(Socket paramSocket)
            throws IOException {
        if (paramSocket == null)
            return;
        paramSocket.close();
    }

    /**
     * 发送指令包
     * @param paramSocket
     * @param paramString
     * @throws IOException
     */
    public static boolean sendPacket(Socket paramSocket, String paramString) {
        // 判断套接字是否链接
        if ((!paramSocket.isConnected()) || (paramString == null) || ("".equals(paramString))){
            return false;
        }
        try {
            OutputStream localOutputStream = paramSocket.getOutputStream();
            localOutputStream.write(paramString.getBytes());
            localOutputStream.flush();
        } catch (IOException e) {
            try {
                paramSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
        return true;
    }

}