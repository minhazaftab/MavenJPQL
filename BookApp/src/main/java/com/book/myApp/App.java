package com.book.myApp;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.book.entity.Author;
import com.book.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class App {
	
    public static void getAllBooks(EntityManager em) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
        List<Book> books = query.getResultList();
        books.forEach(book -> System.out.println(book));
    }

    public static void getBooksByAuthor(EntityManager em, String authorName) {
        TypedQuery<Author> query = em.createQuery(
            "SELECT a FROM Author a WHERE a.name = :authorName", Author.class);
        query.setParameter("authorName", authorName);
        Author author = query.getSingleResult();

        author.getBooks().forEach(book -> System.out.println(book));
    }

    public static void getBooksByPriceRange(EntityManager em, double minPrice, double maxPrice) {
        TypedQuery<Book> query = em.createQuery(
            "SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice", Book.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        List<Book> books = query.getResultList();
        books.forEach(book -> System.out.println(book));
    }

    public static void getAuthorByBookISBN(EntityManager em, int isbn) {
        TypedQuery<Author> query = em.createQuery(
            "SELECT a FROM Author a JOIN a.books b WHERE b.isbn = :isbn", Author.class);
        query.setParameter("isbn", isbn);
        Author author = query.getSingleResult();
        System.out.println(author.getName());
    }
	
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaOneToManyApp");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Book b1 = new Book(1001, "Harry Potter", 599.25);
        Book b2 = new Book(1002, "GoT", 799.25);
        Book b3 = new Book(1003, "Wimpy Kid", 399.25);
        Book b4 = new Book(1004, "Wimpy Kid 2", 999.25);
        Book b5 = new Book(1005, "Looking for Alaska", 199.25);
        Book b6 = new Book(1006, "Harry Potter 2", 899.25);

        List<Book> bookList1 = Arrays.asList(b1, b6);
        List<Book> bookList2 = Arrays.asList(b2);
        List<Book> bookList3 = Arrays.asList(b3, b4);
        List<Book> bookList4 = Arrays.asList(b5);

        Author a1 = new Author(1, "JK Rowling");
        Author a2 = new Author(2, "George RR Martin");
        Author a3 = new Author(3, "Jeff Kinney");
        Author a4 = new Author(4, "John Green");

        a1.setBooks(bookList1);
        a2.setBooks(bookList2);
        a3.setBooks(bookList3);
        a4.setBooks(bookList4);

        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);

        em.getTransaction().commit();
        System.out.println("Book and Author details saved in database!");

        Scanner sc = new Scanner(System.in);
        System.out.println("Select an action:");
        System.out.println("1. Get all books");
        System.out.println("2. Get books by author");
        System.out.println("3. Get books by price range");
        System.out.println("4. Get author by book ISBN");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                getAllBooks(em);
                break;
            case 2:
                System.out.println("Enter author name:");
                String authorName = sc.nextLine();
                getBooksByAuthor(em, authorName);
                break;
            case 3:
                System.out.println("Enter minimum price:");
                double minPrice = sc.nextDouble();
                System.out.println("Enter maximum price:");
                double maxPrice = sc.nextDouble();
                getBooksByPriceRange(em, minPrice, maxPrice);
                break;
            case 4:
                System.out.println("Enter book ISBN:");
                int isbn = sc.nextInt();
                getAuthorByBookISBN(em, isbn);
                break;
            default:
                System.out.println("Invalid choice!");
        }

        // Close the EntityManager
        em.close();
        emf.close();
    }
}
