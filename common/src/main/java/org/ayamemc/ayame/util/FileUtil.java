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

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.ayamemc.ayame.Ayame;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {
    /**
     * 将文件作为字符串获取
     *
     * @param path 文件路径
     * @return 字符串
     */
    public static String getFileAsString(Path path) {
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
    public static InputStream getFileAsStream(Path path) {
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
    public static void overwriteStringToFile(Path path, String content) {
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
     * 将包内文件资源复制到外部目录
     *
     * @param sourcePath 包内文件的源路径
     * @param targetPath 外部目标目录
     */
    public static void copyBuiltinFileToDirectory(String sourcePath, String targetPath) {
        final Path targetPathDir = Path.of(targetPath);
        try (final InputStream inputStream = getBuiltinFileResourceAsStream(sourcePath)) {
            if (inputStream != null) {
                final Path targetFile = targetPathDir.resolve(Path.of(sourcePath).getFileName().toString());
                Files.createDirectories(targetFile.getParent());
                Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
            } else {
                throw new RuntimeException("File not found at: " + sourcePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error copying file resource: " + sourcePath, e);
        }
    }

    /**
     * 将包内目录资源复制到外部目录
     *
     * @param sourcePath 包内目录的源路径
     * @param targetPath 目标目录
     */
    public static void copyBuiltinDirectoryToDirectory(String sourcePath, String targetPath) {
        final Path targetPathDir = Path.of(targetPath);
        try {
            // 获取当前 JAR 文件路径
            String jarPath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            /*
            NeoForge在获取路径时结尾会多8个错误字符，这里进行了剔除
            我知道这个修复方法很诡异，但是能用
             */
            if (Ayame.modLoader.equals("neoforge")) {
                jarPath = jarPath.substring(0, jarPath.length() - 8);
            }
            try (final ZipFile zipFile = new ZipFile(jarPath)) {
                final Enumeration<? extends ZipEntry> entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    final ZipEntry entry = entries.nextElement();
                    final String entryName = entry.getName();

                    // 检查是否属于指定目录
                    if (entryName.startsWith(sourcePath) && !entry.isDirectory()) {
                        final String relativePath = entryName.substring(sourcePath.length()); // 相对路径
                        final Path targetFile = targetPathDir.resolve(relativePath);

                        Files.createDirectories(targetFile.getParent());

                        try (final InputStream inputStream = zipFile.getInputStream(entry)) {
                            Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error copying built-in directory %s to %s.", sourcePath, targetPath), e);
        }
    }

    public static void copyAyameBuiltinDirectoryToDirectory(String sourcePath, String targrtPath) {
        copyBuiltinDirectoryToDirectory("assets/ayame/" + sourcePath, targrtPath);
    }


    /**
     * 从 ZIP 文件中获取指定条目的 InputStream
     *
     * @param zipFile   ZIP 文件
     * @param entryName 条目名称
     * @return InputStream
     */
    public static InputStream getInputStreamFromZip(ZipFile zipFile, String entryName) {
        if (zipFile == null || entryName == null || entryName.isEmpty()) {
            return null;
        }

        try {
            // 获取 ZipEntry
            final ZipArchiveEntry entry = (ZipArchiveEntry) zipFile.getEntry(entryName);

            if (entry == null) {
                // 条目不存在
                return null;
            }

            // 返回文件的 InputStream
            return zipFile.getInputStream(entry);
        } catch (IOException e) {
            throw new RuntimeException("Error reading zip entry: " + entryName, e);
        }
    }

}
