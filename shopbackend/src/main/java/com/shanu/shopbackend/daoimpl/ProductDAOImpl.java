package com.shanu.shopbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shanu.shopbackend.dao.ProductDAO;
import com.shanu.shopbackend.dto.Product;

@Repository("productDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * To fetch the Single Product Based on it's ID.
	 */
	@Override
	public Product get(int productId) {

		try {
			return sessionFactory.getCurrentSession().get(Product.class, Integer.valueOf(productId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * To check weather it's added or not
	 */
	@Override
	public boolean add(Product product) {

		try {

			sessionFactory.getCurrentSession().persist(product);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean update(Product product) {
		try {
			sessionFactory.getCurrentSession().update(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(Product product) {
		try {

			product.setActive(false);
			return this.update(product);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<Product> list() {
		return sessionFactory.getCurrentSession().createQuery("FROM Product", Product.class).getResultList();
	}

	/*
	 * To fetch all the List Active Products
	 */
	@Override
	public List<Product> listActiveProducts() {

		String selectActiveProducts = "FROM Product WHERE active =:active";
		return sessionFactory.getCurrentSession().createQuery(selectActiveProducts, Product.class)
				.setParameter("active", true).getResultList();
	}

	/*
	 * List of Active Products by Category
	 * 
	 */

	@Override
	public List<Product> listActiveProductsByCategory(int categoryId) {
		String selectActiveProductsByCategory = "FROM Product WHERE active=:active AND categoryId=:categoryId";
		return sessionFactory.getCurrentSession().createQuery(selectActiveProductsByCategory, Product.class)
				.setParameter("active", true).setParameter("categoryId", categoryId).getResultList();
	}

	/*
	 * Get Latest Active Products From Count 0 to Max.
	 */
	@Override
	public List<Product> getLatestActiveProducts(int count) {
		String getLatestActiveProducts = "FROM Product WHERE active=:active ORDER BY id";
		return sessionFactory.getCurrentSession().createQuery(getLatestActiveProducts, Product.class)
				.setParameter("active", true).setFirstResult(0).setMaxResults(count).getResultList();
	}

}
