// User requests will be controlled here.
// Designed as the first user interface corresponding logged in user

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class UserInterface {
	private User currentUser = null;


	//SIGNIN userName password

	public void signIn(String username, String password){
		//Checks username and password for every user.
		for (User user: Helper.getUserList()) {
			if (username.equals(user.getUserName()) && password.equals(user.getPassword())){
				this.currentUser = user;
				System.out.println("You have successfully signed in.");
				return;
			}
		}
		System.out.println("Invalid username or password! Please try again.");
		}

	//Signs out the user
	public void signOut() {
		if (LoggedIn()){
			System.out.println("You have successfully signed out.");
			this.currentUser=null;
		}

	}

	// Updates current user profile information
	public void updateProfile(String name, Date dateOfBirth,String graduatedSchool) throws IOException, ParseException {
		//User must be logged in before updating the profile.
		if (LoggedIn()){
			//UPDATEPROFILE<TAB>name<TAB>dateofBirth<TAB>schoolGraduated
			this.currentUser.setName(name);
			this.currentUser.setDateOfBirth(dateOfBirth);
			this.currentUser.setGraduatedSchool(graduatedSchool);
		}
	}
	
	// Changes password upon current user request
	public void changePassword(String password) {
		//CHPASS<TAB>oldPassword<TAB>newPassword
		if (LoggedIn()) {
			if (this.currentUser.getPassword().equals(password))
			{
				this.currentUser.setPassword(password);
			}
			else
			{
				System.out.println("Password mismatch!");
			}
		}
	}
	
	// Adds friend to current user
	// ADDFRIEND<TAB>userName
	public void addFriend(String userName){
		if (!LoggedIn())
		{
			return;
		}	
		// if there is such user
		User friendUser = null;
		for (User user : Helper.getUserList())
		{
			if (user.getUserName().equals(userName))
			{
				friendUser = user;
				break;
			}
		}
		if (friendUser == null)
		{
			System.out.println("No such user!");
			return;
		}
		else if (this.currentUser.getFriendList().contains(friendUser)) 
		{
			System.out.println("This user is already in your friend list!");
			return;
		}
		else
		{
			this.currentUser.getFriendList().add(friendUser);
			System.out.println(friendUser.getUserName() + " has been successfully added to your friend list.");
			return;
		}
	}
	
	// Removes friend if not exists
	// REMOVEFRIEND<TAB>userName
	public void removeFriend(String userName) throws IOException, ParseException {
			boolean isFriend=false;
			for (User user: Helper.getUserList()){
				if (LoggedIn() && user.getUserName().equals(userName)) {
					this.currentUser.getFriendList().remove(user);
					isFriend = true;
					System.out.println(userName + " has been successfully removed from your friend list.");
				}
			}if (isFriend==false){
				System.out.println("No such friend!");
			}
	}
	
	// Lists user friends
	public void listFriends() {
		if (LoggedIn()){
			if (this.currentUser.getFriendList()==null){
				System.out.println("You have not added any friend yet!");
				return;
			}else{
				for (User singleUser : this.currentUser.getFriendList()) {
					System.out.println("Name  : " + singleUser.getName());
					System.out.println("Username  : " + singleUser.getUserName());
					System.out.println("Date Of Birth  : " + singleUser.getDateOfBirth());
					System.out.println("School  : " + singleUser.getGraduatedSchool());
					System.out.println("----------------------------------------------");
				}
			}
		}
	}
	
	// Adds Text posts
	// ADDPOST-TEXT<TAB>textContent<TAB>longitude<TAB>latitude<TAB> userName1<:>userName2<:>..<:>userNameN
	public void addTextPost(String textContent, Double longitude, Double latitude, ArrayList<User> taggedFriends) {
		if (LoggedIn()) {
			Location location = new Location(latitude,longitude);
			Date date = new Date(System.currentTimeMillis());
			//formatter.format(date); (To make a human-readable date)
			ArrayList<User> availableTaggedFriends = new ArrayList<User>();
			for (User allFriend: this.currentUser.getFriendList()) {
				for (User taggedFriend : taggedFriends) {
					//Checks whether this current user have a friend or not
					if (allFriend.getUserName().contains(taggedFriend.getUserName())) {
						availableTaggedFriends.add(taggedFriend);
					}else{
						System.out.println(taggedFriend.getUserName() + " is not your friend, and will not be tagged!");
					}
				}
				TextPost userPost = new TextPost(textContent, location, availableTaggedFriends, date);
				System.out.println("The post has been successfully added.");
				this.currentUser.getPostCollection().add(userPost);
			}
		}
	}

	// Adds Image posts
	// ADDPOST-IMAGE<TAB>textContent<TAB>longitude<TAB>latitude<TAB> userName1<:>userName2<:>..<:>userNameN<TAB>filePath<TAB>resolution
	public void addImagePost(String textContent, Double longitude, Double latitude, ArrayList<User> taggedFriends, String imagineFileName, String imageResolution) {
		if (LoggedIn()) {
			Location location = new Location(latitude,longitude);
			Date date = new Date(System.currentTimeMillis());
			//formatter.format(date); (To make a human-readable date)
			ArrayList<User> availableTaggedFriends = new ArrayList<User>();
			for (User allFriend: this.currentUser.getFriendList()) {
				for (User taggedFriend : taggedFriends) {
					//Checks whether this current user have a friend or not
					if (allFriend.getUserName().contains(taggedFriend.getUserName())) {
						availableTaggedFriends.add(taggedFriend);
					}else{
						System.out.println(taggedFriend.getUserName() + " is not your friend, and will not be tagged!");
					}
				}
				ImagePost userPost = new ImagePost(textContent, location, availableTaggedFriends, date, imagineFileName, imageResolution);
				System.out.println("The post has been successfully added.");
				this.currentUser.getPostCollection().add(userPost);
			}
		}
	}

	// Adds Video posts
	//ADDPOST-VIDEO<TAB>textContent<TAB>longitude<TAB>latitude<TAB> userName1<:>userName2<:>..<:>userNameN<TAB>filePath<TAB>videoDuration
	public void addVideoPost(String textContent, Double longitude, Double latitude, ArrayList<User> taggedFriends, String videoFilename, Double videoDuration) {
		if (LoggedIn()) {
			if (LoggedIn()) {
				Location location = new Location(latitude,longitude);
				Date date = new Date(System.currentTimeMillis());
				//formatter.format(date); (To make a human-readable date)
				ArrayList<User> availableTaggedFriends = new ArrayList<User>();
				for (User allFriend: this.currentUser.getFriendList()) {
					for (User taggedFriend : taggedFriends) {
						//Checks whether this current user have a friend or not
						if (allFriend.getUserName().contains(taggedFriend.getUserName())) {
							availableTaggedFriends.add(taggedFriend);
						}else{
							System.out.println(taggedFriend.getUserName() + " is not your friend, and will not be tagged!");
						}
					}
					VideoPost userPost = new VideoPost(textContent, location, availableTaggedFriends, date, videoFilename, videoDuration);
					System.out.println("The post has been successfully added.");
					this.currentUser.getPostCollection().add(userPost);
				}
			}
		}
	}

	// Removing the last post of current user
	// REMOVELASTPOST
	public void removeLastPost() {
		if (LoggedIn()) {
			//finds the index of the last post
			int lastPostIndex = this.currentUser.getPostCollection().size()-1;
			//checks whether postCollection is null or not
			if (this.currentUser.getPostCollection()==null){
				System.out.println("Error: You do not have any post.");
			}else{
				this.currentUser.getPostCollection().remove(lastPostIndex);
				System.out.println("Your last post has been successfully removed.");
			}
		}
	}

	// Showing all posts related with user
	public void showPosts(String userName) {
		if (LoggedIn()) {
			for (User user: Helper.getUserList()){
				if (user.getUserName().equals(userName)){
					if (user.getPostCollection()==null){
						System.out.println(user.getUserName() + " does not have any posts yet.");
					}else{
						System.out.println(user.getUserName() + "'s Posts");
						System.out.println("**************");
						for (Post post : user.getPostCollection()){
							System.out.println(post.display());
						}
					}
				}
			}
		}
	}
	
	// Blocks user from current user if exists
	// BLOCK<TAB>userName
	public void blockUser(String userName) {
		if (LoggedIn()) {
			// Blocking a user
			for (User singleUser: Helper.getUserList()){
				if (singleUser.getUserName().equals(userName)){
					if (this.currentUser.getFriendList().contains(singleUser)){
						//If these two users are friends, removing these friends from each other will be appropriate.
						this.currentUser.getFriendList().remove(singleUser);
					}
					//Blocks whether friend or a user
					this.currentUser.getBlockedList().add(singleUser);
					System.out.println(userName + " has been successfully blocked.");
					return;
				}
			}
		}
	}
	
	// Unblocks user from current user
	// UNBLOCK<TAB>userName
	public void unblockUser(String userName) {
		if (LoggedIn()) {
			//For friends
			for (User singleUser : this.currentUser.getBlockedFriendList()){
				if (singleUser.getUserName().equals(userName)) {
					//Unblocks user
					this.currentUser.getBlockedFriendList().remove(singleUser);
					System.out.println(singleUser.getUserName() + " has been successfully unblocked.");
					return;
				}
			}
			//For users who aren't friends
			for (User singleUser : this.currentUser.getBlockedList()){
				if (singleUser.getUserName().equals(userName)) {
					//Unblocks user
					this.currentUser.getBlockedList().remove(singleUser);
					System.out.println(singleUser.getUserName() + " has been successfully unblocked.");
					return;
				}
			}
		}
	}
	
	// Lists blocked friends of current user
	public void showBlockedFriends() {
		if (LoggedIn()) {
			if (this.currentUser.getBlockedFriendList()==null){
				System.out.println("You haven’t blocked any friend yet!");
			}else{
				for (User singleUser : this.currentUser.getBlockedFriendList()) {
					System.out.println("Name  : " + singleUser.getName());
					System.out.println("Username  : " + singleUser.getUserName());
					System.out.println("Date Of Birth  : " + singleUser.getDateOfBirth());
					System.out.println("School  : " + singleUser.getGraduatedSchool());
					System.out.println("----------------------------------------------");
				}
			}
		}
	}

	// Lists blocked users of current user
	public void showBlockedUsers() {
		if (LoggedIn()) {
			if (this.currentUser.getBlockedFriendList() == null) {
				System.out.println("You haven’t blocked any friend yet!");
			}
			//For users who are blocked
			for (User singleUser : this.currentUser.getBlockedList()) {
				System.out.println("Name  : " + singleUser.getName());
				System.out.println("Username  : " + singleUser.getUserName());
				System.out.println("Date Of Birth  : " + singleUser.getDateOfBirth());
				System.out.println("School  : " + singleUser.getGraduatedSchool());
				System.out.println("----------------------------------------------");
			}
			//For friends who are blocked (Friends are also users.)
			for (User singleUser : this.currentUser.getBlockedFriendList()) {
				System.out.println("Name  : " + singleUser.getName());
				System.out.println("Username  : " + singleUser.getUserName());
				System.out.println("Date Of Birth  : " + singleUser.getDateOfBirth());
				System.out.println("School  : " + singleUser.getGraduatedSchool());
				System.out.println("----------------------------------------------");
			}
		}
	}

	// Checks whether user logged in or not
	public boolean LoggedIn() {
		if (this.currentUser == null){
			// Login with any user
			System.out.println("Error: Please sign in and try again.");
			return false;
		}else{
			return true;
		}
	}

}
