/*
 * Copyright 2019 Luca D'Amato
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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.st169656.ripetizioni.BookingsManager;
import com.st169656.ripetizioni.R;
import com.st169656.ripetizioni.adapters.HistoryAdapter;
import com.st169656.ripetizioni.adapters.IncomingBookingsAdapter;

public class HistoryActivity extends AppCompatActivity
	{

		RecyclerView incomingBookings, pastBookings;

		public static class RecyclerPairHolder {
			public RecyclerView incoming;
			public RecyclerView past;
			private BookingsManager bookingsManager = new BookingsManager ();

			public RecyclerPairHolder (RecyclerView rv1, RecyclerView rv2)
				{
					if (rv1 != null && rv2 != null)
						{
							this.incoming = rv1;
							this.past = rv2;
						}
				}

			public void update()
				{
					past.getAdapter ().notifyDataSetChanged ();
					incoming.getAdapter ().notifyDataSetChanged ();
				}
		}

		@Override
		protected void onCreate (Bundle savedInstanceState)
			{
				super.onCreate (savedInstanceState);
				setContentView (R.layout.activity_history);

				setSupportActionBar (findViewById (R.id.historybar));

				getSupportActionBar ().setTitle ("History");

				incomingBookings = findViewById (R.id.incomingBookings);
				pastBookings = findViewById (R.id.pastBookings);

				incomingBookings.setLayoutManager (new GridLayoutManager (HistoryActivity.this, 3));
				pastBookings.setLayoutManager (new GridLayoutManager (HistoryActivity.this, 3));

				incomingBookings.setHasFixedSize (true);
				pastBookings.setHasFixedSize (true);

				RecyclerPairHolder rph = new RecyclerPairHolder (incomingBookings, pastBookings);

				incomingBookings.setAdapter (new IncomingBookingsAdapter (rph));
				pastBookings.setAdapter (new HistoryAdapter (rph));

			}
	}
