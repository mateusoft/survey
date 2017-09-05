package com.izoo.survey.model;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.content.Context;
import java.util.*;
import android.widget.Filter;
import android.view.*;
import android.widget.TextView;

import com.izoo.survey.R;

/**
 * Created by dawid.turczak on 20.12.2016.
 */

public class ListAdapter<T> extends BaseAdapter implements Filterable {

    public Context context;
    public List<T> list;
    public List<T> orig;
    int layout;

    public ListAdapter(Context context, List<T> list, int layout) {
        super();
        this.context = context;
        this.list = list;
        this.layout = layout;
    }


    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<T> results = new ArrayList<T>();
                if (orig == null)
                    orig = list;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final T g : orig) {
                            if (g.toString().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                list = (List<T>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }


    public void Clear()
    {
        orig = null;
        list.clear();
    }

    public void AddAll(List<T> list)
    {
        this.list.addAll(list);
        this.notifyDataSetChanged();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null )
        {
            LayoutInflater inflater =
                    LayoutInflater.from(parent.getContext());

            convertView = inflater.inflate(layout, parent, false);
        }

        if(getItem(position) instanceof SurveyList){
            SurveyList surveyList = (SurveyList) getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView lp = (TextView) convertView.findViewById(R.id.lp);
            if(surveyList.getSurvey().isAnonymous() == 0){
                name.setText(surveyList.getSurvey().getName());
                lp.setText("lp." + Integer.toString(surveyList.getLp()));
            }else{
                SpannableString text = new SpannableString(surveyList.getSurvey().getName()+ " (A)");
                text.setSpan(new ForegroundColorSpan(Color.RED), text.length()-3,text.length(),0);
                name.setText(text);
            }
            date.setText(surveyList.getSurvey().getDate_Create().toString());
        }
        return convertView;
    }
}