package com.wang.crm;

import jakarta.servlet.Filter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.DefaultSecurityFilterChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@MapperScan("com.wang.crm.Mapper")
public class CrmApplication {
	// 缓存 被换成redis了
//	public static final Map<String, Object> cache = new HashMap<>();

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(CrmApplication.class, args);
//		DefaultSecurityFilterChain bean = run.getBean(DefaultSecurityFilterChain.class);
//		List<Filter> filters = bean.getFilters();
//		filters.forEach(filter -> System.out.println(filter.getClass().getSimpleName()));
	}

}
