package com.yzp.qrcode.dao;

import com.yzp.qrcode.entity.AttachFileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 上传文件记录表 Mapper 接口
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
public interface AttachFileDao extends BaseMapper<AttachFileEntity> {

    /**
     * 根据用户获取他已经使用的存储空间大小
     *
     * @param userId 用户id
     * @return 文件存储空间size
     */
    long getFileSizeSumForUser(@Param("userId") Long userId);
}
