package br.com.waldirep.bluefood.util;

public class StringUtils {
	
	
	/**
	 * Metodo que verifica se a string esta nula ou com espaços vazios
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str == null) {
			return true;
		}
		// Retorna string vazia ate se for espaços em branco
		return str.trim().length() == 0;
	}
	
	
	/**
	 * Metodo que criptografa a senha
	 * 
	 * @param rawString -> String pura
	 * @return
	 */
	public static String encrypt(String rawString) {
		if(isEmpty(rawString)) {
			return null;
		}
		//PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//return encoder.encode(rawString);
		return rawString;
		}

}
