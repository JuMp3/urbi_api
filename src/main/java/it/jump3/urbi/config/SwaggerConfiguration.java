package it.jump3.urbi.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${swagger.urbi.title}")
    private String swaggerLoginTitle;

    @Value("${swagger.urbi.title.description}")
    private String swaggerLoginTitleDescription;

    @Value("${swagger.urbi.groupname.v1}")
    private String swaggerLoginGroupNamev1;

    @Value("${swagger.urbi.groupname.v2}")
    private String swaggerLoginGroupNamev2;

    /*
     * gestione API Swagger
     */
    @Bean
    public Docket newsLoginApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                //.globalOperationParameters(getHeaderParams())
                .groupName(swaggerLoginGroupNamev1)
                .apiInfo(apiInfov1())
                .select()
                .apis(RequestHandlerSelectors.basePackage("it.jump3.urbi"))
                .paths(Predicates.or(regex("/v1.*")))
                .paths(Predicates.not(regex("/error.*")))
                .paths(Predicates.not(regex("/hub.*")))
                .paths(Predicates.not(regex("/actuator.*")))
                .build();
    }

    private ApiInfo apiInfov1() {
        return new ApiInfoBuilder()
                .title(swaggerLoginTitle)
                .description(swaggerLoginTitleDescription)
                .version("v1")
                .build();
    }

    @Bean
    public Docket newsLoginApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(swaggerLoginGroupNamev2)
                .apiInfo(apiInfov2())
                .select()
                .apis(RequestHandlerSelectors.basePackage("it.jump3.urbi"))
                .paths(Predicates.or(regex("/v2.*")))
                .paths(Predicates.not(regex("/error.*")))
                .paths(Predicates.not(regex("/hub.*")))
                .paths(Predicates.not(regex("/actuator.*")))
                .build();
    }

    private ApiInfo apiInfov2() {
        return new ApiInfoBuilder()
                .title(swaggerLoginTitle)
                .description(swaggerLoginTitleDescription)
                .version("v2")
                .build();
    }
}
