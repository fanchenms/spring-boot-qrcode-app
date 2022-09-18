package com.yzp.qrcode.vo;

import lombok.Data;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/11 - 19:58
 */
@Data
public class OssClientBuilderVo {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}
