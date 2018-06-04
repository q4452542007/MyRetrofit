package mapsoft.com.myretrofit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Even_Kwok on 2016/9/20.
 */
public class Tool {

	public static String TAG = "mapsoft：";
	public static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 检测网络是否可用
	 *
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取手机IP地址
	 *
	 * @return
	 * @throws SocketException
	 */
	public static String getPhoneIp() throws SocketException {
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
					//if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
					return inetAddress.getHostAddress().toString();
				}
			}
		}
		return "";
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 *
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 *
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 获取文件的hash值进行文件完整判断
	 *
	 * @param fileName
	 * @param hashType
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHash(String fileName, String hashType)
			throws IOException, NoSuchAlgorithmException {
		InputStream fis;
		fis = new FileInputStream(fileName);
		byte[] buffer = new byte[1024];
		MessageDigest md5 = MessageDigest.getInstance(hashType);
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			md5.update(buffer, 0, numRead);
		}
		fis.close();
		return toHexString(md5.digest());
	}

    /*调用reboot重启
	execCommand("su root reboot");

    设置：2015年11月16日12时，可执行如下命令：
    execCommand("su root date -s 20151116.120000");*/

	public static void execCommand(String command) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec(command);
		InputStream inputstream = proc.getInputStream();
		InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
		BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
		String line = "";
		StringBuilder sb = new StringBuilder(line);
		while ((line = bufferedreader.readLine()) != null) {
			sb.append(line);
			sb.append('\n');
		}
		try {
			if (proc.waitFor() != 0) {
				System.err.println("exit value = " + proc.exitValue());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 获取屏幕高度
	 *
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		WindowManager manager = activity.getWindowManager();
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getRealMetrics(outMetrics);
		return (int) (outMetrics.widthPixels * outMetrics.density + 0.5f);
	}

	public static List<Bitmap> getImages(int group, int order) {
		List<Bitmap> pBitmaps = new ArrayList<>();
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures";
		File pFile = new File(path);

		if (pFile.exists()) {
			File[] pFiles = pFile.listFiles();

			//int adNum = pFiles.length / group + (pFiles.length % group != 0? 1 : 0);
			//int num = pFiles.length / group;
			//int tmp = order * adNum;
			int flag= 0;
			for (int i = order; i < pFiles.length; i++) {
				if (i == order) {
					Bitmap pBitmap = getLoacalBitmap(pFiles[i].getAbsolutePath());
					pBitmaps.add(pBitmap);
				}
				if (flag == group) {
					Bitmap pBitmap = getLoacalBitmap(pFiles[i].getAbsolutePath());
					pBitmaps.add(pBitmap);
					flag = 0;
				}
				flag++;
			}
		}
		return pBitmaps;
	}

	/**
	 * 加载本地图片
	 * http://bbs.3gstdy.com
	 * @param url
	 * @return
	 */
	private static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 截取字符串str中指定字符 strStart、strEnd之间的字符串
	 *
	 * @param string
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
		int strStartIndex = str.indexOf(strStart);
		int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
		if (strStartIndex < 0) {
			return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
		}
		if (strEndIndex < 0) {
			return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
		}
        /* 开始截取 */
		String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
		return result;
	}
}
