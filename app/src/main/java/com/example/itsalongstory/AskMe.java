package com.example.itsalongstory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AskMe extends Fragment {

    public String selectedFile = null;
    public String questionString = null;
    public static final int request_code = 1;
    ChooseFileDialog newDialog = new ChooseFileDialog();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_askme, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView selectedFileView = getView().findViewById(R.id.selectedFile);
        TextView questionFieldView = getView().findViewById(R.id.questionTextField);
        final EditText question = getView().findViewById(R.id.question);
        Button submitButton = getView().findViewById(R.id.submitButton);
        switch (requestCode) {
            case request_code:
                if (resultCode == getActivity().RESULT_OK) {
                    Bundle bundle = data.getExtras();

                    selectedFile = bundle.getString("selectedFile");

                    selectedFileView.setText("Selected File: "+selectedFile);
                    selectedFileView.setVisibility(View.VISIBLE);
                    questionFieldView.setVisibility(View.VISIBLE);
                    question.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.VISIBLE);
                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            questionString = question.getText().toString();
                            Toast.makeText(getActivity(), "Question Asked: "+questionString, Toast.LENGTH_LONG).show();
                        }
                    });


                } else if (resultCode == getActivity().RESULT_CANCELED) {
                    Log.i("RESULT CANCELED", "onActivityResult: ");
                }
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newDialog.setTargetFragment(this, request_code);
        Button button = getView().findViewById(R.id.chooseFile);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                newDialog.show(getFragmentManager().beginTransaction(), "Choose File");
            }
        });
    }

}
