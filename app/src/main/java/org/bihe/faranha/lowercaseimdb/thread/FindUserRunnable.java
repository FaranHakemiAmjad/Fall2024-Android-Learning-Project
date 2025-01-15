package org.bihe.faranha.lowercaseimdb.thread;

import android.content.Context;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager;
import org.bihe.faranha.lowercaseimdb.data.objects.User;

public class FindUserRunnable implements Runnable {

    private final DbManager dbManager;

    private DbResponse<User> dbResponse;
    private String email;

    public FindUserRunnable(Context context, String email, DbResponse<User> dbResponse) {
        this.dbManager = DbManager.getInstance(context);
        this.email = email;
        this.dbResponse = dbResponse;
    }
    @Override
    public void run() {
        User user = dbManager.userDao().findUser(email);
        if(user==null) {
            dbResponse.onError(new Error("Account Doesn't Exist!"));
        } else {
            dbResponse.onSuccess(user);
        }
    }
}
