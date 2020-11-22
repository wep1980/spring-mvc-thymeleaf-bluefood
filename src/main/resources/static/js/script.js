
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
