package userInterface;



public class User {
  private String userType;
  private String fname;
  private String lname;
  private String username;
  private String password;
  private Permission[] permissions;
  private boolean status;
  
  public User(String userType, String fname, String lname, String username, String password, boolean status, Permission[] permissions) {
	  this.userType = userType;
	  this.fname = fname;
	  this.lname = lname;
	  this.username = username;
	  this.password = password;
	  this.permissions = permissions;
	  this.status = status;
  }
  
  public boolean login(String password) {
	  if (this.password.equals(password)) {
		  this.status = true;
		  return true;
	  } else {
		  return false;
	  }
  }
  
  public boolean logout() {
    this.status = false;
    return true;
  }
  
  public boolean editUsername(String username) {
	  if (status) { // you have to be signed on to edit your username
		  this.username = username;
		  return true;
	  } else {
		  return false;
	  }
  }
  
  public boolean editFName(String fname) {
	  if (status) { // you have to be signed on to edit your username
		  this.fname = fname;
		  return true;
	  } else {
		  return false;
	  }
  }
  
  public boolean editLName(String lname) {
	  if (status) { // you have to be signed on to edit your username
		  this.lname = lname;
		  return true;
	  } else {
		  return false;
	  }
  }
  
  public boolean editPassword(String oldPassword, String newPassword) {
	  if (this.password.equals(newPassword)) {
		  this.password = newPassword;
		  return true;
	  } else {
		  return false;
	  }
  }
  
  public boolean editPassword(User admin, String newPassword) {
	  if (admin.getUserType().equals("Admin")) { // only a admin can change someone's password for them
		  this.password = newPassword;
		  return true;
	  } else {
		  return false;
	  }
  }
  
  public String getUsername() {
	  return this.username;
  }
  
  public String getUserType() {
	  return this.userType;
  }
  
  public String toString() {
	  StringBuilder str = new StringBuilder(this.username);
	  str.append("\n" + this.userType.toString());
	  str.append("\n" + this.fname);
	  str.append("\n" + this.lname);
	  str.append("\n" + this.password);
	  str.append("\n" + this.status);
	  for (int i = 0; i < this.permissions.length; i++) {
		  str.append("\n" + this.permissions[i].catagory + " " + this.permissions[i].allowed + " " + this.permissions[i].bandwidth);
	  }
	  return str.toString();
  } 
}
