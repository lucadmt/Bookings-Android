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

package com.st169656.ripetizioni.model.wrapper;

import com.st169656.ripetizioni.model.Booking;

import java.util.ArrayList;
import java.util.Objects;

public class BookingResponse
	{
		private boolean key;
		private ArrayList<Booking> value;

		public BookingResponse (boolean key, ArrayList <Booking> value)
			{
				this.key = key;
				this.value = value;
			}

		public boolean isKey ()
			{
				return key;
			}

		public void setKey (boolean key)
			{
				this.key = key;
			}

		public ArrayList <Booking> getValue ()
			{
				return value;
			}

		public void setValue (ArrayList <Booking> value)
			{
				this.value = value;
			}

		@Override
		public boolean equals (Object o)
			{
				if (this == o) return true;
				if (! (o instanceof BookingResponse)) return false;
				BookingResponse that = (BookingResponse) o;
				return isKey () == that.isKey () &&
							 Objects.equals (getValue (), that.getValue ());
			}

		@Override
		public int hashCode ()
			{

				return Objects.hash (isKey (), getValue ());
			}

		@Override
		public String toString ()
			{
				return "BookingResponse{" +
							 "key=" + key +
							 ", value=" + value.toString () +
							 '}';
			}
	}
