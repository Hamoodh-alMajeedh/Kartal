package com.theshakaboom.thekartal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class AdopterUsers extends BaseAdapter {

    Context context;
    ArrayList<UserList> arrayList;
    DbHelper dbHelper;

    public AdopterUsers(Context context, ArrayList<UserList> userLists){
        this.context = context;
        this.arrayList = userLists;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public UserList getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.card_users,parent,false);

            holder = new ViewHolder();
            holder.profileImg = convertView.findViewById(R.id.CardUsersUserprofilePic);
            holder.firstName = convertView.findViewById(R.id.CardUsersUsernameFirst);
            holder.lastName = convertView.findViewById(R.id.CardUsersUsernameLast);
            holder.phone = convertView.findViewById(R.id.CardUsersUserPhone);
            holder.email = convertView.findViewById(R.id.CardUsersUserEmail);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        dbHelper = new DbHelper(context);

        UserList userList = arrayList.get(position);
        String FirstName = userList.getFirstName();
        String LastName = userList.getLastName();
        String Phone = userList.getPhone();;
        String Email = userList.getEmail();
        String ProfileImg = userList.getDrivingLBack();

        if (ProfileImg != null) {
            File imageFiles = new File(ProfileImg);
            if (imageFiles.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.getAbsolutePath());
                holder.profileImg.setImageBitmap(bitmap);
            }
        }
        holder.firstName.setText(FirstName);
        holder.lastName.setText(LastName);
        holder.phone.setText(Phone);
        holder.email.setText(Email);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailActivity(position);
            }
        });

        return convertView;
    }


    static class ViewHolder{

        TextView firstName;
        TextView lastName;
        TextView email;
        TextView phone;
        ImageView profileImg;

    }
    private void openDetailActivity(int position) {
        UserList clickedItem = getItem(position);
        Intent intent = new Intent(context, ActivityMyProfile.class);
        intent.putExtra("username", clickedItem.getUsername());
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
    public void filterList(ArrayList<UserList> filteredList) {
        this.arrayList = filteredList;
        notifyDataSetChanged();
    }
}
