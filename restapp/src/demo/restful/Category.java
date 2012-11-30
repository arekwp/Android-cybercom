package demo.restful;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.aegis.type.java5.XmlElement;
import org.apache.cxf.aegis.type.java5.XmlType;

@XmlRootElement(name = "Category")
@XmlType(name="Category")
public class Category
{
    private String categoryId;
    
    private String categoryName;
    
    private Collection<Book> books;
    
    @XmlElement(name="id")
    public String getCategoryId()
    {
	return categoryId;
    }
    
    public void setCategoryId(String categoryId)
    {
	this.categoryId = categoryId;
    }
    
    @XmlElement(name="name")
    public String getCategoryName()
    {
	return categoryName;
    }
    
    public void setCategoryName(String categoryName)
    {
	this.categoryName = categoryName;
    }
    
    public Collection<Book> getBooks()
    {
	return books;
    }
    
    public void setBooks(Collection<Book> books)
    {
	this.books = books;
    }
    
}
