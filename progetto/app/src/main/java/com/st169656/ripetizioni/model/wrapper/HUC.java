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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HUC
	{
		URL url;

		public HUC (String url)
			{
				try
					{
						this.url = new URL (url);
					}
				catch (MalformedURLException mue)
					{
						mue.printStackTrace ();
					}
			}

		public Response get ()
			{
				HttpURLConnection urlConnection = null;
				Response response = null;
				Gson gson = new Gson ();
				try
					{
						urlConnection = (HttpURLConnection) url.openConnection ();
						InputStream in = new BufferedInputStream (urlConnection.getInputStream ());

						Type type = new TypeToken<Response> (){}.getType ();
						response = gson.fromJson (readFromInputStream (in), type);
					}
				catch (IOException ioe)
					{
						ioe.printStackTrace ();
					}
				finally
					{
						urlConnection.disconnect ();
					}
				return response;
			}

		public Response post (String content)
			{
				HttpURLConnection urlConnection = null;
				Response response = null;
				Gson gson = new Gson ();
				try
					{
						urlConnection = (HttpURLConnection) url.openConnection ();
						urlConnection.setDoOutput (true);
						urlConnection.setChunkedStreamingMode (0);

						BufferedOutputStream out = new BufferedOutputStream (urlConnection.getOutputStream ());
						out.write (content.getBytes ());
						out.flush ();

						BufferedInputStream in = new BufferedInputStream (urlConnection.getInputStream ());

						Type type = new TypeToken<Response> (){}.getType ();
						response = gson.fromJson (readFromInputStream (in), type);
					}
				catch (IOException ioe)
					{
						ioe.printStackTrace ();
					}
				finally
					{
						urlConnection.disconnect ();
					}
				return response;
			}

		public String rawGet ()
			{
				HttpURLConnection urlConnection = null;
				Gson gson = new Gson ();
				String response = null;
				try
					{
						urlConnection = (HttpURLConnection) url.openConnection ();
						InputStream in = new BufferedInputStream (urlConnection.getInputStream ());
						response = readFromInputStream (in);
					}
				catch (IOException ioe)
					{

					}
				finally
					{
						urlConnection.disconnect ();
					}
				return response;
			}

		public String rawPost (String content)
			{
				HttpURLConnection urlConnection = null;
				String response = null;
				Gson gson = new Gson ();
				try
					{
						urlConnection = (HttpURLConnection) url.openConnection ();
						urlConnection.setDoOutput (true);
						urlConnection.setChunkedStreamingMode (0);

						BufferedOutputStream out = new BufferedOutputStream (urlConnection.getOutputStream ());
						out.write (content.getBytes ());
						out.flush ();

						BufferedInputStream in = new BufferedInputStream (urlConnection.getInputStream ());
						readFromInputStream (in);
					}
				catch (IOException ioe)
					{

					}
				finally
					{
						urlConnection.disconnect ();
					}
				return response;
			}

		private String readFromInputStream(InputStream in) throws IOException
			{
				String res = "";
				int c;
				while ((c = in.read ()) != - 1) res += (char) c;
				return res;
			}
	}
