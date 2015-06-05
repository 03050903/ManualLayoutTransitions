package com.stylingandroid.manuallayouttransitions.backport;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transitions.everywhere.ChangeBounds;
import android.transitions.everywhere.Fade;
import android.transitions.everywhere.TransitionManager;
import android.transitions.everywhere.TransitionSet;
import android.view.View;
import android.view.ViewGroup;

import com.stylingandroid.manuallayouttransitions.R;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part1);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(R.string.sample_language);

        View input = findViewById(R.id.input);
        View inputDone = findViewById(R.id.input_done);
        final View focusHolder = findViewById(R.id.focus_holder);

        input.setOnFocusChangeListener(this);
        inputDone.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(@NonNull View v) {
                        focusHolder.requestFocus();
                    }
                });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // configure required transitions
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new Fade());
        transitionSet.addTransition(new ChangeBounds());
        TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.layout_container), transitionSet);

        // show/hide bottom transition panel
        findViewById(R.id.translation_panel).setVisibility(hasFocus ? View.INVISIBLE : View.VISIBLE);

        // move up/down input panel with size of toolbar
        View input = findViewById(R.id.input_view);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)
                input.getLayoutParams();
        params.topMargin = hasFocus ? -mToolbar.getMeasuredHeight() : 0;
        input.setLayoutParams(params);

        // show/hide done button
        findViewById(R.id.input_done).setVisibility(hasFocus ? View.VISIBLE : View.GONE);
    }
}
