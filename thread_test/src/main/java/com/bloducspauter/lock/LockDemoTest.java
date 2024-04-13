package com.bloducspauter.lock;

import java.util.concurrent.TimeUnit;

/**
 * This class is used to talking about <b>optimistic locking</b> and <b>pessimistic locking</b>.
 *
 * @author Bloduc Spauter
 */
public class LockDemoTest {
    /**
     * <h4><b>Optimistic locking</b></h4>
     * Optimistic locking is a concurrency control mechanism used in computer systems, particularly in databases,
     * to ensure data integrity when multiple transactions may attempt to modify the same data concurrently.
     * <p>
     * In optimistic locking, instead of locking the data at the beginning of a transaction and holding the lock
     * until the transaction is complete (which is called pessimistic locking),
     * the system allows multiple transactions to proceed concurrently without locking the data.
     * <p>
     * However, before committing changes, each transaction checks whether the data it has modified has been changed
     * by another transaction since it was last read. If the data has been modified by another transaction,
     * the current transaction is aborted or rolled back, and appropriate actions can be taken,
     * such as notifying the user or retrying the transaction.
     * <p>
     * Optimistic locking typically relies on some form of versioning or timestamping mechanism to track changes to the data.
     * When recognizing optimistic locking, you might notice:
     * <p>
     * <b>Version Numbers or Timestamps</b> Each record in the database might have an associated version number
     * or timestamp indicating when it was last modified.
     * </p>
     * <p>
     * <b> Check Before Update</b> Before committing changes,
     * the system compares the version number or timestamp of the data being modified with the version number or timestamp that was read initially.
     * </p>
     * <p>
     * <b> Conflict Resolution</b> If a conflict is detected (i.e., the data has been modified since it was last read),
     * the system resolves the conflict by rolling back the current transaction, notifying the user, and possibly retrying the transaction.
     * </p>
     * <p>
     * optimistic locking is useful in scenarios where conflicts are infrequent, as it minimizes the need for locking and improves concurrency.
     * However, it requires careful handling of conflicts and proper error recovery mechanisms.
     * </p>
     * Learn more About <b>pessimistic locking</b>, seeing {@link Phone#sendLetter()}
     */
    void modifyById() {

    }
}

class Phone {
    /**
     * <h4><b>Pessimistic locking</b></h4>
     * {@code synchronized} and {@link java.util.concurrent.locks.Lock} are pessimistic lock and
     * where resources are locked from the moment they are accessed until they are no longer needed.
     * This means that when one transaction is using a resource,
     * other transactions are prevented from accessing or modifying it until the lock is released.
     * <p>
     * Here are some key points about pessimistic locking:
     * <b>Locking</b>When a transaction wants to access a resource, it first acquires a lock on that resource.
     * This prevents other transactions from accessing or modifying the resource until the lock is released.
     * </p>
     * <p>
     * <b>Exclusive Locks</b> In pessimistic locking, locks are typically exclusive,
     * meaning only one transaction can hold a lock on a resource at a time.
     * This ensures that transactions do not interfere with each other.
     * </p>
     * <p>
     * <b>Blocking</b> If one transaction holds a lock on a resource,
     * other transactions that attempt to access the same resource may be blocked until the lock is released.
     * This can lead to potential delays and decreased concurrency.
     * </p>
     * <p>
     * <b>Deadlocks</b>Pessimistic locking can also lead to deadlocks,
     * where two or more transactions are waiting for locks held by each other,
     * causing them to be stuck indefinitely.
     * <p>
     * <b>Database Support</b> Many database management systems (DBMS) support pessimistic locking through mechanisms
     * such as row-level locks or table-level locks.
     * Developers can explicitly request locks on resources using SQL statements like {@code SELECT ... FOR UPDATE} in relational databases.
     * </p>
     * Pessimistic locking is often used in scenarios where conflicts are expected to be frequent or where the cost of conflict resolution is high.
     * However, it can also lead to decreased concurrency and potential performance issues,
     * especially in systems with high contention for resources.
     * Therefore, it's important to use pessimistic locking judiciously and consider alternative concurrency control mechanisms when appropriate.
     * <p>
     * Learn more about <b>optimistic locking</b>, seeing {@link LockDemoTest#modifyById()}
     * </p>
     */
    public synchronized void sendLetter() {
        System.out.println("Sending letter");
    }

    public synchronized void sendEmail() {
        System.out.println("Send a email");
    }

    /**
     * To simulate delay
     */
    public synchronized void callSomebody() {
        try {
            //To simulate delay
            TimeUnit.MICROSECONDS.sleep(3000000);
        } catch (InterruptedException e) {
            System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
        }
        System.out.println("Call somebody");
    }

    /**
     * A common method
     */
    public void hello() {
        System.out.println("hello");
    }

    /**
     * A static synchronized method
     */
    public static synchronized void callSteve() {
        System.out.println("Call Steve");
    }

    /**
     * Another static synchronized method
     */
    public static synchronized void callAlex() {
        System.out.println("Call Alex");
    }
}

/**
 * Visiting thread A and B normally
 */
final class LockTest1 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(phone::sendEmail, "A").start();
        try {
            //Making sure thread S stars first
            TimeUnit.MICROSECONDS.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
        }
        new Thread(phone::sendLetter, "B").start();
    }
}

/**
 * The method {@link Phone#callSomebody()} needs 3 seconds to waiting for the telephone to be connected,
 * to compare which is the first.
 */
final class LockTest2 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(phone::callSomebody, "A").start();
        //The sentence first printed is "Call somebody"
        new Thread(phone::sendLetter, "B").start();
    }
}

/**
 * Adding a common method {@link Phone#hello()}  to compare it.
 */
final class LockTest3 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(phone::callSomebody, "A").start();
        //The sentence first printed is "Hello"
        new Thread(phone::hello, "B").start();
    }
}

/**
 * Simulating having two phones, compare which is first sentence printed
 */
final class LockTest4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone1 = new Phone();
        new Thread(phone::callSomebody).start();
        //The sentence first printed is "Sending letter"
        new Thread(phone1::sendLetter).start();
    }
}

/**
 * To test two static synchronized methods
 */
final class LockTest5 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> phone.callSteve(), "A").start();
        try {
            //Making sure thread S stars first
            TimeUnit.MICROSECONDS.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Exception thread in main:" + e.getClass().getName() + ":" + e.getMessage());
        }
        new Thread(() -> phone.callAlex(), "B").start();
    }
}

/**
 * To test two static synchronized methods with two phones
 */
final class LockTest6 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> phone1.callSteve(), "A").start();
        //Making sure thread S stars first
        TimeUnit.MICROSECONDS.sleep(100);
        new Thread(() -> phone2.callAlex(), "B").start();
    }
}

final class LockTest7 {
    public static void main(String[] args) {
        Phone phone1 = new Phone();
        new Thread(() -> phone1.callAlex(), "A").start();
        new Thread(() -> phone1.hello(), "B").start();
    }
}

final class LockTest8 {
    public static void main(String[] args) {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> phone1.callSteve(), "A").start();
        new Thread(() -> phone2.hello(), "B").start();
    }
}