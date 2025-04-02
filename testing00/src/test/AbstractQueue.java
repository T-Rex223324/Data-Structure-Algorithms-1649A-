package PACKAGE_NAME.test;

public interface AbstractQueue<E> {
    void enqueue(E element); // Add element to the queue
    E dequeue(); // Remove element from the queue
    E peek(); // View front element
    int size(); // Get size of queue
    boolean isEmpty(); // Check if queue is empty
}
