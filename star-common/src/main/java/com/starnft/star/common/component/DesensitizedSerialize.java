package com.starnft.star.common.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.starnft.star.common.annotations.Desensitized;
import com.starnft.star.common.enums.SensitiveTypeEnum;
import com.starnft.star.common.utils.DesensitizedUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @Date 2022/6/18 10:46 PM
 * @Author ： shellya
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
public class DesensitizedSerialize extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveTypeEnum sensitiveTypeEnum;

    @Override
    public void serialize(String origin, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (sensitiveTypeEnum){
            case BANK_CARD:jsonGenerator.writeString(DesensitizedUtils.backCard(origin));
            break;
            case ID_CARD:jsonGenerator.writeString(DesensitizedUtils.backCard(origin));
            break;
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Desensitized sensitive = beanProperty.getAnnotation(Desensitized.class);
                if (sensitive == null) {
                    sensitive = beanProperty.getContextAnnotation(Desensitized.class);
                }
                if (sensitive != null) {
                    return new DesensitizedSerialize(sensitive.type());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
