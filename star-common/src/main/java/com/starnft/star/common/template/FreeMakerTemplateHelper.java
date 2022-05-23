package com.starnft.star.common.template;

import com.starnft.star.common.utils.FreeMakerHelper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

/**
 * FreeMaker模板处理
 *
 * 参数处理模板化 dataModel 为实体 将实体参数解析到模板中 模板解析好参数生成第三方接口标准报文 通过远程调用领域入口
 * 调用得到返参报文 再通过该工具反解析为固定所需参数进行后面的流程
 *
 * @author admin
 */
@Component
public class FreeMakerTemplateHelper implements TemplateHelper {

    @Override
    public String processWithString(String sourceCode, Map<String, Object> dataModel) throws Exception {
        return FreeMakerHelper.processBySourceCode(sourceCode, dataModel);
    }

    @Override
    public String process(String templateName, Map<String, Object> dataModel) throws Exception {
        return FreeMakerHelper.processByTemplates(templateName,dataModel);
    }

    @Override
    public Map<String, Object> buildDataModel(Supplier<Map<String, Object>> dataModelSupplier) {
        return dataModelSupplier.get();
    }

}
