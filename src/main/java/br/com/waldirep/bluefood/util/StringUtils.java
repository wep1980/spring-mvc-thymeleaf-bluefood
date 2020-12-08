package br.com.waldirep.bluefood.util;

import java.util.Collection;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StringUtils {
	
	
	/**
	 * Metodo que verifica se a string esta nula ou com espa�os vazios
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str == null) {
			return true;
		}
		// Retorna string vazia ate se for espa�os em branco
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
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder.encode(rawString);
	
		}
	
	
	/**
	 * M�todo que recebe qq tipo de cole��o de strings que concatena e separa por virgulas.
	 * A virgula � concatenada antes do proximo, assim evita que o ultimo elemento contenha virgula.
	 * @param strings
	 * @return
	 */
	public static String concatenate(Collection<String> strings) {
		
		if(strings == null || strings.size() == 0) {
			return null;
		}
		
		// Objeto que concatena strings, evita ficar recriando strings na memoria
		StringBuilder sb = new StringBuilder();
		String delimiter = ", ";
		boolean first = true; 
		
		for (String string : strings) {
			if(!first) { // Se n�o for o primeiro elemento concatena uma virgula
				sb.append(delimiter);
			}
			sb.append(string);
			first = false;
		}
		return sb.toString();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
