package br.com.waldirep.bluefood.infrastructure.web.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import br.com.waldirep.bluefood.util.FileType;

/**
 * Classe que contem a logica de validação de upload 
 * 
 * UploadConstraint -> nome da anotação
 * @author wepbi
 *
 */
public class UploadValidator implements ConstraintValidator<UploadConstraint, MultipartFile>{

	
	private List<FileType> acceptedFileTypes;
	
	
	
	/**
	 * Método de inicialização. recebe a constraint criada 
	 */
	@Override
	public void initialize(UploadConstraint constraintAnnotation) {
		acceptedFileTypes = Arrays.asList(constraintAnnotation.acceptedTypes()); // Convertendo de array para lista
	}



	/**
	 * Metodo que valida se o MultipartFile e valido ou não
	 */
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		if(multipartFile == null) {
			return true; // Deixa passar a validação pq em algumas situações esse metodo sera chamado antes do arquivo ser recebido
		}
		
		for(FileType fileType : acceptedFileTypes) {
			if(fileType.sameOf(multipartFile.getContentType())) {
				return true;
			}
		}
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
