package com.starnft.star.common.template;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 模板处理接口
 * @author admin
 *
 */
public interface TemplateHelper {

	/**
	 * 模板处理
	 * @param sourceCode freemarker 串
	 * @param dataModel
	 * @return
	 * @throws Exception 
	 */
	public String processWithString(String sourceCode,Map<String, Object> dataModel) throws Exception;


	/**
	 * 模板处理
	 * @param templateName 配置的ftl文件名 带后缀
	 * @param dataModel
	 * @return
	 * @throws Exception
	 */
	public String process(String templateName,Map<String, Object> dataModel) throws Exception;


	/**
	 * 构建 DataModel
	 * @return 【Map<String, Object>】
	 */
	Map<String, Object> buildDataModel(Supplier<Map<String, Object>> dataModelSupplier);
}
