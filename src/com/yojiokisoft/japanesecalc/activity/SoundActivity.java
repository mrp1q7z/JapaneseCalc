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

package com.yojiokisoft.japanesecalc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yojiokisoft.japanesecalc.R;
import com.yojiokisoft.japanesecalc.dao.SettingDao;
import com.yojiokisoft.japanesecalc.dao.SoundEntity;
import com.yojiokisoft.japanesecalc.utils.MyResource;

/**
 * クリック音設定のアクティビティ
 */
public class SoundActivity extends Activity {
	private ListView mListView;
	private int mCheckedPosition = -1;
	private SoundPool mSound;
	private int[] mSoundId;
	private List<SoundEntity> mList;

	/**
	 * 初期処理
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);

		mListView = (ListView) findViewById(R.id.sound_list);
		mList = getSoundList();

		String resName = SettingDao.getInstance().getClickSound();
		int resId = MyResource.getResourceIdByName(resName, "raw");
		String[] items = new String[mList.size()];
		SoundEntity item;
		for (int i = 0; i < mList.size(); i++) {
			item = mList.get(i);
			items[i] = item.title + " (" + item.description + ")";
			if (resId == item.resId) {
				mCheckedPosition = i;
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice,
				android.R.id.text1, items);
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(mSoundListItemClicked);
		mListView.setItemChecked(mCheckedPosition, true);

		mSound = new SoundPool(mList.size(), AudioManager.STREAM_MUSIC, 0);
		mSoundId = new int[mList.size()];
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).resId == 0) {
				mSoundId[i] = 0;
			} else {
				mSoundId[i] = mSound.load(getApplicationContext(), mList.get(i).resId, 0);
			}
		}
	}

	/**
	 * データの一時保存
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt("SAVE_POSITION", mCheckedPosition);
	}

	/**
	 * 一時保存されたデータを復元
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		mCheckedPosition = savedInstanceState.getInt("SAVE_POSITION");
	}

	/**
	 * クリック音一覧の取得
	 * 
	 * @return
	 */
	private List<SoundEntity> getSoundList() {
		List<SoundEntity> list = new ArrayList<SoundEntity>();

		SoundEntity sound = new SoundEntity();
		sound.title = getString(R.string.sound_none);
		sound.description = getString(R.string.sound_none_note);
		sound.resId = 0;
		sound.checked = false;
		list.add(sound);

		sound = new SoundEntity();
		sound.title = getString(R.string.sound_chiiin);
		sound.description = getString(R.string.sound_chiiin_note);
		sound.resId = R.raw.mp_chiiin;
		sound.checked = false;
		list.add(sound);

		sound = new SoundEntity();
		sound.title = getString(R.string.sound_drop);
		sound.description = getString(R.string.sound_drop_note);
		sound.resId = R.raw.mp_drop;
		sound.checked = false;
		list.add(sound);

		sound = new SoundEntity();
		sound.title = getString(R.string.sound_hyu);
		sound.description = getString(R.string.sound_hyu_note);
		sound.resId = R.raw.mp_hyu;
		sound.checked = false;
		list.add(sound);

		sound = new SoundEntity();
		sound.title = getString(R.string.sound_pi1);
		sound.description = getString(R.string.sound_pi1_note);
		sound.resId = R.raw.mp_pi1;
		sound.checked = false;
		list.add(sound);

		sound = new SoundEntity();
		sound.title = getString(R.string.sound_pi2);
		sound.description = getString(R.string.sound_pi2_note);
		sound.resId = R.raw.mp_pi2;
		sound.checked = false;
		list.add(sound);

		sound = new SoundEntity();
		sound.title = getString(R.string.sound_poku);
		sound.description = getString(R.string.sound_poku_note);
		sound.resId = R.raw.mp_poku;
		sound.checked = false;
		list.add(sound);

		sound = new SoundEntity();
		sound.title = getString(R.string.sound_taiko);
		sound.description = getString(R.string.sound_taiko_note);
		sound.resId = R.raw.mp_taiko;
		sound.checked = false;
		list.add(sound);

		return list;
	}

	/**
	 * クリック音が選択された
	 */
	private OnItemClickListener mSoundListItemClicked = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			if (mCheckedPosition != -1) {
				if (mSoundId[mCheckedPosition] != 0) {
					mSound.stop(mSoundId[mCheckedPosition]);
				}
			}
			if (mSoundId[position] != 0) {
				mSound.play(mSoundId[position], 1.0F, 1.0F, 0, 0, 1.0F);
			}
			mCheckedPosition = position;
		}
	};

	/**
	 * OKボタンのクリック
	 * 
	 * @param view
	 */
	public void onOkButtonClicked(View view) {
		int resId = mList.get(mCheckedPosition).resId;
		String resName = "none";
		if (resId != 0) {
			resName = getResources().getResourceEntryName(resId);
		}
		SettingDao.getInstance().setClickSound(resName);
		finish();
	}

	public void onCancelButtonClicked(View view) {
		finish();
	}

	/**
	 * 終了処理
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mSound.release();
	}
}
