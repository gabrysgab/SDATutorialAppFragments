package pl.sdacademy.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.sdacademy.app.model.Account;
import pl.sdacademy.app.model.Gender;

public class RegisterActivity extends AppCompatActivity {

    public static final String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.firstName)
    EditText firstName;

    @BindView(R.id.lastName)
    EditText lastName;

    @BindView(R.id.birthDate)
    EditText birthDate;

    @BindView(R.id.genderRadioGroup)
    RadioGroup genderGroup;

    @BindView(R.id.street)
    EditText street;

    @BindView(R.id.streetNumber)
    EditText streetNumber;

    @BindView(R.id.postalCode)
    EditText postalCode;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.country)
    EditText country;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.passwordConfirm)
    EditText passwordConfirm;

    @BindView(R.id.newsletterCheck)
    CheckBox newsletter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.buttonRegister)
    public void validateFormAndRegisterUser() {
        Account account = getFormData();

        if (!account.isValid()) {
            showToast(R.string.account_error);
            return;
        }

        if (!isPasswordCorrect()) {
            showToast(R.string.password_error);
            return;
        }

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(EXTRA_ACCOUNT, account);
        startActivity(intent);
    }

    private Account getFormData() {
        Account account = new Account();
        account.setFirstName(firstName.getText().toString());
        account.setLastName(lastName.getText().toString());
        account.setBirthDate(birthDate.getText().toString());
        account.setGender(genderGroup.getCheckedRadioButtonId() == R.id.maleButton ? Gender.MALE : Gender.FEMALE);
        account.setStreet(street.getText().toString());
        account.setStreetNumber(streetNumber.getText().toString());
        account.setPostalCode(postalCode.getText().toString());
        account.setCity(city.getText().toString());
        account.setCountry(country.getText().toString());
        account.setEmail(email.getText().toString());
        account.setPhoneNumber(phoneNumber.getText().toString());
        account.setPassword(password.getText().toString());
        account.setNewsletter(newsletter.isChecked());
        return account;
    }

    private void showToast(int stringResId) {
        Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show();
    }

    private boolean isPasswordCorrect() {
        String password1 = password.getText().toString();
        String password2 = passwordConfirm.getText().toString();
        return password1.equals(password2) && !TextUtils.isEmpty(password1) && password1.length() > 4;
    }
}
