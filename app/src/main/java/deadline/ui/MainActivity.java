package deadline.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.aop.ShopRealmField;
import deadline.annotation.javassist.Restore;
import deadline.annotation.aspect.Toast;
import deadline.aop.R;
import deadline.realm.RealmDbManager;
import deadline.realm.ShopRealm;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Restore
    private int number;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.mTv);
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                number++;
                textView.setText(String.valueOf(number));
                doCount();
            }
        });

        if (savedInstanceState != null) {
            number = savedInstanceState.getInt("NUMBER");
            android.widget.Toast.makeText(this, "reset number from bundle", android.widget.Toast.LENGTH_SHORT).show();
        }
        textView.setText(String.valueOf(number));
    }

    @Toast
    private void doCount() {

    }

    public void shopOfId(long id) {
        Realm realmInstance = RealmDbManager.getInstance().getRealmInstance();
        ShopRealm realmObject = realmInstance.where(ShopRealm.class)
                .equalTo(ShopRealmField.ID, id)
                .findFirst();
        realmInstance.close();
    }
}
