package br.com.waldirep.bluefood.infrastructure.web.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.waldirep.bluefood.util.FormatUtils;

@Component
public class BigDecimalToStringConverter implements Converter<BigDecimal, String>{

	
	
	
	@Override
	public String convert(BigDecimal source) {
		
		return FormatUtils.formatCurrency(source);
	}


}
