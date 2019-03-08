package gr.aueb.mscis.vacpro.service;

import gr.aueb.mscis.vacpro.enums.PrivilegeLevel;
import gr.aueb.mscis.vacpro.model.Address;
import gr.aueb.mscis.vacpro.model.Administrator;
import gr.aueb.mscis.vacpro.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Administrator service.
 */
public class AdministratorService {

	private EntityManager em;

	/**
	 * Instantiates a new Administrator service.
	 */
	public AdministratorService() {
		em = JPAUtil.getCurrentEntityManager();
	}

	/**
	 * Create administrator administrator.
	 *
	 * @param firstName      the first name
	 * @param lastName       the last name
	 * @param userName       the user name
	 * @param password       the password
	 * @param phoneNumber    the phone number
	 * @param email          the email
	 * @param vatNumber      the vat number
	 * @param address        the address
	 * @param privilegeLevel the privilege level
	 * @return the administrator
	 */
	public Administrator createAdministrator(final String firstName, final String lastName, final String userName,
											 final String password, final String phoneNumber, final String email,
											 final String vatNumber, final Address address, final PrivilegeLevel privilegeLevel) {

		em = JPAUtil.getCurrentEntityManager();
		Administrator newAdmin = new Administrator(firstName, lastName, userName, password, phoneNumber,
				email, vatNumber, address, privilegeLevel);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(newAdmin);
		tx.commit();

		return newAdmin;
	}

	/**
	 * Update administrator password administrator.
	 *
	 * @param password      the password
	 * @param administrator the administrator
	 * @return the administrator
	 */
	public Administrator updateAdministratorPassword(final String password, final Administrator administrator) {

		administrator.setPassword(password);
		em = JPAUtil.getCurrentEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(administrator);
		tx.commit();

		return administrator;
	}

	/**
	 * Update administrator privilege administrator.
	 *
	 * @param level         the level
	 * @param administrator the administrator
	 * @return the administrator
	 */
	public Administrator updateAdministratorPrivilege(final PrivilegeLevel level, final Administrator administrator) {

		administrator.setPrivilege(level);
		em = JPAUtil.getCurrentEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(administrator);
		tx.commit();

		return administrator;
	}

	/**
	 * Find admin by username administrator.
	 *
	 * @param username the username
	 * @return the administrator
	 */
	public Administrator findAdminByUsername(final String username) {
		List<Administrator> results = new ArrayList<>();

		em = JPAUtil.getCurrentEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		String queryString = "from User user where user.userName= :user_name and user.class like :usertype";
		Query query = em.createQuery(queryString);
		query.setParameter("user_name", username);
		query.setParameter("usertype", "ADMIN");
		results = (List<Administrator>) query.getResultList();
		tx.commit();
		return results.get(0);
	}

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	public List<Administrator> findAll() {
		List<Administrator> results = new ArrayList<>();

		em = JPAUtil.getCurrentEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		String queryString = "from User user  where user.class like :type";
		Query query = em.createQuery(queryString);
		query.setParameter("type", "ADMIN");
		results = (List<Administrator>) query.getResultList();
		tx.commit();

		return results;
	}
}
