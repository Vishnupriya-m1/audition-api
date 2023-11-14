# Audition API

The purpose of this Spring Boot application is to fetch ```Post``` entities from a third party API. The data is filtered
based
on provided inputs.

## Available Endpoints

### Fetch Posts

#### Fetch all posts

```
/posts
```

- This will list all the posts
- No filters applied

#### Fetch specific post

```
/posts/{id}
```

- This will fetch the post with the corresponding id
- No filters applied

#### Filter posts

posts can be filtered based on

- userId

 ```
/posts?userId={userId}
```  

- title

 ```
/posts?titleContains={titleText}
```

- body

 ```
/posts?bodyContains={bodyText}
```

- All the filters can be applied at the same time

### Fetch Comments

#### Fetch Comments along with Posts

 ```
/post/{id}/comments
/posts/{id}/comments
```

#### Fetch Comments for a Post

- Fetch Posts along with the associated comments

 ```
/comments
 ```

- Fetch all the comments for a post

## TO DO

- The application does not have AuthN and AuthZ mechanism yet
- The application needs a more cleaning up of unused dependencies, they have not yet been removed