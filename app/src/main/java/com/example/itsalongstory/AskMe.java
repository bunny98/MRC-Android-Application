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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AskMe extends Fragment {

    public String selectedFile = null;
    public String questionString = null;
    public static final int request_code = 1;
    ChooseFileDialog newDialog = new ChooseFileDialog();
    TextView selectedFileView, questionFieldView, answerView;
    EditText question;
    Button submitButton, button;
    static String BASE_URL = "http://172.16.203.14:8000/";
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_askme, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case request_code:
                if (resultCode == getActivity().RESULT_OK) {
                    Bundle bundle = data.getExtras();

                    selectedFile = bundle.getString("selectedFile");

                    selectedFileView.setText("Selected File: " + selectedFile);
                    selectedFileView.setVisibility(View.VISIBLE);
                    questionFieldView.setVisibility(View.VISIBLE);
                    question.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.VISIBLE);

                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            questionString = question.getText().toString();

                            String serverURL = BASE_URL + "getanswer/";
                            JSONObject postparams = new JSONObject();
                            try {
                                postparams.put("filename", selectedFile);
                                postparams.put("question", questionString);
                            }catch (JSONException e){}
                            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, serverURL, postparams,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                answerView.setText("Answer: "+response.getString("answer"));
                                                answerView.setVisibility(View.VISIBLE);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.v("Error", "onErrorResponse: " + error);
                                }
                            });
                            requestQueue.add(stringRequest);
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
        button = getView().findViewById(R.id.chooseFile);
        selectedFileView = getView().findViewById(R.id.selectedFile);
        questionFieldView = getView().findViewById(R.id.questionTextField);
        answerView = getView().findViewById(R.id.answer);
        question = getView().findViewById(R.id.question);
        submitButton = getView().findViewById(R.id.submitButton);
        requestQueue = Volley.newRequestQueue(getContext());

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                newDialog.show(getFragmentManager().beginTransaction(), "Choose File");
            }
        });
    }

}
