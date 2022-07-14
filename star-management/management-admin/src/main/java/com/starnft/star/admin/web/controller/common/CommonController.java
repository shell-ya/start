package com.starnft.star.admin.web.controller.common;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.starnft.star.business.service.ICosSupport;
import com.starnft.star.common.config.RuoYiConfig;
import com.starnft.star.common.constant.Constants;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.utils.StringUtils;
import com.starnft.star.common.utils.file.FileUploadUtils;
import com.starnft.star.common.utils.file.FileUtils;
import com.starnft.star.framework.config.ServerConfig;
import com.starnft.star.admin.web.controller.support.key.ITempKeyObtain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用请求处理
 *
 * @author starnft.star
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Resource
    private ServerConfig serverConfig;

    @Resource
    private ICosSupport cosSupport;

    @Resource
    private ITempKeyObtain iTempKeyObtain;

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @GetMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file,String type) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;

//            TempKey tempKey = iTempKeyObtain.obtainTempKey("banner", getUserId());

//            BasicSessionCredentials cred = new BasicSessionCredentials(tempKey.getTmpSecretId(), tempKey.getTmpSecretKey(), tempKey.getSessionToken());
//
//            Region region = new Region("ap-shanghai");
//            ClientConfig clientConfig = new ClientConfig(region);
//
//            clientConfig.setHttpProtocol(HttpProtocol.https);
//            COSClient cosClient = new COSClient(cred, clientConfig);
//
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(file.getSize());
//            PutObjectResult result = cosClient.putObject("banner-1307551757", "首页", file.getInputStream(), objectMetadata);

            COSClient cosClient = cosSupport.cosClientInit();
            cosSupport.createBucket(type,"/",cosClient);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            PutObjectResult putObjectResult = cosClient.putObject(type +"-1307551757", fileName, file.getInputStream(), objectMetadata);
            URL url1 = cosClient.generatePresignedUrl(type +"-1307551757", fileName, null);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url1.toString());
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;

//            TempKey tempKey = iTempKeyObtain.obtainTempKey("banner", getUserId());

//            BasicSessionCredentials cred = new BasicSessionCredentials(tempKey.getTmpSecretId(), tempKey.getTmpSecretKey(), tempKey.getSessionToken());
//
//            Region region = new Region("ap-shanghai");
//            ClientConfig clientConfig = new ClientConfig(region);
//
//            clientConfig.setHttpProtocol(HttpProtocol.https);
//            COSClient cosClient = new COSClient(cred, clientConfig);
//
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(file.getSize());
//            PutObjectResult result = cosClient.putObject("banner-1307551757", "首页", file.getInputStream(), objectMetadata);

            COSClient cosClient = cosSupport.cosClientInit();
//            cosSupport.createBucket("banner","/",cosClient);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            PutObjectResult putObjectResult = cosClient.putObject("banner-1307551757", fileName, file.getInputStream(), objectMetadata);
            URL url1 = cosClient.generatePresignedUrl("banner-1307551757", fileName, null);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url1.toString());
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }
}
