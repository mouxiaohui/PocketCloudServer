package com.xiaohui.pocket.system.constants;

/**
 * @author xiaohui
 * @since 2025/2/28
 */
public interface FileConstants {

    /**
     * 顶级父文件ID
     */
    Long TOP_PARENT_ID = 0L;

    /**
     * 根文件夹名称
     */
    String ROOT_FOLDER_NAME = "全部文件";

    /**
     * 所有文件类型的标识
     */
    String ALL_FILE_TYPE = "-1";

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

    String GB2312_STR = "GB2312";

    String IOS_8859_1_STR = "ISO-8859-1";

}
