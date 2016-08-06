package cs275.langfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cs275.langfinder.data.UserLanguageBundle;

public class LanguagesAdapter extends ArrayAdapter<UserLanguageBundle> {

    public LanguagesAdapter(Context context, UserLanguageBundle[] userLanguageBundles) {
        super(context, 0, userLanguageBundles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserLanguageBundle userLanguageBundle = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.languages_item, parent, false);
        }
        // Lookup view for data population
        TextView tvEditLanguagesTop = (TextView) convertView.findViewById(R.id.tvEditLanguagesTop);
        TextView tvEditLanguagesBottom = (TextView) convertView.findViewById(R.id.tvEditLanguagesBottom);

        String isNative = userLanguageBundle.getUserLanguage().getIsNative() ? ", native speaker" : "";
        String wantsToLearn = userLanguageBundle.getUserLanguage().getWantsToLearn() ? ", wants to learn" : "";

        // Populate the data into the template view using the data object
        tvEditLanguagesTop.setText(userLanguageBundle.getLanguage().getName());
        tvEditLanguagesBottom.setText(userLanguageBundle.getLanguageLevel().getName() + isNative + wantsToLearn);

        // Return the completed view to render on screen
        return convertView;
    }
}