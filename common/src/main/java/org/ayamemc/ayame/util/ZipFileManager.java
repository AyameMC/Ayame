/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class ZipFileManager {

    private Map<String, InputStream> fileContents = new HashMap<>();

    public ZipFileManager(Path path) {
        loadZipFile(path);
    }

    /**
     * 读取ZIP文件，并将内容存储到Map中。
     *
     * @param path ZIP文件的路径
     */
    private void loadZipFile(Path path) {
        try (ZipFile zipFile = new ZipFile(path.toFile())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if (!entry.isDirectory()) { // 忽略目录
                    InputStream is = zipFile.getInputStream(entry);
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
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 获取ZIP文件中的所有文件。
     *
     * @return 所有文件的名称与内容映射
     */
    public Map<String, InputStream> getAllFiles() {
        return fileContents;
    }

    /**
     * 获取指定目录及其子目录下的所有文件。
     *
     * @param dir 目录名称
     * @return 文件名称与内容映射
     */
    public Map<String, InputStream> getFilesInDir(String dir) {
        Map<String, InputStream> filesInDir = new HashMap<>();
        for (Map.Entry<String, InputStream> entry : fileContents.entrySet()) {
            if (entry.getKey().startsWith(dir + "/")) {
                filesInDir.put(entry.getKey(), entry.getValue());
            }
        }
        return filesInDir;
    }

    /**
     * 获取指定目录下的所有文件，不包括子目录。
     *
     * @param dir 目录名称
     * @return 文件名称与内容映射
     */
    public Map<String, InputStream> getFilesInDirWithoutSubdirs(String dir) {
        Map<String, InputStream> filesInDir = new HashMap<>();
        for (Map.Entry<String, InputStream> entry : fileContents.entrySet()) {
            if (entry.getKey().startsWith(dir) && !entry.getKey().contains("/")) {
                filesInDir.put(entry.getKey(), entry.getValue());
            }
        }
        return filesInDir;
    }

    /**
     * 获取ZIP文件中的所有目录路径。
     *
     * @return 目录路径列表
     */
    public List<String> getDirs() {
        List<String> dirs = new ArrayList<>();
        for (String key : fileContents.keySet()) {
            String[] parts = key.split("/");
            if (parts.length > 1) {
                StringBuilder dirPath = new StringBuilder();
                for (int i = 0; i < parts.length - 1; i++) {
                    dirPath.append(parts[i]).append("/");
                    if (!dirs.contains(dirPath.toString())) {
                        dirs.add(dirPath.toString());
                    }
                }
            }
        }
        return dirs;
    }

    /**
     * 获取指定目录下的所有子目录。
     *
     * @param dir 目录名称
     * @return 子目录路径列表
     */
    public List<String> getDirsInDir(String dir) {
        List<String> dirsInDir = new ArrayList<>();
        for (String key : fileContents.keySet()) {
            if (key.startsWith(dir + "/")) {
                String subKey = key.substring(dir.length() + 1);
                String[] parts = subKey.split("/");
                if (parts.length > 1) {
                    StringBuilder dirPath = new StringBuilder(dir).append("/");
                    for (int i = 0; i < parts.length - 1; i++) {
                        dirPath.append(parts[i]).append("/");
                        if (!dirsInDir.contains(dirPath.toString())) {
                            dirsInDir.add(dirPath.toString());
                        }
                    }
                }
            }
        }
        return dirsInDir;
    }

    /**
     * 读取ZIP文件中的特定文件，并返回其内容的InputStream。
     *
     * @param fileName 文件名称
     * @return 文件内容的InputStream
     * @throws IOException 如果读取失败
     */
    public InputStream readFileContent(String fileName) throws IOException {
        InputStream contentStream = fileContents.get(fileName);

        if (contentStream == null) {
            throw new IOException("File not found: " + fileName);
        }

        return contentStream;
    }
}

