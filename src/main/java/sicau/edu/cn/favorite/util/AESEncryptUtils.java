package sicau.edu.cn.favorite.util;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 类名称：AESEncryptUtils <br>
 * 类描述: AES加解密工具 <br>
 * 创建人：felicity <br>
 * 创建时间：2017年8月15日 下午4:48:39 <br>
 * 修改人：felicity <br>
 * 修改时间：2017年8月15日 下午4:48:39 <br>
 * 修改备注:
 * @version
 * @see
 */
public class AESEncryptUtils {

	private static final String ALGORITHM = "AES";

	private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

	private final static String CHARSETNAME = "UTF-8";

	/**
	 * 生成随机密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getSecretKey() {
		return getSecretKey(null);
	}

	/**
	 * 生成密钥
	 * 
	 * @param seed 密钥种子
	 * @return
	 * @throws Exception
	 */
	public static String getSecretKey(String seed) {
		try {
			if (seed == null || "".equals(seed.trim())) {
				seed = UUID.randomUUID().toString();
			}
			// 将种子进行加密
			String hexString = DigestUtils.sha1Hex(seed);
			// 截取种子的16位
			String secretKey = hexString.substring(5, 21);

			// 返回base64加密都的字符串
			return Base64Utils.encode(secretKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 转换密钥
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static SecretKeySpec toKey(String key) throws Exception {
		return new SecretKeySpec(Base64Utils.decode2byte(key), ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param key 通过该方法生成 {@link AESEncryptUtils#getSecretKey(String)}
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String source, String key) {
		try {
			SecretKeySpec secretKeySpec = toKey(key);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] data = source.getBytes(CHARSETNAME);
			data = cipher.doFinal(data);
			return Base64Utils.encode(data);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @param key key 通过该方法生成 {@link AESEncryptUtils#getSecretKey(String)}
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String source, String key) {
		try {
			SecretKeySpec secretKeySpec = toKey(key);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] data = Base64Utils.decode2byte(source);
			data = cipher.doFinal(data);
			return new String(data, CHARSETNAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过密钥种子进行加密
	 * 
	 * @param source 要加密数据
	 * @param seed 密钥种子
	 * @return
	 * @throws Exception
	 */
	public static String encryptBySeed(String source, String seed) {
		try {
			SecretKeySpec secretKeySpec = toKey(getSecretKey(seed));
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] data = source.getBytes(CHARSETNAME);
			data = cipher.doFinal(data);
			return Base64Utils.encode(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过密钥种子进行解密
	 * @param source 要加密数据
	 * @param seed 密钥种子
	 * @return
	 * @throws Exception
	 */
	public static String decryptBySeed(String source, String seed) {
		try {
			SecretKeySpec secretKeySpec = toKey(getSecretKey(seed));
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] data = Base64Utils.decode2byte(source);
			data = cipher.doFinal(data);
			return new String(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String seed = "@#@!!%!@";
		String en = AESEncryptUtils.encryptBySeed("{\"name\":\"你好\"}", seed);
		System.out.println(en);
		String souc = AESEncryptUtils.decryptBySeed(en, seed);
		System.out.println(souc);
		
	}
}
