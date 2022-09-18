package com.yzp.qrcode.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.yzp.qrcode.common.PublicConstant;
import com.yzp.qrcode.exception.QrCodeAppException;
import com.yzp.qrcode.service.FileSaveService;
import com.yzp.qrcode.service.SysConfigService;
import com.yzp.qrcode.utils.FileHandleUtil;
import com.yzp.qrcode.vo.OssClientBuilderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/11 - 2:08
 */
@Slf4j
@Service
public class FileSaveOosServiceImpl implements FileSaveService {

    @Resource
    private SysConfigService sysConfigService;

    @Override
    public String save(MultipartFile file) {
        OSS ossClient = null;
        try {
            OssClientBuilderVo ossClientBuilderVo = sysConfigService.getSysConfigObject(PublicConstant.OSS_CLIENT_BUILDER, OssClientBuilderVo.class);
            // Endpoint以华南1（深圳）为例，其它Region请按实际情况填写。
            String endpoint = ossClientBuilderVo.getEndpoint();
            // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
            String accessKeyId = ossClientBuilderVo.getAccessKeyId();
            String accessKeySecret = ossClientBuilderVo.getAccessKeySecret();
            // 填写Bucket名称，例如examplebucket。
            String bucketName = ossClientBuilderVo.getBucketName();

            // 创建 OSSClient 实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String prefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            // 填写Object完整路径，完整路径中不能包含Bucket名称，例如 exampledir/exampleobject.txt。
            String filePath = FileHandleUtil.getFileName(originalFilename, prefix, null);

            // 上传云端
            ossClient.putObject(bucketName, filePath, file.getInputStream());
            return filePath;
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message: {}" + oe.getErrorMessage());
            log.error("Error Code: {}" + oe.getErrorCode());
            log.error("Request ID: {}" + oe.getRequestId());
            log.error("Host ID: {}" + oe.getHostId());
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "上传文件失败");
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message: {}" + ce.getMessage());
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "上传文件失败");
        } catch (Exception e) {
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "上传文件失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
