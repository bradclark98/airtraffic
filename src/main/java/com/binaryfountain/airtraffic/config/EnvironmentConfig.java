/**
 *
 */
package com.binaryfountain.airtraffic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Profile controlled Config File to handle disabled components such as swagger for testing and upper
 * environments.
 *
 */

@Configuration
@EnableSwagger2
public class EnvironmentConfig {

    @Value(value = "${app.version}")
    private String appVersion;

    @Value(value = "${app.artifactId}")
    private String artifactId;

    /**
     * Creates Docket with default settings for Swagger.
     *
     * @return Docket
     */
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                                                      .select()
                                                      .apis(RequestHandlerSelectors.basePackage("com.binaryfountain.airtraffic.controller"))
                                                      .build();
    }

    /**
     * Creates new ApiInfo for Microservice
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(artifactId)
                                   .description("REST API for " + artifactId)
                                   .version(appVersion)
                                   .build();
    }

}
