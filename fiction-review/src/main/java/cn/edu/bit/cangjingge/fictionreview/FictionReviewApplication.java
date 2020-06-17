package cn.edu.bit.cangjingge.fictionreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "cn.edu.bit.cangjingge.common.service")
@ComponentScan(basePackages = {"cn.edu.bit.cangjingge.fictionreview", "cn.edu.bit.cangjingge.common.exception"})
public class FictionReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(FictionReviewApplication.class, args);
    }

}
