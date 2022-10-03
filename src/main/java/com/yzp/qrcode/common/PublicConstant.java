package com.yzp.qrcode.common;

/**
 * 公共常量
 * @author yzp
 * @version 1.0
 * @date 2022/9/5 - 0:10
 */
public interface PublicConstant {

    /**
     * 用户默认存储空间大小50MB
     * B-KB-MB-GB 1024
     */
    long USER_FILE_SPACE_DEFAULT_SIZE = 50*1024*1024L;

    /**
     * 静态资源域名
     */
    String STATIC_RESOURCE_DOMAIN_NAME = "STATIC_RESOURCE_DOMAIN_NAME";

    /**
     * 静态资源域名备份【阿里云OSS对象存储，暂未绑定域名】，绑定域名后这个就可以不使用了
     */
    String STATIC_RESOURCE_DOMAIN_NAME_BACKUP = "STATIC_RESOURCE_DOMAIN_NAME_BACKUP";

    /**
     * Base64图片前缀
     */
    String BASE64_IMAGE_PREFIX = "data:image/jpg;base64,";

    /**
     * 点
     */
    String POINT = ".";

    /**
     * 斜杠
     */
    String SLASH = "/";

    /**
     * win系统文件保存路径
     */
    String WIN_FILE_PATH = "G:/QRCode/static/";

    /**
     * 阿里云对象存储OSS的创建参数
     */
    String OSS_CLIENT_BUILDER = "OSS_CLIENT_BUILDER";

    /**
     * 永久时间
     */
    String PERMANENT_TIME = "9999-01-01 00:00:00";

    /**
     * 日期格式 年月日时分秒
     */
    String DATE_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式 年月日
     */
    String DATE_FORMAT_DATE = "yyyy-MM-dd";

}
