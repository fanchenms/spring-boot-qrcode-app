package com.yzp.qrcode.controller;

import com.yzp.qrcode.service.AttachFileService;
import com.yzp.qrcode.utils.R;
import com.yzp.qrcode.vo.UploadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 上传文件记录表 前端控制器
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
@Slf4j
@Api(tags = "文件")
@RestController
@RequestMapping("/file")
public class AttachFileController {

    @Resource
    private AttachFileService attachFileService;

    @ApiOperation(value = "文件上传", notes = "文件上传接口")
    @PostMapping("/upload")
    public R upload(@ApiParam(name = "file", value = "文件", required = true) MultipartFile file,
                    @ApiParam(name = "fileName", value = "文件名称", example = "abc")
                        @RequestParam(value = "fileName", required = false) String fileName,
                    @ApiParam(name = "type", value = "文件 1:图片 2:音频 3:视频 4:其他文件", required = true, example = "1")
                        @RequestParam("type") Integer type) {
        log.info("文件上传 file name {}", file.getOriginalFilename());
        log.info("文件上传 file size {}", file.getSize());
        UploadVo uploadVo = attachFileService.upload(file, fileName, type);
        return R.success().putData(uploadVo);
    }

    @ApiOperation(value = "图片压缩", tags = "图片压缩")
    @PostMapping("/scale")
    public void compressPicture(@ApiParam(name = "file", value = "文件", required = true)
                                    @RequestParam(value = "file") MultipartFile file,
                                @ApiParam(name = "scale", value = "压缩比例 0~1 0-根据文件大小自动压缩，1-不压缩", example = "0")
                                    @RequestParam(value = "scale", required = false, defaultValue = "0") float scale,
                                HttpServletResponse response) {
        log.info("图片压缩 文件：{}", file.getOriginalFilename());
        log.info("图片压缩 文件大小：{}B", file.getSize());
        attachFileService.compressPicture(file, scale, response);
    }

}
