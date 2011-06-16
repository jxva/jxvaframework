package demo.dao.other;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.jxva.dao.model.Author;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;




public class MultiThreadTestTrascation extends Thread{

	private static final long s=System.nanoTime();
	private static final DAOFactory factory =DAOFactory.getInstance();
	private static final AtomicInteger count=new AtomicInteger(0);
	private static final int MAX_THREAD=1000;
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletionService<Integer> completionService =new ExecutorCompletionService<Integer>(exec);

		for (int index = 0; index < MAX_THREAD; index++) {
			//final int NO = index;
			completionService.submit(new Callable<Integer>() {
				public Integer call() {
					//for(int i=0;i<4;i++){
					DAO dao =factory.createDAO();
					//System.out.println("create....................................");
					try{
						//dao.beginTransaction();
						Author author=new Author();
						//author.setAuthorId(Long.valueOf(dao.getAutoId(Author.class)).intValue());
						author.setName("fdsfsafsa");
						dao.save(author);
						//System.out.println("insert....................................");
						//dao.update(author,"fdsfds"); //has execption for test
						//dao.find(Author.class,"fdsaf=d"); //has execption for test
						//dao.commit();
					}catch(Exception e){
						//dao.rollback();
						e.printStackTrace();
					}finally{
						//System.out.println("close....................................");
						dao.close();
					}
					count.getAndIncrement();
					return count.intValue();
				}
			});
		};
		try {
			for(int i=0;i<MAX_THREAD;i++){
				if(MAX_THREAD==completionService.take().get()){
					//System.out.print(completionService.take().get());
					System.out.println((System.nanoTime()-s)+" ns");
				}
			}
			System.out.println("---end---");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exec.shutdown();
	}	
}