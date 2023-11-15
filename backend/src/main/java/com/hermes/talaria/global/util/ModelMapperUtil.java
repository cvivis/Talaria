package com.hermes.talaria.global.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {
	private static final ModelMapper modelMapper = new ModelMapper();

	public static ModelMapper getModelMapper() {
		return modelMapper;
	}
}
