package com.example.android.uchu.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.uchu.ChooseSkillActivity;
import com.example.android.uchu.R;
import com.example.android.uchu.ResultActivity;

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private SearchViewModel searchViewModel;
    private Spinner searchSpinner;
    private Button searchButton;
    private String chosenSkill = "";
    public static String CHOOSE_SKILL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        CHOOSE_SKILL = getResources().getString(R.string.skill_zero);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Поиск");
        searchSpinner = root.findViewById(R.id.search_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.skills, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        searchSpinner.setAdapter(adapter);
        searchSpinner.setOnItemSelectedListener(this);
        searchButton = root.findViewById(R.id.search_button_1);
        searchButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = (String) parent.getItemAtPosition(position);
        if (!TextUtils.isEmpty(selection) && !selection.equals(ChooseSkillActivity.CHOOSE_SKILL)) {
            chosenSkill = selection;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (!chosenSkill.equals(CHOOSE_SKILL)) {
            Intent intent = new Intent(getActivity(), ResultActivity.class);
            intent.putExtra("chosenSkill", chosenSkill);
            startActivity(intent);
        }
    }
}