package cs275.langfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavBar {

    public NavBar(final Context context, String active) {
        Activity activityGlobal = ((Activity) context);

        LinearLayout navSearch = (LinearLayout) activityGlobal.findViewById(R.id.navSearch);
        LinearLayout navMessages = (LinearLayout) activityGlobal.findViewById(R.id.navMessages);
        LinearLayout navProfile = (LinearLayout) activityGlobal.findViewById(R.id.navProfile);
        LinearLayout navFriends = (LinearLayout) activityGlobal.findViewById(R.id.navFriends);

        ImageView navSearchImage = (ImageView) activityGlobal.findViewById(R.id.navSearchImage);
        ImageView navMessagesImage = (ImageView) activityGlobal.findViewById(R.id.navMessagesImage);
        ImageView navProfileImage = (ImageView) activityGlobal.findViewById(R.id.navProfileImage);
        ImageView navFriendsImage = (ImageView) activityGlobal.findViewById(R.id.navFriendsImage);

        Drawable navSearchDrawOn = context.getResources().getDrawable(R.drawable.android_search_on, null);
        Drawable navMessagesDrawOn = context.getResources().getDrawable(R.drawable.android_email_on, null);
        Drawable navProfileDrawOn = context.getResources().getDrawable(R.drawable.android_person_on, null);
        Drawable navFriendsDrawOn = context.getResources().getDrawable(R.drawable.android_group_on, null);

        TextView navSearchText = (TextView) activityGlobal.findViewById(R.id.navSearchText);
        TextView navMessagesText = (TextView) activityGlobal.findViewById(R.id.navMessagesText);
        TextView navProfileText = (TextView) activityGlobal.findViewById(R.id.navProfileText);
        TextView navFriendsText = (TextView) activityGlobal.findViewById(R.id.navFriendsText);

        Drawable button_selected = context.getResources().getDrawable(R.drawable.button_nav_selected, null);

        switch (active) {
            case "Search":
                navSearchImage.setBackground(navSearchDrawOn);
                navSearchText.setTextColor(Color.parseColor("#f4f4f4"));
                navSearch.setBackground(button_selected);
                break;

            case "Messages":
                navMessagesImage.setBackground(navMessagesDrawOn);
                navMessagesText.setTextColor(Color.parseColor("#f4f4f4"));
                navMessages.setBackground(button_selected);
                break;

            case "Profile":
                navProfileImage.setBackground(navProfileDrawOn);
                navProfileText.setTextColor(Color.parseColor("#f4f4f4"));
                navProfile.setBackground(button_selected);
                break;

            case "Friends":
                navFriendsImage.setBackground(navFriendsDrawOn);
                navFriendsText.setTextColor(Color.parseColor("#f4f4f4"));
                navFriends.setBackground(button_selected);
                break;
        }

        navSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Search.class);
                context.startActivity(intent);
            }
        });

        navMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageList.class);
                context.startActivity(intent);
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Profile.class);
                intent.putExtra("userId", -1);
                context.startActivity(intent);
            }
        });

        navFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FriendsList.class);
                context.startActivity(intent);
            }
        });

    }
}