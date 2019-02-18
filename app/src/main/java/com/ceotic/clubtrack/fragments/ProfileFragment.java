package com.ceotic.clubtrack.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.control.AppControl;

import io.realm.Realm;


public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private EditText edtName, edtEmail, edtDni, edtPhone, edtCell, edtUserName, edtPass;
    private AppControl appControl;
    private Realm realm;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        appControl = AppControl.getInstance();

        edtName = view.findViewById(R.id.edt_name_settings_fragment);
        edtEmail = view.findViewById(R.id.edt_email_settings_fragment);
        edtDni = view.findViewById(R.id.edt_dni_settings_fragment);
        edtPhone = view.findViewById(R.id.edt_phone_settings_fragment);
        edtCell = view.findViewById(R.id.edt_cell_settings_fragment);
        edtUserName = view.findViewById(R.id.edt_username_settings_fragment);
        edtPass = view.findViewById(R.id.edt_pass_settings_fragment);

        try {
            edtName.setText(appControl.currentUser.getNameUser());
            edtEmail.setText(appControl.currentUser.getEmail());
            edtDni.setText(appControl.currentUser.getDniUser());
            edtCell.setText(appControl.currentUser.getCellphone());
            edtPhone.setText(appControl.currentUser.getTelephone());
            edtUserName.setText(appControl.currentUser.getUser());
            edtPass.setText(appControl.currentUser.getPassword());

            Log.e(TAG, "asigno los valores");

        } catch (Exception e) {
            Log.e(TAG, "No asigno los valores");
        }
        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
