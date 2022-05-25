package com.starnft.star.domain.support.process.helper;

import com.starnft.star.common.utils.SpringUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestTemplateHelper {


    /**
     * 自定义请求头
     * 简单的 Get请求
     *
     * @param headers 参数
     * @param url     参数
     * @return 返回
     */
    public static ResponseEntity<String> simpleGet(HttpHeaders headers, String url) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        //将请求头部和参数合成一个请求
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, String.class);
    }

    /**
     * 表单 - Query
     * 带参数 Get请求
     *
     * @param headers 参数
     * @param url     参数
     * @param params  参数
     * @return 返回
     */
    public static ResponseEntity<String> executeGetParam(HttpHeaders headers, String url, Map<String, String> params) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        //将请求头部和参数合成一个请求
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, String.class);
    }


    /**
     * 自定义请求头
     * - Query
     * - Body
     * 简单的 Post 请求
     *
     * @param headers 参数
     * @param url     参数
     * @return 返回
     */
    public static ResponseEntity<String> simplePost(HttpHeaders headers, String url) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        //将请求头部和参数合成一个请求
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        return restTemplate.postForEntity(url, httpEntity, String.class);
    }


    /**
     * 表单 - Query
     * 带参数的 Post 请求
     *
     * @param headers 参数
     * @param url     参数
     * @param params  参数
     * @return 返回
     */
    public static ResponseEntity<String> executePostFromParam(HttpHeaders headers, String url, Map<String, String> params) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        return restTemplate.postForEntity(url, getMultiValueMap(headers, null, params), String.class);
    }

    /**
     * 表单 - Query
     * 上传单个文件的 Post 请求
     *
     * @param headers 参数
     * @param url     参数
     * @param file    参数
     * @return 返回
     */
    public static ResponseEntity<String> executePostFromFile(HttpHeaders headers, String url, File file) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        List<File> files = new ArrayList<>();
        files.add(file);
        return restTemplate.postForEntity(url, getMultiValueMap(headers, files, null), String.class);
    }

    /**
     * 表单 - Query
     * 上传多个文件的 Post 请求
     *
     * @param headers 参数
     * @param url     参数
     * @param files   参数
     * @return 返回
     */
    public static ResponseEntity<String> executePostFromFile(HttpHeaders headers, String url, List<File> files) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        return restTemplate.postForEntity(url, getMultiValueMap(headers, files, null), String.class);
    }

    /**
     * 表单 - Query
     * 上传单个文件且带参数的 Post 请求
     *
     * @param headers 参数
     * @param url     参数
     * @param file    参数
     * @param params  参数
     * @return 返回
     */
    public static ResponseEntity<String> executePostFromFileParam(HttpHeaders headers, String url, File file, Map<String, String> params) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        List<File> files = new ArrayList<>();
        files.add(file);
        return restTemplate.postForEntity(url, getMultiValueMap(headers, files, params), String.class);
    }

    /**
     * 表单 - Query
     * 上传多个文件且带参数的 Post 请求
     *
     * @param headers 参数
     * @param url     参数
     * @param files   参数
     * @param params  参数
     * @return 返回
     */
    public static ResponseEntity<String> executePostFromFileParam(HttpHeaders headers, String url, List<File> files, Map<String, String> params) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        return restTemplate.postForEntity(url, getMultiValueMap(headers, files, params), String.class);
    }

    private static HttpEntity<MultiValueMap<String, Object>> getMultiValueMap(HttpHeaders headers, List<File> files, Map<String, String> params) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        if (files != null && files.size() > 0) {
            headers.set("Content-Type", "multipart/form-data");
            for (File file : files) {
                multiValueMap.add("file", new org.springframework.core.io.FileSystemResource(file));
            }
        } else {
            headers.set("Content-Type", "application/x-www-form-urlencoded");
        }
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                multiValueMap.add(entry.getKey(), entry.getValue());
            }
        }
        return new HttpEntity<>(multiValueMap, headers);
    }


    /**
     * Body请求 - Body
     *
     * @param headers 参数
     * @param url     参数
     * @return 返回
     */
    public static ResponseEntity<String> executePostBody(HttpHeaders headers, String url) {
        return executePostBodyParam(headers, url, null);
    }

    /**
     * Body请求 - Body
     *
     * @param headers 参数
     * @param url     参数
     * @param str     参数
     * @return 返回
     */
    public static ResponseEntity<String> executePostBodyParam(HttpHeaders headers, String url, String str) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        if (headers.get("Content-Type") == null) {
            headers.set("Content-Type", "application/json;charset=UTF-8");
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(str, headers);
        return restTemplate.postForEntity(url, httpEntity, String.class);
    }


    /**
     * Put请求 - Body
     *
     * @param headers 参数
     * @param url     参数
     * @param str     参数
     * @return 返回
     */
    public static ResponseEntity<String> executePutBodyParam(HttpHeaders headers, String url, String str) {
        RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);
        headers.set("Content-Type", "application/json;charset=UTF-8");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<String> httpEntity = new HttpEntity<>(str, headers);
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.PUT, httpEntity, String.class);
    }


}
