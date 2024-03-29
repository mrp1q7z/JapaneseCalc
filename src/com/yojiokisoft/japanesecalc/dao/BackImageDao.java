/*
 * Copyright (C) 2013 YojiokiSoft
 * 
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this
 * program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.yojiokisoft.japanesecalc.dao;

import java.util.ArrayList;
import java.util.List;

import com.yojiokisoft.japanesecalc.utils.MyResource;

/**
 * 背景画像のデータアクセス
 */
public class BackImageDao {
	/**
	 * 全データを検索
	 * 
	 * @return 背景画像の一覧
	 */
	public List<BackImageEntity> queryForAll() {
		List<BackImageEntity> list = new ArrayList<BackImageEntity>();
		String resName;
		for (int i = 1; i <= 99; i++) {
			resName = "bg_image_" + String.format("%1$02d", i);
			int resId = MyResource.getResourceIdByName(resName);
			if (resId == 0) {
				break;
			}
			BackImageEntity item = new BackImageEntity();
			item.type = BackImageEntity.IT_RESOURCE;
			item.resourceName = resName;
			list.add(item);
		}

		return list;
	}
}
