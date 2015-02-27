package fr.univ_lille.iut.crosnief;

public class User {
	private String login;
	private String name;
	private String mail;
	
	public User() {}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean equals(Object u) {
		return login.equals(((User) u).login) || name.equals(((User) u).name) || mail.equals(((User) u).mail);
	}

	public String toString() {
		return login + " " + name + " " + mail;
	}
}
