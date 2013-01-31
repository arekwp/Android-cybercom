package com.example.restfulclient.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

public class PhotoHelper
{
	@TargetApi(Build.VERSION_CODES.FROYO)
	public static String PhotoToString(String path)
	{
		Log.v("PhotoToString path: ", path);
		File bmp = new File(path);

		Bitmap bmpImg = resAndScaleBitmap(bmp);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmpImg.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		byte[] b = baos.toByteArray();
		String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		return encodedImage;
	}

	public static String BitmapToString(Bitmap bmpImg)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmpImg.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		byte[] b = baos.toByteArray();
		String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		return encodedImage;
	}

	private static Bitmap resAndScaleBitmap(File bmp)
	{
		// Get the dimensions of the View
		int targetW = 300;
		int targetH = 500;

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(bmp.getPath(), bmOptions);

		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(bmp.getPath(), bmOptions);

		Matrix mx = new Matrix();

		mx.postRotate(90);

		Bitmap resScaledBmp = Bitmap.createBitmap(bitmap, 0, 0,
		        bitmap.getWidth(), bitmap.getHeight(), mx, true);

		return resScaledBmp;
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	public static Bitmap StringToPhoto(String photo)
	{
		byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
		        decodedString.length);

		return decodedByte;
	}
}
