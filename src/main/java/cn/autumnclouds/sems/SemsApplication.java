package cn.autumnclouds.sems;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Oreki
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.autumnclouds.sems.mapper")
public class SemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemsApplication.class, args);
    }

}
