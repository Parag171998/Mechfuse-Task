package com.example.mechfuse.ui.slideshow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mechfuse.R;
import com.google.android.material.navigation.NavigationView;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    ImageView imageView;
    EditText name;
    EditText email;

    ImageView nav_img;
    TextView nav_name;
    TextView nav_email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        imageView = (ImageView) root.findViewById(R.id.profilePic);
        name = root.findViewById(R.id.profileName);
        email = root.findViewById(R.id.profileEmail);


        final NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        nav_img = navigationView.findViewById(R.id.nav_imageView);
        nav_name = navigationView.findViewById(R.id.nav_Name);
        nav_email = navigationView.findViewById(R.id.nav_email);


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nav_name.setText(name.getText().toString());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                nav_email.setText(email.getText().toString());
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);

            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null )
        {
            if(data.getData() != null) {
                Uri selectedImage = data.getData();
                imageView.setImageURI(selectedImage);

                nav_img.setImageURI(selectedImage);
            }
        }
    }
}
