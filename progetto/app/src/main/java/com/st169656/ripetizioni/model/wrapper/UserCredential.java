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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserCredential
  {
    private String username;
    private String userpass;

    public UserCredential (String username, String userpass)
      {
        this.username = username;
        this.userpass = strengthen (userpass);
      }

    public String getUsername ()
      {
        return username;
      }

    public String getUserpass ()
      {
        return userpass;
      }

    public String strengthen (String password)
      {
        return hash (salt (password, "NaCl"));
      }

    private String salt (String word, String salt)
      {
        int oldcntr = 0;
        String saltedword = "";
        for (int i = 3; i < word.length (); i += 3, oldcntr += 3)
          {
            saltedword += word.substring (oldcntr, i);
            saltedword += salt;
          }
        return saltedword;
      }

    private String hash (String str)
      {
        MessageDigest md = null;
        try
          {
            md = MessageDigest.getInstance ("MD5");

          }
        catch (NoSuchAlgorithmException nsae)
          {
            System.err.println ("Cannot encode password securely");
            nsae.printStackTrace ();
            // we should change algorithm, but that's out of the scope
            // of the project, furthermore, it's a server problem
            // in which case we could just change this code to fit our needs
          }
        byte[] bts = md.digest (str.getBytes (StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer ();
        for (byte bt : bts)
          {
            sb.append (Integer.toHexString ((bt & 0xFF) | 0x100).substring (1, 3));
          }
        return sb.toString ();
      }

  }
