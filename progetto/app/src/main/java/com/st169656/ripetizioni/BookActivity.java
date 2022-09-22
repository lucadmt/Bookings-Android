package com.st169656.ripetizioni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.st169656.ripetizioni.activities.HistoryActivity;
import com.st169656.ripetizioni.activities.LoginActivity;
import com.st169656.ripetizioni.adapters.BookingsAdapter;
import com.st169656.ripetizioni.model.Model;
import com.st169656.ripetizioni.model.User;

import java.util.concurrent.ExecutionException;

public class BookActivity extends AppCompatActivity
	{

		MenuItem logout, history;
		RecyclerView rv;
		RecyclerView.LayoutManager layoutManager;
		BookingsManager bookingsManager = Model.getInstance ().getBookingsManager ();

		@Override
		protected void onCreate (Bundle savedInstanceState)
			{
				super.onCreate (savedInstanceState);
				setContentView (R.layout.activity_book);

				setSupportActionBar (findViewById (R.id.toolbar));

				rv = findViewById (R.id.bookings_list);
				layoutManager = new GridLayoutManager (BookActivity.this, 3);
				rv.setLayoutManager (layoutManager);
				rv.setHasFixedSize (true);
				rv.setAdapter (new BookingsAdapter (rv));

				Model.loadBookings (rv);
				if (getUser ())
					{
						Model.loadIncomingBookings (null);
						Model.loadPastBookings (null);
					}

				findViewById (R.id.floatingActionButton).setOnClickListener (
						v ->
						{
							bookingsManager.book ();
							rv.getAdapter ().notifyDataSetChanged ();
						});
			}

		@Override
		protected void onPostResume ()
			{
				super.onPostResume ();
				rv.getAdapter ().notifyDataSetChanged ();
			}

		@Override
		public boolean onCreateOptionsMenu (Menu menu)
			{
				getUser ();
				getMenuInflater ().inflate (R.menu.toolbar_menu, menu);
				logout = menu.findItem (R.id.action_logout);
				history = menu.findItem (R.id.action_history);
				if (Model.getInstance ().getUser () == null)
					{
						logout.setIcon (R.drawable.ic_account_circle);
						history.setEnabled (false);
					}
				return true;
			}

		@Override
		public boolean onOptionsItemSelected (MenuItem item)
			{
				Model m = Model.getInstance ();
				switch (item.getItemId ())
					{
						case R.id.action_logout:
							HttpClient hc = new HttpClient ();
							try
								{
									if (m.getUser () == null)
										{
											startActivity (new Intent (BookActivity.this, LoginActivity.class));
											finish ();
										}
									else
										{
											Boolean logout = (Boolean) hc.request (hc.logout ()).get ();
											if (logout)
												{
													new AlertDialog.Builder (BookActivity.this)
															.setTitle ("Success")
															.setMessage ("You logged out successfully")
															.create ()
															.show ();
													item.setIcon (R.drawable.ic_account_circle);
													history.setEnabled (false);
													m.setUser (null);
													getSharedPreferences ("usr_pref", MODE_PRIVATE).edit ().remove ("user")
															.apply ();
												}
										}
								}
							catch (ExecutionException e)
								{
									e.printStackTrace ();
								}
							catch (InterruptedException e)
								{
									e.printStackTrace ();
								}
							break;

						case R.id.action_history:
							startActivity (new Intent (BookActivity.this, HistoryActivity.class));
							break;
					}
				return super.onOptionsItemSelected (item);
			}

		private boolean getUser()
			{
				if (Model.getInstance ().getUser () == null)
					{
						String usr = getSharedPreferences ("usr_pref", MODE_PRIVATE).getString ("user", null);
						Model.getInstance ().setUser (new Gson ().fromJson (usr, User.class));
					}
				return Model.getInstance ().getUser () != null;
			}
	}
