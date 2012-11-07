package com.example.formularz;

import android.R.string;

public class FormularzData {

	string name;
	string surname;
	int id;
	string description;
	string blog;
	string languages;
	string colours;
	string birthDate;
	string phone;
	string gender;
	boolean doHaveFbAcc;
	int doHaveFbSince;

	public string getName() {
		return name;
	}

	public void setName(string name) {
		this.name = name;
	}

	public string getSurname() {
		return surname;
	}

	public void setSurname(string surname) {
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public FormularzData() {
		super();
	}

	public FormularzData(string name, string surname, int id,
			string description, string blog, string languages, string colours,
			string birthDate, string phone, string gender, boolean doHaveFbAcc,
			int doHaveFbSince) {
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

	public string getDescription() {
		return description;
	}

	public void setDescription(string description) {
		this.description = description;
	}

	public string getBlog() {
		return blog;
	}

	public void setBlog(string blog) {
		this.blog = blog;
	}

	public string getLanguages() {
		return languages;
	}

	public void setLanguages(string languages) {
		this.languages = languages;
	}

	public string getColours() {
		return colours;
	}

	public void setColours(string colours) {
		this.colours = colours;
	}

	public string getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(string birthDate) {
		this.birthDate = birthDate;
	}

	public string getPhone() {
		return phone;
	}

	public void setPhone(string phone) {
		this.phone = phone;
	}

	public string getGender() {
		return gender;
	}

	public void setGender(string gender) {
		this.gender = gender;
	}

	public boolean isDoHaveFbAcc() {
		return doHaveFbAcc;
	}

	public void setDoHaveFbAcc(boolean doHaveFbAcc) {
		this.doHaveFbAcc = doHaveFbAcc;
	}

	public int getDoHaveFbSince() {
		return doHaveFbSince;
	}

	public void setDoHaveFbSince(int doHaveFbSince) {
		this.doHaveFbSince = doHaveFbSince;
	}

}
