package demo.restful.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import demo.restful.Category;
import demo.restful.CategoryService;

public class CategoryServerStart
{
    
    public static void main(String[] args)
    {
	
	ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
	        new String[]
	        { "restapp.xml" });
	
	CategoryService categoryService = (CategoryService) appContext
	        .getBean("categoryService");
	
	// Service instance
	
	JAXRSServerFactoryBean restServer = new JAXRSServerFactoryBean();
	
	restServer.setResourceClasses(Category.class);
	
	restServer.setServiceBean(categoryService);
	
	try
        {
	    InetAddress netAddr = InetAddress.getLocalHost();
	    
	    //restServer.setAddress("http://192.168.1.2:8020/");
	    String address = "http://" + netAddr.getHostAddress() + ":8020/"; // fizyczny telefon
	    //String address = "http://" + "127.0.0.1" + ":8020/"; // emualtor
	    System.out.println("Server starting at: " + address);
	    restServer.setAddress(address);
	    restServer.create();
	    
        } catch (UnknownHostException e1)
        {
	    e1.printStackTrace();
        }
	
	
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	try
	{
	    br.readLine();
	} catch (IOException e)
	{
	    
	}
	System.out.println("Server Stopped");
	System.exit(0);
	
    }
    
}
