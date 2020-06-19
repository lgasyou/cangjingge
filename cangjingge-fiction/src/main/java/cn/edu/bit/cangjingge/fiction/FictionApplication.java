package cn.edu.bit.cangjingge.fiction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "cn.edu.bit.cangjingge.common.service")
@ComponentScan(basePackages = {"cn.edu.bit.cangjingge.fiction", "cn.edu.bit.cangjingge.common"})
public class FictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FictionApplication.class, args);
    }

}
