package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.ui.Model;

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

}
