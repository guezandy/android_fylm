package com.fylm.login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fylm.R;
import com.fylm.service.ParseService;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for registering new accounts. This prompts a user for a user name
 * (email address), First and Last name, and a password. After validation, it
 * creates a new ParseUser and redirects the user back to the login page for
 * Authentication.
 */
public class RegisterNewAccountActivity extends Activity {
    private final String TAG = RegisterNewAccountActivity.class.getSimpleName();

    protected EditText mEditUsername;
    protected EditText mEditFirstName;
    protected EditText mEditLastName;
    protected EditText mEditEmailAddress;
    protected EditText mEditPassword;
    protected EditText mEditPasswordConfirm;
    protected TextView mRegisterAccount;
    private ParseService mParseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parse_register);
        mEditUsername = (EditText) findViewById(R.id.username);
        mEditFirstName = (EditText) findViewById(R.id.fname);
        mEditLastName = (EditText) findViewById(R.id.lname);
        mEditEmailAddress = (EditText) findViewById(R.id.unEmail);
        mEditPassword = (EditText) findViewById(R.id.pass);
        mEditPasswordConfirm = (EditText) findViewById(R.id.comPass);
        mRegisterAccount = (TextView) findViewById(R.id.registerButton);
        mRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount(v);
            }
        });


    }

    public List<String> getUserInformation() {
        final List<String> registerDetails = new ArrayList<String>();
        registerDetails.add(0, mEditUsername.getText().toString());
        registerDetails.add(1, mEditPassword.getText().toString());
        registerDetails.add(2, mEditEmailAddress.getText().toString());
        registerDetails.add(3, mEditFirstName.getText().toString());
        registerDetails.add(4, mEditLastName.getText().toString());

        return registerDetails;
    }
    public void registerAccount(View view) {
        if (validateFields()) {
            if (validatePasswordMatch()) {
                //processSignup(view);
                mParseService = new ParseService(view.getContext());
                mParseService.registerNewUser(view.getContext(), getUserInformation());
            } else {
                Toast.makeText(this, "Password doesn't match",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Fields not filled in", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // TODO: FIX THESE VALUES
    private boolean validateFields() {
        if (mEditFirstName.getText().length() > 0
                && mEditLastName.getText().length() > 0
                && mEditEmailAddress.length() > 0
                && mEditPassword.getText().length() > 0
                && mEditPasswordConfirm.getText().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePasswordMatch() {
        if (mEditPassword.getText().toString()
                .equals(mEditPasswordConfirm.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calls the createUserWithUsername method of the Parse to create a new
     * user. After sign-up is successful, the First and Last name are added to
     * the user, and an Intent is instantiated to bring the user back to the
     * login page to confirm authentication.
     *
     * Note that a user is not Authorized here to the Android Account Manager,
     * but rather is added to AccountManager upon successful authentication.
     */
    public void processSignup(final View view) {
        Toast.makeText(this, "Creating user...", Toast.LENGTH_SHORT).show();

        final ParseUser user = new ParseUser();
        // username is set to email
        user.setUsername(mEditEmailAddress.getText().toString());
        user.setPassword(mEditPassword.getText().toString());
        user.setEmail(mEditEmailAddress.getText().toString().toLowerCase());

        //TODO: set other fields in a user object
        user.put("first_name", mEditFirstName.getText().toString());
        user.put("last_name", mEditLastName.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(
                            view.getContext(),
                            "Registration Successful\nSending Confirmation Email",
                            Toast.LENGTH_SHORT).show();
                    // TODO: Email notification???
                    user.setEmail(user.getEmail());
                    Intent i = new Intent(RegisterNewAccountActivity.this,
                            ParseLoginDispatchActivity.class);
                    startActivity(i);
                    // Hooray! Let them use the app now.
                } else {
                    Toast.makeText(view.getContext(), "Registration Failed",
                            Toast.LENGTH_SHORT).show();
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "Login failed: "+e.getMessage());
                }
            }
        });
    }
}