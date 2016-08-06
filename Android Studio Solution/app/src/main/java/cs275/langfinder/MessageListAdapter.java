package cs275.langfinder;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cs275.langfinder.data.User;
import cs275.langfinder.data.UserMessage;
import cs275.langfinder.data.UserProfileBundle;
import cs275.langfinder.svcclient.UserController;

class MessageListAdapter extends ArrayAdapter<UserMessage> {

    MessageListAdapter(Context context, UserMessage[] messages) {
        super(context, R.layout.message_list_item, messages);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mailInflater = LayoutInflater.from(getContext());
        final View mailView = mailInflater.inflate(R.layout.message_list_item, parent, false);

        final UserMessage message = getItem(position);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                int id = message.getSenderUserId();
                UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                UserProfileBundle user = userController.getUserWithProfile(id);
                String name = user.getUser().getFirstName() + " " + user.getUser().getLastName();
                return name;
            }

            @Override
            protected void onPostExecute(String name) {
                TextView txtname = (TextView) mailView.findViewById(R.id.txtName);
                txtname.setText(name);

            }
        }.execute();

        TextView txtInfo = (TextView) mailView.findViewById(R.id.txtInfo);
        TextView txtDate = (TextView) mailView.findViewById(R.id.txtDate);
        String info = message.getSubject();
        String date = message.getDateSentUTC().toString();
        txtInfo.setText(info);
        txtDate.setText(date);
        return mailView;

    }
}
