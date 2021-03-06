
/**
Funcao que não permite digitar letras
 */
function isNumberKey(evt){
	var charCode = (evt.which) ? evt.which : evt.keyCode; // identifica a propriedade do navegador para capturar a tecla pressionada(para funcionar em todos os navegadores)
	
	if(charCode >= 48 && charCode <= 57 || charCode <= 31){ // pesquisar tabela ASC
		return true;
	}
	return false;
}

/**
Função javaScript que define o tipo de busca que fera feita
 */
function searchRes(categoriaId){
	
	var t = document.getElementById("searchType"); // Captura o campo da tela
	
	// Se foi passado uma categoriaId a busca e por categoria, se a busca for pelo campo de texto é texto
	if(categoriaId == null){
		t.value = "Texto"; // Texto -> enum da classe SearchType
	} else {
		t.value = "Categoria"; // Categoria -> enum da classe SearchType
		document.getElementById("categoriaId").value = categoriaId;
	}
	
	document.getElementById("form").submit();
}


/**
Função que coloca o valor do cmd dentro do campo <input type="hidden" id="cmd" name="cmd"> e submeti o formulario
 */
function setCmd(cmd){
	
	document.getElementById("cmd").value = cmd;
	document.getElementById("form").submit();
}


/**
Função que pega o id="categoria" e atribui ao value o valor da categoria passado como parametro
 */
function filterCardapio(categoria){
	document.getElementById("categoria").value = categoria;
	document.getElementById("filterForm").submit();
}