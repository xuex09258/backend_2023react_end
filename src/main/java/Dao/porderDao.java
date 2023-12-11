package Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import Model.member;
import Model.porder;

public class porderDao implements implDao{

	public static void main(String[] args) {
//add===================================================
		//porder p=new porder("A",2,3,4);
		//new porderDao().add(p);
//queryId(3)=======================================================
		//System.out.println(new porderDao().queryId(3));
//update  delete 沒測試===================================================
//###########################################################################
//JPQL-----------------------------------------------------------------------
		/*String JPQL="select p from porder p";//也可用member
		//String JPQL="select m from member m";

		EntityManager em=implDao.getDb();
		Query q=em.createQuery(JPQL);
		List l=q.getResultList();
		
		System.out.println(l);//先看有幾筆*/
//7777 3:51  list
		
		
		
/*//加條件-------------------------------------------------------------
		String JPQL="select p from porder p";//加條件看Sample 5
		EntityManager em=implDao.getDb();
		Query q=em.createQuery(JPQL);
		List l=q.getResultList();
		
		System.out.println(l);*/
		
//測試12 queryAll 用JPQL--------------------------------------------------------		
		//System.out.println(new porderDao().queryAll());
//印出結果 [ ] 是矩陣 因為轉成list 了		
//[Model.porder@6bcbf05b, Model.porder@59712875, Model.porder@47b2e9e1, Model.porder@59942b48]
//8888888888888888888888888888888888888888888888888888888888888888888888888888888888
//8888 3"31		
		//8888測試1  	
		//System.out.println(porderDao.queryAllshow());
		//8888測試2   3"51
		System.out.println(porderDao.querySum2(1000, 1600));
	}

	@Override
	public void add(Object o) {
		EntityManager em=implDao.getDb();
		EntityTransaction et=em.getTransaction();
		et.begin();
	    em.persist(o);
		et.commit();
		
	}

	@Override
	public Object queryId(Integer id) {
		EntityManager em=implDao.getDb();
		porder p=em.find(porder.class, id);
		return p;
		
	}

	@Override //3:30 JPQL
	public List queryAll() {
		String JPQL="select p from porder p";
		EntityManager em=implDao.getDb();
		Query q=em.createQuery(JPQL);
		List l=q.getResultList();
				
		return l;
	}

	@Override
	public void update(Object o) {
		EntityManager em=implDao.getDb();
		EntityTransaction et=em.getTransaction();
		et.begin();
		em.merge(o);
		et.commit();
		
	}

	@Override
	public void delete(Integer id) {
		EntityManager em=implDao.getDb();
		EntityTransaction et=em.getTransaction();
		porder p=em.find(porder.class, id);
		
		et.begin();
		em.remove(p);
		et.commit();
		
	}
	//8888 自己的方法queryAllshow()
	public static String queryAllshow()
	{
		String show="";
		List l=new porderDao().queryAll();
	 	porder[] p=(porder[])l.toArray(new porder[l.size()]);
		
	 	for(int i=0;i<p.length;i++)
	 	{
	 		show=show+"<tr><td>"+p[i].getId()+
	 				"<td>"+p[i].getDesk()+
	 				"<td>"+p[i].getA()+
	 				"<td>"+p[i].getB()+
	 				"<td>"+p[i].getC()+
	 				"<td>"+p[i].getSum();
	 	}
	
	 	return show;
	
	}
	//8888 自己的方法querySum1() 先調出來 用list
	public static List querySum1(int start,int end) {
		String JPQL="select p from porder p where p.sum>=?1 and p.sum<=?2";
		EntityManager em=implDao.getDb();
		Query q=em.createQuery(JPQL);
		q.setParameter(1, start);
		q.setParameter(2, end);
		
		List l=q.getResultList();	
		
		return l;
	}
	//8888 自己的方法querySum2() 再轉成String
	public static String querySum2(int start,int end)
	{
		String show="";
		List l=porderDao.querySum1(start, end);
		porder[] p=(porder[]) l.toArray(new porder[l.size()]);
		for(int i=0;i<p.length;i++)
	 	{
	 		show=show+"<tr><td>"+p[i].getId()+
	 				"<td>"+p[i].getDesk()+
	 				"<td>"+p[i].getA()+
	 				"<td>"+p[i].getB()+
	 				"<td>"+p[i].getC()+
	 				"<td>"+p[i].getSum();
	 	}
		
		
		return show;
	}


}

