package com.yzp.qrcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzp.qrcode.entity.AttachFileEntity;
import com.yzp.qrcode.vo.UploadVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 上传文件记录表 服务类
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
public interface AttachFileService extends IService<AttachFileEntity> {

    /**
     * 文件上传
     * @param file 文件
     * @param fileName 文件名称
     * @param type 文件类型
     * @return 文件URL与id
     */
    UploadVo upload(MultipartFile file, String fileName, Integer type);

    /**
     * 压缩图片
     *
     * @param file      图片文件
     * @param scale     压缩比例 0~1 0-根据文件大小自动压缩，1-不压缩
     * @param response  响应流
     */
    void compressPicture(MultipartFile file, float scale, HttpServletResponse response);
}
