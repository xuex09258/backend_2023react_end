package Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface implDao {
	//7777 1:24 新增連線方法 產生entityManager物件
	static EntityManager getDb() //2:03 java 8--------------------------------------
	{
		EntityManagerFactory EMF=Persistence.createEntityManagerFactory("Company");
		EntityManager EM=EMF.createEntityManager();//連上線再丟給 EM
		return EM;
	}
	
	//新增物件 2:05
		void add(Object o);
		
		
		//查詢物件
		Object queryId(Integer id);//查一筆 用id
		List queryAll();           //全部 下午3:28 用JPQL
		
		//修改物件
		void update(Object o);
		
		
		//刪除物件
		//void delete(Object o);此方法不行
		void delete(Integer id);
}
