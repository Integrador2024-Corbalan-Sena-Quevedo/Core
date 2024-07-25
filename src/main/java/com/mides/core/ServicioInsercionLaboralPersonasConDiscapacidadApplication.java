package com.mides.core;

import com.mides.core.controller.FileAttachmentController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement
public class ServicioInsercionLaboralPersonasConDiscapacidadApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ServicioInsercionLaboralPersonasConDiscapacidadApplication.class, args);
		//SpringApplication.run(FileAttachmentController.class, args);
	}

}
