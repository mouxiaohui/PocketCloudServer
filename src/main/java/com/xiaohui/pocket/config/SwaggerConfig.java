package com.xiaohui.pocket.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;

/**
 * OpenAPI 接口文档配置
 *
 * @author xiaohui
 * @since 2025/2/20
 * @see <a href="https://doc.xiaominfo.com/docs/quick-start">knife4j 快速开始</a>
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SwaggerConfig {

    private final Environment environment;

    /**
     * 接口文档信息
     */
    @Bean
    public OpenAPI openApi() {

        String appVersion = environment.getProperty("project.version", "1.0.0");

        return new OpenAPI()
                .info(new Info()
                        .title("Pocket Cloud 接口文档")
                        .description("本文档涵盖Pocket Cloud的所有API接口。")
                        .version(appVersion)
                        .license(new License()
                                .name("Apache License 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0")
                        )
                        .contact(new Contact()
                                .name("xiaohui")
                                .url("https://github.com/mouxiaohui")
                        )
                )
                // 配置全局鉴权参数-Authorize
                .components(new Components()
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                                new SecurityScheme()
                                        .name(HttpHeaders.AUTHORIZATION)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("Bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }


    /**
     * 全局自定义扩展
     */
    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
        return openApi -> {
            // 全局添加Authorization
            if (openApi.getPaths() != null) {
                openApi.getPaths().forEach((path, pathItem) -> {

                    // todo 忽略认证的请求无需携带 Authorization
//                    String[] ignoreUrls = securityProperties.getIgnoreUrls();
//                    if (ArrayUtil.isNotEmpty(ignoreUrls)) {
//                        // Ant 匹配忽略的路径，不添加Authorization
//                        AntPathMatcher antPathMatcher = new AntPathMatcher();
//                        if (Stream.of(ignoreUrls).anyMatch(ignoreUrl -> antPathMatcher.match(ignoreUrl, path))) {
//                            return;
//                        }
//                    }

                    // 其他接口统一添加Authorization
                    pathItem.readOperations()
                            .forEach(operation ->
                                    operation.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                            );
                });
            }
        };
    }

}
