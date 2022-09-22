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

public class Course
  {
    private int id;
    private String courseTitle;

    public Course (int _id, String title)
      {
        this.id = _id;
        this.courseTitle = title;
      }

    public int getId ()
      {
        return id;
      }

    public String getCourseTitle ()
      {
        return courseTitle;
      }

    @Override
    public boolean equals (Object o)
      {
        if (this == o) return true;
        if (! (o instanceof Course)) return false;
        Course course = (Course) o;
        return getId () == course.getId () &&
               Objects.equals (getCourseTitle (), course.getCourseTitle ());
      }

    @Override
    public int hashCode ()
      {
        return Objects.hash (getId (), getCourseTitle ());
      }

    @Override
    public String toString ()
      {
        return "Course{" +
               "id=" + id +
               ", courseTitle='" + courseTitle + '\'' +
               '}';
      }
  }
