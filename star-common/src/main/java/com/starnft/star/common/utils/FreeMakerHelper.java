package com.starnft.star.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.util.Map;

/**
 * FreeMakerHelper
 *
 **/
public class FreeMakerHelper {

    private FreeMakerHelper() {
    }

    public static final String DEFAULT_PATTERN = "templates/";

    /**
     * 默认路径下templates模板处理
     *
     * @Param: [templateName, dataModel]
     * @Return: java.lang.String
     **/
    public static String processByTemplates(String templateName, Map<String, Object> dataModel) throws Exception {
        return process(DEFAULT_PATTERN + templateName, dataModel);
    }

    /**
     * 模板处理
     *
     * @Param: [templatePath, dataModel]
     * @Return: java.lang.String
     **/
    public static String process(String templatePath, Map<String, Object> dataModel) throws Exception {
        Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        config.setDefaultEncoding("UTF-8");
        config.setNumberFormat("#.####");
        InputStream in = ClassUtils.getDefaultClassLoader().getResourceAsStream(templatePath);
        return process(new InputStreamReader(in), dataModel, config);
    }

    /**
     * 通过报文模板sourceCode处理模板
     *
     * @param sourceCode
     * @param dataModel
     * @return
     * @throws Exception
     */
    public static String processBySourceCode(String sourceCode, Map<String, Object> dataModel) throws Exception {
        Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        config.setDefaultEncoding("UTF-8");
        config.setNumberFormat("#.####");
        return process(new StringReader(sourceCode), dataModel, config);
    }

    /**
     * 模板处理
     *
     * @Param: [reader, dataModel, config]
     * @Return: java.lang.String
     **/
    public static String process(Reader reader, Map<String, Object> dataModel, Configuration config) throws Exception {
        StringWriter out = new StringWriter();
        try {
            Template template = new Template(FreeMakerHelper.class.getSimpleName(), reader, config);
            template.process(dataModel, out);
        } finally {
            IOUtils.closeQuietly(out);
        }
        return out.toString();
    }

}
