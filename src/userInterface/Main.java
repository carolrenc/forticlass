/**
 * 
 */
package userInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author danielcantrelle
 *
 */
public class Main {
	
	public static void printOptions(String[] options) {
		System.out.println("********************************************************************************");
		System.out.println("Below are the commands currently available:");
		for (String str : options) {
			System.out.println( "  -" + str);
		}
		System.out.println("********************************************************************************");
	}
	
	public static void main(String[] args) {
		
		List<String> userCommands = new ArrayList<String>(12);
		userCommands.add(0, "edit password");
		userCommands.add(1, "edit username");
		userCommands.add(2, "edit fname");
		userCommands.add(3, "edit lname");
		userCommands.add(4, "exit");
		userCommands.add(5, "logout");
		
		List<String> adminCommands = new ArrayList<String>(12);
		adminCommands.add(0, "edit password");
		adminCommands.add(1, "edit username");
		adminCommands.add(2, "edit fname");
		adminCommands.add(3, "edit lname");
		adminCommands.add(4, "add user");
		adminCommands.add(5, "remove user");
		adminCommands.add(6, "edit user");
		adminCommands.add(7, "exit");
		adminCommands.add(8, "logout");
		
		List<String> editUserCommands = new ArrayList<String>(12);
		editUserCommands.add(0, "type");
		editUserCommands.add(1, "password");
		editUserCommands.add(2, "permission");
		
		UsersAPI users = new UsersAPI("/Users/danielcantrelle/Documents/workspace/SeniorDesign/src/Users");
		Scanner in = new Scanner(System.in);
		String str;
		User currentUser = null;
		String state = "Welcome";
		
		while (true) {
			
			if (state.equals("Welcome")) { // Initial welcome state
				System.out.print("Username: ");
				str = in.nextLine();
				str = str.trim();
				if (str.equals("exit")) {
					state = str;
					continue;
				}
				// get user
				try {
					currentUser = users.getUser(str);
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
					continue;
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					continue;
				}
				// signin to user
				System.out.print("Password: ");
				str = in.nextLine();
				str = str.trim();
				if (currentUser.login(str)) { // attempt to sign in
					users.saveUser(currentUser); // save user because user is now online
					state = currentUser.getUserType(); // go to user welcome page
					continue;
				} else {
					System.out.println("Invalid Password");
					continue;
				}
			} else if (state.equals("Admin"))  { // after signed in admin
				String[] temp = new String[adminCommands.size()];
				adminCommands.toArray(temp);
				printOptions(temp);
				System.out.print(currentUser.getUsername() + ": ");
				str = in.nextLine();
				str = str.trim().toLowerCase();
				if (adminCommands.contains(str)) {
					state = str;
				} else {
					System.out.println("Invalid command: " + str);
					continue;
				}
			} else if (state.equals("Student"))  { // after signed in base user
				String[] temp = new String[userCommands.size()];
				userCommands.toArray(temp);
				printOptions(temp);
				System.out.print(currentUser.getUsername() + ": ");
				str = in.nextLine();
				str = str.trim().toLowerCase();
				if (userCommands.contains(str)) {
					state = str;
					continue;
				} else {
					System.out.println("Invalid command: " + str);
					continue;
				}
			} else if (state.equals("Professor"))  { // after signed in base user
				String[] temp = new String[userCommands.size()];
				userCommands.toArray(temp);
				printOptions(temp);
				System.out.print(currentUser.getUsername() + ": ");
				str = in.nextLine();
				str = str.trim().toLowerCase();
				if (userCommands.contains(str)) {
					state = str;
					continue;
				} else {
					System.out.println("Invalid command: " + str);
					continue;
				}
			} else if (state.equals("edit password"))  { // wants to edit their password
				String oldPassword;
				String newPassword;
				System.out.println(currentUser.getUsername() + " current password: ");
				oldPassword = in.nextLine();
				oldPassword = oldPassword.trim();
				System.out.println(currentUser.getUsername() + " new password: ");
				newPassword = in.nextLine();
				newPassword = newPassword.trim();
				System.out.println(currentUser.getUsername() + " repeat password: ");
				str = in.nextLine();
				str = str.trim();
				if (newPassword.equals(str)) { // repeated new password and new password are equal
					if (currentUser.editPassword(oldPassword, newPassword)) { // password changed
						state = currentUser.getUserType();
						System.out.println("password changed.");
						users.saveUser(currentUser);
						continue;
					} else {
						System.out.println("password not changed, wrong current password");
						state = currentUser.getUserType();
						continue;
					}
				} else {
					System.out.println("new passwords are not equal");
					state = currentUser.getUserType();
					continue;
				}
			} else if (state.equals("edit username")) {
				System.out.print(currentUser.getUsername() + " are you sure you want to edit your username(y/n)? ");
				str = in.nextLine();
				str = str.trim();
				if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {
					System.out.print("new username: ");
					str = in.nextLine();
					str.trim();
					try {
						users.deleteUser(currentUser.getUsername());
						currentUser.editUsername(str);
						users.addUser(currentUser);
					} catch (IllegalArgumentException | IOException e) {
						e.printStackTrace();
					}
				}
				state = currentUser.getUserType();
				continue;
			} else if (state.equals("edit fname")) {
				System.out.print(currentUser.getUsername() + " are you sure you want to edit your first name(y/n)? ");
				str = in.nextLine();
				str = str.trim();
				if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {
					System.out.print("new first name: ");
					str = in.nextLine();
					str.trim();
					currentUser.editFName(str);
					users.saveUser(currentUser);
				}
				state = currentUser.getUserType();
				continue;
			} else if (state.equals("edit lname")) {
				System.out.print(currentUser.getUsername() + " are you sure you want to edit your last name(y/n)? ");
				str = in.nextLine();
				str = str.trim();
				if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {
					System.out.print("new last name: ");
					str = in.nextLine();
					str.trim();
					currentUser.editLName(str);
					users.saveUser(currentUser);
				}
				state = currentUser.getUserType();
				continue;
			} else if (state.equals("exit")) {
				currentUser.logout();
				users.saveUser(currentUser);
				in.close();
				break;
			} else if (state.equals("logout")) {
				currentUser.logout();
				users.saveUser(currentUser);
				state = "Welcome";
				continue;
			} else if (state.equals("add user")) {
				System.out.print(currentUser.getUsername() + " are you sure you want to add a user (y/n)? ");
				str = in.nextLine();
				str = str.trim();
				if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {
					String userType;
					String fname;
					String lname;
					String username;
					String password;
					Permission permissions[] = new Permission[UsersAPI.PERMISSION_COUNT];
					
					System.out.print("user type: ");
					userType = in.nextLine();
					userType = userType.trim();
					
					System.out.print("first name: ");
					fname = in.nextLine();
					fname = fname.trim();
					
					System.out.print("last name: ");
					lname = in.nextLine();
					lname = lname.trim();
					
					System.out.print("username: ");
					username = in.nextLine();
					username = username.trim();
					
					System.out.print("password: ");
					password = in.nextLine();
					password = password.trim();
					
					for (int i = 0; i < UsersAPI.PERMISSION_COUNT; i++) {
						System.out.print("allow acess to " + UsersAPI.catagories[i] + " (y/n)? ");
						str = in.nextLine();
						str = str.trim();
						if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {
							System.out.print("allow max bandwidth for " + UsersAPI.catagories[i] + " (y/n)? ");
							str = in.nextLine();
							str = str.trim();
							if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {
								permissions[i] = new Permission(UsersAPI.catagories[i], true, true);
							} else {
								permissions[i] = new Permission(UsersAPI.catagories[i], true, false);
							}
						} else {
							permissions[i] = new Permission(UsersAPI.catagories[i], false, false);
						}
					}
					
					User newUser = new User(userType, fname, lname, username, password, false, permissions);
					try {
						users.addUser(newUser);
					} catch (IllegalArgumentException | IOException e) {
						e.printStackTrace();
					}
				} 
				
				state = currentUser.getUserType();
				continue;
			} else if (state.equals("remove user")) {
				String username;
				
				System.out.print("username: ");
				username = in.nextLine();
				username = username.trim();
				System.out.print(currentUser.getUsername() + " are you sure you want to remove a user " + username + " (yes/no)? ");
				str = in.nextLine();
				str = str.trim();
				if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {			
					try {
						users.deleteUser(username);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
				state = currentUser.getUserType();
				continue;
			} else if (state.equals("edit user")) {
				
			}
		}
	}

}
