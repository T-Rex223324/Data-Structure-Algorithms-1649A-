package PACKAGE_NAME.test;

import java.util.Scanner;
import java.util.Random;

class Book {
    String title;
    String code;
    double price;
    int quantity;

    public Book(String title, String code, double price, int quantity) {
        this.title = title;
        this.code = code;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book Name: " + title + " | Code: " + code + " | Price: " + price + " USD | Quantity: " + quantity;
    }
}

class MyArray<T> {
    private Object[] data;
    private int size;

    public MyArray(int capacity) {
        data = new Object[capacity];
        size = 0;
    }

    public void add(T item) {
        if (size == data.length) {
            resize(data.length * 2);
        }
        data[size++] = item;
    }

    public void add(int index, T item) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (size == data.length) {
            resize(data.length * 2);
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = item;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (T) data[index];
    }

    public void set(int index, T item) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        data[index] = item;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null;
        size--;
        if (size > 0 && size == data.length / 3) {
            resize(data.length / 2);
        }
        return removed;
    }

    public boolean contains(T item) {
        return indexOf(item) != -1;
    }

    public int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int newCapacity) {
        Object[] newData = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            result.append(data[i]);
            if (i < size - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}


class MyQueue<T> {
    // Node class to represent each element in the queue
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> front, rear;
    private int size;

    // Constructor to initialize the queue
    public MyQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    // Add an element to the end of the queue (enqueue)
    public void enqueue(T item) {
        Node<T> node = new Node<>(item);
        if (rear != null) {
            rear.next = node;  // Link the last node to the new node
        }
        rear = node;  // Update rear to the new node
        if (front == null) {
            front = rear;  // If the queue was empty, set front to the new node
        }
        size++;
    }

    // Remove and return the first element from the queue (dequeue)
    public T dequeue() {
        if (front == null) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = front.data;
        front = front.next;  // Move front to the next node
        if (front == null) {
            rear = null;  // If the queue is now empty, set rear to null
        }
        size--;
        return item;
    }

    // Return the first element without removing it (peek)
    public T peek() {
        if (front == null) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the size of the queue
    public int size() {
        return size;
    }

    // Return a string representation of the queue
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> current = front;
        while (current != null) {
            result.append(current.data);
            if (current.next != null) {
                result.append(", ");
            }
            current = current.next;
        }
        result.append("]");
        return result.toString();
    }
}

class Order {
    int orderNumber;
    String customerName;
    String address;
    MyArray<Book> booksOrdered = new MyArray<>(10);
    MyArray<Integer> quantities = new MyArray<>(10);
    String status;

    public Order(int orderNumber, String customerName, String address) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.address = address;
        this.status = "Pending";
    }

    public void addBook(Book book, int quantity) {
        booksOrdered.add(book);
        quantities.add(quantity);
    }

    public void processOrder() {
        this.status = "Completed";
    }

    @Override
    public String toString() {
        StringBuilder orderDetails = new StringBuilder("Order Number: " + orderNumber + " | Customer: " + customerName + " | Address: " + address + " | Status: " + status + "\nBooks Ordered:");
        for (int i = 0; i < booksOrdered.size(); i++) {
            orderDetails.append("\n - ").append(booksOrdered.get(i).title).append(" | Quantity: ").append(quantities.get(i));
        }
        return orderDetails.toString();
    }
}

public class Bookstore {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MyArray<Book> bookCatalog = new MyArray<>(10);
    private static final MyQueue<Order> orders = new MyQueue<>();
    private static int orderCounter = 1;

    public static void main(String[] args) {
        bookCatalog.add(new Book("Java Programming", "#1010", 39.99, 10));
        bookCatalog.add(new Book("Python Basics", "#2052", 29.99, 5));
        bookCatalog.add(new Book("C++ Fundamentals", "#8303", 45.50, 8));
        bookCatalog.add(new Book("A", "#8717", 15, 12));
        bookCatalog.add(new Book("Z", "#4291", 1190.99, 2));

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Order");
            System.out.println("2. Process Order");
            System.out.println("3. View All Orders");
            System.out.println("4. Search orders");
            System.out.println("5. Add Book");
            System.out.println("6. View Book");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = getValidIntInput();
            switch (choice) {
                case 1 -> addOrder();
                case 2 -> processOrder();
                case 3 -> viewOrders();
                case 4 -> searchOrders();
                case 5 -> addBook();
                case 6 -> viewBooks();
                case 7 -> {
                    System.out.println("Exiting program...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addOrder() {
        String customerName;
        do {
            System.out.print("Enter customer name: ");
            customerName = scanner.nextLine().trim();
            if (customerName.isEmpty()) {
                System.out.println("Customer name cannot be empty. Please enter a valid name.");
            } else if (!customerName.matches("[a-zA-Z ]+")) {
                System.out.println("Customer name cannot contain numbers or special characters. Please enter a valid name.");
                customerName = "";
            }
        } while (customerName.isEmpty());

        String address;
        do {
            System.out.print("Enter shipping address: ");
            address = scanner.nextLine().trim();
            if (address.isEmpty()) {
                System.out.println("Address cannot be empty. Please enter a valid address.");
            }
        } while (address.isEmpty());

        Order newOrder = new Order(orderCounter++, customerName, address);

        while (true) {
            System.out.println("\nAvailable Books:");
            for (int i = 0; i < bookCatalog.size(); i++) {
                System.out.println((i + 1) + ". " + bookCatalog.get(i));
            }
            System.out.print("Enter book number (or 0 to finish): ");
            int bookIndex = getValidIntInput();

            if (bookIndex == 0) break;
            if (bookIndex < 1 || bookIndex > bookCatalog.size()) {
                System.out.println("Invalid selection. Please try again.");
                continue;
            }

            Book selectedBook = bookCatalog.get(bookIndex - 1);
            int quantity;
            do {
                System.out.print("Enter quantity: ");
                quantity = getValidIntInput();
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than zero. Please enter a valid quantity.");
                } else if (quantity > selectedBook.quantity) {
                    System.out.println("Not enough stock available. Stock available: " + selectedBook.quantity);
                }
            } while (quantity <= 0 || quantity > selectedBook.quantity);

            selectedBook.quantity -= quantity;
            newOrder.addBook(selectedBook, quantity);
        }

        if (!newOrder.booksOrdered.isEmpty()) {
            orders.enqueue(newOrder);
            sortBooksInOrder(newOrder);
            System.out.println("Order added successfully!\n" /*+ newOrder*/);
            viewOrders();
        } else {
            System.out.println("No books added. Order canceled.");
        }
    }

    private static void processOrder() {
        if (orders.isEmpty()) {
            System.out.println("No orders to process.");
            return;
        }

        Order order = orders.dequeue();
        order.processOrder();
        System.out.println("Processing order: " + order);
    }

//    private static void viewOrders() {
//        if (orders.isEmpty()) {
//            System.out.println("No orders found.");
//            return;
//        }
//        System.out.println("\nAll Orders:");
//        MyQueue<Order> tempQueue = new MyQueue<>();
//        while (!orders.isEmpty()) {
//            Order order = orders.dequeue();
//            System.out.println(order);
//            tempQueue.enqueue(order);
//        }
//        while (!tempQueue.isEmpty()) {
//            orders.enqueue(tempQueue.dequeue());
//        }
//    }

    private static void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\nAll Orders:");

        MyQueue<Order> tempQueue = new MyQueue<>();

        // Iterate over all orders
        while (!orders.isEmpty()) {
            Order order = orders.dequeue();

            // Combine books with the same title and add quantities
            MyArray<Book> combinedBooks = new MyArray<>(10);
            MyArray<Integer> combinedQuantities = new MyArray<>(10);

            // Process each book in the order
            for (int i = 0; i < order.booksOrdered.size(); i++) {
                Book book = order.booksOrdered.get(i);
                int quantity = order.quantities.get(i);

                // Check if the book is already in the combined list
                boolean found = false;
                for (int j = 0; j < combinedBooks.size(); j++) {
                    if (combinedBooks.get(j).title.equals(book.title)) {
                        // If found, update the quantity
                        combinedQuantities.set(j, combinedQuantities.get(j) + quantity);
                        found = true;
                        break;
                    }
                }

                // If not found, add the book and quantity to the list
                if (!found) {
                    combinedBooks.add(book);
                    combinedQuantities.add(quantity);
                }
            }

            // Prepare the string to display the combined books and quantities
            StringBuilder orderDetails = new StringBuilder("Order Number: " + order.orderNumber + " | Customer: " + order.customerName + " | Address: " + order.address + " | Status: " + order.status + "\nBooks Ordered:");
            for (int i = 0; i < combinedBooks.size(); i++) {
                orderDetails.append("\n - ").append(combinedBooks.get(i).title).append(" | Quantity: ").append(combinedQuantities.get(i));
            }

            // Print the order details
            System.out.println(orderDetails);

            // Add the order back to the tempQueue
            tempQueue.enqueue(order);
        }

        // Restore the original orders queue
        while (!tempQueue.isEmpty()) {
            orders.enqueue(tempQueue.dequeue());
        }
    }




    private static int getValidIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a valid number: ");
            }
        }
    }

    private static void searchOrders() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().toLowerCase();

        MyQueue<Order> tempQueue = new MyQueue<>();
        boolean found = false;

        while (!orders.isEmpty()) {
            Order order = orders.dequeue();

            if (order.customerName.toLowerCase().contains(keyword) ||
                    order.address.toLowerCase().contains(keyword) ||
                    order.status.toLowerCase().contains(keyword)) {
                System.out.println(order);
                found = true;
            } else {
                for (int i = 0; i < order.booksOrdered.size(); i++) {
                    if (order.booksOrdered.get(i).title.toLowerCase().contains(keyword)) {
                        System.out.println(order);
                        found = true;
                        break;
                    }
                }
            }
            tempQueue.enqueue(order);
        }

        while (!tempQueue.isEmpty()) {
            orders.enqueue(tempQueue.dequeue());
        }

        if (!found) {
            System.out.println("No matching orders found.");
        }

    }

    private static void quickSortBooksByTitle(Book[] books, int low, int high) {
        if (low < high) {
            int pi = partition(books, low, high);
            quickSortBooksByTitle(books, low, pi - 1);
            quickSortBooksByTitle(books, pi + 1, high);
        }
    }

    // Partition method to handle the quick sort partition logic
    private static int partition(Book[] books, int low, int high) {
        String pivot = books[high].title; // pivot is the last element's title
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If the current book's title is lexicographically less than or equal to pivot
            if (books[j].title.compareToIgnoreCase(pivot) <= 0) {
                i++;
                // Swap books[i] and books[j]
                Book temp = books[i];
                books[i] = books[j];
                books[j] = temp;
            }
        }
        // Swap books[i + 1] and books[high] (pivot)
        Book temp = books[i + 1];
        books[i + 1] = books[high];
        books[high] = temp;

        return i + 1;
    }

    private static void sortBooksInOrder(Order order) {
        // Convert the booksOrdered MyArray to an array for quick sort
        Book[] booksArray = new Book[order.booksOrdered.size()];
        for (int i = 0; i < order.booksOrdered.size(); i++) {
            booksArray[i] = order.booksOrdered.get(i);
        }

        // Perform QuickSort on books based on title
        quickSortBooksByTitle(booksArray, 0, booksArray.length - 1);

        // After sorting, clear the booksOrdered list
        order.booksOrdered = new MyArray<>(10);

        // Add the sorted books back to the order
        for (Book book : booksArray) {
            order.booksOrdered.add(book);
        }
        //System.out.println("Books in the order have been sorted by title.");
    }

    private static void addBook() {
        String title;
        while (true) {
            System.out.print("Enter book title: ");
            title = scanner.nextLine().trim();
            if (!title.isEmpty()) {
                break;
            } else {
                System.out.println("Title cannot be blank. Please enter a valid title.");
            }
        }

        String code;
        while (true) {
            System.out.print("Enter book code (Format: # followed by 4 digits or press Enter for a random code): ");
            code = scanner.nextLine().trim();

            if (code.isEmpty()) {
                // Generate a random code if the user leaves it blank
                code = generateRandomCode();
                System.out.println("Generated random book code: " + code);
                break;
            } else if (code.matches("#\\d{4}")) {
                // Valid code format: # followed by 4 digits
                break;
            } else {
                System.out.println("Invalid code format. Please enter a valid code like #1234.");
            }
        }

        double price;
        while (true) {
            System.out.print("Enter book price: ");
            price = getValidDoubleInput();
            if (price > 0) {
                break;
            } else {
                System.out.println("Price must be positive. Please enter a valid price.");
            }
        }

        int quantity;
        while (true) {
            System.out.print("Enter book quantity: ");
            quantity = getValidIntInput();
            if (quantity > 0) {
                break;
            } else {
                System.out.println("Quantity must be positive. Please enter a valid quantity.");
            }
        }

        // Create a new Book object and add it to the catalog
        Book newBook = new Book(title, code, price, quantity);
        bookCatalog.add(newBook);
        System.out.println("Book added successfully!\n" + newBook);
    }

    private static void viewBooks() {
        if (bookCatalog.isEmpty()) {
            System.out.println("No books available in the catalog.");
            return;
        }

        System.out.println("\nBooks in Catalog:");
        for (int i = 0; i < bookCatalog.size(); i++) {
            System.out.println((i + 1) + ". " + bookCatalog.get(i));
        }
    }


    private static double getValidDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a valid number: ");
            }
        }
    }

    private static String generateRandomCode() {
        Random random = new Random();
        int randomCode = random.nextInt(10000);  // Generate a number between 0 and 9999
        return String.format("#%04d", randomCode);  // Format to always show 4 digits with a leading #
    }




}
