package com.hailang.visitormodel.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	public static boolean isNetworkAvailable(Context context) {
		//Context context = mActivity.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String decodeContent(String paramString) {
		return URLDecoder.decode(paramString);
	}

	public static void dialTheNumber(Context paramContext, String paramString) {
		Uri localUri = Uri.parse("tel:" + paramString);
		Intent localIntent = new Intent("android.intent.action.DIAL", localUri);
		paramContext.startActivity(localIntent);
	}

	public static String encodeContent(String paramString) {
		return URLEncoder.encode(paramString);
	}

	private static byte[] getBytes(InputStream paramInputStream)
			throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		byte[] arrayOfByte = new byte[1024];
		while (true) {
			int i = paramInputStream.read(arrayOfByte, 0, 1024);
			if (i == -1)
				return localByteArrayOutputStream.toByteArray();
			localByteArrayOutputStream.write(arrayOfByte, 0, i);
			localByteArrayOutputStream.flush();
		}
	}
	public static boolean isEmail(String paramString) {
		return Pattern
				.compile(
						"^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
				.matcher(paramString).matches();
	}

	/**
	 * 检测网络是否存在
	 */
	public static void HttpTest(Activity mActivity) {
		if (!isNetworkAvailable(mActivity)) {
			AlertDialog.Builder builders = new AlertDialog.Builder(mActivity);
			builders.setTitle("温馨提示");
			builders.setMessage("连接异常,请检查您的网络是否正常！");
			/*
			 * LayoutInflater _inflater = LayoutInflater.from(mActivity); View
			 * convertView = _inflater.inflate(R.layout.error,null);
			 * builders.setView(convertView);
			 */
			builders.setPositiveButton("确定", null);
			builders.create().show();
		}
	}

	public static Bitmap laodImageFromFile(String filePath) {
		File localFile = new File(filePath);
		Bitmap localBitmap = null;
		if (localFile.exists()) {
			localBitmap = BitmapFactory.decodeFile(filePath);
		} else {
			localBitmap = null;// BitmapFactory.decodeFile("item_default_picture");
		}
		return localBitmap;
	}

	public static Bitmap loadImageFromStream(InputStream is) {
		byte[] arrayOfByte;
		Bitmap bitMap = null;
		try {
			arrayOfByte = getBytes(is);
			int i = arrayOfByte.length;
			bitMap = BitmapFactory.decodeByteArray(arrayOfByte, 0, i);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭InputStream
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitMap;
	}

	public static Bitmap loadImageFromUrl(String strImageUrl) {
		Bitmap bitMap = null;
		URL aryURI;
		try {
			aryURI = new URL(strImageUrl);
			// 打开连接
			URLConnection conn = aryURI.openConnection();
			conn.connect();
			// 转变为 InputStream
			InputStream is = conn.getInputStream();
			bitMap = BitmapFactory.decodeStream(is);
			//byte[] arrayOfByte = getBytes(is);
			//int i = arrayOfByte.length;
			//bitMap = BitmapFactory.decodeByteArray(arrayOfByte, 0, i);
			// 关闭InputStream
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitMap;
	}
	public static Bitmap loadImageFromUrlWithOpt(String strImageUrl) {
		Bitmap bitMap = null;
		URL aryURI;
		try {
			aryURI = new URL(strImageUrl);
			// 打开连接
			URLConnection conn = aryURI.openConnection();
			conn.connect();
			// 转变为 InputStream
			InputStream is = conn.getInputStream();
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 4;
			bitMap = BitmapFactory.decodeStream(is,null,opts);
			//byte[] arrayOfByte = getBytes(is);
			//int i = arrayOfByte.length;
			//bitMap = BitmapFactory.decodeByteArray(arrayOfByte, 0, i);
			// 关闭InputStream
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitMap;
	}
	public static String md5(String paramString) {
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			byte[] arrayOfByte = paramString.getBytes();
			localMessageDigest.update(arrayOfByte);
			String str1 = toHexString(localMessageDigest.digest());
			return str1;
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			while (true) {
				localNoSuchAlgorithmException.printStackTrace();
				String str2 = paramString;
			}
		}
	}

	public static boolean saveFile(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    File localFile1 = new File(paramString1);
    boolean bool1;
	if (!localFile1.exists())
      bool1 = localFile1.mkdirs();
    File localFile2 = new File(paramString2);
    boolean bool2 = false;
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream);
      if (paramBitmap != null)
      {
        Bitmap.CompressFormat localCompressFormat = Bitmap.CompressFormat.PNG;
        bool2 = paramBitmap.compress(localCompressFormat, 100, localBufferedOutputStream);
        localBufferedOutputStream.flush();
        localBufferedOutputStream.close();
      }
      return bool2;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

	public static String toHexString(byte[] paramArrayOfByte) {
		int i = paramArrayOfByte.length * 2;
		StringBuilder localStringBuilder1 = new StringBuilder(i);
		int j = 0;
		while (true) {
			int k = paramArrayOfByte.length;
			if (j >= k)
				return localStringBuilder1.toString();
			char[] arrayOfChar1 = new char[16];
			int m = (paramArrayOfByte[j] & 0xF0) >>> 4;
			char c1 = arrayOfChar1[m];
			StringBuilder localStringBuilder2 = localStringBuilder1.append(c1);
			char[] arrayOfChar2 = new char[16];
			int n = paramArrayOfByte[j] & 0xF;
			char c2 = arrayOfChar2[n];
			StringBuilder localStringBuilder3 = localStringBuilder1.append(c2);
			j += 1;
		}
	}
	public static void exitApp(Context context){
		ActivityManager activityMgr=(ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
		activityMgr.restartPackage(context.getPackageName());
	}
	public static List<PackageInfo> getAllApp(Context context){
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
		return packageInfoList;
	}
}