package it.fabrick.bankaccount.common.util;

import java.util.EnumSet;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.spi.MappingContext;
/**
 * //I create this configuration class for mapping from Entities to Dto class but if not used, must be deleted
 * 
 * @author Gueye Nouroudine
 *
 */
@Configuration
public class ModelMapperConfig {
	@Bean
	private static ModelMapper configureModelMapper() {
		ModelMapper toRet = new ModelMapper();
		toRet.addConverter( new Converter<EnumSet<?>, EnumSet<?>>() {
				@Override
				public EnumSet<?> convert(MappingContext<EnumSet<?>, EnumSet<?>> context) {
				    Object source = context.getSource();
				    if (source == null)
				      return null;
				    return EnumSet.copyOf((EnumSet<?>) source);
				}
		} );
		toRet.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		toRet.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		return toRet;
	}
	
	public ModelMapperConfig() { }

	private static final ModelMapper MODEL_MAPPER = configureModelMapper();
	
	public static <T, S> T map(Object o, Class<T> clazz, PropertyMap<S, T> property) {
		ModelMapper modelMapper = configureModelMapper();
		modelMapper.addMappings(property);
		return modelMapper.map(o, clazz);
	}
	
	public static <T> T map(Object o, Class<T> clazz) {
		return MODEL_MAPPER.map(o, clazz);
	}

	public static <T> T mapWithNulls(Object o, Class<T> clazz) {
		if(o==null) {
			return null;
		}
		return MODEL_MAPPER.map(o, clazz);
	}

	public static void map(Object source, Object destination) {
		MODEL_MAPPER.map(source, destination);
	}
}
