package br.com.waldirep.bluefood.infrastructure.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestauranteRepository;

/**
 * Helper - Design pattern (Padrão de projeto) - Centraliza o codigo que serve para varios controllers ao mesmo tempo
 * @author wepbi
 *
 */
public class ControllerHelper {
	
	/**
	 * Metodo que indica se é edição ou inserção.
	 * Metodo que pode ser utilizado nos controllers.
	 * True = Editar
	 * false = Inserir
	 * static -> o metodo sera acessivel diretamente na classe sem a necessidade de criar um objeto
	 * @param model
	 * @param isEdit
	 */
	public static void setEditMode(Model model, boolean isEdit) {
		model.addAttribute("editMode", isEdit);
	}
	
	
	/**
	 * Metodo que retorna todas as categorias, como e um metodo que vai ser utilizado em varios pontos do projeto ele esta sendo centralizado aqui.
	 * Ordenando as categorias por ordem crescente de nome
	 * @param repository
	 * @param model
	 */
	public static void addCategoriasToRequest(CategoriaRestauranteRepository repository, Model model) {
		
		List<CategoriaRestaurante> categorias = repository.findAll(Sort.by("nome")); // Carregando a lista de categorias e ordenando por nome ascendente
		model.addAttribute("categorias", categorias);
	}

}
