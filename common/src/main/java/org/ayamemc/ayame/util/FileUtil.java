/*
 *      Custom player model mod. Based on GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class FileUtil {
    /**
     * 读取文件
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
     * @param path {@link Path}类型，文件路径
     * @param content{@link String}
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
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }


    /**
     * 读取ZIP文件，并将内容存储到Map中。
     *
     * @param path ZIP文件的路径
     * @return 包含文件名和内容的Map
     */
    public static Map<String, InputStream> readZipFile(Path path) {
        Map<String, InputStream> fileContents = new HashMap<>();

        try (ZipFile zipFile = new ZipFile(path.toFile())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if (!entry.isDirectory()) { // 忽略目录
                    try (InputStream is = zipFile.getInputStream(entry)) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len;

                        while ((len = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }

                        // 将内容转换为字节数组输入流并存储在 Map 中
                        InputStream contentStream = new ByteArrayInputStream(baos.toByteArray());
                        fileContents.put(entry.getName(), contentStream);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return fileContents;
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
        }catch (IOException e){
            LOGGER.error("Cannot convert inputStream to String: ", e);
            return "";
        }
    }


    public static void copyResource(String resourcePath, Path targetPath) {
        try {
            // 使用ClassLoader读取资源文件
            InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(resourcePath);

            if (inputStream != null) {
                // 读取资源文件内容，指定编码为UTF-8
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                // 将读取到的内容写入文件
                FileUtil.overwriteFile(targetPath, content.toString());
            } else {
                LOGGER.error("Cannot find resource: {}", resourcePath);
            }
        } catch (IOException e) {
            LOGGER.error("Cannot copy resource: ", e);
        }
    }

}
