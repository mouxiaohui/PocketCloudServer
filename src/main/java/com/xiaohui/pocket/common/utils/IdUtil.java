package com.xiaohui.pocket.common.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import com.xiaohui.pocket.common.exception.BusinessException;
import com.xiaohui.pocket.common.result.ResultCode;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * id 工具类
 *
 * @author xiaohui
 * @since 2025/3/3
 */
public class IdUtil {

    /**
     * 加密ID
     *
     * @param id long 型
     * @return 加密id字符串
     */
    public static String encrypt(Long id) {
        if (Objects.nonNull(id)) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(8);
                byteBuffer.putLong(0, id);
                byte[] content = byteBuffer.array();
                return AES128Util.encrypt(content);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * 解密ID
     *
     * @param decryptId 加密 id 字符串
     * @return long 型的 id
     */
    public static Long decrypt(String decryptId) {
        Assert.notBlank(decryptId, "the decryptId can not be empty");

        byte[] content = new byte[0];
        try {
            content = AES128Util.decrypt(decryptId);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.PARENT_FOLDER_ID_ERROR);
        }

        if (ArrayUtil.isNotEmpty(content)) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(content);
            return byteBuffer.getLong();
        }

        throw new BusinessException(ResultCode.PARENT_FOLDER_ID_ERROR);
    }

}
