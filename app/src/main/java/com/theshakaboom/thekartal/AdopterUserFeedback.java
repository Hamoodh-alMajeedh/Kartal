package com.theshakaboom.thekartal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AdopterUserFeedback extends BaseAdapter {
    Context context;
    ArrayList<PassengerFeedbackList> arrayList;
    DbHelper dbHelper;


    public AdopterUserFeedback(Context context, ArrayList<PassengerFeedbackList> passengerFeedbackLists){
        this.context = context;
        this.arrayList = passengerFeedbackLists;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public PassengerFeedbackList getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        dbHelper = new DbHelper(context);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.card_userfeedback, parent, false);

            holder = new ViewHolder();

            holder.rentalDate = convertView.findViewById(R.id.TxtvCardUserFeedbackRentalDate);
            holder.rentalId = convertView.findViewById(R.id.TxtvCardUserFeedbackRentalID);
            holder.rentalComment = convertView.findViewById(R.id.TxtvCardUserFeedbackComment);
            holder.ratingBar = convertView.findViewById(R.id.TxtvCardUserFeedbackRatingBar);
            holder.owner = convertView.findViewById(R.id.TxtvCardUserFeedbackVehicleOwner);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        PassengerFeedbackList passengerFeedbackList = arrayList.get(position);



/*
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("select * from passenger_feedback where reg_no = ?", new String[]{VehicleID});
        cursor.moveToFirst();

        String HeadText = cursor.getString(0);


 */
        String Owner = passengerFeedbackList.getOwnerId();
        String RentalDate = passengerFeedbackList.getBooking();

        String RentalId = passengerFeedbackList.getFeedbackID();
        String RentalComment = passengerFeedbackList.getDetails();
        int RatingBar = passengerFeedbackList.getRating();


        //holder.username.setText(Username);
        holder.rentalId.setText("Rental ID : " + RentalId);
        holder.rentalComment.setText(RentalComment);
        holder.owner.setText(Owner);
        holder.rentalDate.setText(RentalDate);
        holder.ratingBar.setRating(RatingBar);

        return convertView;
    }

    static class ViewHolder {
        TextView owner, rentalId, rentalComment, rentalDate;
        RatingBar ratingBar;
    }
    private void openDetailActivity(int position) {
        PassengerFeedbackList clickedItem = getItem(position);
        Intent intent = new Intent(context, ActivityDetailedActivity.class);
        // intent.putExtra("bookId",orderId);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
    public void filterList(ArrayList<PassengerFeedbackList> filteredList) {
        this.arrayList = filteredList;
        notifyDataSetChanged();
    }
}
