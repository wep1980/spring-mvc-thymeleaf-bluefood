package br.com.waldirep.bluefood.application.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.waldirep.bluefood.util.IOUtils;

@Service
public class ImageService {
	
	
	/**
	 * Diretirio para logotipos
	 */
	@Value("${bluefood.files.logotipo}") // Propriedade criada no application.properties
	private String logotiposDir; // Atributo de servi�o
	
	
	/**
	 * Diretirio para categorias
	 */
	@Value("${bluefood.files.categoria}") // Propriedade criada no application.properties
	private String categoriasDir; // Atributo de servi�o
	
	
	/**
	 * Diretirio para comidas
	 */
	@Value("${bluefood.files.comida}") // Propriedade criada no application.properties
	private String comidasDir; // Atributo de servi�o
	
	
	
	
	public void uploadLogotipo(MultipartFile multipartFile, String fileName) {
		try {
			IOUtils.copy(multipartFile.getInputStream(), fileName, logotiposDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	
	public void uploadComida(MultipartFile multipartFile, String fileName) {
		try {
			IOUtils.copy(multipartFile.getInputStream(), fileName, comidasDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}

	
	
	/**
	 * Metodo que verifica o tipo da imagem, verifica qual diretorio esta a imagem, pega a imagem trasnforma em um array[] de bytes e retorna o array de bytes para o ImageController
	 * @param type -> categoria, comida ou logotipo
	 * Como e um servi�o n�o sera lan�ada uma IOException e sim ApplicationServiceException
	 * @return
	 */
	public byte[] getBytes(String type, String imgName) {
		
		try {
			
		String dir;
		
		if("comida".equals(type)) {
			dir = comidasDir;
			
		}else if("logotipo".equals(type)) {
			dir = logotiposDir;
			
		}else if("categoria".equals(type)) {
			dir = categoriasDir;
			
		}else {
			throw new Exception(type + "Não é um tipo de imagem válido");
		}
		
		return IOUtils.getBytes(Paths.get(dir, imgName)); // Vai vir do banco de dados
		
		} catch (Exception e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
