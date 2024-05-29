package jpa.data_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing //Auditing : data jpa 활용
@SpringBootApplication
public class DataJpaApplication {
	@Bean
	public AuditorAware<String> auditorProvider(){
		//AuditorAware : spring bean으로 등록 -> 등록자, 수정자 처리
		return () -> Optional.of(UUID.randomUUID().toString());
	}

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

}
