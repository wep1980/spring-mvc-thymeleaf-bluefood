package br.com.waldirep.bluefood.infrastructure.web.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.waldirep.bluefood.util.StringUtils;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal>{

	
	
	
	@Override
	public BigDecimal convert(String source) {
		
		if(StringUtils.isEmpty(source)) {
			return null;
		}
		
		/*
		 *  Transforma todas as , em .
		 *  trim() -> Retira os espaços em branco
		 */
		source = source.replace("," , ".").trim();
		
		return BigDecimal.valueOf(Double.valueOf(source));
	}

	
	
	
	


}
