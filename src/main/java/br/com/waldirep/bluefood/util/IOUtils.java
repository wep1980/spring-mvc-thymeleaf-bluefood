package br.com.waldirep.bluefood.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Esse codigo pode ser reaproveitado em outro contexto
 * @author wepbi
 *
 */
public class IOUtils {

	
	/**
	 * Método que faz a copia
	 * 
	 * @param in -> Entrada, de onde e lido os bytes que serão gravados
	 * @param fileName -> nome do arquivo onde os bytes serão gravados
	 * @param outputDir -> diretorio onde o arquivo vai ficar
	 * @throws IOException 
	 */
	public static void copy(InputStream in, String fileName, String outputDir) throws IOException {
		Files.copy(in, Paths.get(outputDir, fileName), StandardCopyOption.REPLACE_EXISTING);
	}
	
	
	/**
	 * Metodo que pega os bytes
	 * @param path -> Variavel que representa o caminho de um arquivo
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(Path path) throws IOException{
		return Files.readAllBytes(path);
	}
}
