package br.com.araujo.birtfs.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.araujo.birtfs.service.GerarService;

@Controller
public class GerarController {

	@Autowired
	private GerarService service;
	
	@GetMapping(value = "/gerar")
	@ResponseBody
	public byte[] gerar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return service.geradorRelatorio(request, response);
	}
}
