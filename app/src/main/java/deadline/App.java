package deadline;

import android.app.Application;
import android.content.Context;

/**
 * @author deadline
 * @time 2018/5/3
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
