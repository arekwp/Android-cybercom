package demo.restful;

//JAX-RS Imports
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

/*
 * CategoryService class - Add/Removes category for books 
 */

@Path("/categoryservice")
@Produces("application/xml")
public class CategoryService {

	private CategoryDAO categoryDAO = new CategoryDAO();

	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@GET
	@Path("/category/{id}")
	public Category getCategory(@PathParam("id") String id) {

		System.out.println("getCategory called with category id: " + id);

		Category cat = getCategoryDAO().getCategory(id);
		if (cat == null) {
			ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
			builder.type("application/xml");
			builder.entity("<error>Category Not Found</error>");
			throw new WebApplicationException(builder.build());
		} else {
			return cat;
		}
	}

	@POST
	@Path("/category")
	@Consumes("application/xml")
	public Response addCategory(Category category) {

		System.out.println("addCategory called");

		Category cat = getCategoryDAO().getCategory(category.getCategoryId());

		if (cat != null) {
			return Response.status(Status.BAD_REQUEST).build();
		} else {
			getCategoryDAO().addCategory(category);
			return Response.ok(category).build();
		}

	}

	@POST
	@Path("/category/{cid}/books")
	@Consumes("application/xml")
	public Response addBook(@PathParam("cid") String cid, Book book) throws UnsupportedEncodingException {

		System.out.println("addBook called with book id: " + book.getBookId());
		cid = URLDecoder.decode(cid, "UTF-8");
		//return Response.status(Status.BAD_REQUEST).build();
		Category c = getCategoryDAO().getCategory(cid);

		if (c == null)
		{
			return Response.status(Status.BAD_REQUEST).build();
		} 
		else 
		{
			
			System.out.println("addBook: " + book.getBookId()+" into category cid: " + cid);

			getCategoryDAO().addBook(cid, book);
			return Response.ok().build();
		}

	}

	@DELETE
	@Path("/category/{id}")
	public Response deleteCategory(@PathParam("id") String id)
			throws UnsupportedEncodingException {
		id = URLDecoder.decode(id, "UTF-8");
		System.out.println("deleteCategory with category id : " + id);

		Category cat = getCategoryDAO().getCategory(id);
		if (cat == null) {
			return Response.status(Status.BAD_REQUEST).build();
		} else {
			getCategoryDAO().deleteCategory(id);
			return Response.ok().build();
		}
	}

	@PUT
	@Path("/category")
	public Response updateCategory(Category category) {

		System.out.println("updateCategory with category id : "
				+ category.getCategoryId());

		Category cat = getCategoryDAO().getCategory(category.getCategoryId());
		if (cat == null) {
			return Response.status(Status.BAD_REQUEST).build();
		} else {
			getCategoryDAO().updateCategory(category);
			return Response.ok(category).build();
		}
	}

	@PUT
	@Path("/category/{cid}/books")
	public Response updateBook(@PathParam("cid") String cid, Book book) throws UnsupportedEncodingException {

		cid = URLDecoder.decode(cid, "UTF-8");

		System.out.println("updateBook with id : " + book.getBookId());

	
		Book b = getCategoryDAO().getBook(cid, book.getBookId());

		if (b == null)
		{
			return Response.status(Status.BAD_REQUEST).build();
		} 
		else 
		{

			getCategoryDAO().updateBook(cid, book);
			return Response.ok().build();
		}

	}

	@POST
	@Path("/category/books")
	@Consumes("application/xml")
	public Response addBooks(Category category) {

		System.out.println("addBooks with category id : "
				+ category.getCategoryId());

		Category cat = getCategoryDAO().getCategory(category.getCategoryId());
		if (cat == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			getCategoryDAO().addBooks(category);
			return Response.ok(category).build();
		}
	}

	@DELETE
	@Path("/category/{cid}/books/{id}")
	public Response deleteBook(@PathParam("cid") String cid,
			@PathParam("id") String id) throws UnsupportedEncodingException {
		id = URLDecoder.decode(id, "UTF-8");
		cid = URLDecoder.decode(cid, "UTF-8");
		System.out.println("deleteBook: " + id + " from category cid : " + cid);

		Category cat = getCategoryDAO().getCategory(cid);
		getCategoryDAO().deleteBook(cat.getCategoryId(), id);
		return Response.ok().build();
	}

	@GET
	@Path("/category/{id}/books")
	@Consumes("application/xml")
	public Response getBooks(@PathParam("id") String id)
			throws UnsupportedEncodingException {
		id = URLDecoder.decode(id, "UTF-8");
		System.out.println("getBooks called with category id : " + id);

		Category cat = getCategoryDAO().getCategory(id);

		if (cat == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			cat.setBooks(getCategoryDAO().getBooks(id));
			return Response.ok(cat).build();

		}
	}

	@GET
	@Path("/category/{cid}/books/{id}")
	@Consumes("application/xml")
	public Response getBook(@PathParam("cid") String cid)
			throws UnsupportedEncodingException {
		cid = URLDecoder.decode(cid, "UTF-8");
		System.out.println("getBooks called with category cid : " + cid);

		Category cat = getCategoryDAO().getCategory(cid);

		if (cat == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			cat.setBooks(getCategoryDAO().getBooks(cid));
			Collection<Book> tmp = cat.getBooks();
			return Response.ok(cat).build();

		}
	}

	@GET
	@Path("/category")
	@Consumes("application/xml")
	public Response getCategories() {

		System.out.println("getCategories called");

		Map<String, Category> cats = getCategoryDAO().getCategories();

		if (cats == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			// cats.setBooks(getCategoryDAO().getBooks(id));
			return Response.ok(new Categories(cats)).build();

		}
	}
}
