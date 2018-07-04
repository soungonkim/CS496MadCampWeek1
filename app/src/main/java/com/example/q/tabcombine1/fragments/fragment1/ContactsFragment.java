package com.example.q.tabcombine1.fragments.fragment1;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.q.tabcombine1.R;

//import com.example.q.tabcombine1.activity.SimpleTabsActivity;
//
//import info.androidhive.materialtabs.activity.SimpleTabsActivity;


public class ContactsFragment extends Fragment implements View.OnClickListener{

    ArrayList<Map<String, String>> dataList;
    ListView mListview;
    Button mBtnAddress;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBtnAddress.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mListview =  getView().findViewById(R.id.listview);
        mBtnAddress =  getView().findViewById(R.id.btnAddress);
    }
    public void onClick(View v) {

        //SimpleTabsActivity activity = (SimpleTabsActivity) getActivity();

        switch (v.getId()) {
            case R.id.btnAddress:

                dataList = new ArrayList<Map<String, String>>();
                Cursor c = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null,
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");


                while (c.moveToNext()) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    // 연락처 id 값
                    String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    // 연락처 대표 이름
                    String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    map.put("name", name);

                    // ID로 전화 정보 조회
                    Cursor phoneCursor = getActivity().getApplicationContext().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);

                    // 데이터가 있는 경우
                    if (phoneCursor.moveToFirst()) {
                        String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        map.put("phone", number);
                    }

                    phoneCursor.close();
                    dataList.add(map);
                }// end while
                c.close();

                SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
                        dataList,
                        android.R.layout.simple_list_item_2,
                        new String[]{"name", "phone"},
                        new int[]{android.R.id.text1, android.R.id.text2});
                mListview.setAdapter(adapter);
        }
    }

}