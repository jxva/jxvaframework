package demo.dao.jdbc;

public class Batch {

	/**
	 * @param args
	 * Statememt
PreparedStatement 都有addBatch方法

Statememt  stmt=conn.createStatement();
stmt.addBatch("insert inti .....");
stmt.addBatch("insert inti .....");
stmt.addBatch("insert inti .....");
stmt.executeBatch();
stmt.close();

PreparedStatement  ps=conn.prepareStatement("insert Into biao values(?,?)");
ps.setInt(1,61);
ps.setInt(2,61);
ps.addBatch()
ps.setInt(1,61);
ps.setInt(2,61);
ps.addBatch()

ps.executeBatch();
ps.close(); 

====

有时候JDBC运行得不够快,这使得有些程序员使用数据库相关的存储过程。作为一个替代方案,可以试试使用Statement 的批量处理特性看看能否同时执行所有的SQL以提高速度。

　　存储过程的最简单的形式就是包含一系列SQL语句的过程,将这些语句放在一起便于在同一个地方管理也可以提高速度。Statement 类可以包含一系列SQL语句,因此允许在同一个数据库事务执行所有的那些语句而不是执行对数据库的一系列调用。

　　使用批量处理功能涉及下面的两个方法:
　　· addBatch(String) 方法
　　· executeBatch方法

　　如果你正在使用Statement 那么addBatch 方法可以接受一个通常的SQL语句,或者如果你在使用PreparedStatement ,那么也可以什么都不向它增加。executeBatch 方法执行那些SQL语句并返回一个int值的数组,这个数组包含每个语句影响的数据的行数。如果将一个SELECT语句或者其他返回一个 ResultSet的SQL语句放入批量处理中就会导致一个SQLException异常。

　　关于java.sql.Statement 的简单范例可以是:

　　Statement stmt = conn.createStatement();
　　stmt.insert("DELETE FROM Users");
　　stmt.insert("INSERT INTO Users VALUES("rod", 37, "circle")");
　　stmt.insert("INSERT INTO Users VALUES("jane", 33, "tr i a n g l e ")");
　　stmt.insert("INSERT INTO Users VALUES("freddy", 29, "square")");
　　int[] counts = stmt.executeBatch();

　　PreparedStatement 有些不同,它只能处理一部分SQL语法,但是可以有很多参数,因此重写上面的范例的一部分就可以得到下面的结果:

　　// 注意这里没有DELETE语句
　　PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users VALUES(?,?,?)");

　　User[ ] users = ...;
　　for(int i=0; i<users.length; i++) {
　　　stmt.setInt(1, users[i].getName());
　　　stmt.setInt(2, users[i].getAge());
　　　stmt.setInt(3, users[i].getShape());
　　　stmt.addBatch( );
　　}
　　int[ ] counts = stmt.executeBatch();

　　如果你不知道你的语句要运行多少次,那么这是一个很好的处理SQL代码的方法。在不使用批量处理的情况下,如果添加50个用户,那么性能就有影响,如果某个人写了一个脚本添加一万个用户,程序可能变得很糟糕。添加批处理功能就可以帮助提高性能,而且在后面的那种情况下代码的可读性也更好。
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
