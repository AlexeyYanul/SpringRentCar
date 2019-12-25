package itacademy.configuration;

import itacademy.converter.dozer.LocalDateTimeConverter;
import itacademy.dto.request.RentRequestDTO;
import itacademy.dto.request.UserFinesRequestDTO;
import itacademy.dto.response.RentResponseDTO;
import itacademy.dto.response.UserFinesResponseDTO;
import itacademy.model.RentCar;
import itacademy.model.UserFines;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig {
    @Bean
    public Mapper mapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        List<CustomConverter> converters = new ArrayList<>();
        converters.add(new LocalDateTimeConverter());
        dozerBeanMapper.setCustomConverters(converters);
        dozerBeanMapper.addMapping(mappingBuilder());
        return dozerBeanMapper;
    }

    private BeanMappingBuilder mappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(UserFines.class, UserFinesResponseDTO.class)
                        .fields("date", "date",
                                FieldsMappingOptions.customConverter(LocalDateTimeConverter.class));
                mapping(UserFinesRequestDTO.class, UserFines.class)
                        .fields("date", "date",
                                FieldsMappingOptions.customConverter(LocalDateTimeConverter.class));
                mapping(RentCar.class, RentResponseDTO.class)
                        .fields("startDate", "startDate",
                                FieldsMappingOptions.customConverter(LocalDateTimeConverter.class))
                        .fields("finishDate", "finishDate",
                                FieldsMappingOptions.customConverter(LocalDateTimeConverter.class));
                mapping(RentRequestDTO.class, RentCar.class)
                        .fields("startDate", "startDate",
                                FieldsMappingOptions.customConverter(LocalDateTimeConverter.class))
                        .fields("finishDate", "finishDate",
                                FieldsMappingOptions.customConverter(LocalDateTimeConverter.class));
            }
        };
    }
}
