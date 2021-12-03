package com.my.atark.dao;

import com.my.atark.exceptions.DataBaseConnectionException;
import com.my.atark.exceptions.DataBaseNotSupportedException;
import com.my.atark.exceptions.IncorrectPropertyException;
import org.apache.log4j.Logger;

public abstract class DaoFactory {

    private static final Logger log = Logger.getLogger(DaoFactory.class);

    /** DAO Factory methods */
    /**
     * Creates User DAO
     * @return User DAO
     */
    public abstract IUserDao getUserDao();

    /**
     * Product User DAO
     * @return Product DAO
     */
    public abstract IProductDao getProductDao();

    /**
     * Creates Invoice DAO
     * @return Invoice DAO
     */
    public abstract IInvoiceDao getInvoiceDao();

    /**
     * Creates Payment DAO
     * @return Payment DAO
     */
    public abstract IPaymentDao getPaymentDao();

    /**
     * Creates Transaction DAO
     * @return Transaction DAO
     */
    public abstract ITransactionDao getTransactionDao();

    /**
     * Opens connection to Data Source
     * @throws DataBaseConnectionException if unable to open connection
     */
    public abstract void open() throws DataBaseConnectionException;

    /**
     * Closes connection to Data Source
     */
    public abstract void close();

    /**
     * Opens DB data transaction
     * @throws DataBaseConnectionException if unable to open data transaction
     */
    public abstract void beginTransaction() throws DataBaseConnectionException;

    /**
     * Commits transaction results and closes transaction
     * @throws DataBaseConnectionException if unable to commit data transaction
     */
    public abstract void commitTransaction() throws DataBaseConnectionException;

    /**
     * Rollbacks transaction results and closes transaction
     * @throws DataBaseConnectionException if unable to rollback transaction
     */
    public abstract void rollbackTransaction() throws DataBaseConnectionException;

    public static DaoFactory getDaoFactory(DataBaseSelector dataBase) throws
            DataBaseNotSupportedException,
            IncorrectPropertyException,
            DataBaseConnectionException {
        switch (dataBase) {
            case MY_SQL:
                return new MySQLDaoFactory();
            default:
                log.error("Database type not set");
                throw new DataBaseNotSupportedException("Database type not set");
        }
    }
}