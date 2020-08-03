package com.fh;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.fh.*.mapper")
@EnableSwagger2
public class SpringbootShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShopApiApplication.class, args);

    }

    @EnableTransactionManagement
    @Configuration
    public class Config {
        /**
         * 分页插件
         */
        @Bean
        public PaginationInterceptor paginationInterceptor() {
            return new PaginationInterceptor();
        }
    }


}
