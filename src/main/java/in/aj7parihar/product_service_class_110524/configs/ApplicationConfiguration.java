package in.aj7parihar.product_service_class_110524.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Need to tell Spring that this is a special class hence annotating this

/* Diff. b/w @Configuration and @Component -
    @Component:
        Think of @Component as a worker in a factory.
        You tell the factory (Spring) to hire this worker (your class) to do a specific job.

    @Configuration:
        Imagine @Configuration as the factory’s blueprint where you define which workers to hire,
        what tools they need, and how they should work together.

        Automatically picked up during ComponentScanning process since it is a specialized
        version of @Component annotation.
 */


public class ApplicationConfiguration {

    @Bean // I am telling Spring that this method is special hence create a Bean of this
    // and store it in <<ApplicationContext>> or IoC container.
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper createModelMapper(){
        // Model Mapper is used for Entity To-and-from DTO Conversion for a Spring REST API.
        // https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
        return new ModelMapper();
    }
}
