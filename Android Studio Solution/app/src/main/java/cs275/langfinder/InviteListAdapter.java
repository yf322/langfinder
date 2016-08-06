package cs275.langfinder;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs275.langfinder.data.User;
import cs275.langfinder.data.UserConnectionBundle;
import cs275.langfinder.svcclient.UserController;

/**
 * Created by Yongqiang on 2015/9/3.
 */
class InviteListAdapter extends ArrayAdapter<UserConnectionBundle>{
    public InviteListAdapter(Context context, UserConnectionBundle[] userConnectionBundles) {
        super(context, R.layout.invite_list_item, userConnectionBundles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inviteInflater = LayoutInflater.from(getContext());
        View inviteView = inviteInflater.inflate(R.layout.invite_list_item, parent, false);

        UserConnectionBundle userConnectionBundle = getItem(position);
        final User user = userConnectionBundle.getUser();
        String name = user.getFirstName() + " " + user.getLastName();
        TextView txtName = (TextView) inviteView.findViewById(R.id.txtName);
        txtName.setText( name );

        Button btnAccept = (Button) inviteView.findViewById(R.id.btnAccept);
        Button btnIgnore = (Button) inviteView.findViewById(R.id.btnIgnore);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int toId = user.getId();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                        int userId = userController.getCurrentUserId();
                        userController.updateInvite( userId, toId, true, false );
                        return null;
                    }
                }.execute();
            }
        });

        btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int toId = user.getId();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                        int userId = userController.getCurrentUserId();
                        userController.updateInvite( userId, toId, false, true );
                        return null;
                    }
                }.execute();
            }
        });
        return inviteView;
    }
}
