package com.theshakaboom.thekartal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import java.util.Random;

public class ActivityOtpVarification extends AppCompatActivity {

    EditText phone, otp;
    Button submit, requestOtp;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_varification);

        phone = findViewById(R.id.EdtOtpVerificationPhone);
        otp = findViewById(R.id.EdtOtpVerificationOtp);
        submit = findViewById(R.id.BtnOtpVerificationSend);
        requestOtp = findViewById(R.id.BtnOtpVerificationRequestOtp);

        requestOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OtpVerificationTask().execute();
            }
        });


    }

    public static void otpvarification() throws IOException {
        // This URL is used for sending messages
        String myURI = "https://api.bulksms.com/v1/messages";

        // change these values to match your own account
        String myUsername = "kartal_dev";
        String myPassword = ".3W-#VSAzgYWsZ*";

        Random random = new Random();
        int otpMin = 1000;
        int otpMax = 9999;
        int otp = random.nextInt((otpMax - otpMin) + otpMin);
        String number = "+94715442951";


        // the details of the message we want to send
        String myData = "{to: \""+ number +"\", body: \"Your OTP : "+ otp +"\"}";

        // if your message does not contain unicode, the "encoding" is not required:
        // String myData = "{to: \"1111111\", body: \"Hello Mr. Smith!\"}";

        // build the request based on the supplied settings
        URL url = null;
        try {
            url = new URL(myURI);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection request = null;
        try {
            request = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        request.setDoOutput(true);

        // supply the credentials
        String authStr = myUsername + ":" + myPassword;
        String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes());
        request.setRequestProperty("Authorization", "Basic " + authEncoded);

        // we want to use HTTP POST
        try {
            request.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        request.setRequestProperty( "Content-Type", "application/json");

        // write the data to the request
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(request.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.write(myData);
        out.close();

        // try ... catch to handle errors nicely
        try {
            // make the call to the API
            InputStream response = request.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(response));
            String replyText;
            while ((replyText = in.readLine()) != null) {
                System.out.println(replyText);
            }
            in.close();
        } catch (IOException ex) {
            System.out.println("An error occurred:" + ex.getMessage());
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getErrorStream()));
            // print the detail that comes with the error
            String replyText;
            while ((replyText = in.readLine()) != null) {
                System.out.println(replyText);
            }
            in.close();
        }
        request.disconnect();
    }

    private class OtpVerificationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                otpvarification();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

}