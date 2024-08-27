/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.model;

import net.jpountz.xxhash.StreamingXXHash64;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 模型元数据
 * @param authors 作者
 * @param name 名称
 * @param description 描述
 * @param license 许可证
 * @param links 链接
 */
public record ModelMetaData(@NotNull String[] authors, @NotNull String name, @Nullable String description,
                            @Nullable String license, @Nullable String[] links, @NotNull String type) {
}
