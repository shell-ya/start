package com.starnft.star.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

/**
 * YmlUtil
 * Yml工具类
 *
 */
@Slf4j
public class YmlUtil {

    private YmlUtil() {
    }

    private static final Yaml YAML = new Yaml();
    private static final String BOOTSTRAP_YML = "bootstrap.yml";
    private static final String APPLICATION_YML = "application.yml";

    /**
     * 获取bootstrap.yml配置内容
     *
     * @param key 键
     * @return 值
     */
    public static Object getBootstrapValue(String key) {
        return getValueByYml(BOOTSTRAP_YML, key);
    }

    /**
     * 获取application.yml配置内容
     *
     * @param key 键
     * @return 值
     */
    public static Object getApplicationValue(String key) {
        return getValueByYml(APPLICATION_YML, key);
    }

    /**
     * 获取yml配置内容
     *
     * @param fileName yml文件名
     * @param key      键
     * @return 值
     */
    public static Object getValueByYml(String fileName, String key) {
        Map<String, Object> map = getYml(fileName);
        if (MapUtils.isEmpty(map)) {
            return null;
        }

        Object result = null;
        String[] keys = key.split("\\.");
        for (String k : keys) {
            Object o = map.get(k);
            if (ObjectUtils.isNotEmpty(o)) {
                if (o instanceof Map && !k.equals(key) && !key.endsWith("." + k)) {
                    map = (Map<String, Object>) o;
                } else {
                    result = o;
                }
            } else {
                result = null;
            }
        }

        return result;
    }

    /**
     * 获取 yml 文件内容
     *
     * @param fileName yml文件名
     * @return
     */
    public static Map<String, Object> getYml(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        try (InputStream inputStream = YmlUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (ObjectUtils.isEmpty(inputStream)) {
                return null;
            }
            Yaml yaml = new Yaml();
            return yaml.loadAs(inputStream, Map.class);
        } catch (IOException e) {
            log.error("IO流处理失败", e);
        }
        return null;
    }

    /**
     * parserYaml
     *
     * @Param: [file, clazz]
     * @Return: T
     * @Date: 2022/5/12
     **/
    public static <T> T parseYaml(File file, Class<T> clazz) {
        try {
            return parseYaml(new FileInputStream(file), clazz);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * parserYaml
     *
     * @Param: [input, clazz]
     * @Return: T
     * @Date: 2022/5/12
     **/
    public static <T> T parseYaml(InputStream input, Class<T> clazz) {
        return YAML.loadAs(input, clazz);
    }


    public static <T> T parseYaml(String input, Class<T> clazz) {
        return YAML.loadAs(input, clazz);
    }


    public static <T> T parseYaml(Reader io, Class<T> clazz) {
        return YAML.loadAs(io, clazz);
    }

}
