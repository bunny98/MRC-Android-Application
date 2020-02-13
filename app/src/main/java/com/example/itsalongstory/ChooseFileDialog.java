package com.example.itsalongstory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class ChooseFileDialog extends DialogFragment {
    private String selectedFile = null;
    private String BASE_URL = AskMe.BASE_URL;
    RequestQueue queue;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        ListView listView = view.findViewById(R.id.files);
        builder.setView(view);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_text_view, list);
        listView.setAdapter(adapter);

//        GET REQUEST FOR FILE NAMES
        String serverURL = BASE_URL + "getfilenames";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    for(int i = 0; i<jsonObj.getJSONArray("name").length(); i++){
                        list.add(jsonObj.getJSONArray("name").get(i).toString());
                        adapter.notifyDataSetChanged();
                    }
//                    Toast.makeText(getContext(), "RESPONSE: "+jsonObj.getJSONArray("name"), Toast.LENGTH_LONG).show();
                }catch (JSONException e){Toast.makeText(getContext(), "Eroor JSON ARRAY", Toast.LENGTH_LONG).show();}
//                Toast.makeText(getContext(), "Received files!"+response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error: ", "Error receiving response");
                Toast.makeText(getContext(), "Error receiving files!"+error, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent().putExtra("selectedFile", list.get(position));
                getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK, i);
                dismiss();

            }
        });
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
