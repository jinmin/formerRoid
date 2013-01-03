package com.jinmin.formerroid;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jinmin.formerroid.adapters.StoredContactListArrayAdapter;
import com.jinmin.formerroid.dao.StoredContactService;
import com.jinmin.formerroid.model.StoredContact;

public class ListPageFragment extends Fragment
{

	public static final String ARG_SECTION_NUMBER = "section_number";

	public static final int CONTACT_REQUEST_CODE = 99;

	private Context _context;
	private StoredContactListArrayAdapter arrayAdapter;

	StoredContactService storedContactService;

	EditText nameEditText;
	EditText telEditText;

	public ListPageFragment()
	{
	}

	public ListPageFragment(Context _context)
	{
		this._context = _context;
	}

	public static Fragment newInstance(Context _context)
	{
		return new ListPageFragment(_context);
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (storedContactService == null) {
			storedContactService = new StoredContactService(getActivity().getApplicationContext());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d("onCreateView", "called");
		if (container == null)
			return null;

		View view = inflater.inflate(R.layout.listview_layout, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		Log.d("onActivityCreated", "called");
		if (storedContactService == null) {
			storedContactService = new StoredContactService(getActivity().getApplicationContext());
		}
		initEditText();
		displayListView();
		refreshListView();
	}

	@Override
	public void onResume()
	{
		Log.d("Resume", "called");
		super.onResume();
	}

	@Override
	public void onPause()
	{
		Log.d("onPause", "called");
		super.onPause();
	}

	@Override
	public void onDestroyView()
	{
		Log.d("onDestroyView", "called");
		super.onDestroyView();
	}

	@Override
	public void onDestroy()
	{
		Log.d("onDestroy", "called");
		super.onDestroy();
	}

	private void initEditText()
	{
		if (nameEditText == null) {
			nameEditText = (EditText)getActivity().findViewById(R.id.name);
		}
		if (telEditText == null) {
			telEditText = (EditText)getActivity().findViewById(R.id.tel);
		}
	}

	public List<StoredContact> getListViewData()
	{
		if (storedContactService == null)
			storedContactService = new StoredContactService(_context);
		return storedContactService.getStoredContactList();
	}

	public void displayListView()
	{

		if (arrayAdapter == null) {
			arrayAdapter = new StoredContactListArrayAdapter(_context, R.layout.listview_row, storedContactService.getStoredContactList());
			arrayAdapter.notifyDataSetChanged();
			ListView listView = (ListView)getView().findViewById(R.id.listView);
			listView.setAdapter(arrayAdapter);
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{

					// final AdapterView<?> parentView = parent;
					final int selectedPosition = position;
					AlertDialog.Builder confirmDeleteAlert = new AlertDialog.Builder(parent.getContext()).setMessage("삭제하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							StoredContact item = arrayAdapter.getItem(selectedPosition);
							if (item != null) {
								if (storedContactService.removeContact(item.getId())) {
									refreshListView();
								}
							}
						}
					}).setNegativeButton("취소", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();
						}
					});
					confirmDeleteAlert.show();
				}

			});
		}

		// 등록 버튼
		Button registBtn = (Button)getView().findViewById(R.id.insertBtn);
		registBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());

				String name = nameEditText.getText().toString();
				String tel = telEditText.getText().toString().replace("-", "");

				if (name.trim().length() == 0) {
					alert.setMessage("이름을 확인해주세요!");
				}
				else if (tel.trim().length() == 0 || (tel.trim().length() < 9)) {
					alert.setMessage("전화번호를 확인해주세요(지역번호 포함)!");
				}
				else {
					int duplicated = storedContactService.checkDuplicatedStoredContact(name, tel);
					if (duplicated > 0) {
						alert.setMessage("이미 존재하는 데이터입니다.");
					}
					else {
						RadioGroup radioGroup = ((RadioGroup)getView().findViewById(R.id.ring_mode));
						RadioButton radioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
						int ring_mode = radioGroup.indexOfChild(radioButton);
						int ring_mode_option = 0;

						if (storedContactService.insertContact(new StoredContact(name, tel, ring_mode, ring_mode_option))) {
							refreshListView();

							// clear
							nameEditText.setText("");
							telEditText.setText("");
							alert.setMessage("등록 성공!");
						}
						else {
							alert.setMessage("등록 실패!");
						}
					}
				}
				alert.setPositiveButton("확인", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();// 닫기
					}
				});
				alert.show();

			}
		});

		// 찾기 버튼
		Button contactBtn = (Button)getView().findViewById(R.id.searchBtn);
		contactBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
				getActivity().startActivityForResult(intent, CONTACT_REQUEST_CODE);
			}
		});
	}

	public void setContactData(String _name, String _phoneNumber)
	{
		initEditText();
		if (_name != null) {
			nameEditText.setText(_name);
		}
		if (_phoneNumber != null) {
			telEditText.setText(_phoneNumber.replace("-", ""));
		}
	}

	public void refreshListView()
	{
		arrayAdapter = new StoredContactListArrayAdapter(_context, R.layout.listview_row, storedContactService.getStoredContactList());
		ListView listView = (ListView)getView().findViewById(R.id.listView);
		listView.setAdapter(arrayAdapter);
		arrayAdapter.notifyDataSetChanged();
	}

	public AlertDialog.Builder showAlertDialog(Context context, String message)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(message);
		alert.setPositiveButton("확인", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		return alert;
	}

}