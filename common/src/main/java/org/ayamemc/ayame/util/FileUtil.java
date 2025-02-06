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
import org.ayamemc.ayame.Ayame;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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

    public static @NotNull String getTruncatedJarPath(@NotNull String input) {
        // 查找 .jar 出现的起始位置
        int jarIndex = input.indexOf(".jar");

        if (jarIndex != -1) {
            // 因为要包含 .jar 整个字符串，所以需要加上4（".jar" 的长度）
            return input.substring(0, jarIndex + 4);
        } else {
            // 如果没有找到 .jar，则返回原始字符串或根据需要处理
            return input;
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
            try (JarFile targetFile = getCurrentJarFile()) {
                final Enumeration<JarEntry> entries = targetFile.entries();
                while (entries.hasMoreElements()) {
                    final JarEntry entry = entries.nextElement();
                    final String name = entry.getName();

                    if (name.startsWith(sourcePath)) {
                        final String relativePath = name.substring(sourcePath.length());
                        final File target = targetPathDir.resolve(relativePath).toFile();

                        if (entry.isDirectory()) {
                            if (target.mkdirs()) {
                                Ayame.LOGGER.info("Creating directory for built-in files: {}", name);
                            }
                            continue;
                        }

                        Ayame.LOGGER.info("Copying built-in files: {}", name);

                        try (
                                InputStream is = targetFile.getInputStream(entry);
                                FileOutputStream fos = new FileOutputStream(target)
                        ) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = is.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                            fos.flush();
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error copying built-in directory %s to %s.", sourcePath, targetPath), e);
        }
    }

    /**
     * 获取当前 Jar 文件
     * @return JarFile
     * @throws URISyntaxException URI 语法错误
     * @throws IOException IO 异常
     */
    public static JarFile getCurrentJarFile() throws URISyntaxException, IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        final URI target = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        JarFile targetFile;

        final String scheme = target.getScheme();

        switch (scheme) {
            case "file" -> targetFile = new JarFile(target.getRawSchemeSpecificPart());

            case "jar" -> targetFile = ((JarURLConnection) target.toURL().openConnection()).getJarFile();

            // This is neoforge's black magic
            case "union" -> {
                String spec = target.getRawSchemeSpecificPart();

                int sep = spec.indexOf("!/");
                if (sep != -1) {
                    spec = spec.substring(0, sep);
                }

                // Remove the key which is generated by the union file system
                spec = spec.replaceAll("%.*", "");

                return new JarFile(spec);
            }

            default -> throw new RuntimeException("Unknown scheme " + target);
        }

        return targetFile;
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
            ZipEntry entry = zipFile.getEntry(entryName);
            return (entry != null) ? zipFile.getInputStream(entry) : null;
        } catch (IOException e) {
            // 记录错误，避免直接抛异常导致程序崩溃
            throw new RuntimeException("Error reading zip entry: " + entryName, e);
        }
    }

}
