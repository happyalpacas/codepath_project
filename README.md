Original App Design Project - README Template
===

# 

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
A social media application for college-wide clubs and organizations.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** 
- **Mobile:**
- **Story:**
- **Market:**
- **Habit:**
- **Scope:**

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
* User/club can register a new account
* User/club can login
* Club can make announcements
* User can personalize feed (follow clubs)
* User can view feed with posts from followed clubs

**Optional Nice-to-have Stories**
* Club can send a message to club members
* User can chat with clubs at their college
* Suggesting clubs for the user
* Users can chat with each other
* User can react to the announcement
* App can support multiple colleges

### 2. Screen Archetypes

* Register
   * User can create a student account
   * User can create a club account
* Login 
   * User can log in to a student account
   * User can log in to a club account
* Profile
    * User profile including picture, name, bio
    * User can view the list of clubs they follow
    * Clubs can view the list of club members
* Stream
    * User can view a feed of posts from the clubs that they follow 
* Detail
    * User can view details of a specific post 
* Chat (?)
    * User can message clubs they follow and vice versa
    * Clubs can message each other 
* Post
    * Clubs can create events 
    * Clubs can post updates

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Stream/Home page
* Search
* Post
* Profile

**Flow Navigation** (Screen to Screen)

* Stream/Home
   * Profile
* Search
   * Proflile
* Post
* User Profile/ Club Pahe
  * Chat

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models

User
| Property | Type | Description |
| ------------- | -------- | ------------|
|username | String | unique id for user |
|password | String | unique password for user |
|profilePic | File | image for user profile |
|dateJoined | DateTime | date the user joined |
|clubsFollowed | Array | the clubs this user follows |
|bio | String | user self-description 


Club_User

| Property      | Type     | Description |
| ------------- | -------- | ------------|
| clubname      | String   | unique id for the club (default field) |
| password      | String   | unique password for the club |
| image         | File     | image that user posts |
| description       | String   | a description of the club |
| followes | Array   | an array of the users who follow this club |
| createdAt     | DateTime | date when post is created (default field) |

Post

 | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | author        | Pointer to User| image author |
   | image         | File     | image that user posts |
   | caption       | String   | image caption by author |
   | likesCount    | Number   | number of likes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |

Message

 | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the chat (default field) |
   | sender        | Pointer to Sender| message sender |
| recipient        | Pointer to recipient| message recipient |
   | content       | String   | message 
   | sentAt | DateTime | time message sent |
   |read | boolean | whether or not message opened |


### Networking

List of network requests by screen:

* Stream/Home Screen
    * (Read/GET) Query all posts by clubs the user follows
        ```swift
        protected void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + " usernname: " + post.getUser().getUsername() );
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
    ```
    * (Create/POST) Create a new like on a post
    ```swift
    
    ParseQuery<Post> query = ParseQuery.getQuery("post");
    // Retrieve the object by id
    query.getInBackground(currentUser, new GetCallback<Post>() {
      public void done(Post post, ParseException e) {
    if (e == null) {
      int likes = post.getLikesCount();
      likes++;
      post.put("likesCount", likes);
      post.saveInBackground();
        }
      }
    });
    ```
    * (Delete) Delete an existing like
    ```swift
    ParseQuery<Post> query = ParseQuery.getQuery("Post");
    ParseUser currentUser = ParseUser.get(currentUser);
    // Retrieve the object by id
    query.getInBackground(currentUser, new GetCallback<Post>() {
      public void done(Post post, ParseException e) {
    if (e == null) {
      // Now let's update it with some new data. In this case, only cheatMode and score
      // will get sent to your Parse Server. playerName hasn't changed.
      int likes = post.getLikesCount();
      likes--;
      post.put("likesCount", likes);
      post.saveInBackground();
    }
  }
});
    ```
* Search
    * (Read/GET) Query all clubs and organizations
    ```swift
    protected void queryClubUsers() {
        // Specify which class to query
    ParseQuery<ClubUser> query = ParseQuery.getQuery(ClubUser.class);
        query.include(ClubUser.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(ClubUser.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<ClubUser>() {
            @Override
            public void done(List<ClubUser> clubs, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting clubs", e);
                    return;
                }
                for (ClubUser club : clubs){
                    Log.i(TAG, "Description: " + club.getDescription() + " username: " + club.getUser().getUsername());
                }
                allClubs.addAll(clubs);
                adapter.notifyDataSetChanged();
            }
        });
    }
```
* Profile

    * (Read/GET) Query logged in user object


    ```swift
    ParseQuery<User> query = ParseQuery.getQuery("User");
    query.getInBackground(currentUser, new GetCallback<ParseObject>() {
  public void done(User user, ParseException e) {
    if (e == null) {
      Log.i(TAG, "Username: " + user.getUsername() + "Bio: " +     user.getBio() + "Date joined: " + user.getDateJoined());
    } else {
      Log.e(TAG, "error");
    }
      }
    });
    ```
    * (Update/PUT) Update user profile image
    ```swift
    ParseQuery<User> query = ParseQuery.getQuery("user");
    ParseUser currentUser = ParseUser.get(currentUser);
// Retrieve the object by id
query.getInBackground(currentUser.getId(), new GetCallback<User>() {
  public void done(User user, ParseException e) {
    if (e == null) {
      // Now let's update it with some new data. In this case, only cheatMode and score
      // will get sent to your Parse Server. playerName hasn't changed.
      user.put("image", File file);
      user.saveInBackground();
    }
  }
});
    ```
* Post
    * (Create/POST) Create a new post object
    ```swift
    // Create the post
    ParseObject myPost = new ParseObject("Post");
    myPost.put("objectId", "exId");
    myPost.put("author", User user);
    myPost.put("image", File file);
    myPost.put("caption", "This is an example of a post!");
    myPost.put("likesCount", 0);
    myPost.put("createdAt", DateTime now);
    myPost.put("updatedAt", DateTime now);

    // This will save post
    myPost.saveInBackground();
    ```
* Chat
    * (Create/POST) Create new message
    ```swift
    ParseObject myMessage = new ParseObject("Message");
    
    myPost.put("objectId", "exId");
    myPost.put("sender", User user);
    myPost.put("recipient", User user);
    
    myPost.put("content", "This is an example of a message!");
    myPost.put("sentAt", DateTime now);
    myPost.put("read", false);
    
    myMessage.saveInBackground();
    ```
