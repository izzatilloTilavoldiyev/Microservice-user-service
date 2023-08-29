package uz.pdp.userservice.config;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configure ModelMapper to retain old values when the source value is null
        modelMapper.getConfiguration().setPropertyCondition(
                new NotNullPropertyCondition()
        );

        return modelMapper;
    }

    private static class NotNullPropertyCondition implements Condition<Object, Object> {
        @Override
        public boolean applies(MappingContext<Object, Object> context) {
            return context.getSource() != null;
        }
    }
}