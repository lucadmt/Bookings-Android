package com.st169656.ripetizioni.model;

import java.util.Objects;

public class User
	{
		private int id;
		private String username;
		private String password;
		private Role role;

		public User (int id, String username, String password, Role role)
			{
				this.id = id;
				this.username = username;
				this.password = password;
				this.role = role;
			}

		public int getId ()
			{
				return id;
			}

		public String getUsername ()
			{
				return username;
			}

		public String getPassword ()
			{
				return password;
			}

		public Role getRole ()
			{
				return role;
			}

		@Override
		public boolean equals (Object o)
			{
				if (this == o) return true;
				if (! (o instanceof User)) return false;
				User user = (User) o;
				return getId () == user.getId () &&
							 Objects.equals (getUsername (), user.getUsername ()) &&
							 Objects.equals (getPassword (), user.getPassword ()) &&
							 Objects.equals (getRole (), user.getRole ());
			}

		@Override
		public int hashCode ()
			{
				return Objects.hash (getId (), getUsername (), getPassword (), getRole ());
			}

		@Override
		public String toString ()
			{
				return "User{" +
							 "id=" + id +
							 ", username='" + username + '\'' +
							 ", password='" + password + '\'' +
							 ", role=" + role +
							 '}';
			}
	}
