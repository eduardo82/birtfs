package br.com.araujo.birtfs.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GerarService {
	
	private static final String REPORT = "report";
	private static final String EXTENSAO_RPT = ".rptdesign";
	
	@Value("${aplication.diretorio.relatorios}")
	private String diretorioRelatorios;
	
	public byte[] geradorRelatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return gerarRelatorio(buscaNomeRelatorio(request), montaParametrosRelatorio(request));
	}
	
	private byte[] gerarRelatorio(String nomeRelatorio, Map<String, Object> birtParams) {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        EngineConfig config = new EngineConfig();
	        File resource = new File(diretorioRelatorios + nomeRelatorio + EXTENSAO_RPT);

	        Platform.startup(config);
            IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            IReportEngine engine = factory.createReportEngine(config);
	        
            IReportRunnable reportDesign = engine.openReportDesign(new FileInputStream(resource)); 
            
            IRunAndRenderTask task = engine.createRunAndRenderTask(reportDesign);
        	task.setParameterValues(birtParams);
            	
            PDFRenderOption options = new PDFRenderOption();
            options.setOutputFormat("pdf");
            options.setOutputStream(stream);

            task.setRenderOption(options);

            task.run();
            task.close();
            engine.destroy();
            
            return stream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Platform.shutdown();
		}
		
		return null;
	}
	
	private String buscaNomeRelatorio(HttpServletRequest request) {
		return request.getParameter(REPORT);
	}

	private Map<String, Object> montaParametrosRelatorio(HttpServletRequest request) {
		Map<String, Object> parametros = new HashMap<>();
		Set<String> chaves = request.getParameterMap().keySet();
		
		for (String chave : chaves) {
			parametros.put(chave, request.getParameter(chave));
		}
		
		parametros.remove(REPORT);
		return parametros;
	}
}
