package io.github.vwenx.mpext;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("io.github.vwenx.mpext.mapper")
@SpringBootApplication
public class MybatisPlusExtTestApplication {


	public static void main(String[] args) {
		System.out.println("启动...");
		SpringApplication.run(MybatisPlusExtTestApplication.class, args);
	}

}
