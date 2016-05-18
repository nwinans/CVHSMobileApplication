package com.benrcarvergmail.cvhsmobileapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Benjamin on 5/17/2016.
 */
public class ClassesActivity extends Activity {

    // References to Text Views
    private TextView mTextViewClassesOne;
    private TextView mTextViewClassesTwo;
    private TextView mTextViewClassesThree;
    private TextView mTextViewClassesFour;
    private TextView mTextViewClassesFive;
    private TextView mTextViewClassesSix;
    private TextView mTextViewClassesSeven;

    // References to Edit Text views
    private EditText mEditTextClassesOne;
    private EditText mEditTextClassesTwo;
    private EditText mEditTextClassesThree;
    private EditText mEditTextClassesFour;
    private EditText mEditTextClassesFive;
    private EditText mEditTextClassesSix;
    private EditText mEditTextClassesSeven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call the super onCreate method, passing savedInstanceState
        super.onCreate(savedInstanceState);
        // Set the content view (XML file to render what the user sees) to activity_main.xml
        setContentView(R.layout.activity_classes);

        mTextViewClassesOne = (TextView) findViewById(R.id.text_view_classes_one);
        mTextViewClassesTwo = (TextView) findViewById(R.id.text_view_classes_two);
        mTextViewClassesThree = (TextView) findViewById(R.id.text_view_classes_three);
        mTextViewClassesFour = (TextView) findViewById(R.id.text_view_classes_four);
        mTextViewClassesFive = (TextView) findViewById(R.id.text_view_classes_five);
        mTextViewClassesSix = (TextView) findViewById(R.id.text_view_classes_six);
        mTextViewClassesSeven = (TextView) findViewById(R.id.text_view_classes_seven);

        mEditTextClassesOne = (EditText) findViewById(R.id.edit_text_classes_one);
        mEditTextClassesTwo = (EditText) findViewById(R.id.edit_text_classes_two);
        mEditTextClassesThree = (EditText) findViewById(R.id.edit_text_classes_three);
        mEditTextClassesFour = (EditText) findViewById(R.id.edit_text_classes_four);
        mEditTextClassesFive = (EditText) findViewById(R.id.edit_text_classes_five);
        mEditTextClassesSix = (EditText) findViewById(R.id.edit_text_classes_six);
        mEditTextClassesSeven = (EditText) findViewById(R.id.edit_text_classes_seven);
    }
}

