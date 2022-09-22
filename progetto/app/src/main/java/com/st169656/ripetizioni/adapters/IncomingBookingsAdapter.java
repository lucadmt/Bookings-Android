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

package com.st169656.ripetizioni.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.st169656.ripetizioni.HttpClient;
import com.st169656.ripetizioni.R;
import com.st169656.ripetizioni.BookingsManager;
import com.st169656.ripetizioni.activities.HistoryActivity;
import com.st169656.ripetizioni.model.Booking;
import com.st169656.ripetizioni.model.Model;
import com.st169656.ripetizioni.model.wrapper.HistoryElementResponse;

import java.util.concurrent.ExecutionException;

public class IncomingBookingsAdapter
		extends RecyclerView.Adapter <IncomingBookingsAdapter.BookingsViewHolder>
	{
		BookingsManager bookingsManager = Model.getInstance ().getBookingsManager ();
		HistoryActivity.RecyclerPairHolder rph;

		public IncomingBookingsAdapter (HistoryActivity.RecyclerPairHolder rph)
			{
				this.rph = rph;
			}

		public static class BookingsViewHolder extends RecyclerView.ViewHolder
			{
				// each data item is just a string in this case
				TextView card_title;
				TextView card_subheader;
				TextView card_content;
				Button cancel_booking;
				private Model model = Model.getInstance ();

				public BookingsViewHolder (@NonNull View view)
					{
						super (view);
						card_title = view.findViewById (R.id.booking_title);
						card_subheader = view.findViewById (R.id.booking_subheader);
						card_content = view.findViewById (R.id.booking_content);
						cancel_booking = view.findViewById (R.id.cancel_button);
					}

				public void setTextFromElement (Booking b)
					{
						card_title.setText (b.getFrom ().getCourse ().getCourseTitle ());
						String teacherName = b.getFrom ().getName () + " " + b.getFrom ().getSurname ();
						card_subheader.setText (teacherName);
						card_content.setText (b.getDate ().toString ());
					}
			}

		@NonNull
		@Override
		public IncomingBookingsAdapter.BookingsViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int type)
			{
				HttpClient hc = new HttpClient ();
				View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.booking_card_cancelable, parent, false);
				IncomingBookingsAdapter.BookingsViewHolder bvh = new IncomingBookingsAdapter.BookingsViewHolder (v);
				bvh.cancel_booking.setOnClickListener (
						(view) ->
						{
							Booking selected = bookingsManager.incomingAt (bvh.getAdapterPosition ());
							bookingsManager.cancelBooking (selected);
							rph.update ();
						}
				);
				return bvh;
			}

		@Override
		public void onBindViewHolder (@NonNull IncomingBookingsAdapter.BookingsViewHolder bookingsViewHolder, int position)
			{
				bookingsViewHolder.setTextFromElement (bookingsManager.incomingAt ((position)));
			}

		@Override
		public int getItemCount ()
			{
				return bookingsManager.sizeOfIncoming ();
			}

		@Override
		public int getItemViewType (int position)
			{
				return position;
			}
	}
