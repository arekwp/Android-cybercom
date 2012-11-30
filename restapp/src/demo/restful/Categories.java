package demo.restful;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.cxf.aegis.type.java5.XmlElement;

@XmlRootElement(name="Categories")
@XmlSeeAlso(Category.class)
public class Categories
{
    private Collection<Category> coll;
    
    public Categories()
    {
	coll = new ArrayList<Category>();
    }
    
    public Categories(Map<String, Category> map)
    {
	coll = new ArrayList<Category>();
	toCollection(map);
    }
    
    private void toCollection(Map<String, Category> map)
    {
	for (Map.Entry<String, Category> e : map.entrySet())
	{
	    getColl().add(e.getValue());
	}
    }

    @XmlElementWrapper(name="cats")
    @XmlElement(name="Category")
    public Collection<Category> getColl()
    {
	return coll;
    }
}
