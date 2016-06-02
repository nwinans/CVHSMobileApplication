package com.benrcarvergmail.cvhsmobileapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Benjamin on 5/17/2016.
 */
public class ClassesActivity extends Activity {

    /* References to Text Views
    private TextView mTextViewClassesOne;
    private TextView mTextViewClassesTwo;
    private TextView mTextViewClassesThree;
    private TextView mTextViewClassesFour;
    private TextView mTextViewClassesFive;
    private TextView mTextViewClassesSix;
    private TextView mTextViewClassesSeven; */

    // References to Edit Text views
    private EditText mEditTextClassesOne;
    private EditText mEditTextClassesTwo;
    private EditText mEditTextClassesThree;
    private EditText mEditTextClassesFour;
    private EditText mEditTextClassesFive;
    private EditText mEditTextClassesSix;
    private EditText mEditTextClassesSeven;

    private boolean editsMade;

    private static final String TAG = "ClassesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call the super onCreate method, passing savedInstanceState
        super.onCreate(savedInstanceState);
        // Set the content view (XML file to render what the user sees) to activity_main.xml
        setContentView(R.layout.activity_classes);

        // Commented out because we do not need references to these text views. They're just
        // the numbers for each period displayed to the left of each edit text.
        /* mTextViewClassesOne = (TextView) findViewById(R.id.text_view_classes_one);
        mTextViewClassesTwo = (TextView) findViewById(R.id.text_view_classes_two);
        mTextViewClassesThree = (TextView) findViewById(R.id.text_view_classes_three);
        mTextViewClassesFour = (TextView) findViewById(R.id.text_view_classes_four);
        mTextViewClassesFive = (TextView) findViewById(R.id.text_view_classes_five);
        mTextViewClassesSix = (TextView) findViewById(R.id.text_view_classes_six);
        mTextViewClassesSeven = (TextView) findViewById(R.id.text_view_classes_seven); */

        mEditTextClassesOne = (EditText) findViewById(R.id.edit_text_classes_one);
        mEditTextClassesTwo = (EditText) findViewById(R.id.edit_text_classes_two);
        mEditTextClassesThree = (EditText) findViewById(R.id.edit_text_classes_three);
        mEditTextClassesFour = (EditText) findViewById(R.id.edit_text_classes_four);
        mEditTextClassesFive = (EditText) findViewById(R.id.edit_text_classes_five);
        mEditTextClassesSix = (EditText) findViewById(R.id.edit_text_classes_six);
        mEditTextClassesSeven = (EditText) findViewById(R.id.edit_text_classes_seven);

        // Watches to see if any of the EditText views change (which means the user made some edits)
        TextWatcher textWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
                // Do nothing...
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing...
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                editsMade = true;
            }
        };

        // Set each EditText to have the Text Watcher as it's text changed listener
        mEditTextClassesOne.addTextChangedListener(textWatcher);
        mEditTextClassesTwo.addTextChangedListener(textWatcher);
        mEditTextClassesThree.addTextChangedListener(textWatcher);
        mEditTextClassesFour.addTextChangedListener(textWatcher);
        mEditTextClassesFive.addTextChangedListener(textWatcher);
        mEditTextClassesSix.addTextChangedListener(textWatcher);
        mEditTextClassesSeven.addTextChangedListener(textWatcher);

        Button buttonConfirm = (Button) findViewById(R.id.button_classes_confirm);

        // This button saves the user's changes
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ClassesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Save Changes")
                        .setMessage("Are you sure you want to save changes?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveEditTextChanges();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        ImageButton buttonTrashOne = (ImageButton) findViewById(R.id.button_classes_trash_one);
        ImageButton buttonTrashTwo = (ImageButton) findViewById(R.id.button_classes_trash_two);
        ImageButton buttonTrashThree = (ImageButton) findViewById(R.id.button_classes_trash_three);
        ImageButton buttonTrashFour = (ImageButton) findViewById(R.id.button_classes_trash_four);
        ImageButton buttonTrashFive = (ImageButton) findViewById(R.id.button_classes_trash_five);
        ImageButton buttonTrashSix = (ImageButton) findViewById(R.id.button_classes_trash_six);
        ImageButton buttonTrashSeven = (ImageButton) findViewById(R.id.button_classes_trash_seven);

        buttonTrashOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextClassesOne.setText("");
                editsMade = true;
            }
        });

        buttonTrashTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextClassesTwo.setText("");
                editsMade = true;
            }
        });

        buttonTrashThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextClassesThree.setText("");
                editsMade = true;
            }
        });

        buttonTrashFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextClassesFour.setText("");
                editsMade = true;
            }
        });

        buttonTrashFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextClassesFive.setText("");
                editsMade = true;
            }
        });

        buttonTrashSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextClassesSix.setText("");
                editsMade = true;
            }
        });

        buttonTrashSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextClassesSeven.setText("");
                editsMade = true;
            }
        });

        // Load the user's classes into the EditText views as hints
        loadEditTextHints();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (editsMade) {
            // Confirm that the user actually wants to update their classes
            AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(ClassesActivity.this);

            // Create a LayoutInflater to inflate our custom dialog layout
            LayoutInflater inflater = getLayoutInflater();

            // Create a view and inflate the custom dialog layout for the view
            View dialogView = inflater.inflate(R.layout.custom_alert_dialog_classes_confirm, null);

            Button buttonSave = (Button)
                    dialogView.findViewById(R.id.button_classes_warning_save_and_exit);
            Button buttonDiscard = (Button)
                    dialogView.findViewById(R.id.button_classes_warning_discard_and_exit);
            Button buttonCancel = (Button)
                    dialogView.findViewById(R.id.button_classes_warning_cancel);

            alertDialogBuilder.setView(dialogView);
            final AlertDialog alertDialog = alertDialogBuilder.create();

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveEditTextChanges();
                    finish();
                }
            });

            buttonDiscard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        } else {
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadEditTextHints();
        editsMade = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadEditTextHints();
        editsMade = false;
    }

    @Override
    public void onRestart() {
        super.onRestart();
        loadEditTextHints();
        editsMade = false;
    }

    // Save the user's changes
    private void saveEditTextChanges() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("period_one", mEditTextClassesOne.getText().toString());
        editor.putString("period_two", mEditTextClassesTwo.getText().toString());
        editor.putString("period_three", mEditTextClassesThree.getText().toString());
        editor.putString("period_four", mEditTextClassesFour.getText().toString());
        editor.putString("period_five", mEditTextClassesFive.getText().toString());
        editor.putString("period_six", mEditTextClassesSix.getText().toString());
        editor.putString("period_seven", mEditTextClassesSeven.getText().toString());

        editor.apply();

        editsMade = false;
    }

    // Loads the EditText view's hints to be the user's specified classes
    private void loadEditTextHints() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mEditTextClassesOne.setText(sharedPreferences.getString("period_one", "Period 1: "));
        mEditTextClassesTwo.setText(sharedPreferences.getString("period_two", "Period 2: "));
        mEditTextClassesThree.setText(sharedPreferences.getString("period_three", "Period 3: "));
        mEditTextClassesFour.setText(sharedPreferences.getString("period_four", "Period 4: "));
        mEditTextClassesFive.setText(sharedPreferences.getString("period_five", "Period 5: " ));
        mEditTextClassesSix.setText(sharedPreferences.getString("period_six", "Period 6: "));
        mEditTextClassesSeven.setText(sharedPreferences.getString("period_seven", "Period 7: "));
    }
}

