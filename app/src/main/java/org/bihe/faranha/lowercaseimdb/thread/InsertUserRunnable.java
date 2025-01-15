package org.bihe.faranha.lowercaseimdb.thread;

import android.content.Context;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.objects.User;

public class InsertUserRunnable implements Runnable{

    private final DbManager dbManager;

    private DbResponse<User> dbResponse;
    private User user;
    private String email;

    public InsertUserRunnable(Context context, User user, String email,DbResponse<User> dbResponse) {
        this.dbManager = DbManager.getInstance(context);
        this.user = user;
        this.email = email;
        this.dbResponse = dbResponse;
    }
    @Override
    public void run() {
        String duplicateEmail = dbManager.userDao().searchDuplicate(email);
        if (duplicateEmail.isEmpty() || duplicateEmail.isBlank()) {
            long id = dbManager.userDao().insert(user);
            if (id>0) {
                user.setUserID(id);
                dbResponse.onSuccess(user);
            } else {
                Error error = new Error("Something Went Wrong!");
                dbResponse.onError(error);
            }
        } else {
            Error error = new Error("Email Already Signed Up!");
            dbResponse.onError(error);
        }


    }
}
