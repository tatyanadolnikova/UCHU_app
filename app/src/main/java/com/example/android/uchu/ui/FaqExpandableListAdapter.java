package com.example.android.uchu.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.uchu.R;

public class FaqExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    public FaqExpandableListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return 5;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View groupView, ViewGroup parent) {
        if (groupView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            groupView = inflater.inflate(R.layout.listview_groupview, null);
        }

        switch (groupPosition) {
            case 0: {
                ((TextView) groupView.findViewById(R.id.question)).setText(R.string.faq_1);
            } break;

            case 1: {
                ((TextView) groupView.findViewById(R.id.question)).setText(R.string.faq_2);
            } break;

            case 2: {
                ((TextView) groupView.findViewById(R.id.question)).setText(R.string.faq_3);
            } break;
            case 3: {
                ((TextView) groupView.findViewById(R.id.question)).setText(R.string.faq_4);
            } break;

            case 4: {
                ((TextView) groupView.findViewById(R.id.question)).setText(R.string.faq_5);
            } break;
        }
        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View childView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        childView = inflater.inflate(R.layout.listview_groupview_childview, null);

        switch (groupPosition) {
            case 0: {
                ((TextView) childView.findViewById(R.id.answer)).setText(R.string.faq_1_answer);
            } break;

            case 1: {
                ((TextView) childView.findViewById(R.id.answer)).setText(R.string.faq_2_answer);
            } break;

            case 2: {
                ((TextView) childView.findViewById(R.id.answer)).setText(R.string.faq_3_answer);
            } break;

            case 3: {
                ((TextView) childView.findViewById(R.id.answer)).setText(R.string.faq_4_answer);
            } break;

            case 4: {
                ((TextView) childView.findViewById(R.id.answer)).setText(R.string.faq_5_answer);
            } break;
        }

        return childView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

