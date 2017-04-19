package userInterface;

public class Permission {
	public String catagory;
	public boolean allowed;
	public boolean bandwidth;
	
	public Permission(String catagory, boolean allowed, boolean bandwidth) {
		this.catagory = catagory;
		this.allowed = allowed;
		this.bandwidth = bandwidth;
	}
}
