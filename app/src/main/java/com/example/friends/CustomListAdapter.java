package com.example.friends;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CustomListAdapter extends ArrayAdapter<User> {
    LayoutInflater inflater;

    public CustomListAdapter(Context ctx, ArrayList<User> userArrayList) {
        super(ctx, R.layout.friend, userArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        User user = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friend, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView lastMsg = convertView.findViewById(R.id.last_massage);
        TextView time = convertView.findViewById(R.id.time);

        String picture = user.getUser().getProfilePic();
        if (picture != null && picture.startsWith("data:image/jpeg;base64,")) {
            picture = picture.replace("data:image/jpeg;base64,", "");
            byte[] imageBytes = Base64.decode(picture, Base64.DEFAULT);
            Bitmap myPic = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(myPic);
        }


        User.Messagetwo message = user.getLastMessage();
        if (message != null) {
            lastMsg.setText(message.getContent());
            try {
                Date currentDate = new Date();
                Date messageDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(message.getCreated());

                if (isSameDay(currentDate, messageDate)) {
                    String formattedTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(messageDate);
                    time.setText(formattedTime);
                } else {
                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(messageDate);
                    time.setText(formattedDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        userName.setText(user.getUser().getDisplayName());

        return convertView;
    }
    private static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return fmt.format(date1).equals(fmt.format(date2));
    }
}

