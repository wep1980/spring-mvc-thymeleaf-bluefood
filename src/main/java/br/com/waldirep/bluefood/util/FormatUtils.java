package br.com.waldirep.bluefood.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {

	
	
	private static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");
	
    
	/**
	 * NumberFormat -> formatador numerico do java
	 * @return
	 */
	public static NumberFormat newCurrencyFormat() {
		
		NumberFormat nf = NumberFormat.getNumberInstance(LOCALE_BRAZIL);
		
		nf.setMaximumFractionDigits(2); // 2 casas decimais
		nf.setMinimumFractionDigits(2); // 2 casas decimais
		nf.setGroupingUsed(false); // Não sera usado os separadores de milhar
		
		return nf;
	}
	
	
	/**
	 * Metodo que transforma um BigDecimal em String
	 * @param number
	 * @return
	 */
	public static String formatCurrency(BigDecimal number) {
		
		if(number == null) {
			return null;
		}
		
		return newCurrencyFormat().format(number);
	}

}
