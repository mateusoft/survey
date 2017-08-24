package com.izoo.survey;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.izoo.survey.model.DatabaseHelper;

public class StatisticsFragment extends Fragment {
    private SavedTabsListAdapter stla;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stla = new SavedTabsListAdapter();
//        stla.groups[0]="ala";
        stla.CreateGroups();
        View v = inflater.inflate(R.layout.fragment_statistics, null);
        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.list);
        elv.setAdapter(stla);
        return v;
    }

    public class SavedTabsListAdapter extends BaseExpandableListAdapter {
        private DatabaseHelper myDb;
        public  String[] groups;// = { "Ace" };

        private String[][] children;
        public void CreateGroups()
        {
            myDb = new DatabaseHelper(getActivity());
            Cursor cursor =myDb.getCountSurveyHistoryUser(MainActivity.getLoggedUser().getID_User());
            cursor.moveToFirst();
            groups=new String[cursor.getCount()];
            children = new String[cursor.getCount()][];
            for(int i=0;i<cursor.getCount();i++)
            {
                groups[i]=" "+cursor.getString(1)+"\t"+cursor.getInt(2);
                CreateChildren(MainActivity.getLoggedUser().getID_User(),cursor.getInt(0),i);
                cursor.moveToNext();
            }
        }
        public void CreateChildren(int ID_user,int ID_survey,int position)
        {
            myDb = new DatabaseHelper(getActivity());
            Cursor cursor =myDb.getListSurveyHistory(ID_user,ID_survey);
            children[position]=new String[cursor.getCount()+2];
            cursor.moveToFirst();
            int i=0;
            for(;i<cursor.getCount();i++)
            {
                children[position][i]=" "+(i+1)+"\t"+cursor.getString(0);
                cursor.moveToNext();
            }
            children[position][i]="Moje wypełnienia:  "+cursor.getCount();
            cursor = myDb.getCountSurveyHistoryAll(ID_survey);
            cursor.moveToFirst();
            children[position][i+1]="Wypełnienia wszystkich użytkowników:  "+cursor.getInt(0);
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return children[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return groups[i];
        }

        @Override
        public Object getChild(int i, int i1) {
            return children[i][i1];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(StatisticsFragment.this.getActivity());
            textView.setText(getGroup(i).toString());
            return textView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(StatisticsFragment.this.getActivity());
            textView.setText(getChild(i, i1).toString());
            return textView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

    }

}