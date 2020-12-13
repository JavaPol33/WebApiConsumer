package database;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.html.Option;
import java.util.Optional;

public class CurrencyDao {

    // CRUD
    // C - create
    // R - read
    // U - update
    // D - delete

    public void create(Currency currency) {
        Transaction transaction = null;

        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(currency);
            transaction.commit();
        } catch (IllegalStateException | RollbackException e) {
            System.err.println("Błąd zapisu encji Currency");
            e.printStackTrace();
        }
    }

    public Currency getById(long id) {
        return null;
    }

    public Currency getByDateAndByFromAndTo(String date, String from, String to) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Currency> query = cb.createQuery(Currency.class);
            Root<Currency> table = query.from(Currency.class);

            Predicate[] predicates = new Predicate[3];
            predicates[0] = cb.equal(table.get("date"), date);
            predicates[1] = cb.equal(table.get("base"), from);
            predicates[2] = cb.equal(table.get("rateName"), to);

            query.select(table).where(predicates);

            Currency currency = session.createQuery(query).getSingleResult();

            return currency;
        } catch (PersistenceException e) {
            System.err.println("Błąd zapisu encji Currency");
            e.printStackTrace();
        }

        return null;
    }

    public void update(Currency currency) {

    }

    public void delete(long id) {

        Currency currency = getById(id);
        if (currency != null) {

            Transaction transaction = null;
            try {
                Session session = HibernateConfig.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                session.delete(currency);
                transaction.commit();
            } catch (IllegalStateException | RollbackException e) {
                System.err.println("Błąd usuwania Currency");
                e.printStackTrace();

                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
