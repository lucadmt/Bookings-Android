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

package com.st169656.ripetizioni.model;

import java.sql.Timestamp;
import java.util.Objects;

public class History
  {
    private int history_id;
    private Booking booking_id;
    private User booked_by;
    private State booking_state;
    private Timestamp action_date;

    public History (int history_id, Booking booking_id, User booked_by, State booking_state, Timestamp action_date)
      {
        this.history_id = history_id;
        this.booking_id = booking_id;
        this.booked_by = booked_by;
        this.booking_state = booking_state;
        this.action_date = action_date;
      }

    public int getId ()
      {
        return history_id;
      }

    public Booking getBooking ()
      {
        return booking_id;
      }

    public User getBookedBy ()
      {
        return booked_by;
      }

    public State getState ()
      {
        return booking_state;
      }

    public Timestamp getActionDate ()
      {
        return action_date;
      }

		@Override
		public boolean equals (Object o)
			{
				if (this == o) return true;
				if (! (o instanceof History)) return false;
				History history = (History) o;
				return history_id == history.history_id &&
							 Objects.equals (booking_id, history.booking_id) &&
							 Objects.equals (booked_by, history.booked_by) &&
							 Objects.equals (booking_state, history.booking_state) &&
							 Objects.equals (action_date, history.action_date);
			}

		@Override
		public int hashCode ()
			{
				return Objects.hash (history_id, booking_id, booked_by, booking_state, action_date);
			}

		@Override
		public String toString ()
			{
				return "History{" +
							 "history_id=" + history_id +
							 ", booking_id=" + booking_id +
							 ", booked_by=" + booked_by +
							 ", booking_state=" + booking_state +
							 ", action_date=" + action_date +
							 '}';
			}
	}
