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

import android.app.AlertDialog;
import android.content.Context;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Objects;

public class Response
	{
		private boolean key;
		private String value;

		public Response (boolean value, String response)
			{
				this.key = value;
				this.value = response;
			}

		public boolean getKey ()
			{
				return key;
			}

		public String getValue ()
			{
				return value;
			}

		public <T> T toObj (Type t)
			{
				if (key)
					return new Gson ().fromJson (value, t);
				else
					return null;
			}

		public void dispatch (Context context)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder (context);
				if (key)
					builder.setTitle ("Success").setMessage (value);
				else
					builder.setTitle ("Failure").setMessage (value);
				builder.setPositiveButton ("Ok", (dialog, which) ->
				{

				});
				builder.create ().show ();
			}

		@Override
		public boolean equals (Object o)
			{
				if (this == o) return true;
				if (! (o instanceof Response)) return false;
				Response response = (Response) o;
				return Objects.equals (key, response.key) &&
							 Objects.equals (getValue (), response.getValue ());
			}

		@Override
		public int hashCode ()
			{
				return Objects.hash (key, getValue ());
			}

		@Override
		public String toString ()
			{
				return "Response{" +
							 "key=" + key +
							 ", value='" + value + '\'' +
							 '}';
			}
	}
