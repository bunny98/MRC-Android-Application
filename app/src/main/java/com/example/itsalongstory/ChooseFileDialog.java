package com.example.itsalongstory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


public class ChooseFileDialog extends DialogFragment {
    private String selectedFile = null;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        final String[] items = {"Android","IPhone","WindowsMobile","Blackberry",
                "WebOS","Ubuntu","Windows7","Max OS X"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_text_view, items);
        ListView listView = (ListView) view.findViewById(R.id.files);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent().putExtra("selectedFile", items[position]);
                getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK, i);
                dismiss();

            }
        });
        return builder.create();
    }
    public String getSelectedFile(){
        return selectedFile;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
