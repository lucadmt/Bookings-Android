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

public class Teacher
  {
    private int id;
    private String name;
    private String surname;
    private Course course;

    public Teacher (int id, String name, String surname, Course course)
      {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.course = course;
      }

    public int getId ()
      {
        return id;
      }

    public String getName ()
      {
        return name;
      }

    public String getSurname ()
      {
        return surname;
      }

    public Course getCourse ()
      {
        return course;
      }

    @Override
    public boolean equals (Object o)
      {
        if (this == o) return true;
        if (! (o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return getId () == teacher.getId () &&
               Objects.equals (getName (), teacher.getName ()) &&
               Objects.equals (getSurname (), teacher.getSurname ()) &&
               Objects.equals (getCourse (), teacher.getCourse ());
      }

    @Override
    public int hashCode ()
      {
        return Objects.hash (getId (), getName (), getSurname (), getCourse ());
      }

    @Override
    public String toString ()
      {
        return "Teacher{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", surname='" + surname + '\'' +
               ", course=" + course +
               '}';
      }
  }
