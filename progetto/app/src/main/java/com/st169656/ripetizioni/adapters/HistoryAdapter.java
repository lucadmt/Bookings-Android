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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.st169656.ripetizioni.R;
import com.st169656.ripetizioni.BookingsManager;
import com.st169656.ripetizioni.activities.HistoryActivity;
import com.st169656.ripetizioni.model.History;
import com.st169656.ripetizioni.model.Model;

public class HistoryAdapter extends RecyclerView.Adapter <HistoryAdapter.HistoryViewHolder>
	{
		BookingsManager bookingsManager = Model.getInstance ().getBookingsManager ();
		HistoryActivity.RecyclerPairHolder rph;

		public HistoryAdapter (HistoryActivity.RecyclerPairHolder rph)
			{
				this.rph = rph;
			}

		public static class HistoryViewHolder extends RecyclerView.ViewHolder
			{
				// each data item is just a string in this case
				public TextView card_title;
				public TextView card_subheader;
				public TextView card_content;
				private Model model = Model.getInstance ();

				public HistoryViewHolder (@NonNull View view)
					{
						super (view);
						card_title = view.findViewById (R.id.booking_title);
						card_subheader = view.findViewById (R.id.booking_subheader);
						card_content = view.findViewById (R.id.booking_content);
					}

				public void setTextFromElement (History h)
					{
						card_title.setText (h.getBooking ().getFrom ().getCourse ().getCourseTitle ());
						String teacherName = h.getBooking ().getFrom ().getName () + " " + h.getBooking ().getFrom ().getSurname ();
						card_subheader.setText (teacherName);
						card_content.setText (h.getBooking ().getDate ().toString () + "\n\nState: " + h.getState ()
																	 .getTitle () + "\n on " + h.getActionDate ());
					}
			}

		@NonNull
		@Override
		public HistoryAdapter.HistoryViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int type)
			{
				View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.booking_card, parent, false);
				HistoryAdapter.HistoryViewHolder bvh = new HistoryAdapter.HistoryViewHolder (v);
				return bvh;
			}

		@Override
		public void onBindViewHolder (@NonNull HistoryAdapter.HistoryViewHolder bookingsViewHolder, int position)
			{
				bookingsViewHolder.setTextFromElement (bookingsManager.historyAt (position));
			}

		@Override
		public int getItemCount ()
			{
				return bookingsManager.sizeOfHistory ();
			}

		@Override
		public int getItemViewType (int position)
			{
				return position;
			}
	}