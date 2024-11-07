package com.book.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {
		
		@Id
		private int isbn;
		private String title;
		private double price;
		
		public Book() {
			// TODO Auto-generated constructor stub
		}

		public Book(int isbn, String title, double price) {
			super();
			this.isbn = isbn;
			this.title = title;
			this.price = price;
		}

		public int getIsbn() {
			return isbn;
		}

		public void setIsbn(int isbn) {
			this.isbn = isbn;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "Books [isbn=" + isbn + ", title=" + title + ", price=" + price + "]";
		}		
	
}
