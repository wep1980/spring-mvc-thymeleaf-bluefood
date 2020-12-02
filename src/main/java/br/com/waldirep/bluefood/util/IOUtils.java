package br.com.waldirep.bluefood.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class IOUtils {

	
	/**
	 * M�todo que faz a copia
	 * 
	 * @param in -> Entrada, de onde e lido os bytes que ser�o gravados
	 * @param fileName -> nome do arquivo onde os bytes ser�o gravados
	 * @param outputDir -> diretorio onde o arquivo vai ficar
	 * @throws IOException 
	 */
	public static void copy(InputStream in, String fileName, String outputDir) throws IOException {
		Files.copy(in, Paths.get(outputDir, fileName), StandardCopyOption.REPLACE_EXISTING);
	}
}
