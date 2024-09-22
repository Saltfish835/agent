package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.agent.monitor.api.MonitorResponse;
import org.example.agent.monitor.node.NodeMonitor;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        NodeMonitor nodeMonitor = new NodeMonitor();
        MonitorResponse response = nodeMonitor.monitor();
        System.out.println(response);
        assertTrue( response != null );
    }
}
