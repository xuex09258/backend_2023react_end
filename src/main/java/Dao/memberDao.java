package Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Model.member;

public class memberDao implements implDao{

	public static void main(String[] args) {
		//EntityManagerFactory em= Persistence.createEntityManagerFactory("Company");
		//測試一
		//System.out.println(em);//6666 5:48 測試一 連線的
		//org.hibernate.internal.SessionFactoryImpl@ec50f54 有抓出記憶體位置
		
		//測試二
		//EntityManager E=em.createEntityManager();
		//System.out.println(E);//測試二  物件class 有沒有寫對
        //SessionImpl(778337881<open>) 有抓出記憶體位置=========================
//delete 測試七 結果看mysql------------------------------------------------
        /*member m=E.find(member.class, 5);
		
		EntityTransaction et=E.getTransaction();
	    et.begin();
		E.remove(m);
		et.commit();*/
		
//修改測試六--結果看mysql ---------------------------------------
				/*member m=E.find(member.class, 3);
				m.setPassword("33333");
				m.setAddress("abce");
				
				EntityTransaction et=E.getTransaction();
				et.begin();
				E.merge(m);
				et.commit();*/
		
//查詢一筆 測試四========================================================
		//測試System.out.println(E.find(member.class, 10));沒有第10筆 就是null
		//System.out.println(E.find(member.class, 1));//測試四
		//印出內容測試五=======================================================
		/*member m=E.find(member.class, 1);		
		System.out.println(m.getId()+"\t"+m.getName());//先印兩欄 測試五*/
		
//7777 40 新增測試三 =================================================================
		//member m=new member("teer","vbn","1234","ai","11122","3333");
		//EntityTransaction et=E.getTransaction();//交易模式
		//et.begin();
	    //E.persist(m);
		//et.commit();
//看mysql ======================================================================
		
//===========開始 測試八 下面@override add方法
		/*member m=new member("tabcjj","klaa","1234","tei","111","33");
		new memberDao().add(m);*/
//===========開始 測試9下面@override queryid方法
		//System.out.println(new memberDao().queryId(1));
		//System.out.println(new memberDao().queryId(10));沒有第10筆 就是null
//===========開始 測試10下面@override update方法		
		//member m=(member) new memberDao().queryId(4);//id抓出來是整數 但下面方法是object
		                                             //所以要 轉型
		//m.setAddress("tainan");//改的內容 
		//new memberDao().update(m);//改的內容 再放回去
//===========開始 測試11下面@override update方法		
		//member m=(member) new memberDao().queryId(8);此方法不行
		//new memberDao().delete(8);
		//重新測試
		//new memberDao().delete(8);
//下午5:07開始 111判斷有沒有這個帳號--測試12------------------------------------------------------------
		//System.out.println(memberDao.queryUser("bbb", "33333"));//判斷有沒有這個帳號(人)
		                                            //有 執行會先 先抓出記憶體位置
//下午5:56開始 222判斷有沒有帳號重複--測試13
		System.out.println(memberDao.queryUser("bbb"));
	}
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
//$$$$$$$$$$$$$$$$$上面為測試段-----------------------------------------------
//memberDao 自己的 兩個有條件查詢方法 111判斷有沒有這個帳號(兩個參數)(下午5:07分)----------------------------
public static member queryUser(String username,String password) {
      
	member m1=null;//先宣告m1 沒有這個人就 null
	
	String JPQL="select m from member m where m.username=?1 and m.password=?2";
	EntityManager em=implDao.getDb();
	Query q=em.createQuery(JPQL);
	q.setParameter(1, username);
	q.setParameter(2, password);//以上就是把這個人抓出來
	
	List l=q.getResultList();

	if(l.size()!=0)
	{
		member[] m2=(member[]) l.toArray(new member[l.size()]);
		m1=m2[0];
	}
		return m1;
}
//(5;56)222222判斷帳號重複(一個參數)222222222222222222222222222222222222222222222
public static member queryUser(String username) {
   member m1=null;//先宣告m1 沒有這個人就 null

   String JPQL="select m from member m where m.username=?1";
   EntityManager em=implDao.getDb();
   Query q=em.createQuery(JPQL);
   q.setParameter(1, username);
   //以上就是把這個人抓出來

   List l=q.getResultList();

   if(l.size()!=0)
   {
	  member[] m2=(member[]) l.toArray(new member[l.size()]);
	  m1=m2[0];
   }
	  return m1;
   }
//---------------------------------------------------------------------------
//剛開始的 CRUD---------------------------------------------------------------
	@Override  //2:12
	public void add(Object o) {
		EntityManager em=implDao.getDb();
		EntityTransaction et=em.getTransaction();
		et.begin();
	    em.persist(o);
		et.commit();
		
	}

	@Override  //2:18
	public Object queryId(Integer id) {
		EntityManager em=implDao.getDb();
		member m=em.find(member.class, id);
		return m;
	}

	@Override
	public List queryAll() { //下午的JPQL------------------------------------
		String JPQL="select m from member m";
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
		member m=em.find(member.class, id);	
		EntityTransaction et=em.getTransaction();
		
		et.begin();
		em.remove(m);
		et.commit();
		
	  /*EntityManager em=implDao.getDb();
		EntityTransaction et=em.getTransaction();
		et.begin();
		em.remove(o);
		et.commit();*/
		
	}

}
