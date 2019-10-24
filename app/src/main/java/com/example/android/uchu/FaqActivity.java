package com.example.android.uchu;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.uchu.ui.FaqExpandableListAdapter;

public class FaqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ExpandableListView faqListView = findViewById(R.id.faq_list_view);
        faqListView.setAdapter(new FaqExpandableListAdapter(getApplicationContext()));
        faqListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        TextView title = findViewById(R.id.arc_text);
        title.setText("FAQ");

        ImageButton faqBackButton = findViewById(R.id.back_button);
        faqBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
