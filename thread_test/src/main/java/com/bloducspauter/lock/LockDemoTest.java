package com.bloducspauter.lock;

/**
 * This class is used to talking about <b>optimistic locking</b> and <b>pessimistic locking</b>.
 *
 * @author Bloduc Spauter
 * @see #modify()
 * @see #modifyById()
 */
public class LockDemoTest {
    /**
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
     */
    synchronized void modify() {

    }

    /**
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
     */
    void modifyById() {

    }
}
