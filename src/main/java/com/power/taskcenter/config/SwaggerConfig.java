package com.power.taskcenter.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.beust.jcommander.internal.Lists.newArrayList;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
    	// 添加请求参数，我们这里把token作为请求头部参数传入后端
//		ParameterBuilder parameterBuilder = new ParameterBuilder();
//		List<Parameter> parameters = new ArrayList<Parameter>();
//		parameterBuilder.name("token").description("令牌")
//			.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//		parameters.add(parameterBuilder.build());
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
//				.build().globalOperationParameters(parameters);
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.daily.taskcenter.controller"))
                .paths(PathSelectors.any()).build().globalResponseMessage(RequestMethod.POST,
                newArrayList(new ResponseMessageBuilder()
                                .code(500)
                                .message("系统繁忙！")
                                .build(),
                        new ResponseMessageBuilder()
                                .code(200)
                                .message("请求成功!")
                                .build())).globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("系统繁忙！")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(200)
                                        .message("请求成功!")
                                        .build()));
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("接口 Restful API")
                .description("接口 Restful API")
                .termsOfServiceUrl("http://127.0.0.1")
                .version("1.0")
                .build();
    }

}