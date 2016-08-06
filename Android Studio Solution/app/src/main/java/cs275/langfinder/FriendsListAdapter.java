package cs275.langfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cs275.langfinder.data.UserConnectionBundle;

public class FriendsListAdapter extends ArrayAdapter<UserConnectionBundle> {

    public FriendsListAdapter(Context context, UserConnectionBundle[] userConnectionBundles) {
        super(context, 0, userConnectionBundles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserConnectionBundle userConnectionBundle = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friends_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvFriendsListName = (TextView) convertView.findViewById(R.id.tvFriendsListName);

        // Populate the data into the template view using the data object
        tvFriendsListName.setText(userConnectionBundle.getUser().getFirstName() + " " + userConnectionBundle.getUser().getLastName());

        // Return the completed view to render on screen
        return convertView;
    }
}