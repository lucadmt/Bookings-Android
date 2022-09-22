/*
 * Copyright 2018 Luca D'Amato
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.st169656.ripetizioni.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.st169656.ripetizioni.BookActivity;
import com.st169656.ripetizioni.HttpClient;
import com.st169656.ripetizioni.R;
import com.st169656.ripetizioni.model.Model;
import com.st169656.ripetizioni.model.User;
import com.st169656.ripetizioni.model.wrapper.Response;
import com.st169656.ripetizioni.model.wrapper.UserCredential;

import java.util.concurrent.ExecutionException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
	{

		// UI references.
		private AutoCompleteTextView mEmailView;
		private EditText mPasswordView;
		private View mProgressView;
		private View mLoginFormView;

		@Override
		protected void onCreate (Bundle savedInstanceState)
			{
				super.onCreate (savedInstanceState);
				setContentView (R.layout.activity_login);
				// Set up the login form.
				mEmailView = findViewById (R.id.email);

				mPasswordView = findViewById (R.id.password);
				mPasswordView.setOnEditorActionListener (
						(textView, id, keyEvent) ->
						{
							if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL)
								{
									attemptLogin ();
									return true;
								}
							return false;
						});

				Button mEmailSignInButton = findViewById (R.id.email_sign_in_button);
				mEmailSignInButton.setOnClickListener (view -> attemptLogin ());

				mLoginFormView = findViewById (R.id.login_form);
				mProgressView = findViewById (R.id.login_progress);
			}


		/**
		 * Attempts to sign in or register the account specified by the login form.
		 * If there are form errors (invalid email, missing fields, etc.), the
		 * errors are presented and no actual login attempt is made.
		 */
		private void attemptLogin ()
			{

				// Reset errors.
				mEmailView.setError (null);
				mPasswordView.setError (null);

				// Store values at the time of the login attempt.
				String username = mEmailView.getText ().toString ();
				String password = mPasswordView.getText ().toString ();

				boolean cancel = false;
				View focusView = null;

				// Check for a valid password, if the user entered one.
				if (TextUtils.isEmpty (password))
					{
						mPasswordView.setError (getString (R.string.error_invalid_password));
						focusView = mPasswordView;
						cancel = true;
					}

				// Check for a valid email address.
				if (TextUtils.isEmpty (username))
					{
						mEmailView.setError (getString (R.string.error_field_required));
						focusView = mEmailView;
						cancel = true;
					}

				if (cancel)
					{
						// There was an error; don't attempt login and focus the first
						// form field with an error.
						focusView.requestFocus ();
					}
				else
					{
						// Show a progress spinner, and kick off a background task to
						// perform the user login attempt.
						showProgress (true);

						HttpClient hc = new HttpClient ();
						try
							{
								Response r = (Response) hc.request (hc.login (new UserCredential (username, password))).get ();
								//r.dispatch (LoginActivity.this);
								if (r.getKey ())
									{
										Model.getInstance ().setUser (r.toObj (User.class));
										SharedPreferences.Editor editor = getSharedPreferences ("usr_pref", MODE_PRIVATE).edit ();
										editor.putString ("user", r.getValue ());
										editor.apply ();
										startActivity (new Intent (LoginActivity.this, BookActivity.class));
										finish ();
									}
								else r.dispatch (LoginActivity.this);
							}
						catch (ExecutionException | InterruptedException e)
							{
								e.printStackTrace ();
							}
						showProgress (false);
					}
			}

		/**
		 * Shows the progress UI and hides the login form.
		 */
		@TargetApi (Build.VERSION_CODES.HONEYCOMB_MR2)
		private void showProgress (final boolean show)
			{
				// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
				// for very easy animations. If available, use these APIs to fade-in
				// the progress spinner.
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
					{
						int shortAnimTime = getResources ().getInteger (android.R.integer.config_shortAnimTime);

						mLoginFormView.setVisibility (show ? View.GONE : View.VISIBLE);
						mLoginFormView.animate ().setDuration (shortAnimTime).alpha (
								show ? 0 : 1).setListener (new AnimatorListenerAdapter ()
							{
								@Override
								public void onAnimationEnd (Animator animation)
									{
										mLoginFormView.setVisibility (show ? View.GONE : View.VISIBLE);
									}
							});

						mProgressView.setVisibility (show ? View.VISIBLE : View.GONE);
						mProgressView.animate ().setDuration (shortAnimTime).alpha (
								show ? 1 : 0).setListener (new AnimatorListenerAdapter ()
							{
								@Override
								public void onAnimationEnd (Animator animation)
									{
										mProgressView.setVisibility (show ? View.VISIBLE : View.GONE);
									}
							});
					}
				else
					{
						// The ViewPropertyAnimator APIs are not available, so simply show
						// and hide the relevant UI components.
						mProgressView.setVisibility (show ? View.VISIBLE : View.GONE);
						mLoginFormView.setVisibility (show ? View.GONE : View.VISIBLE);
					}
			}
	}

