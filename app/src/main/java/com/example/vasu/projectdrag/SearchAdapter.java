package com.example.vasu.projectdrag;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    Context context;
    private ArrayList<String> ssonameList;
    private ArrayList<String> addressList;
    private ArrayList<String> profilepicList;
    StorageReference storageReference;


    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView ssoname,address;
        RelativeLayout parentLayout;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = (ImageView)itemView.findViewById(R.id.id_profilepic);
            ssoname = (TextView)itemView.findViewById(R.id.id_name);
            address = (TextView)itemView.findViewById(R.id.id_address);
            parentLayout = (RelativeLayout)itemView.findViewById(R.id.id_relative);

        }
    }

    public SearchAdapter(Context context, ArrayList<String> ssoNameList, ArrayList<String> addressList,ArrayList<String> profilepicList) {
        this.context = context;
        this.ssonameList = ssoNameList;
        this.addressList = addressList;
        this.profilepicList = profilepicList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_layout,viewGroup,false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder searchViewHolder,final int i) {

        searchViewHolder.ssoname.setText(ssonameList.get(i));
        searchViewHolder.address.setText(addressList.get(i));

        final RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.profile);

        storageReference=FirebaseStorage.getInstance().getReference();

        storageReference.child("profilepics/"+profilepicList.get(i)+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .asBitmap()
                        .load(uri)
                        .into(searchViewHolder.profileImage);
            }
        });


        Log.d("problem",profilepicList.get(i));


        searchViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,SProfile.class);

                intent.putExtra("Uid",profilepicList.get(i));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ssonameList.size();
    }
}
