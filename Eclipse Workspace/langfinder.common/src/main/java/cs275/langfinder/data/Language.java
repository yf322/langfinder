package cs275.langfinder.data;

public class Language {
	Integer id;
	String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Language() {
		super();
	}
	public Language(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
