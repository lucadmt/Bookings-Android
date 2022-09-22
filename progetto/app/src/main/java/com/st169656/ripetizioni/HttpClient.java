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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.st169656.ripetizioni.model.Booking;
import com.st169656.ripetizioni.model.Model;
import com.st169656.ripetizioni.model.User;
import com.st169656.ripetizioni.model.wrapper.BookingResponse;
import com.st169656.ripetizioni.model.wrapper.HUC;
import com.st169656.ripetizioni.model.wrapper.HistoryElementResponse;
import com.st169656.ripetizioni.model.wrapper.HistoryResponse;
import com.st169656.ripetizioni.model.wrapper.Response;
import com.st169656.ripetizioni.model.wrapper.UserCredential;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpClient
	{
		private static final String BASE_URL = "http://192.168.1.64:8080/api?";
		private static ExecutorService threadPool = Executors.newFixedThreadPool (5);

		public HttpClient ()
			{
			}

		public <T> Future <T> request (Callable r)
			{
				return threadPool.submit (r);
			}

		public Callable <Response> login (UserCredential u)
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=login");
					Response result = huc.post (new Gson ().toJson (u));
					Model.getInstance ().setUser (result.toObj (new TypeToken <User> () {}.getType ()));
					return result;
				};
			}

		public Callable <Boolean> logout ()
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=logout&by_user=" + Model.getInstance ().getUser ().getId ());
					String response = huc.rawGet ();
					return new Gson ().fromJson (response, Boolean.class);
				};
			}

		public Callable <HistoryElementResponse> book (int booking_id)
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=book&by_user=" + Model.getInstance ().getUser ().getId () +
														 "&booking_id=" + booking_id);
					String response = huc.rawGet ();
					Type type = new TypeToken <HistoryElementResponse> () {}.getType ();
					return new Gson ().fromJson (response, type);
				};
			}

		public Callable <HistoryElementResponse> unbook (int booking_id)
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=unbook&by_user=" + Model.getInstance ().getUser ().getId ()
														 + "&booking_id=" + booking_id);
					String res = huc.rawGet ();
					Type type = new TypeToken <HistoryElementResponse> () {}.getType ();
					return new Gson ().fromJson (res, type);
				};
			}

		public Callable <ArrayList <Booking>> getBookings ()
			{
				return () ->
				{
					int idToSearch = Model.getInstance ().getUser () != null ? Model.getInstance ().getUser ().getId () : 0;
					HUC huc = new HUC (BASE_URL + "method=getBookings&by_user=" + idToSearch);
					String res = huc.rawGet ();
					Type type = new TypeToken <ArrayList <Booking>> () {}.getType ();
					return new Gson ().fromJson (res, type);
				};
			}

		public Callable <BookingResponse> getIncomingBookings ()
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=getIncomingBookings&id=" + Model.getInstance ().getUser ()
																																							 .getId ());
					String r = huc.rawGet ();
					Type t = new TypeToken <BookingResponse> () {}.getType ();
					return new Gson ().fromJson (r, t);
				};
			}

		public Callable <HistoryResponse> getPastBookings ()
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=getPastBookings&id=" + Model.getInstance ().getUser ().getId ());
					String r = huc.rawGet ();
					Type t = new TypeToken <HistoryResponse> () {}.getType ();
					return new Gson ().fromJson (r, t);
				};
			}
	}
