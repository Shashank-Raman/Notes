package com.shashankraman.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1);

        if(noteID != -1)
            editText.setText(MainActivity.notes.get(noteID)); //Set the text of the EditText to have what is already in the notes list at that position
        else
        {
            MainActivity.notes.add(""); //Creating an empty note
            noteID = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() { //When the text in the EditText is updated, update the lists accordingly
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                MainActivity.notes.set(noteID, String.valueOf(s)); //Update notes list
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.shashankraman.notes",
                        Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<String>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
}
