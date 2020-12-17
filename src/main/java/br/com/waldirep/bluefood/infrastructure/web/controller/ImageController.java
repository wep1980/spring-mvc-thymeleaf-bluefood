package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.waldirep.bluefood.application.service.ImageService;

/**
 * Controller que vai realizar todas as operações com imagens
 * @author wepbi
 *
 */
@Controller
public class ImageController {

	
	@Autowired
	private ImageService imageService;
	
	
	/**
	 * Metodo generico que retorna qq tipo de imagem -- Para o nevagador e enviado os bytes
	 * @ResponseBody -> (O esta sendo retornado tem que ser colocado no corpo do protocolo ) O conteudo de bytes tem ser enviado no corpo do protocolo http para que o navegador receba essa informação e possa decodificar ela
	 * A Imagem sera acessada atraves de um caminho que não sera na imagem direto e sim em um controller
	 * @return um array de bytes
	 * path = "/images/{path}/{imgName}" -> Path variable (templates de caminhos)
	 * Recebe como parametros -> @PathVariable("type") String type, @PathVariable("imgName") String imgName
	 * produces = MediaType.IMAGE_PNG_VALUE -> para funcionar em qq tipo de navegador
	 */
	@GetMapping(path = "/images/{type}/{imgName}", produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody()
	public byte[] getBytes(
			@PathVariable("type") String type,
			@PathVariable("imgName") String imgName) {
		
		return imageService.getBytes(type, imgName);
	}
}
