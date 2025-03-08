package com.xiaohui.pocket.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xiaohui.pocket.system.constants.FileConstants;
import jakarta.activation.MimetypesFileTypeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.Objects;

/**
 * 公用的文件工具类
 *
 * @author xiaohui
 * @since 2025/3/3
 */
@Slf4j
public class FileUtils {

    /**
     * 获取文件的后缀
     *
     * @param filename 文件名
     * @return 文件的后缀
     */
    public static String getFileSuffix(String filename) {
        if (StringUtils.isBlank(filename) || filename.lastIndexOf('.') == -1) {
            return StringUtils.EMPTY;
        }
        return filename.substring(filename.lastIndexOf('.')).toLowerCase();
    }

    /**
     * 获取文件的content-type
     *
     * @param filePath 文件路径
     * @return 文件的content-type
     */
    public static String getContentType(String filePath) {
        // 利用nio提供的类判断文件ContentType
        File file = new File(filePath);
        String contentType = null;
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            log.error("getContentType error: {}", e.getMessage());
        }
        //若失败则调用另一个方法进行判断
        if (StringUtils.isBlank(contentType)) {
            contentType = new MimetypesFileTypeMap().getContentType(file);
        }
        return contentType;
    }

    /**
     * 创建文件
     * <p>
     * 包含父文件一起创建
     *
     * @param targetFile 目标文件
     */
    public static void createFile(File targetFile) throws IOException {
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        targetFile.createNewFile();
    }

    /**
     * 生成默认的文件存储路径
     * <p>
     * 生成规则：当前登录用户的文件目录 + pocket
     *
     * @return 默认的文件存储路径
     */
    public static String generateDefaultStoreFileRealPath() {
        return System.getProperty("user.home") + File.separator + "pocket";
    }

    /**
     * 生成文件分片的存储路径
     * <p>
     * 生成规则：基础路径 + 年 + 月 + 日 + 唯一标识 + 随机的文件名称 + __,__ + 文件分片的下标
     *
     * @param basePath    基础路径
     * @param identifier  唯一标识
     * @param chunkNumber 文件分片的下标
     * @return 文件分片的存储路径
     */
    public static String generateStoreFileChunkRealPath(String basePath, String identifier, Integer chunkNumber) {
        return basePath + File.separator + DateUtil.thisYear() + File.separator + (DateUtil.thisMonth() + 1) + File.separator + DateUtil.thisDayOfMonth() + File.separator + identifier + File.separator + IdUtil.randomUUID() + FileConstants.COMMON_SEPARATOR + chunkNumber;
    }

    /**
     * 生成默认的文件分片的存储路径前缀
     *
     * @return 默认的文件分片的存储路径前缀
     */
    public static String generateDefaultStoreFileChunkRealPath() {
        return System.getProperty("user.home") + File.separator + "pocket" + File.separator + "chunks";
    }

    /**
     * 生成文件的存储路径
     * 生成规则：基础路径 + 年 + 月 + 日 + uuid
     *
     * @param basePath 基础路径
     * @param filename 文件名
     * @return 文件的存储路径
     */
    public static String generateStoreFileRealPath(String basePath, String filename) {
        return basePath + File.separator + DateUtil.thisYear() + File.separator + (DateUtil.thisMonth() + 1) + File.separator + DateUtil.thisDayOfMonth() + File.separator + IdUtil.randomUUID() + getFileSuffix(filename);
    }

    /**
     * 将文件的输入流写入到文件中
     *
     * @param inputStream 输入流
     * @param targetFile  目标文件
     * @param totalSize   文件的总大小
     */
    public static void writeStreamToFile(InputStream inputStream, File targetFile, Long totalSize) throws IOException {
        // 创建目标文件
        createFile(targetFile);

        try (
                // 将 InputStream 转换为 ReadableByteChannel
                ReadableByteChannel sourceChannel = Channels.newChannel(inputStream);
                // 使用 RandomAccessFile 打开目标文件并获取 FileChannel
                RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw"); FileChannel targetChannel = randomAccessFile.getChannel()) {
            targetChannel.transferFrom(sourceChannel, 0, totalSize);
        } catch (IOException e) {
            throw new IOException("Error writing stream to file: " + targetFile.getAbsolutePath(), e);
        }
    }

    /**
     * 将文件的输入流写入到文件中
     * 使用底层的sendfile零拷贝来提高传输效率
     *
     * @param inputStream 输入流
     * @param targetFile  目标文件
     * @param totalSize   文件的总大小
     */
    public static void writeStream2File(InputStream inputStream, File targetFile, Long totalSize) throws IOException {
        createFile(targetFile);
        RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");
        FileChannel outputChannel = randomAccessFile.getChannel();
        ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
        outputChannel.transferFrom(inputChannel, 0L, totalSize);
        inputChannel.close();
        outputChannel.close();
        randomAccessFile.close();
        inputStream.close();
    }

    /**
     * 通过文件大小转化文件大小的展示名称，字符串格式
     * <p>
     * (20) -> 20 bytes <p>
     * (20 * 1024) -> 20 KB <p>
     * (20 * 1024  * 1024) -> 20 MB
     *
     * @param totalSize 文件大小
     * @return 文件大小的展示名称
     */
    public static String byteCountToDisplaySize(Long totalSize) {
        if (Objects.isNull(totalSize)) {
            return StringUtils.EMPTY;
        }

        return org.apache.commons.io.FileUtils.byteCountToDisplaySize(totalSize);
    }

}
