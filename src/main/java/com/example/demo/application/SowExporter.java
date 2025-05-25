package com.example.demo.application;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * SowExporter
 *
 * @author tl
 * @since 20250522 22:36
 */
public class SowExporter {

	public void export(ProductDetail productDetail, OutputStream fileOutputStream) throws IOException, TemplateException {

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setTemplateLoader(new ClassTemplateLoader(SowExporter.class, "/templates"));
		Template template = cfg.getTemplate("document.ftl");
		template.process(productDetail, new BufferedWriter(new OutputStreamWriter(fileOutputStream)));
	}
}