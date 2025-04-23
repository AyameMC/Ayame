/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024-2025  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
 *
 *     This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Ayame.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class FileUtil {
    /**
     * 将文件作为字符串获取
     *
     * @param path 文件路径
     * @return 字符串
     */
    public static String getFileAsString(@NotNull Path path) {
        try {
            return FileUtils.readFileToString(path.toFile(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("File not found at: " + path, e);
        }
    }

    /**
     * 获取文件的字节流
     *
     * @param path 文件路径
     * @return 字节流
     */
    public static @NotNull InputStream getFileAsStream(Path path) {
        try {
            return FileUtils.openInputStream(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException("File not found at: " + path, e);
        }
    }


    /**
     * 以新字符串覆盖文件原有的内容
     *
     * @param path    文件路径
     * @param content 要写入的字符串
     */
    public static void overwriteStringToFile(@NotNull Path path, String content) {
        try {
            FileUtils.writeStringToFile(path.toFile(), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot overwrite string to %s, content: %s", path, content), e);
        }
    }

    /**
     * 将 InputStream 转换为字符串
     *
     * @param inputStream 输入流
     * @return 转换后的字符串
     */
    public static String convertInputStreamToString(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot convert input stream to string", e);
        }
    }

    /**
     * 返回包内文件资源的 InputStream
     *
     * @param location 路径
     * @return 字节流
     */
    public static InputStream getBuiltinFileResourceAsStream(String location) {
        // 使用 ClassLoader 获取资源
        InputStream inputStream;
        try {
            inputStream = FileUtil.class.getClassLoader().getResourceAsStream(location);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find resource at: " + location, e);
        }
        return inputStream;
    }

    /**
     * 返回包内文件资源的 String
     *
     * @param location 路径
     * @return 字符串
     */
    public static String getBuiltinFileResourceAsString(String location) {
        return convertInputStreamToString(getBuiltinFileResourceAsStream(location));
    }

    /**
     * 返回 Ayame 包内文件资源的 String
     *
     * @param location 路径
     * @return 字符串
     */
    public static String getAyameBuiltinFileResourceAsString(String location) {
        return convertInputStreamToString(getAyameBuiltinFileResourceAsStream(location));
    }

    /**
     * 返回 Ayame 包内文件资源的 InputStream
     *
     * @param location 路径
     * @return 字节流
     */
    public static InputStream getAyameBuiltinFileResourceAsStream(String location) {
        return getBuiltinFileResourceAsStream("assets/ayame/" + location);
    }

    /**
     * 将 Ayame 包内文件资源复制到外部目录
     *
     * @param sourcePath 包内文件的源路径
     * @param targetPath 外部目标目录
     */
    public static void copyAyameBuiltinFileToDirectory(String sourcePath, String targetPath) {
        copyBuiltinFileToDirectory("assets/ayame/" + sourcePath, targetPath);
    }

    /**
     * 复制外部文件或目录到目标路径
     *
     * @param sourcePath 源路径
     * @param targetPath 目标路径
     */
    public static void copyFileOrDirectory(String sourcePath, String targetPath) {
        final Path sourcePathDir = Path.of(sourcePath);
        final Path targetPathDir = Path.of(targetPath);

        try {
            if (Files.isDirectory(sourcePathDir)) {
                FileUtils.copyDirectory(sourcePathDir.toFile(), targetPathDir.toFile());
            } else {
                FileUtils.copyFile(sourcePathDir.toFile(), targetPathDir.toFile());
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot copy file to target directory", e);
        }

    }

    /**
     * 将包内文件资源复制到外部目录，并保留原始路径结构
     *
     * @param sourcePath 包内文件的源路径（需包含完整子目录结构，如 "models/ayame_chan/default/model.json"）
     * @param targetDir  外部目标目录（父目录，如 "config/ayame/models/ayame_chan"）
     */
    public static void copyBuiltinFileToDirectory(String sourcePath, String targetDir) {
        final Path sourceFilePath = Path.of(sourcePath);
        final Path targetParentDir = Path.of(targetDir);

        try (final InputStream inputStream = getBuiltinFileResourceAsStream(sourcePath)) {
            if (inputStream != null) {
                // 保留原始路径结构（如将 "default/model.json" 复制到目标目录的 "default" 子目录下）
                final Path targetFile = targetParentDir.resolve(sourceFilePath);  // 关键修改：直接拼接完整路径
                Files.createDirectories(targetFile.getParent());  // 确保父目录存在
                Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
            } else {
                throw new RuntimeException("File not found at: " + sourcePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error copying file resource: " + sourcePath, e);
        }
    }

    /**
     * 将包内的多个文件复制到外部目录
     *
     * @param sourcePaths 源文件数组
     * @param targetDir  外部目标目录
     */
    public static void copyBuiltinFilesToDirectory(String[] sourcePaths, Path targetDir) {
        for (String sourcePath : sourcePaths) {
            LOGGER.info("Copying file {} to {}...", sourcePath, targetDir);
            copyBuiltinFileToDirectory(sourcePath, targetDir.toString());
        }
    }



    /**
     * 将Ayame包内的多个文件复制到外部目录
     *
     * @param sourcePaths 源文件数组
     * @param targetDir  外部目标目录
     */
    public static void copyAyameBuiltinFilesToDirectory(String[] sourcePaths, Path targetDir) {
        // TODO 增加删除目标路径参数
        String[] fullPaths = Arrays.stream(sourcePaths)
                .map(file -> "assets/ayame/" + file)
                .toArray(String[]::new);
        copyBuiltinFilesToDirectory(fullPaths, targetDir);
    }

}
