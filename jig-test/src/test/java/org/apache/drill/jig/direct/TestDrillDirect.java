package org.apache.drill.jig.direct;

import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

import org.apache.drill.jig.api.AlterSessionKeys;
import org.apache.drill.jig.api.ResultCollection;
import org.apache.drill.jig.api.Statement;
import org.apache.drill.jig.exception.JigException;
import org.apache.drill.jig.test.CompareFiles;
import org.apache.drill.jig.util.RowDisplay;
import org.junit.Test;

public class TestDrillDirect
{

  @Test
  public void testDirect() throws Exception {
    
    // Cannot use embedded Drillbit because that version does not have
    // employee.json on the class path.
    
    new DrillContextFactory( )
        .build( );
    DirectConnection session = new DrillConnectionFactory( )
        .drillbit( "localhost" )
        .connect( );
//    new DrillContextFactory( )
//          .withEmbeddedDrillbit( )
//          .build( )
//          .startEmbedded( );
//    DrillSession session = new DrillConnectionFactory( )
//          .embedded( )
//          .connect( );
    
    session.alterSession( AlterSessionKeys.MAX_WIDTH_PER_NODE, 2 );
    
    testQueryPrinter( session );
    
    session.close();
    DrillClientContext.instance( ).stopEmbeddedDrillbit( );
  }

  private void testQueryPrinter(DirectConnection session) throws JigException {
    String stmt = "SELECT * FROM cp.`employee.json` LIMIT 20";
    Statement statement = session.prepare( stmt );
    ResultCollection results = statement.execute( );
    StringWriter out = new StringWriter( );
    RowDisplay.printResults( results, out );
    assertTrue( CompareFiles.compareResource( "/employees-20.txt", out.toString() ) );
    results.close();
  }
}
