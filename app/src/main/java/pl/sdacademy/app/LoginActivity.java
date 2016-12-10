package pl.sdacademy.app;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sdacademy.app.model.Account;

public class LoginActivity extends AppCompatActivity {

    private static final int READ_WRITE_CONTACTS_REQUEST = 123;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.permissionMessage)
    TextView messageText;

    @BindView(R.id.noConnectionMessage)
    TextView noConnectionMessageText;

    @BindView(R.id.appLogo)
    ImageView logoImageView;

    @BindView(R.id.loginEdit)
    EditText loginEditText;

    @BindView(R.id.passwordEdit)
    EditText passwordEditText;

    @BindView(R.id.content)
    LinearLayout content;

    private BroadcastReceiver networkConnectionReceiver;
    private IntentFilter networkConnectionIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (dontHavePermission(Manifest.permission.READ_CONTACTS) || dontHavePermission(Manifest.permission.WRITE_CONTACTS)) {
            if (shoudShowPermissionRationale(Manifest.permission.READ_CONTACTS) && shoudShowPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                showRationaleDialog();
            } else {
                requestReadWriteContactsPermission();
            }
        } else {
            loginButton.setEnabled(true);
            messageText.setVisibility(View.INVISIBLE);
        }

        Animation rotation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.image_anim);
        logoImageView.startAnimation(rotation);

        networkConnectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isConnected()) {
                    loginButton.setEnabled(true);
                    noConnectionMessageText.setVisibility(View.GONE);
                } else {
                    loginButton.setEnabled(false);
                    noConnectionMessageText.setVisibility(View.VISIBLE);
                }
            }
        };

        networkConnectionIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        checkForAccount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkConnectionReceiver, networkConnectionIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(networkConnectionReceiver);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.loginButton)
    public void login() {
        Intent startMainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(startMainActivityIntent);
    }

    @OnClick(R.id.buttonRegister)
    public void register() {
        Intent createAccountIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(createAccountIntent);
    }

    private void checkForAccount() {
        Intent intent = getIntent();
        Account account = (Account) intent.getSerializableExtra(RegisterActivity.EXTRA_ACCOUNT);
        if (account != null) {
            loginEditText.setText(account.getEmail());
            passwordEditText.setText(account.getPassword());
            Toast.makeText(this, R.string.account_create, Toast.LENGTH_LONG).show();
        }
    }

    private boolean dontHavePermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean shoudShowPermissionRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }

    private void requestReadWriteContactsPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                READ_WRITE_CONTACTS_REQUEST);
    }

    private void showRationaleDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestReadWriteContactsPermission();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .create()
                .show();
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_WRITE_CONTACTS_REQUEST: {
                if (grantResults.length >= 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // nadano uprawnienie
                    loginButton.setEnabled(true);
                    messageText.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
