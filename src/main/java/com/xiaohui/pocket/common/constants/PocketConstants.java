package com.xiaohui.pocket.common.constants;

/**
 * @author xiaohui
 * @since 2025/3/23
 */
public interface PocketConstants {

    /**
     * 公用的字符串分隔符
     */
    String COMMON_SEPARATOR = "__,__";

    String CONTENT_TYPE_STR = "Content-Type";

    /**
     * 文件内容的部署方式
     */
    String CONTENT_DISPOSITION_STR = "Content-Disposition";

    /**
     * 以附件的方式下载
     */
    String CONTENT_DISPOSITION_VALUE_PREFIX_STR = "attachment;fileName=";
    /**
     * 字符编码常量: GB2312
     * <p>
     * 中文汉字字符集国家标准编码，适用于简体中文环境下的字符处理
     * 典型应用场景：中文文本的编码转换、字符集声明等场景
     */
    String GB2312_STR = "GB2312";

    /**
     * 字符编码常量: ISO-8859-1
     * <p>
     * Latin-1 单字节编码标准，覆盖大多数西欧语言字符
     * 典型应用场景：网络协议标准编码、跨平台字节流转换等场景
     */
    String IOS_8859_1_STR = "ISO-8859-1";

}
