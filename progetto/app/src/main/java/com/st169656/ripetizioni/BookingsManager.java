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

package com.st169656.ripetizioni;

import com.st169656.ripetizioni.model.Booking;
import com.st169656.ripetizioni.model.History;
import com.st169656.ripetizioni.model.wrapper.HistoryElementResponse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookingsManager
	{
		private static List<Booking> integralBookings = new ArrayList ();
		private static List<Booking> bookings = new ArrayList <> ();
		private static List<Booking> selected = new ArrayList <> ();
		private static List<Booking> incomingBookings = new ArrayList <> ();
		private static List<History> pastBookings = new ArrayList<> ();

		private static HttpClient hc = new HttpClient ();

		public BookingsManager()
			{

			}

		public void loadIntegral(ArrayList<Booking> b)
			{
				integralBookings.addAll (b);
				bookings.addAll (b);
				bookings.sort (Booking::compareTo);
			}

		public ArrayList<Integer> getSelectedPositions()
			{
				ArrayList<Integer> i = new ArrayList <> ();
				for (Booking b : selected)
					{
						i.add (bookings.indexOf (b));
					}
				return i;
			}

		public void loadIncoming(ArrayList<Booking> b)
			{
				incomingBookings.addAll (b);
			}

		public void loadPast(ArrayList<History> b)
			{
				pastBookings.addAll (b);
			}

		public void select(Booking b)
			{
				selected.add (b);
				updateBookingsBySelection ();
			}

		public void deSelect(Booking b)
			{
				selected.remove (b);
				updateBookingsBySelection ();
			}

		public void clearSelection()
			{
				selected.clear ();
			}

		public void book()
			{
				for (Booking b : selected)
					{
						try
							{
								HistoryElementResponse r = (HistoryElementResponse) hc.request (hc.book (b.getId ())).get ();
								pastBookings.add (r.getValue ());
							}
						catch (ExecutionException | InterruptedException e)
							{
								e.printStackTrace ();
							}
						incomingBookings.add (b);
					}
				clearSelection ();
				updateBookings ();
			}


		public void cancelBooking(Booking b)
			{
				HistoryElementResponse hr = null;
				try
					{
						hr = (HistoryElementResponse) hc.request (hc.unbook (b.getId ())).get ();
					}
				catch (ExecutionException | InterruptedException e)
					{
						e.printStackTrace ();
					}
				incomingBookings.remove (b);
				pastBookings.add (hr.getValue ());
				updateBookings ();
			}

		public Booking bookingAt(int pos)
			{
				return bookings.get (pos);
			}

		public Booking incomingAt(int pos)
			{
				return incomingBookings.get (pos);
			}

		public History historyAt(int pos)
			{
				return pastBookings.get (pos);
			}

		public boolean isSelected (Booking b)
			{
				return selected.contains (b);
			}

		public int sizeOfBookings()
			{
				return bookings.size ();
			}

		public int sizeOfIncoming()
			{
				return incomingBookings.size ();
			}

		public int sizeOfHistory()
			{
				return pastBookings.size ();
			}

		private void updateBookings()
			{
				bookings.clear ();
				bookings.addAll (integralBookings);
				filterBookings ();
				bookings.sort (Booking::compareTo);
			}

		private void updateBookingsBySelection()
			{
				bookings.clear ();
				bookings.addAll (integralBookings);
				filterBookingsBySelection ();
				bookings.sort (Booking::compareTo);
			}

		private void filterBookings()
			{
				ArrayList<Booking> toRemove = new ArrayList <> ();
				for (Booking i : incomingBookings)
					toRemove.addAll (flaggedForRemoval (i.getDate ()));
				bookings.removeAll (toRemove);
			}

		private void filterBookingsBySelection()
			{
				ArrayList<Booking> toRemove = new ArrayList <> ();
				for (Booking i : selected)
					toRemove.addAll (flaggedForRemoval (i.getDate ()));
				bookings.removeAll (toRemove);
				bookings.addAll (selected);
			}

		private ArrayList<Booking> flaggedForRemoval(Timestamp t)
			{
				ArrayList<Booking> forRemoval = new ArrayList <> ();
				for (Booking b : integralBookings)
					{
						if (b.getDate ().equals (t))
							forRemoval.add (b);
					}
				return forRemoval;
			}
	}
