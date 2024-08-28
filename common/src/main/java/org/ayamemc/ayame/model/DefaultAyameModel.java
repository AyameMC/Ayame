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

public class DefaultAyameModel implements AyameModel{
    private final ModelMetaData metaData;
    public DefaultAyameModel(ModelMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public ModelMetaData getMetaData() {
        return metaData;
    }

    public static DefaultAyameModel of(ModelMetaData metaData){
        return new DefaultAyameModel(metaData);
    }
}
