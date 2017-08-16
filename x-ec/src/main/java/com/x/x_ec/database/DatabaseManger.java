package com.x.x_ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by 熊猿猿 on 2017/8/15/015.
 */

public class DatabaseManger {
    private DaoSession mDaoSession = null;
    private UserProfileDao mUserProfileDao = null;

    public DatabaseManger() {
    }

    public DatabaseManger init(Context context) {
        initDao(context);
        return this;
    }

    public static DatabaseManger getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final DatabaseManger INSTANCE = new DatabaseManger();
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "x_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao() {
        return mUserProfileDao;
    }
}
