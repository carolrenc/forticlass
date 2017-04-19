package userInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class UsersAPI {
	static final int PERMISSION_COUNT = 10;
	static final String catagories[] = {"category0", "category1", "category2", "category3", "category4", "category5", "category6", "category7", "category8", "category9"};
	
	private String filePath;
	
	public UsersAPI(String filePath) {
		this.filePath = filePath;
	}

	public User getUser(String username) throws FileNotFoundException, IllegalArgumentException {
		File usersFile = new File(filePath);
		if (usersFile.exists() && usersFile.isDirectory()) {
		} else {
			throw new IllegalArgumentException("File path does not exist or is not a directory");
		}
		
		usersFile = new File(filePath + "/" + username + ".txt");
		
		if (usersFile.exists()) {
			
			Scanner in = new Scanner(usersFile);

			String userType;
			String fName;
			String lName;
			String password;
			boolean status;
			boolean allowed;
			boolean bandwidth;
			String catagory;
			Permission permissions[] = new Permission[UsersAPI.PERMISSION_COUNT];
			
			if (in.hasNext()) {
				in.next().trim(); // get username but i already have it so it doesnt matter
			} else {
				in.close();
				throw new IllegalArgumentException("Corrupt user file");
			}
			if (in.hasNext()) {
				userType = in.next().trim();
			} else {
				in.close();
				throw new IllegalArgumentException("Corrupt user file");
			}
			if (in.hasNext()) {
				fName = in.next().trim();
			} else {
				in.close();
				throw new IllegalArgumentException("Corrupt user file");
			}
			if (in.hasNext()) {
				lName = in.next().trim();
			} else {
				in.close();
				throw new IllegalArgumentException("Corrupt user file");
			}
			if (in.hasNext()) {
				password = in.next().trim();
			} else {
				in.close();
				throw new IllegalArgumentException("Corrupt user file");
			}
			if (in.hasNextBoolean()) {
				status = in.nextBoolean();
			} else {
				in.close();
				throw new IllegalArgumentException("Corrupt user file");
			}
			for (int i = 0; i < UsersAPI.PERMISSION_COUNT; i++) {
				if (in.hasNext()) {
					catagory = in.next();
					if (in.hasNextBoolean()) {
						allowed = in.nextBoolean();
						if (in.hasNextBoolean()) {
							bandwidth = in.nextBoolean();
							permissions[i] = new Permission(catagory, allowed, bandwidth);
						} else {
							in.close();
							throw new IllegalArgumentException("Corrupt user file");
						}
					} else {
						in.close();
						throw new IllegalArgumentException("Corrupt user file");
					}
				} else {
					in.close();
					throw new IllegalArgumentException("Corrupt user file");
				}
			}
			in.close();
			
			if (userType.equals("Admin") || userType.equals("Student") || userType.equals("Professor")) {
				User newUser = new User(userType, fName, lName, username, password, status, permissions);
				return newUser;
			} else {
				throw new IllegalArgumentException("Corrupt user file");
			}
		} else {
			throw new IllegalArgumentException("User does not exist");
		}
	}
	
	public void saveUser(User updatedUser) {
		try  {
			this.deleteUser(updatedUser.getUsername());
			this.addUser(updatedUser);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage() + "\n");
		} catch (IOException e) {
			System.out.println(e.getMessage() + "\n" + "User file may have been corupted\n");
		}
		
	}
	
	public void deleteUser(String username) throws IllegalArgumentException {
		File usersFile = new File(filePath);
		if (usersFile.exists() && usersFile.isDirectory()) {
		} else {
			throw new IllegalArgumentException("File path does not exist or is not a directory");
		}
		usersFile = new File(filePath + "/" + username + ".txt");
		if(!usersFile.delete()) {
			throw new IllegalArgumentException("User " + username +  " is not in the usersfile");
		}
	}
	
	public void addUser(User updatedUser) throws IOException, IllegalArgumentException {
		File usersFile = new File(filePath);
		if (usersFile.exists() && usersFile.isDirectory()) {
		} else {
			throw new IllegalArgumentException("File path does not exist or is not a directory");
		}
		usersFile = new File(filePath + "/" + updatedUser.getUsername() + ".txt");
		Files.write(usersFile.toPath(), updatedUser.toString().getBytes());
		
	}
}
