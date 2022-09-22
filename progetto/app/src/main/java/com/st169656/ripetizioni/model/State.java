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

public class State
  {
    private int state_id;
    private String state_title;

    public static final int BOOKED = 1;
    public static final int UNAVAILABLE = 2;
    public static final int CANCELLED = 3;
    public static final int AVAILABLE = 4;

    public State (int id, String title)
      {
        this.state_id = id;
        this.state_title = title;
      }

    public int getId ()
      {
        return state_id;
      }

    public String getTitle ()
      {
        return state_title;
      }

    @Override
    public boolean equals (Object o)
      {
        if (this == o) return true;
        if (! (o instanceof State)) return false;
        State state = (State) o;
        return state_id == state.state_id &&
               Objects.equals (state_title, state.state_title);
      }

    @Override
    public int hashCode ()
      {
        return Objects.hash (state_id, state_title);
      }

    @Override
    public String toString ()
      {
        return "State{" +
               "state_id=" + state_id +
               ", state_title='" + state_title + '\'' +
               '}';
      }
  }
