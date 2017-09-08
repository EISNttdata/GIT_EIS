package com.dell.dims.controller;

import com.dell.dims.dto.DimsDTO;
import com.dell.dims.service.DimsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class DimsController {

	@RequestMapping("/submitNew")
	public String migrationPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DimsDTO dimsDTO = new DimsDTO();
		String source_path = request.getParameter("source_text");
		dimsDTO.setInputPath(source_path);
		String target_path = request.getParameter("target_text");
		dimsDTO.setOutputPath(target_path);
		String templates_path = request.getParameter("templates_text");
		dimsDTO.setTemplatesPath(templates_path);
		DimsServiceImpl dimsServiceImpl = new DimsServiceImpl();
	//	System.out.println("source path is:::::::" + source_path);
	//	System.out.println("target path is:::::::" + target_path);
		String retVal=dimsServiceImpl.process(dimsDTO);
		if(retVal.equalsIgnoreCase("ERROR"))
		{
			return "eis/error.html";
		}
		System.out.println("source path is::::" + source_path);
		System.out.println("target path is::::" + target_path);
		//return "/success.html";
		return "eis/success.html";
	}

}
