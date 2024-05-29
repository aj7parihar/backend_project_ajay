package in.aj7parihar.product_service_class_110524.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Need to tell Spring that this is a special class hence annotating this
public class ApplicationConfiguration {

    @Bean // I am telling Spring that this method is special hence creating a Bean of this
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper createModelMapper(){
        // Model Mapper is used for Entity To DTO Conversion for a Spring REST API.
        // https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
        return new ModelMapper();
    }
}
