package sicau.edu.cn.favorite.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * 类名称：Base64Utils <br>
 * 类描述: 工具 <br>
 * 创建人：felicity <br>
 * 创建时间：2017年8月25日 下午2:24:13 <br>
 * 修改人：felicity <br>
 * 修改时间：2017年8月25日 下午2:24:13 <br>
 * 修改备注:
 * @version
 * @see
 */
public class Base64Utils {
	/**
	 * 文件读取缓冲区大小
	 */
	private static final int CACHE_SIZE = 1024;

	/**
	 * BASE64字符串解码为字节数组
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static byte[] decode2byte(String str) throws Exception {
		return Base64.decodeBase64(str);
	}

	/**
	 * BASE64字符串解码字符串
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String decode2String(String str) throws Exception {
		return new String(Base64.decodeBase64(str));
	}

	/**
	 * 字符串数据编码为BASE64字符串
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(String str) throws Exception {
		return new String(Base64.encodeBase64(str.getBytes()));
	}

	/**
	 * 字节数组编码为BASE64字符串
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(byte[] bytes) throws Exception {
		return new String(Base64.encodeBase64(bytes));
	}

	/**
	 * 将文件编码为BASE64字符串
	 * 大文件慎用，可能会导致内存溢出
	 * 
	 * @param filePath 文件绝对路径
	 * @return
	 * @throws Exception
	 */
	public static String encodeFile(String filePath) throws Exception {
		byte[] bytes = fileToByte(filePath);
		return encode(bytes);
	}

	/**
	 * BASE64字符串转回文件
	 * 
	 * @param filePath 文件绝对路径
	 * @param base64 编码字符串
	 * @throws Exception
	 */
	public static void decodeToFile(String filePath, String base64) throws Exception {
		byte[] bytes = decode2byte(base64);
		byteArrayToFile(bytes, filePath);
	}

	/**
	 * 文件转换为二进制数组
	 * 
	 * @param filePath 文件路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] fileToByte(String filePath) throws Exception {
		byte[] data = new byte[0];
		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				out.write(cache, 0, nRead);
				out.flush();
			}
			out.close();
			in.close();
			data = out.toByteArray();
		}
		return data;
	}

	/**
	 * 二进制数据写文件
	 * 
	 * @param bytes 二进制数据
	 * @param filePath 文件生成目录
	 */
	public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
		InputStream in = new ByteArrayInputStream(bytes);
		File destFile = new File(filePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		destFile.createNewFile();
		OutputStream out = new FileOutputStream(destFile);
		byte[] cache = new byte[CACHE_SIZE];
		int nRead = 0;
		while ((nRead = in.read(cache)) != -1) {
			out.write(cache, 0, nRead);
			out.flush();
		}
		out.close();
		in.close();
	}
}
