package com.alvino.mavappextintor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SobreFragment extends Fragment {

    TextView tvGoogle = null;
    TextView tvFacebook = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_sobre));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_sobre, container, false);

        tvGoogle = (TextView) rootLayout.findViewById(R.id.google);
        tvFacebook = (TextView) rootLayout.findViewById(R.id.facebook);

        tvGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirUrl(getResources().getString(R.string.developer_url_google));
            }
        });

        tvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirUrl(getResources().getString(R.string.developer_url_facebook));
            }
        });

        return rootLayout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void abrirUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
