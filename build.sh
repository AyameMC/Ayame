#
#     Custom player model mod. Powered by GeckoLib.
#     Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
#
#     This file is part of Ayame.
#
#     Ayame is free software: you can redistribute it and/or modify
#     it under the terms of the GNU Lesser General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     (at your option) any later version.
#
#     Ayame is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU Lesser General Public License for more details.
#
#     You should have received a copy of the GNU Lesser General Public License
#     along with Ayame.  If not, see <https://www.gnu.org/licenses/>.
#

chmod +x gradlew
# ./gradlew runDatagen &      # 将命令放入后台运行
# pid=$!              # 获取该命令的PID
# sleep 60
# kill -9 $pid       # 60s后强制kill

./gradlew build
mkdir result
cp -r fabric/build/libs/* result
cp -r neoforge/build/libs/* result
cp -r common/build/libs/* result
cd result
echo "Unstable Nightly Development Build, DO NOT Use for Normal Play!" > " A_Unstable Nightly Development Build，DO NOT Use for Normal Play！.txt"
echo "不稳定每夜开发构建，切勿用于正常游玩！" > " A不稳定每夜开发构建，切勿用于正常游玩！.txt"