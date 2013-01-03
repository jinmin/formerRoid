package com.jinmin.formerroid.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.jinmin.formerroid.ListPageFragment;
import com.jinmin.formerroid.R;

public class ViewPagerAdapter extends FragmentPagerAdapter
{

	private Context _context;

	private FragmentManager _fm;

	private Fragment[] fragmentArr;

	public ViewPagerAdapter(Context context, FragmentManager fm)
	{
		super(fm);
		this._fm = fm;
		_context = context;
		fragmentArr = new Fragment[getCount()];
	}

	@Override
	public Fragment getItem(int position)
	{
		Fragment f = new Fragment();
		// Bundle bundle = new Bundle();
		Log.d("position == > ", String.valueOf(position));
		switch (position) {
			case 0:
				if (fragmentArr[position] == null) {
					fragmentArr[position] = ListPageFragment.newInstance(_context);
				}
				f = fragmentArr[position];
				// f = ListPageFragment.newInstance(_context);
				// f = ListPageFragment.instantiate(_context, ListPageFragment.class.getName());
				break;
			case 1:
				if (fragmentArr[position] == null) {
					// fragmentArr[position] = new DummyFragment();
				}
				// f = fragmentArr[position];
				// f = MapLayerFragment.newInstance(_context);
				break;
			case 2:
				if (fragmentArr[position] == null) {
					// fragmentArr[position] = new DummyFragment();
				}
				// f = fragmentArr[position];
				// bundle.putInt(DummyFragment.ARG_SECTION_NUMBER, position + 1);
				// f.setArguments(bundle);
				break;
		}

		// Fragment f = Fragment.instantiate(mContext, info.clss.getName(),
		// info.args);
		//
		// fragmentMap.put(f.getClass(), f);

		return f;
	}

	@Override
	public int getCount()
	{
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		switch (position) {
			case 0:
				return _context.getString(R.string.title_section1).toUpperCase();
			case 1:
				return _context.getString(R.string.title_section2).toUpperCase();
			case 2:
				return _context.getString(R.string.title_section3).toUpperCase();
		}
		return null;
	}
}
