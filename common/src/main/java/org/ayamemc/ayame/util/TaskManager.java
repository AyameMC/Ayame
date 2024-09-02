/*
 *      Custom player model mod. Powered by GeckoLib.
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

import java.util.ArrayList;
import java.util.List;

/**
 * 任务管理器，用于管理任务列表和执行任务
 * @param <T> 任务的类型
 */
public class TaskManager<T extends TaskManager.Task> {
    // 任务接口，表示可以执行的任务
    @FunctionalInterface
    public interface Task {
        void execute();
    }

    // 用于存储任务的列表
    private final List<T> taskList = new ArrayList<>();

    // 控制任务是否可以立即执行的变量
    private boolean canExecute = false;

    // 添加任务的方法
    public void addTask(T task) {
        if (canExecute) {
            // 如果可以立即执行，直接执行并不保存任务
            task.execute();
        } else {
            // 否则将任务保存到列表中
            taskList.add(task);
        }
    }

    // 执行所有保存的任务并清空任务列表
    public void executeAll() {
        for (T task : taskList) {
            task.execute();
        }
        // 清空任务列表
        taskList.clear();
    }

    // 设置任务是否可以立即执行
    public void setCanExecute(boolean canExecute) {
        this.canExecute = canExecute;
    }

    public class TaskManagerImpls {
        /**
         * 客户端在游戏世界时使用的任务管理器，只有加入世界后才能执行任务
         */
        public static final TaskManager<Task> CLIENT_IN_WORLD_TASKS = new TaskManager<>();
    }
}
