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

public class AdepterVehicls extends BaseAdapter {
    Context context;
    ArrayList<VehicleList> arrayList;
    DbHelper dbHelper;

public AdepterVehicls(Context context, ArrayList<VehicleList> vehicleLists){
    this.context = context;
    this.arrayList = vehicleLists;
}

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public VehicleList getItem(int position) {
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
            convertView = inflater.inflate(R.layout.card_vehicle,parent,false);

            holder = new ViewHolder();
            holder.carFrontImg = convertView.findViewById(R.id.ImgCardVehicleCarFront);
            holder.make = convertView.findViewById(R.id.TxtvCardVehicleVehicleMake);
            holder.model = convertView.findViewById(R.id.TxtvCardVehicleVehicleModel);
            holder.fuel = convertView.findViewById(R.id.TxtvCardVehicleFuelType);
            holder.transmission = convertView.findViewById(R.id.TxtvCardVehicleTransmissionType);
            holder.year = convertView.findViewById(R.id.TxtvCardVehicleVehicleYear);
            holder.ownerName = convertView.findViewById(R.id.TxtvCardVehicleOwnerName);
            holder.vehicleNumber = convertView.findViewById(R.id.TxtvCardVehicleVehicleNumber);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        dbHelper = new DbHelper(context);

        VehicleList vehicleList = arrayList.get(position);
        String Make = vehicleList.getMake();
        String Model = vehicleList.getModel();
        String Fuel = vehicleList.getFuel();;
        String Transmission = vehicleList.getTransmission();
        String Year = String.valueOf(vehicleList.getYear());
        String VehicleNumber = vehicleList.getRegNo();
        String Owner = vehicleList.getOwnerId();
        String CarFront = vehicleList.getCarImageRight();

        if (CarFront != null) {
            File imageFiles = new File(CarFront);
            if (imageFiles.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.getAbsolutePath());
                holder.carFrontImg.setImageBitmap(bitmap);
            }
        }
        holder.vehicleNumber.setText(VehicleNumber);
        holder.year.setText(Year);
        holder.fuel.setText(Fuel);
        holder.transmission.setText(Transmission);
        holder.ownerName.setText(Owner);
        holder.model.setText(Model);
        holder.make.setText(Make);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailActivity(position);
            }
        });

        return convertView;
    }

    static class ViewHolder{

    TextView make;
    TextView model;
    TextView fuel;
    TextView transmission;
    TextView year;
    TextView ownerName;
    TextView vehicleNumber;
    ImageView carFrontImg;

    }
    private void openDetailActivity(int position) {
        VehicleList clickedItem = getItem(position);
        Intent intent = new Intent(context, ActivityVehicleDetails.class);
        intent.putExtra("ID", clickedItem.getRegNo());
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
    public void filterList(ArrayList<VehicleList> filteredList) {
        this.arrayList = filteredList;
        notifyDataSetChanged();
    }

}
