package demo.dao.jdbc;
/******************************************************************************/
/*                                                                            */
/*                                           FILE: JDBCCallableStatement.java */
/*                                                                            */
/*  A simple callable statement for JDBC                                      */
/*  ====================================                                      */
/*                                                                            */
/*  V1.00   19-FEB-2008   Te          http://www.heimetli.ch/                 */
/*                                                                            */
/*  ------------------------------------------------------------------------- */
/*                                                                            */
/*  This example was coded and tested with JDK1.5.08                          */
/*                                                                            */
/*  Microsoft SQL Express was used as database, with sqljdbc V1.2 as driver   */
/*                                                                            */
/*  To run this example, you need SQL Server with the following setup:        */
/*  => a database named "JDBCDemo"                                            */
/*  => user "zhaw"                                                            */
/*  => password "gugus"                                                       */
/*  => a table called "Person" with columns named "Vorname" and "Nachname"    */
/*  => a stored procedure called "SelectPeople" with a string argument        */
/*                                                                            */
/******************************************************************************/

import java.sql.* ;

class JDBCCallableStatement
{
 public static void main( String args[] )
 {
  try
  {
      // Load the database driver
      Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver" ) ;

      // Get a connection to the database
      Connection conn = DriverManager.getConnection( "jdbc:sqlserver://localhost:1058;user=zhaw;password=gugus;databaseName=JDBCDemo" ) ;

      // Print all warnings
      for( SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning() )
      {
          System.out.println( "SQL Warning:" ) ;
          System.out.println( "State  : " + warn.getSQLState()  ) ;
          System.out.println( "Message: " + warn.getMessage()   ) ;
          System.out.println( "Error  : " + warn.getErrorCode() ) ;
      }

      // Prepare a statement
      CallableStatement cs = conn.prepareCall( "{call SelectPeople(?)}" ) ;

      // Set the first parameter of the statement
      cs.setString( 1, "Meier" ) ;

      // Execute the query
      ResultSet rs = cs.executeQuery() ;

      // Loop through the result set
      while( rs.next() )
         System.out.println( rs.getString("Vorname") + " " + rs.getString("Nachname") ) ;

      // Close the result set, statement and the connection
      rs.close() ;
      cs.close() ;
      conn.close() ;
  }
  catch( SQLException se )
  {
      System.out.println( "SQL Exception:" ) ;

      // Loop through the SQL Exceptions
      while( se != null )
      {
          System.out.println( "State  : " + se.getSQLState()  ) ;
          System.out.println( "Message: " + se.getMessage()   ) ;
          System.out.println( "Error  : " + se.getErrorCode() ) ;

          se = se.getNextException() ;
      }
  }
  catch( Exception e )
  {
      System.out.println( e ) ;
  }
 }
}
