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

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipFile;

import static org.ayamemc.ayame.Ayame.*;

public class FileUtil {
    /**
     * 读取文件
     *
     * @param path 文件路径
     * @return 字符串
     */
    public static String readFile(Path path) {
        try {
            return FileUtils.readFileToString(path.toFile(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Error reading file {}", path, e);
            return "";
        }
    }


    /**
     * 覆盖文件
     *
     * @param path    文件路径
     * @param content 覆盖的内容
     */
    public static void overwriteFile(Path path, String content) {
        try {
            FileUtils.writeStringToFile(path.toFile(), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 将 InputStream 转换为 String
     *
     * @param inputStream 输入流
     * @return 转换后的字符串
     */
    public static String inputStreamToString(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return "";
        }
    }

    /**
     * 返回包内文件资源的 InputStream
     *
     * @param location 路径
     * @return 字节流
     */
    public static InputStream getBuiltinFileResourceAsStream(ResourceLocation location) {
        // 使用 ClassLoader 获取资源
        InputStream inputStream = null;
        try {
            inputStream = FileUtil.class.getClassLoader().getResourceAsStream(location.getPath());
        } catch (Exception e) {
            LOGGER.warn("Cannot find resource: {}", location, e);
        }
        return inputStream;
    }

    /**
     * 返回包内目录资源的 InputStream
     *
     * @param location 路径
     * @return 字节流数组
     */
    public static InputStream[] getBuiltinDirectoryResourceAsStream(ResourceLocation location) {
        List<InputStream> inputStreams = new ArrayList<>();

        try {
            // 获取目录资源路径（通过 ClassLoader 查找）
            String path = location.getPath();
            ClassLoader classLoader = FileUtil.class.getClassLoader();

            // 获取目录下的资源文件路径
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resourceUrl = resources.nextElement();
                inputStreams.add(resourceUrl.openStream());
            }
        } catch (Exception e) {
            LOGGER.warn("Cannot find directory resource: {}", location, e);
        }

        return inputStreams.toArray(new InputStream[0]);
    }

    /**
     * 返回 Ayame 文件资源的 InputStream
     *
     * @param location 路径
     * @return 字节流
     */
    public static InputStream getAyameFileResourceAsStream(String location) {
        return getBuiltinFileResourceAsStream(withAyamePath(location));
    }

    /**
     * 返回 Ayame 目录资源的 InputStream
     *
     * @param location 路径
     * @return 字节流
     */
    public static InputStream[] getAyameDirectoryResourceAsStream(String location) {
        return getBuiltinDirectoryResourceAsStream(withAyamePath(location));
    }

    /**
     * 复制文件或目录
     *
     * @param sourcePathStr 源路径
     * @param targetPath    目标路径
     */
    public static void copyFileOrDirectory(String sourcePathStr, Path targetPath) {
        final Path sourcePath = Path.of(sourcePathStr);

        try {
            if (Files.isDirectory(sourcePath)) {
                FileUtils.copyDirectory(sourcePath.toFile(), targetPath.toFile());
            } else {
                FileUtils.copyFile(sourcePath.toFile(), targetPath.toFile());
            }
        } catch (IOException e) {
            LOGGER.error("Cannot copy resource: ", e);
        }

    }

    /**
     * 将包内文件资源复制到外部目录
     *
     * @param location  包内文件的路径
     * @param targetDir 目标目录
     */
    public static void copyBuiltinFileToDirectory(ResourceLocation location, Path targetDir) {
        try (InputStream inputStream = getBuiltinFileResourceAsStream(location)) {
            if (inputStream != null) {
                Path targetFile = targetDir.resolve(location.getPath());
                FileUtils.copyInputStreamToFile(inputStream, targetFile.toFile());
            } else {
                LOGGER.warn("File resource not found: {}", location);
            }
        } catch (IOException e) {
            LOGGER.error("Error copying file resource: {}", location, e);
        }
    }

    /**
     * 将包内目录资源复制到外部目录
     *
     * @param location  包内目录的路径
     * @param targetDir 目标目录
     */
    public static void copyBuiltinDirectoryToDirectory(ResourceLocation location, Path targetDir) {
        InputStream[] inputStreams = getBuiltinDirectoryResourceAsStream(location);

        for (InputStream inputStream : inputStreams) {
            if (inputStream != null) {
                Path targetFile = targetDir.resolve(location.getPath());
                try {
                    FileUtils.copyInputStreamToFile(inputStream, targetFile.toFile());
                } catch (IOException e) {
                    LOGGER.warn("Error copying directory resource: {}", location, e);
                }
            }
        }
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
            LOGGER.error("Error reading file from ZipFile: ", e);
            return null;
        }
    }

}
