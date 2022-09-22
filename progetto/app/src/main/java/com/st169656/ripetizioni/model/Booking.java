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

public class Booking implements Comparable<Booking>
  {
    private int booking_id;
    private Teacher booking_from;
    private Timestamp booking_date;
    private State booking_state;

    public Booking (int booking_id, Teacher booking_from, Timestamp booking_date, State state)
      {
        this.booking_id = booking_id;
        this.booking_from = booking_from;
        this.booking_date = booking_date;
        this.booking_state = state;
      }

    public int getId ()
      {
        return booking_id;
      }

    public Teacher getFrom ()
      {
        return booking_from;
      }

    public Timestamp getDate ()
      {
        return booking_date;
      }

    public void setState(State state)
      {
        this.booking_state = state;
      }

    public State getState ()
      {
        return booking_state;
      }

    @Override
    public boolean equals (Object o)
      {
        if (this == o) return true;
        if (! (o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return booking_id == booking.booking_id &&
               Objects.equals (booking_from, booking.booking_from) &&
               Objects.equals (booking_date, booking.booking_date) &&
               Objects.equals (booking_state, booking.booking_state);
      }

    @Override
    public int hashCode ()
      {

        return Objects.hash (booking_id, booking_from, booking_date, booking_state);
      }

    @Override
    public String toString ()
      {
        return "Booking{" +
               "booking_id=" + booking_id +
               ", booking_from=" + booking_from +
               ", booking_date=" + booking_date +
               ", booking_state=" + booking_state +
               '}';
      }

    @Override
    public int compareTo (Booking o)
      {
        if (this.getId () == o.getId ())
          return 0;
        else
          {
            boolean b = this.getId () > o.getId ();
            if (b)
              return +1;
            else
              return -1;
          }
      }
  }
