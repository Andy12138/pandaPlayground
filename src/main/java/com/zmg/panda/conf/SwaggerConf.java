package com.zmg.panda.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andy
 */
@Configuration
@EnableSwagger2
public class SwaggerConf {

    @Bean
    public Docket AllApi() {
        return createDocket("com.zmg.panda.controller", "全部接口");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("熊猫精神时光屋接口文档")
                .description("write by zhongminggui").build();
    }

    private Docket createDocket(String basePack, String groupName){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(basePack))
                .paths(PathSelectors.any())
                .build()
                .groupName(groupName)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                ;
    }

    private List<ApiKey> securitySchemes() {
        return Collections.singletonList(new ApiKey("Authorization", "auth-token", "header"));
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>(1);
        securityContexts.add(SecurityContext.builder().securityReferences(defualtAuth()).build());
        return securityContexts;
    }

    private List<SecurityReference> defualtAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")};
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }
}
