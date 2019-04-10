package cn.edu.imut.jm.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ServletComponentScan
@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@EnableDiscoveryClient

@EnableFeignClients

@MapperScan("cn.edu.imut.jm.fair.*.repo.dao.**")
public class JmUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmUserApplication.class, args);
	}

}
