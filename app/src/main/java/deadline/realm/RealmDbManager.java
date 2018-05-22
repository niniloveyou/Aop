package deadline.realm;


import android.os.Looper;
import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * @author deadline
 * @time 2018/5/7
 */

public class RealmDbManager {

    private static final int SCHEMA_VERSION = 1;
    private static final String REALM_DB_NAME = "DUOKE_PRO_REALM_DB";

    private static RealmConfiguration configuration;

    private static final RealmDbManager instance = new RealmDbManager();

    public static RealmDbManager getInstance() {
        return instance;
    }

    /**
     * 获取 Realm 示例的配置项
     * @return
     */
    public synchronized RealmConfiguration getRealmConfiguration() {
        if (configuration == null) {
            configuration = new RealmConfiguration.Builder()
                    .name(REALM_DB_NAME)
                    .schemaVersion(SCHEMA_VERSION)
                    .migration(new DRealmMigration())
                    .build();
        }

        return configuration;
    }

    /**
     * 获取 Realm 实例。
     */
    public Realm getRealmInstance() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        Realm realm = Realm.getInstance(getRealmConfiguration());
        realm.setAutoRefresh(true);
        return realm;
    }

    /**
     * realm 版本迁移处理类。
     * 详情见 https://realm.io/cn/docs/java/latest/#migrations
     */
    public static class DRealmMigration implements RealmMigration {

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            RealmSchema schema = realm.getSchema();

            Log.e("DRealmMigration", "oldVersion :" + oldVersion + "newVersion:" + newVersion);
        }

    }

}
