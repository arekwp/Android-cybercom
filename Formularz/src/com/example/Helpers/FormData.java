package com.example.Helpers;

public class FormData {

	String name;
	String surname;
	int id;
	String description;
	String blog;
	String languages;
	String colours;
	String birthDate;
	String phone;
	String gender;
	int doHaveFbAcc;
	int doHaveFbSince;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public FormData() {
		super();
	}

	public FormData(String name, String surname, String description,
			String blog, String languages, String colours, String birthDate,
			String phone, String gender, int doHaveFbAcc, int doHaveFbSince) {
		super();
		this.name = name;
		this.surname = surname;
		this.description = description;
		this.blog = blog;
		this.languages = languages;
		this.colours = colours;
		this.birthDate = birthDate;
		this.phone = phone;
		this.gender = gender;
		this.doHaveFbAcc = doHaveFbAcc;
		this.doHaveFbSince = doHaveFbSince;
	}

	public FormData(String name, String surname, int id, String description,
			String blog, String languages, String colours, String birthDate,
			String phone, String gender, int doHaveFbAcc, int doHaveFbSince) {
		super();
		this.name = name;
		this.surname = surname;
		this.id = id;
		this.description = description;
		this.blog = blog;
		this.languages = languages;
		this.colours = colours;
		this.birthDate = birthDate;
		this.phone = phone;
		this.gender = gender;
		this.doHaveFbAcc = doHaveFbAcc;
		this.doHaveFbSince = doHaveFbSince;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public String getColours() {
		return colours;
	}

	public void setColours(String colours) {
		this.colours = colours;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getDoHaveFbAcc() {
		return doHaveFbAcc;
	}

	public void setDoHaveFbAcc(int doHaveFbAcc) {
		this.doHaveFbAcc = doHaveFbAcc;
	}

	public int getDoHaveFbSince() {
		return doHaveFbSince;
	}

	public void setDoHaveFbSince(int doHaveFbSince) {
		this.doHaveFbSince = doHaveFbSince;
	}

	@Override
	public String toString()
	{
		return name + ", " + surname + ", " + description + ", " + blog + ", " + colours + ", " + languages; 
	}
}
