package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.IAutorRepository;
import com.alura.literalura.repository.IIdiomaRepository;
import com.alura.literalura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	IAutorRepository repoAutor;
	@Autowired
	ILibroRepository repoLibro;
	@Autowired
	IIdiomaRepository repoIdioma;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repoLibro, repoAutor, repoIdioma);
		principal.muestraElMenu();


	}
}
