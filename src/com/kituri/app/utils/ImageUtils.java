package com.kituri.app.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 图片操作工具包
 * 
 * @author yuweichen
 * @version 1.0
 * @created 2012-3-21
 */
public class ImageUtils {

	public final static String SDCARD_MNT = "/mnt/sdcard";
	public final static String SDCARD = "/sdcard";
	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int LEFT_TOP = 4;
	public static final int LEFT_BOTTOM = 5;
	public static final int RIGHT_TOP = 6;
	public static final int RIGHT_BOTTOM = 7;
	/** 请求相册 */
	public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
	/** 请求相机 */
	public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
	/** 请求裁剪 */
	public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;
	
	public int MAX_IMAGE_WIDTH = 52;   //原值
	public int MAX_IMAGE_HEIGHT = 52;

	/**
	 * 写图片文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @throws IOException
	 */
	public static void saveImage(Context context, String fileName, Bitmap bitmap)
			throws IOException {
		saveImage(context, fileName, bitmap, 100);
	}
	
	public static String getDir(String path) {
		String subString = path.substring(0, path.lastIndexOf('/'));
		return subString.substring(subString.lastIndexOf('/') + 1, subString.length());
    }

	public static void saveImage(Context context, String fileName,
			Bitmap bitmap, int quality) throws IOException {
		if (bitmap == null)
			return;

		FileOutputStream fos = context.openFileOutput(fileName,
				Context.MODE_PRIVATE);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, quality, stream);
		byte[] bytes = stream.toByteArray();
		fos.write(bytes);
		fos.close();
	}

	/**
	 * 写图片文件到SD卡
	 * 
	 * @throws IOException
	 */
	public static void saveImageToSD(String filePath, Bitmap bitmap, int quality)
			throws IOException {
		if (bitmap != null) {
			FileOutputStream fos = new FileOutputStream(filePath);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, quality, stream);
			byte[] bytes = stream.toByteArray();
			fos.write(bytes);
			fos.close();
		}
	}
	
	public static String getPathFromContentUri(ContentResolver cr, Uri contentUri) {
        String returnValue = null;

        if (ContentResolver.SCHEME_CONTENT.equals(contentUri.getScheme())) {
            // can post image
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = cr.query(contentUri, proj, null, null, null);

            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    returnValue = cursor
                            .getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                }
                cursor.close();
            }
        } else if (ContentResolver.SCHEME_FILE.equals(contentUri.getScheme())) {
            returnValue = contentUri.getPath();
        }

        return returnValue;
    }

	@SuppressLint("NewApi")
	public static String saveMyBitmap(Bitmap mBitmap) {
		String path = FileManager.getUploadPicFile() + FileUtils.getFileName();
		saveBitmap(mBitmap, FileUtils.getFileName());
		return path;
	}
	
	public static String saveBitmap(Bitmap mBitmap, String pathName) {
		if (mBitmap == null) {
			return "";
		}
		String path = FileManager.getUploadPicFile() + pathName;
		File file = new File(path);
		if (file.exists()) {
			return path;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int bitmapSize = baos.toByteArray().length / 2;
		Bitmap bitmap = null;
		double imageSize = bitmapSize / 1024 / 1024;
		// 对可用内存进行判断，来决定压缩策略。。。。需要修改的。
		if (imageSize >= 1) {
			bitmap = resizeImage(mBitmap, 1080, path);
		} else {
			bitmap = resizeImage(mBitmap, path);
		}

		if (bitmap == null) {
			path = "";
		}
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
		return path;
	}
	

	static public Bitmap decodeSampledBitmapFromFileDescriptor(
			String path, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		if(reqWidth == 0 || reqHeight == 0){
			options.inSampleSize = calculateInSampleSize(options, Integer.MAX_VALUE,
					Integer.MAX_VALUE);
		}else{
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
		}
		

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.ARGB_8888;  
        options.inPurgeable = true;//允许可清除  
        options.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果  
        //options.inTempStorage = new byte[12 * 1024]; 
		
        File file = new File(path);
        FileInputStream fs = null;
        try {
           fs = new FileInputStream(file);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
        Bitmap bmp = null;
        if(fs != null)
           try {
               bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, options);
           } catch (IOException e) {
               e.printStackTrace();
           }finally{ 
               if(fs!=null) {
                   try {
                       fs.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
        //return BitmapFactory.decodeFile(path, options);
        return bmp;
	}

//	static public int calculateInSampleSize(BitmapFactory.Options options,
//			int reqWidth, int reqHeight) {
//		// Raw height and width of image
//		final int height = options.outHeight;
//		final int width = options.outWidth;
//		int inSampleSize = 1;
//
//		if (height > reqHeight || width > reqWidth) {
//
//			// Calculate ratios of height and width to requested height and
//			// width
//			final int heightRatio = Math.round((float) height
//					/ (float) reqHeight);
//			final int widthRatio = Math.round((float) width / (float) reqWidth);
//
//			// Choose the smallest ratio as inSampleSize value, this will
//			// guarantee
//			// a final image with both dimensions larger than or equal to the
//			// requested height and width.
//			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//		}
//
//		return inSampleSize;
//	}
	
	
//	public static boolean clearImage() {
//		File cacheDir = null;
//		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
//            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),Constants.catchPath);
//        if(!cacheDir.exists())cacheDir.mkdirs();     
//        try {
//        	File[] files=cacheDir.listFiles();
//        	for(File f:files)
//        		f.delete();
//		} catch (Exception e) {
//			// TODO: handle exception
//			return false;
//		}
//		return true;
//	}
	
	public static void saveEditImage(Bitmap mBitmap, String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int bitmapSize = baos.toByteArray().length / 2;
		Bitmap bitmap = null;
		double imageSize = bitmapSize / 1024 / 1024;
		//对可用内存进行判断，来决定压缩策略。。。。需要修改的。
		if (imageSize >= 1) {
			bitmap=resizeImage(mBitmap, 1080, path);
		} else {
			bitmap = resizeImage(mBitmap, path);
		}
		
		if(bitmap==null){
			path="";
		}
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}
	
	public static Bitmap lightImage(Bitmap bm, int saturation, int hue, int lum) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        ColorMatrix mLightnessMatrix = new ColorMatrix();
        ColorMatrix mSaturationMatrix = new ColorMatrix();
        ColorMatrix mHueMatrix = new ColorMatrix();
        ColorMatrix mAllMatrix = new ColorMatrix();
        float mSaturationValue = saturation * 1.0F / 127;
        float mHueValue = hue * 1.0F / 127;
        float mLumValue = (lum - 127) * 1.0F / 127 * 180;
        mHueMatrix.reset();
        mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1);

        mSaturationMatrix.reset();
        mSaturationMatrix.setSaturation(mSaturationValue);
        mLightnessMatrix.reset();

        mLightnessMatrix.setRotate(0, mLumValue);
        mLightnessMatrix.setRotate(1, mLumValue);
        mLightnessMatrix.setRotate(2, mLumValue);

        mAllMatrix.reset();
        mAllMatrix.postConcat(mHueMatrix);
        mAllMatrix.postConcat(mSaturationMatrix);
        mAllMatrix.postConcat(mLightnessMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);
        return bmp;
    }

	public static String saveImageToSD(Intent data, String filePath) {
		String result = "-1"; // 1表示成功
		Bundle extras = data.getExtras();
		ByteArrayOutputStream stream = null;
		if (extras != null) {
			try {
				Bitmap photo = extras.getParcelable("data");
				// Drawable drawable = new BitmapDrawable(photo);
				stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byte[] bytes = stream.toByteArray();
				result = saveImageToSD(filePath, bytes);
			} catch (Exception e) {
				result = "-1";
				e.printStackTrace();
			} finally {
				try {
					if (stream != null) {
						stream.flush();
						stream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
		return result;
	}

	public static String saveImageToSD(String filePath, byte[] buffer) {
		String result = "-1"; // 1表示成功
		FileOutputStream fos = null;
		try {
			File file = new File(filePath);
			File parentFile = file.getParentFile();

			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			file.createNewFile();
			fos = new FileOutputStream(filePath);
			fos.write(buffer);
			result = "1";
		} catch (FileNotFoundException e) {
			result = "-1";
			e.printStackTrace();
		} catch (IOException e) {
			result = "-1";
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 读取输入流
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
	
	// 显示原生图片尺寸大小
		public static Bitmap getPathBitmap(Context context, Uri imageFilePath, int dw, int dh, String path)
				throws FileNotFoundException {
			// 获取屏幕的宽和高
			/**
			 * 为了计算缩放的比例，我们需要获取整个图片的尺寸，而不是图片
			 * BitmapFactory.Options类中有一个布尔型变量inJustDecodeBounds，将其设置为true
			 * 这样，我们获取到的就是图片的尺寸，而不用加载图片了。
			 * 当我们设置这个值的时候，我们接着就可以从BitmapFactory.Options的outWidth和outHeight中获取到值
			 */
			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inJustDecodeBounds = true;
			// 由于使用了MediaStore存储，这里根据URI获取输入流的形式
			Bitmap pic = BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(imageFilePath), null, op);
			
			int wRatio = (int) Math.ceil(op.outWidth / (float) dw); // 计算宽度比例
			int hRatio = (int) Math.ceil(op.outHeight / (float) dh); // 计算高度比例

			/**
			 * 接下来，我们就需要判断是否需要缩放以及到底对宽还是高进行缩放。 如果高和宽不是全都超出了屏幕，那么无需缩放。
			 * 如果高和宽都超出了屏幕大小，则如何选择缩放呢》 这需要判断wRatio和hRatio的大小
			 * 大的一个将被缩放，因为缩放大的时，小的应该自动进行同比率缩放。 缩放使用的还是inSampleSize变量
			 */
			if (wRatio > 1 && hRatio > 1) {
				if (wRatio > hRatio) {
					op.inSampleSize = wRatio;
				} else {
					op.inSampleSize = hRatio;
				}
			}
			op.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
			pic = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageFilePath), null, op);
			return BmpRotate(pic, readPictureDegree(path));
		}
		
		public static Bitmap BmpRotate(Bitmap bm, int angle) {
			if(bm==null){
				return null;
			}
			Matrix m = new Matrix();
			m.setRotate(angle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
			try {
				Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
				return bm1;
			} catch (OutOfMemoryError ex) {
				return null;
			}
		}
		
		// 根据用户的选择加载图片，并根据系统设置对图片进行缩放，然后显示在m_btnShareimg图片控件中
		public static Bitmap attachResizedImage(Context context,Uri image_Uri) {
			Bitmap m_bitmap = null;
			try {
				ContentResolver cr =context.getContentResolver();
				BitmapFactory.Options opt_size = new BitmapFactory.Options();
				opt_size.inJustDecodeBounds = true;
				m_bitmap = BitmapFactory.decodeStream(cr.openInputStream(image_Uri), null, opt_size);

				BitmapFactory.Options opt = new BitmapFactory.Options();
//				if ((opt_size.outWidth > opt_size.outHeight)
//						&& (opt_size.outWidth > MAX_IMAGE_WIDTH)) {
//					opt.inSampleSize = opt_size.outWidth / MAX_IMAGE_WIDTH;
//				} else if ((opt_size.outHeight > opt_size.outWidth)
//						&& (opt_size.outHeight > MAX_IMAGE_HEIGHT)) {
//					opt.inSampleSize = opt_size.outHeight / MAX_IMAGE_HEIGHT;
//				}

				m_bitmap = BitmapFactory.decodeStream(cr.openInputStream(image_Uri), null, opt);

//				if ((opt.outWidth > opt.outHeight)&& (opt.outWidth > MAX_IMAGE_WIDTH)) {
//					int scaleWidth = MAX_IMAGE_WIDTH;
//					int scaleHeight = opt.outHeight * scaleWidth / opt.outWidth;
//					m_bitmap = Bitmap.createScaledBitmap(m_bitmap, scaleWidth,
//							scaleHeight, true);
//				} else if ((opt.outHeight > opt.outWidth)
//						&& (opt.outHeight > MAX_IMAGE_HEIGHT)) {
//					int scaleHeight = MAX_IMAGE_HEIGHT;
//					int scaleWidth = opt.outWidth * scaleHeight / opt.outHeight;
//					m_bitmap = Bitmap.createScaledBitmap(m_bitmap, scaleWidth,scaleHeight, true);
//				}
				return m_bitmap;
				//share_pic.setBackgroundResource(R.drawable.background2);
			} catch (Exception e) {
//				new AlertDialog.Builder(this).setTitle("发生异常：" + e.getMessage())
//						.create().show();
			}
			return m_bitmap;
		}
		// 根据用户的选择加载图片，并根据系统设置对图片进行缩放，然后显示在m_btnShareimg图片控件中
				public static Bitmap ResizedImage(Uri image_Uri,int width,int height,Context inContext) {
					Bitmap m_bitmap = null;
					try {
						ContentResolver cr = inContext.getContentResolver();
						BitmapFactory.Options options = new BitmapFactory.Options();
						//取属性
						options.inJustDecodeBounds = true;
						options.inPreferredConfig = Bitmap.Config.RGB_565;
						m_bitmap = BitmapFactory.decodeStream(cr.openInputStream(image_Uri), null, options);
						
						/*if(options.outWidth * options.outHeight > MAX_IMAGE_WIDTH * MAX_IMAGE_HEIGHT){
							options.inSampleSize = Tools.computeSampleSize(options, -1, MAX_IMAGE_WIDTH * MAX_IMAGE_HEIGHT);
						}else{
							options.inSampleSize = 1;
						}*/
						
						if((options.outWidth/width)>(options.outHeight/height)){
							if(options.outWidth > width){
								options.inSampleSize = options.outWidth / width;
							}
						}else{
							if(options.outHeight > height){
								options.inSampleSize = options.outHeight / height;
							}
						}
				
						if(options.inSampleSize<1)
							options.inSampleSize=1;
						options.inJustDecodeBounds =false; 
						
						m_bitmap = BitmapFactory.decodeStream(cr.openInputStream(image_Uri), null, options);
						
						if((options.outWidth/width)>(options.outHeight/height)){
							if(options.outWidth > width){
								int scaleWidth = width;
								int scaleHeight = options.outHeight * scaleWidth / options.outWidth;
								m_bitmap = Bitmap.createScaledBitmap(m_bitmap, scaleWidth,
										scaleHeight, true);
							}
						}else{
							if(options.outHeight > height){
								int scaleHeight = height;
								int scaleWidth = options.outWidth * scaleHeight / options.outHeight;
								m_bitmap = Bitmap.createScaledBitmap(m_bitmap, scaleWidth,
										scaleHeight, true);
							}
						}				
					
						return m_bitmap;
					} catch (Exception e) {
						e.printStackTrace();
					}
					return m_bitmap;
				}

	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable loadImageFromUrlWithStore(String folder,
			String fileName, String url) {
		try {
			// 注意url可能包含?的情况，需要在?前截断
			if (url.indexOf("?") > 0) {
				url = url.substring(0, url.indexOf("?"));
			}
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			String status = Environment.getExternalStorageState();
			if (status.equals(Environment.MEDIA_MOUNTED)) {
				// FileUtils.createFile(folder, "");
				String outFilename = folder + "/" + fileName;
				FileOutputStream fos = new FileOutputStream(outFilename);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.JPEG, 70, stream);
				byte[] bytes = stream.toByteArray();
				fos.write(bytes);
				fos.close();
				/*
				 * bitmap.compress(CompressFormat.PNG, 100, new
				 * FileOutputStream( outFilename));
				 */
				Bitmap bitmapCompress = BitmapFactory.decodeFile(outFilename);
				Drawable drawable = new BitmapDrawable(bitmapCompress);
				return drawable;
			}
		} catch (Exception e) {
			Log.e("download_img_err", e.toString());
		}
		return null;
	}
	

		public static Bitmap resizeImage(Bitmap bitmap, int w, String filePath) {
			Bitmap BitmapOrg = bitmap;
			int width = BitmapOrg.getWidth();
			int height = BitmapOrg.getHeight();
			int newWidth = 0;
			int newHeight = 0;
			if (width > height) {
				newWidth = w;
				newHeight = (int)(((float)height) / width * w);
			} else {
				newWidth = (int)(((float)width) / height * w);
				newHeight = w;
			}
			// calculate the scale
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;
			// create a matrix for the manipulation
			Matrix matrix = new Matrix();
			// resize the Bitmap
			matrix.postScale(scaleWidth, scaleHeight);
			// if you want to rotate the Bitmap
			//matrix.postRotate(readPictureDegree(filePath));
			// recreate the new Bitmap
			Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
					height, matrix, true);
			resizedBitmap = compressImage(resizedBitmap, filePath);
			return resizedBitmap;
		}
		public static Bitmap resizeImage(Bitmap bitmap, String filePath) {
			Bitmap BitmapOrg = bitmap;
			int width = BitmapOrg.getWidth();
			int height = BitmapOrg.getHeight();
//			int newWidth = w;
//			int newHeight = h;
			// calculate the scale
//			float scaleWidth = ((float) newWidth) / width;
//			float scaleHeight = ((float) newHeight) / height;
//			// create a matrix for the manipulation
			//Matrix matrix = new Matrix();
//			// resize the Bitmap
//			matrix.postScale(scaleWidth, scaleHeight);
			// if you want to rotate the Bitmap
			// matrix.postRotate(45);
			//matrix.postRotate(readPictureDegree(filePath));
			// recreate the new Bitmap
			Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
					height, null, true);
			 resizedBitmap = compressImage(resizedBitmap, filePath);
			return resizedBitmap;
		}
	
	
		public static int computeSampleSize(BitmapFactory.Options options,
		        int minSideLength, int maxNumOfPixels) {
		    int initialSize = computeInitialSampleSize(options, minSideLength,
		            maxNumOfPixels);
		 
		    int roundedSize;
		    if (initialSize <= 8) {
		        roundedSize = 1;
		        while (roundedSize < initialSize) {
		            roundedSize <<= 1;
		        }
		    } else {
		        roundedSize = (initialSize + 7) / 8 * 8;
		    }
		 
		    return roundedSize;
		}
		 
		private static int computeInitialSampleSize(BitmapFactory.Options options,
		        int minSideLength, int maxNumOfPixels) {
		    double w = options.outWidth;
		    double h = options.outHeight;
		 
		    int lowerBound = (maxNumOfPixels == -1) ? 1 :
		            (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		    int upperBound = (minSideLength == -1) ? 128 :
		            (int) Math.min(Math.floor(w / minSideLength),
		            Math.floor(h / minSideLength));
		 
		    if (upperBound < lowerBound) {
		        return lowerBound;
		    }
		 
		    if ((maxNumOfPixels == -1) &&
		            (minSideLength == -1)) {
		        return 1;
		    } else if (minSideLength == -1) {
		        return lowerBound;
		    } else {
		        return upperBound;
		    }
		} 
		
	// 压缩图片大小 100以内
		public static Bitmap compressImage(Bitmap image, String filePath) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while (baos.toByteArray().length / 1024 > 80) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				if (options >= 10) {
					baos.reset();// 重置baos即清空baos
					image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
					options -= 10;// 每次都减少10
				} else {
					break;
				}
				
			}
			//System.out.println("resizeBitmap====>"+baos.toByteArray().length);
			
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
			final BitmapFactory.Options ops = new BitmapFactory.Options();
			ops.inJustDecodeBounds = false;
			ops.inPreferredConfig = Config.ARGB_8888;  
			ops.inPurgeable = true;//允许可清除  
			ops.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果  
			//ops.inTempStorage = new byte[12 * 1024]; 
	        
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			try {
				FileUtils.createFile(filePath);
				File file = new File(filePath);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
				bos.flush();
				bos.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
			if (image != null && !image.isRecycled()) {
				image.recycle();
			}
			return bitmap;
		}


	/**
	 * 下载图片
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable loadImageFromUrl(String url) {
		InputStream is = null;
		try {
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			is = (InputStream) imageUrl.getContent();
		} catch (Exception e) {
			Log.e("There", e.toString());
		}
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}

	/**
	 * 获取bitmap
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Bitmap getBitmap(Context context, String fileName) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			fis = context.openFileInput(fileName);
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	/**
	 * 获取bitmap
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getBitmapByPath(String filePath) {
		return getBitmapByPath(filePath, null);
	}

	/**
	 * 获取本地图片并指定高度和宽度
	 */
	public static Bitmap getNativeImage(String imagePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap myBitmap = BitmapFactory.decodeFile(imagePath, options); // 此时返回myBitmap为空
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 480);
		int ys = options.outHeight % 480;// 求余数
		float fe = ys / (float) 480;
		if (fe >= 0.5)
			be = be + 1;
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;

		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
		options.inJustDecodeBounds = false;

		myBitmap = BitmapFactory.decodeFile(imagePath, options);
		return myBitmap;
	}
	
	public static Bitmap pathToBitmap(String imagePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();  
	    options.inJustDecodeBounds = true;  
	    BitmapFactory.decodeFile(imagePath, options);  
	  
	    // Calculate inSampleSize  
	    options.inSampleSize = calculateInSampleSize(options, 480, 800);  
	  
	    // Decode bitmap with inSampleSize set  
	    options.inJustDecodeBounds = false;  
	      
	    return BitmapFactory.decodeFile(imagePath, options);
	}
	

	/**
	 * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
	 * 
	 * @param imagePath
	 *            图片在SDCard中的路径
	 * @return
	 */
	public static Bitmap getSDCardImg(String imagePath) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true; 
		opt.inInputShareable = true;
		// 获取资源图片
		Bitmap btm = BitmapFactory.decodeFile(imagePath, opt);
		return BmpRotate(btm, readPictureDegree(imagePath));
	}

	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public static Bitmap getBitmapByPath(String filePath,
			BitmapFactory.Options opts) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	/**
	 * 获取bitmap
	 * 
	 * @param file
	 * @return
	 */
	public static Bitmap getBitmapByFile(File file) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 使用当前时间戳拼接一个唯一的文件名
	 * 
	 * @param format
	 * @return
	 */
	public static String getTempFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS");
		String fileName = format.format(new Timestamp(System
				.currentTimeMillis()));
		return fileName;
	}

	/**
	 * 获取照相机使用的目录
	 * 
	 * @return
	 */
	public static String getCamerPath() {
		return Environment.getExternalStorageDirectory() + File.separator
				+ "FounderNews" + File.separator;
	}

	/**
	 * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路径
	 * 
	 * @param uri
	 * @return
	 */
	public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
		String filePath = null;

		String mUriString = mUri.toString();
		mUriString = Uri.decode(mUriString);

		String pre1 = "file://" + SDCARD + File.separator;
		String pre2 = "file://" + SDCARD_MNT + File.separator;

		if (mUriString.startsWith(pre1)) {
			filePath = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + mUriString.substring(pre1.length());
		} else if (mUriString.startsWith(pre2)) {
			filePath = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + mUriString.substring(pre2.length());
		}
		return filePath;
	}

	/**
	 * 通过uri获取文件的绝对路径
	 * 
	 * @param uri
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getAbsoluteImagePath(Activity context, Uri uri) {

		String imagePath = "";

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		// String[] proj = { MediaStore.Images.Media.DATA };
		// Cursor cursor = context.managedQuery(uri, proj, // Which columns to
		// // return
		// null, // WHERE clause; which rows to return (all rows)
		// null, // WHERE clause selection arguments (none)
		// null); // Order-by clause (ascending by name)
		//
		// if (cursor != null) {
		// int column_index = cursor
		// .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		// if (cursor.getCount() > 0 && cursor.moveToFirst()) {
		// imagePath = cursor.getString(column_index);
		// }
		// }

		return imagePath;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * 获取图片缩略图 只有Android2.1以上版本支持
	 * 
	 * @param imgName
	 * @param kind
	 *            MediaStore.Images.Thumbnails.MICRO_KIND
	 * @return
	 */
	/*
	 * public static Bitmap loadImgThumbnail(Activity context, String imgName,
	 * int kind) { Bitmap bitmap = null;
	 * 
	 * String[] proj = { MediaStore.Images.Media._ID,
	 * MediaStore.Images.Media.DISPLAY_NAME };
	 * 
	 * Cursor cursor = context.managedQuery(
	 * MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,
	 * MediaStore.Images.Media.DISPLAY_NAME + "='" + imgName + "'", null, null);
	 * 
	 * if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
	 * ContentResolver crThumb = context.getContentResolver();
	 * BitmapFactory.Options options = new BitmapFactory.Options();
	 * options.inSampleSize = 1; bitmap = MethodsCompat.getThumbnail(crThumb,
	 * cursor.getInt(0), kind, options); } return bitmap; }
	 * 
	 * public static Bitmap loadImgThumbnail(String filePath, int w, int h) {
	 * Bitmap bitmap = getBitmapByPath(filePath); return zoomBitmap(bitmap, w,
	 * h); }
	 */
	/**
	 * 获取SD卡中最新图片路径
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getLatestImage(Activity context) {
		String latestImage = null;
		String[] items = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA };
		Cursor cursor = context.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, items, null,
				null, MediaStore.Images.Media._ID + " desc");

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				latestImage = cursor.getString(1);
				break;
			}
		}

		return latestImage;
	}

	/**
	 * 计算缩放图片的宽高
	 * 
	 * @param img_size
	 * @param square_size
	 * @return
	 */
	public static int[] scaleImageSize(int[] img_size, int square_size) {
		if (img_size[0] <= square_size && img_size[1] <= square_size)
			return img_size;
		double ratio = square_size
				/ (double) Math.max(img_size[0], img_size[1]);
		return new int[] { (int) (img_size[0] * ratio),
				(int) (img_size[1] * ratio) };
	}

	/**
	 * 创建缩略图
	 * 
	 * @param context
	 * @param largeImagePath
	 *            原始大图路径
	 * @param thumbfilePath
	 *            输出缩略图路径
	 * @param square_size
	 *            输出图片宽度
	 * @param quality
	 *            输出图片质量
	 * @throws IOException
	 */
	public static void createImageThumbnail(Context context,
			String largeImagePath, String thumbfilePath, int square_size,
			int quality) throws IOException {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 1;
		// 原始图片bitmap
		Bitmap cur_bitmap = getBitmapByPath(largeImagePath, opts);

		if (cur_bitmap == null)
			return;

		// 原始图片的高宽
		int[] cur_img_size = new int[] { cur_bitmap.getWidth(),
				cur_bitmap.getHeight() };
		// 计算原始图片缩放后的宽高
		int[] new_img_size = scaleImageSize(cur_img_size, square_size);
		// 生成缩放后的bitmap
		Bitmap thb_bitmap = zoomBitmap(cur_bitmap, new_img_size[0],
				new_img_size[1]);
		// 生成缩放后的图片文件
		saveImageToSD(thumbfilePath, thb_bitmap, quality);
	}

	/**
	 * 放大缩小图片
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		Bitmap newbmp = null;
		if (bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Matrix matrix = new Matrix();
			float scaleWidht = ((float) w / width);
			float scaleHeight = ((float) h / height);
			matrix.postScale(scaleWidht, scaleHeight);
			newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
					true);
		}
		return newbmp;
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, int engle) {
		// 获取这个图片的宽和高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 定义预转换成的图片的宽度和高度
		int newWidth = 200;
		int newHeight = 200;
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		// 旋转图片 动作
		matrix.postRotate(engle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return resizedBitmap;
	}

	/*
	 * 旋转图片
	 * 
	 * @param engle
	 * 
	 * @param bitmap
	 * 
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int engle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(engle);
		// System.out.println("angle2=" + engle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	
	/**
	 * 把URI转为Bitmap
	 * @param uri
	 * @return
	 */
	public static Bitmap uriToBitmap(Context context ,Uri uri) {
		if(uri==null){
			return null;
		}
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		     bitmapOptions.inSampleSize = 4;
		     bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null , bitmapOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * Bitmap转byte[]
	 * */
	public static byte[] bitmapToByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		return out.toByteArray();
	}

	/**
	 * byte[]转Bitmap
	 * */
	public static Bitmap byteToBitmap(byte[] data) {
		if (data.length != 0) {
			return BitmapFactory.decodeByteArray(data, 0, data.length);
		}
		return null;
	}

	/**
	 * (缩放)重绘图片
	 * 
	 * @param context
	 *            Activity
	 * @param bitmap
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Bitmap reDrawBitMap(Activity context, Bitmap bitmap) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int rHeight = dm.heightPixels;
		int rWidth = dm.widthPixels;
		// float rHeight=dm.heightPixels/dm.density+0.5f;
		// float rWidth=dm.widthPixels/dm.density+0.5f;
		// int height=bitmap.getScaledHeight(dm);
		// int width = bitmap.getScaledWidth(dm);
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		float zoomScale;
		/** 方式1 **/
		// if(rWidth/rHeight>width/height){//以高为准
		// zoomScale=((float) rHeight) / height;
		// }else{
		// //if(rWidth/rHeight<width/height)//以宽为准
		// zoomScale=((float) rWidth) / width;
		// }
		/** 方式2 **/
		// if(width*1.5 >= height) {//以宽为准
		// if(width >= rWidth)
		// zoomScale = ((float) rWidth) / width;
		// else
		// zoomScale = 1.0f;
		// }else {//以高为准
		// if(height >= rHeight)
		// zoomScale = ((float) rHeight) / height;
		// else
		// zoomScale = 1.0f;
		// }
		/** 方式3 **/
		if (width >= rWidth)
			zoomScale = ((float) rWidth) / width;
		else
			zoomScale = 1.0f;
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		matrix.postScale(zoomScale, zoomScale);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 获得圆角图片的方法
	 * 
	 * @param bitmap
	 * @param roundPx
	 *            一般设成14
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 获得带倒影的图片方法
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * 独立的倒影图像
	 * */
	public static Bitmap createReflectionBitmapForSingle(Bitmap src) {
		final int w = src.getWidth();
		final int h = src.getHeight();
		// 绘制高质量32位图
		Bitmap bitmap = Bitmap.createBitmap(w, h / 2, Config.ARGB_8888);
		// 创建沿X轴的倒影图像
		Matrix m = new Matrix();
		m.setScale(1, -1);
		Bitmap t_bitmap = Bitmap.createBitmap(src, 0, h / 2, w, h / 2, m, true);

		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		// 绘制倒影图像
		canvas.drawBitmap(t_bitmap, 0, 0, paint);
		// 线性渲染-沿Y轴高到低渲染
		Shader shader = new LinearGradient(0, 0, 0, h / 2, 0x70ffffff,
				0x00ffffff, Shader.TileMode.MIRROR);
		paint.setShader(shader);
		// 取两层绘制交集。显示下层。
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// 绘制渲染倒影的矩形
		canvas.drawRect(0, 0, w, h / 2, paint);
		return bitmap;
	}

	/**
	 * 变成灰色图像
	 * */
	public static Bitmap createGrayBitmap(Bitmap src) {
		final int w = src.getWidth();
		final int h = src.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		// 颜色变换的矩阵
		ColorMatrix matrix = new ColorMatrix();
		// saturation 饱和度值，最小可设为0，此时对应的是灰度图; 为1表示饱和度不变，设置大于1，就显示过饱和
		matrix.setSaturation(0);
		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		paint.setColorFilter(filter);
		canvas.drawBitmap(src, 0, 0, paint);
		return bitmap;
	}

	/**
	 * 添加水印效果
	 * */
	public static Bitmap createWatermark(Bitmap src, Bitmap watermark,
			int direction, int spacing) {
		final int w = src.getWidth();
		final int h = src.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(src, 0, 0, null);
		if (direction == LEFT_TOP) {
			canvas.drawBitmap(watermark, spacing, spacing, null);
		} else if (direction == LEFT_BOTTOM) {
			canvas.drawBitmap(watermark, spacing, h - watermark.getHeight()
					- spacing, null);
		} else if (direction == RIGHT_TOP) {
			canvas.drawBitmap(watermark, w - watermark.getWidth() - spacing,
					spacing, null);
		} else if (direction == RIGHT_BOTTOM) {
			canvas.drawBitmap(watermark, w - watermark.getWidth() - spacing, h
					- watermark.getHeight() - spacing, null);
		}
		return bitmap;
	}

	/**
	 * 合成图像
	 * */
	public static Bitmap composeBitmap(int direction, Bitmap... bitmaps) {
		if (bitmaps.length < 2) {
			return null;
		}
		Bitmap firstBitmap = bitmaps[0];
		for (int i = 1; i < bitmaps.length; i++) {
			firstBitmap = composeBitmap(firstBitmap, bitmaps[i], direction);
		}
		return firstBitmap;
	}

	private static Bitmap composeBitmap(Bitmap firstBitmap,
			Bitmap secondBitmap, int direction) {
		if (firstBitmap == null) {
			return null;
		}
		if (secondBitmap == null) {
			return firstBitmap;
		}
		final int fw = firstBitmap.getWidth();
		final int fh = firstBitmap.getHeight();
		final int sw = secondBitmap.getWidth();
		final int sh = secondBitmap.getHeight();
		Bitmap bitmap = null;
		Canvas canvas = null;
		if (direction == TOP) {
			bitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			canvas = new Canvas(bitmap);
			canvas.drawBitmap(secondBitmap, 0, 0, null);
			canvas.drawBitmap(firstBitmap, 0, sh, null);
		} else if (direction == BOTTOM) {
			bitmap = Bitmap.createBitmap(fw > sw ? fw : sw, fh + sh,
					Config.ARGB_8888);
			canvas = new Canvas(bitmap);
			canvas.drawBitmap(firstBitmap, 0, 0, null);
			canvas.drawBitmap(secondBitmap, 0, fh, null);
		} else if (direction == LEFT) {
			bitmap = Bitmap.createBitmap(fw + sw, sh > fh ? sh : fh,
					Config.ARGB_8888);
			canvas = new Canvas(bitmap);
			canvas.drawBitmap(secondBitmap, 0, 0, null);
			canvas.drawBitmap(firstBitmap, sw, 0, null);
		} else if (direction == RIGHT) {
			bitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			canvas = new Canvas(bitmap);
			canvas.drawBitmap(firstBitmap, 0, 0, null);
			canvas.drawBitmap(secondBitmap, fw, 0, null);
		}
		return bitmap;
	}

	/**
	 * 将Drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * 将bitmap转化为drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * 将view转换成bitMap
	 * 
	 * @param view
	 * @param filePath
	 */
	public static void getImageFromView(View view, File file) {
		Bitmap bitmap = null;
		FileOutputStream fileOutputStream = null;
		try {

			bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas();
			canvas.setBitmap(bitmap);
			view.draw(canvas);
			fileOutputStream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public static String uriToPathName(ContentResolver cr, Uri uri) {
		String fileName = null;
	       Uri filePathUri = uri;
	       if (uri != null)
	       {
	           if (uri.getScheme().toString().compareTo("content") == 0)
	           {
	               // content://开头的uri
	              Cursor cursor = cr.query(uri, null, null, null, null);
	              if (cursor != null && cursor.moveToFirst())
	              {
	                  int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	                  fileName = cursor.getString(column_index); // 取出文件路径
	 
	                  // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
	                  if (!fileName.startsWith("/storage") && !fileName.startsWith("/mnt"))
	                  {
	                     // 检查是否有”/mnt“前缀
	                     fileName = "/mnt" + fileName;
	                  }
	                  cursor.close();
	              }
	           }
	           else if (uri.getScheme().compareTo("file") == 0) // file:///开头的uri
	           {
	              fileName = filePathUri.toString();// 替换file://
	              fileName = filePathUri.toString().replace("file://", "");
	              int index = fileName.indexOf("/sdcard");
	              fileName = index == -1 ? fileName : fileName.substring(index);
	              /*if (!fileName.startsWith("/mnt")) {
	                  // 加上"/mnt"头
	                  fileName = "/mnt" + fileName;
	              }*/
	           }
	       }
	       return fileName;
	}

	/**
	 * 获取图片类型
	 * 
	 * @param file
	 * @return
	 */
	public static String getImageType(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			String type = getImageType(in);
			return type;
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * detect bytes's image type by inputstream
	 * 
	 * @param in
	 * @return
	 * @see #getImageType(byte[])
	 */
	public static String getImageType(InputStream in) {
		if (in == null) {
			return null;
		}
		try {
			byte[] bytes = new byte[8];
			in.read(bytes);
			return getImageType(bytes);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * detect bytes's image type
	 * 
	 * @param bytes
	 *            2~8 byte at beginning of the image file
	 * @return image mimetype or null if the file is not image
	 */
	public static String getImageType(byte[] bytes) {
		if (isJPEG(bytes)) {
			return "image/jpeg";
		}
		if (isGIF(bytes)) {
			return "image/gif";
		}
		if (isPNG(bytes)) {
			return "image/png";
		}
		if (isBMP(bytes)) {
			return "application/x-bmp";
		}
		return null;
	}

	private static boolean isJPEG(byte[] b) {
		if (b.length < 2) {
			return false;
		}
		return (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
	}

	private static boolean isGIF(byte[] b) {
		if (b.length < 6) {
			return false;
		}
		return b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8'
				&& (b[4] == '7' || b[4] == '9') && b[5] == 'a';
	}

	private static boolean isPNG(byte[] b) {
		if (b.length < 8) {
			return false;
		}
		return (b[0] == (byte) 137 && b[1] == (byte) 80 && b[2] == (byte) 78
				&& b[3] == (byte) 71 && b[4] == (byte) 13 && b[5] == (byte) 10
				&& b[6] == (byte) 26 && b[7] == (byte) 10);
	}

	private static boolean isBMP(byte[] b) {
		if (b.length < 2) {
			return false;
		}
		return (b[0] == 0x42) && (b[1] == 0x4d);
	}
	
	public static void recycleView(View view){
//		if(view == null){
//			return;
//		}
//		if(view.getBackground() != null){
//			if(view.getBackground() instanceof BitmapDrawable){
//				BitmapDrawable bd = (BitmapDrawable)view.getBackground();
//				view.setBackgroundResource(0);
//				view.setBackgroundDrawable(null);
//				if(bd != null){
//					bd.setCallback(null);
//					bd.getBitmap().recycle();
//				}
//			}
//		}
		if(view instanceof ViewGroup){
			Utility.recycleViewGroupAndChildViews((ViewGroup)view, false);
		}else{
			Utility.recycleView(view, false);
		}
		
	}
	
}
