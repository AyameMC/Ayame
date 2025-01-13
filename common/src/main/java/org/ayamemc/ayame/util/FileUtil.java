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

import org.ayamemc.ayame.Ayame;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class FileUtil {
    /**
     * 读取文件
     *
     * @param path {@link Path}类型，文件路径
     * @return {@link String}
     */
    public static String readFileWithException(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return ""; // 返回空字符串
        }
    }


    /**
     * 覆盖文件
     *
     * @param path    {@link Path}类型，文件路径
     * @param content 覆盖的内容
     */
    public static void overwriteFile(Path path, String content) {
        try {
            // 检查文件夹是否存在，如果不存在则创建
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 检查文件是否存在，如果不存在则创建
            if (Files.notExists(path)) {
                Files.createFile(path);
            }

            // 写入文件内容
            Files.writeString(path, content);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }


    /**
     * 将指定文件夹中的文件以名称和内容的形式保存到 Map 中。
     *
     * @param folderPath 文件夹路径
     * @return 包含文件名称和内容的 Map
     * @throws IOException 如果读取文件或目录时发生错误
     */
    public static Map<String, InputStream> convertFilesToMap(Path folderPath) throws IOException {
        Map<String, InputStream> fileMap = new HashMap<>();

        Stream<Path> files = Files.list(folderPath);
        files.filter(Files::isRegularFile).forEach(file -> {
            try {
                fileMap.put(file.getFileName().toString(), Files.newInputStream(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        return fileMap;
    }


    /**
     * 将 InputStream 转换为 String
     *
     * @param inputStream 输入流
     * @return 转换后的字符串
     */
    public static String inputStreamToString(InputStream inputStream) {
        try {
            if (inputStream == null) {
                return "";
            }

            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }

            return content.toString();
        } catch (IOException e) {
            LOGGER.error("Cannot convert inputStream to String: ", e);
            return "";
        }
    }

    /**
     * 返回 Ayame 资源路径下的 InputStream
     *
     * @param resourcePath 路径
     * @return 字节流
     */
    public static InputStream getAyameResourceAsStream(String resourcePath) {
        return getResourceAsStream("assets/ayame/" + resourcePath);
    }

    /**
     * 返回资源的 InputStream
     *
     * @param resourcePath 路径
     * @return 字节流
     */
    public static InputStream getResourceAsStream(String resourcePath) {
        try {
            // 使用ClassLoader读取资源文件
            InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(resourcePath);

            if (inputStream == null) {
                LOGGER.error("Cannot find resource: {}", resourcePath);
                return null;
            }

            return inputStream;
        } catch (Exception e) {
            LOGGER.error("Cannot get resource stream: ", e);
            return null;
        }
    }

    /**
     * 将资源复制到指定路径（支持文件和目录）
     *
     * @param resourcePath 起始路径
     * @param targetPath   目标路径
     */
    public static void copyResource(String resourcePath, Path targetPath) {
        Path resource = Paths.get(resourcePath).toAbsolutePath();  // 转换为绝对路径

        if (Files.isDirectory(resource)) {
            // 如果是目录，则调用复制目录的方法
            copyDirectory(resource, targetPath);
        } else if (Files.isRegularFile(resource)) {
            // 如果是文件，则调用复制文件的方法
            copyFile(resource, targetPath);
        } else {
            System.err.println("Resource is neither a file nor a directory: " + resource);
        }
    }


    /**
     * 复制文件
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     */
    public static void copyFile(Path sourcePath, Path targetPath) {
        try (InputStream inputStream = Files.newInputStream(sourcePath)) {

            // 创建目标路径的父目录
            Files.createDirectories(targetPath.getParent());

            // 创建输出流
            try (OutputStream outputStream = Files.newOutputStream(targetPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                byte[] buffer = new byte[1024];
                int length;
                // 读取并写入二进制数据
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        } catch (IOException e) {
            Ayame.LOGGER.error("Cannot copy resource: ", e);
        }
    }

    /**
     * 复制目录及其内容
     *
     * @param sourceDir 源目录路径
     * @param targetDir 目标目录路径
     */
    private static void copyDirectory(Path sourceDir, Path targetDir) {
        try {
            // 创建目标目录
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            // 遍历源目录中的文件和子目录
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
                @Override
                public @NotNull FileVisitResult visitFile(Path file, @NotNull BasicFileAttributes attrs) {
                    Path targetFile = targetDir.resolve(sourceDir.relativize(file));
                    copyFile(file, targetFile); // 复制文件
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public @NotNull FileVisitResult preVisitDirectory(Path dir, @NotNull BasicFileAttributes attrs) throws IOException {
                    // 创建子目录
                    Path targetDirPath = targetDir.resolve(sourceDir.relativize(dir));
                    if (!Files.exists(targetDirPath)) {
                        Files.createDirectories(targetDirPath);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            LOGGER.error("Cannot copy directory: ", e);
        }
    }

    /**
     * 从 ZipFile 中获取指定文件的 InputStream。
     *
     * @param zipFile   ZipFile 对象
     * @param entryName 要读取的文件名
     * @return 指定文件的 InputStream，如果文件不存在则返回 null
     */
    public static InputStream getInputStreamFromZip(ZipFile zipFile, String entryName) {
        if (zipFile == null || entryName == null || entryName.isEmpty()) {
            return null;
        }

        // 获取指定文件的 ZipEntry 对象
        ZipEntry zipEntry = zipFile.getEntry(entryName);

        if (zipEntry == null) {
            // 文件不存在
            return null;
        }

        try {
            // 返回文件的 InputStream
            return zipFile.getInputStream(zipEntry);
        } catch (Exception e) {
            // 处理异常，例如文件读取失败
            LOGGER.error("Error reading file from ZipFile: ", e);
            return null;
        }
    }

}
