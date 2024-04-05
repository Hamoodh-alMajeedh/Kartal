package com.theshakaboom.thekartal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class AdopterVehicleFeedback extends BaseAdapter {
    Context context;
    ArrayList<RentFeedbackList> arrayList;

    public AdopterVehicleFeedback(Context context, ArrayList<RentFeedbackList> rentFeedbackLists){
        this.context = context;
        this.arrayList = rentFeedbackLists;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public RentFeedbackList getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.card_vehiclefeedback, parent, false);

            holder = new ViewHolder();
            holder.username = convertView.findViewById(R.id.TxtvCardVehicleFeedbackUsername);
            holder.rentalId = convertView.findViewById(R.id.TxtvCardVehicleFeedbackRentalID);
            holder.rentalComment = convertView.findViewById(R.id.TxtvCardVehicleFeedbackComment);
            holder.ratingBar = convertView.findViewById(R.id.TxtvCardVehicleFeedbackRatingBar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        RentFeedbackList rentFeedbackList = arrayList.get(position);
        String Username = rentFeedbackList.getUserID();
        String RentalId = rentFeedbackList.getFeedbackID();
        String RentalComment = rentFeedbackList.getDetails();
        int RatingBar = rentFeedbackList.getRating();


        holder.username.setText(Username);
        holder.rentalId.setText(RentalId);
        holder.rentalComment.setText(RentalComment);
        holder.ratingBar.setRating(RatingBar);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailActivity(position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView username, rentalId, rentalComment;
        RatingBar ratingBar;
    }
    private void openDetailActivity(int position) {
        RentFeedbackList clickedItem = getItem(position);
        Intent intent = new Intent(context, ActivityDetailedActivity.class);
        // intent.putExtra("bookId",orderId);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
    public void filterList(ArrayList<RentFeedbackList> filteredList) {
        this.arrayList = filteredList;
        notifyDataSetChanged();
    }
}
