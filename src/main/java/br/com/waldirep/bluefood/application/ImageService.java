package br.com.waldirep.bluefood.application;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.waldirep.bluefood.util.IOUtils;

@Service
public class ImageService {
	
	
	@Value("${bluefood.files.logotipo}")
	private String logotiposDir; // Atributo de servi�o
	
	public void uploadLogotipo(MultipartFile multipartFile, String fileName) {
		try {
			IOUtils.copy(multipartFile.getInputStream(), fileName, logotiposDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}

}
