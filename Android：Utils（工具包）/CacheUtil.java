package github.common.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import android.content.Context;
public class CacheUtil 
{
	private static DecimalFormat mDecimalFormat = new DecimalFormat("0.0");
	/**
	 * 获取当前程序缓存文件大小
	 * @param context
	 * @return
	 */
	public static String getCacheFileSize(Context context){
		try {
			return getFileAutoSize(context.getCacheDir());
		} catch (FileNotFoundException e) {
			return "";
		}
	}
	/**清空当前程序缓存
	 * @param context
	 * @return
	 */
	public static boolean deleteCacheFile(Context context)
	{
		try {
			return deleteFile(context.getCacheDir());
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	/**
	 * 获取文件大小自动转化最优单位
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	private static String getFileAutoSize(File file) throws FileNotFoundException{
		long size = getFileSize(file);
		if (size < 1024) {
			return size + "B";
		}else if (size < Math.pow(1024, 2)) {
			return mDecimalFormat.format(size / 1024) + "K";
		}else if (size < Math.pow(1024, 3)) {
			return mDecimalFormat.format(size / Math.pow(1024, 2)) + "M";
		}else {
			return mDecimalFormat.format(size / Math.pow(1024, 3)) + "G";
		}
	}
	/**
	 * 获取文件大小
	 * @param file
	 * @return B
	 * @throws FileNotFoundException
	 */
	private static long getFileSize(File file) throws FileNotFoundException{
		if (!file.exists() || file == null) {
			throw new FileNotFoundException();
		}
		long size = 0;
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				size += getFileSize(f);
			}else {
				size += f.length();
			}
		}
		return size;
	}

	public enum Size{

		B("B"), K("K"), M("M"), G("G"), KB("KB"), MB("MB"), GB("GB");

		public String name;

		private Size(String name){
			this.name = name;
		}
	}
	/**删除文件或文件夹
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	private static boolean deleteFile(File file) throws FileNotFoundException{
		if (!file.exists() || file == null) {
			throw new FileNotFoundException();
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				deleteFile(f);
			}
			return file.delete();
		}else {
			return file.delete();
		}
	}
	
}
