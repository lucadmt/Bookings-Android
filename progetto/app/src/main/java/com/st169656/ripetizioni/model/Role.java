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

import java.util.Objects;

public class Role
  {
    private int id;
    private String title;

    public static final int ADMINISTRATOR = 1;
    public static final int CLIENT = 2;

    public Role (int id, String title)
      {
        this.id = id;
        this.title = title;
      }

    public int getId ()
      {
        return id;
      }

    public String getTitle ()
      {
        return title;
      }

    @Override
    public boolean equals (Object o)
      {
        if (this == o) return true;
        if (! (o instanceof Role)) return false;
        Role role = (Role) o;
        return getId () == role.getId () &&
               Objects.equals (getTitle (), role.getTitle ());
      }

    @Override
    public int hashCode ()
      {

        return Objects.hash (getId (), getTitle ());
      }

    @Override
    public String toString ()
      {
        return "Role{" +
               "id=" + id +
               ", title='" + title + '\'' +
               '}';
      }
  }