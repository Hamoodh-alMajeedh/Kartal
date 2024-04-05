package com.theshakaboom.thekartal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class AdopterActivity extends BaseAdapter {
    Context context;
    ArrayList<BookingList> arrayList;

    public AdopterActivity(Context context, ArrayList<BookingList>arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public BookingList getItem(int position) {
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
            convertView = inflater.inflate(R.layout.card_activity, parent, false);

            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.CardActivityTitle);
            holder.rentalId = convertView.findViewById(R.id.CardActivityRentalID);
            holder.rentalDate = convertView.findViewById(R.id.CardActivityRentalDate);
            holder.vehicleNo = convertView.findViewById(R.id.CardActivityVehicleNo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        BookingList bookingList = arrayList.get(position);
        String Title = bookingList.getStatus();
        String RentalId = bookingList.getBookingId();
        String RentalDate = bookingList.getStartDate();
        String VehicleNo = bookingList.getVehicleId();


        holder.title.setText(Title);
        if (Objects.equals(Title, "Ongoing")){
            holder.title.setTextColor(0xFFDDAD1B);
        } else if (Objects.equals(Title, "Cancelled")) {
            holder.title.setTextColor(0xFFFF5555);
        } else if (Objects.equals(Title, "Completed")) {
            holder.title.setTextColor(0xFF4CAF50);
        } else if (Objects.equals(Title, "in Review")) {
            holder.title.setTextColor(0xFF0899FF);
        }

        holder.rentalId.setText(RentalId);
        holder.rentalDate.setText(RentalDate);
        holder.vehicleNo.setText(VehicleNo);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailActivity(position);
            }
        });

        return convertView;
    }
    static class ViewHolder {
        TextView title, rentalId, rentalDate, vehicleNo;
    }
    private void openDetailActivity(int position) {
        BookingList clickedItem = getItem(position);
        Intent intent = new Intent(context, ActivityDetailedActivity.class);
        BookingList bookingList = arrayList.get(position);
        String bookID = bookingList.getBookingId();
        intent.putExtra("bookId",bookingList.getBookingId());
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
    public void filterList(ArrayList<BookingList> filteredList) {
        this.arrayList = filteredList;
        notifyDataSetChanged();
    }
}
