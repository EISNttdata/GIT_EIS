package com.dell.dims.dto;

import java.io.File;

public class DimsDTO {
	
	private String inputPath;
	private String outputPath;
	private String templatesPath;
	private String projectName;
		
	public String getInputPath() {
		return inputPath;
	}
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	public String getTemplatesPath() {
		return templatesPath;
	}
	public void setTemplatesPath(String templatesPath) {
		this.templatesPath = templatesPath;
	}

	public String getProjectName() {
		if(inputPath.lastIndexOf(File.separator)!=0)
		{
			projectName=inputPath.substring(inputPath.lastIndexOf(File.separator)+1,inputPath.length());
		}
		return projectName;
	}
}
