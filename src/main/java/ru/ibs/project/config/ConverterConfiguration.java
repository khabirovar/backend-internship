package ru.ibs.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ConverterConfiguration {

    private final Set<Converter<?, ?>> converters;
    private final ConfigurableConversionService conversionService;

    @Autowired
    public ConverterConfiguration(
            Set<Converter<?, ?>> converters,
            @Qualifier("mvcConversionService") ConfigurableConversionService conversionService) {
        this.converters = converters;
        this.conversionService = conversionService;
    }

    @EventListener(classes = ContextRefreshedEvent.class)
    public void onApplicationEvent() {
        converters.forEach(conversionService::addConverter);
    }
}