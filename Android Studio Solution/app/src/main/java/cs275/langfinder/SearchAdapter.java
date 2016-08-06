package cs275.langfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cs275.langfinder.data.User;
import cs275.langfinder.data.UserLanguage;
import cs275.langfinder.data.UserProfile;
import cs275.langfinder.data.UserProfileBundle;

class SearchAdapter extends ArrayAdapter<UserProfileBundle> {

    SearchAdapter(Context context, UserProfileBundle[] userProfileBundles) {
        super( context, R.layout.search_item, userProfileBundles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater searchInflater = LayoutInflater.from(getContext());
        View searchView = searchInflater.inflate(R.layout.search_item, parent, false);

        UserProfileBundle userProfileBundle = getItem(position);
        User user = userProfileBundle.getUser();
        UserProfile userProfile = userProfileBundle.getUserProfile();
        String name = user.getFirstName() + " " + user.getLastName();
        String lookingFor = userProfile.getLookingForText();
        TextView txtName = (TextView) searchView.findViewById(R.id.tvEditLanguagesTop);
        TextView txtInfo = (TextView) searchView.findViewById(R.id.tvEditLanguagesBottom);
        txtName.setText(name);
        txtInfo.setText(lookingFor);
        return searchView;
    }
}
