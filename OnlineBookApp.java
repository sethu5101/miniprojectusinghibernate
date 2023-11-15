package com.entity;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.utility.hibernateUtility;

public class OnlineBookApp {
    private SessionFactory sessionFactory;

    public OnlineBookApp() {
        this.sessionFactory = hibernateUtility.getSessionFactory();
    }

    public void addBook(String bookTitle, String author, double price, int stock) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        OnlineBook book = new OnlineBook();
        book.setBookTitle(bookTitle);
        book.setAuthor(author);
        book.setPrice(price);
        book.setStock(stock);

        session.save(book);

        tx.commit();
        session.close();
    }

    public void displayAllBooks() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<OnlineBook> books = session.createQuery("FROM OnlineBook", OnlineBook.class).list();
        for (OnlineBook book : books) {
            System.out.println(book.toString() + "\n");
        }

        session.getTransaction().commit();
        session.close();
    }

    public OnlineBook findBookById(int bookId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        OnlineBook book = session.get(OnlineBook.class, bookId);

        session.getTransaction().commit();
        session.close();

        return book;
    }

    public void purchaseBook(int bookId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        OnlineBook book = session.get(OnlineBook.class, bookId);
        if (book != null && book.getStock() > 0) {
            book.setStock(book.getStock() - 1);
            System.out.println("Purchase successful! Enjoy your book.");
        } else if (book != null && book.getStock() == 0) {
            System.out.println("Sorry, this book is out of stock.");
        } else {
            System.out.println("Book not found. Invalid book ID.");
        }

        tx.commit();
        session.close();
    }
}
