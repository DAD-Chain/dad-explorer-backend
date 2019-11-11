package com.github.dadchain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.SessionAttribute;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean isEnable;
    public static final String DEFAULT_INCLUDE_PATTERN = "/v2.*";

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(HttpSessionConfig.AUTH_TOKEN_HEADER)
                .description("AccessToken token")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        Class[] clazz = {HttpSession.class, HttpServletResponse.class,
                SessionAttribute.class, HttpServletResponse.class};

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(clazz)
                .enable(isEnable)
                .protocols(Collections.singleton("http"))
                .directModelSubstitute(Date.class, Long.class)//将Date类型全部转为Long类型
                .directModelSubstitute(Timestamp.class, Long.class)//将Timestamp类型全部转为Long类型
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.dadchain"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        //联系信息
        Contact contact = new Contact(
                "explorer",
                "https://explorer.dad.one",
                "explorer@dad.one");
        //api基本信息，展示在页面
        return new ApiInfoBuilder()
                .title("dad Explorer APIs")
                .description("This is Ontology explorer apis")
                .termsOfServiceUrl("https://github.com/dad-chain/dad-explorer")
                .contact(contact)
                .version("2.0")
                .build();
    }


}
