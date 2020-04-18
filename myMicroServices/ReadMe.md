# (14) Data JPA

## 14.1 Add dependancies.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

here we are using in memory database(h2)

## 14.2 Configurations
***apllication.property***
```properties
# === add jpa configuration ===
#= enable sql log in =
spring.jpa.show-sql=true

#= enable h2 console =
spring.h2.console.enabled=true
```

## 14.3 JPA Annotations

`@Entity`: mark this as a entity(DB Table)

`@Id` : mark it as a primary key

`@GeneratedValue`: 

## 14.4 Create entity class

***Entity class: User1.java***
```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Data   //  lombok
@AllArgsConstructor //  lombok
@NoArgsConstructor  //  lombok
@Entity     //  data jpa
public class User1 {

    /* make this a primary key**/
    @Id     //  data jpa
    @GeneratedValue
    private int id;

    @Size(min = 2 , message = "Name should have at least 2 char") // validate the size for 2 letters
    private String name;

    @Past // validate this should be a past date
    private Date birthDate;

    /**generate setters and getters*/
}
```

## 14.5 insert user Data

- create file in resource data.sql

**error:** 
```sql
insert into user values(3,sysdate(), "mala");
```
Don't add double quotes "mala" in sql file, add single quotes.

```sql
insert into user values(3,sysdate(), 'mala');
```

*data.sql*
```sql
insert into user1 values(1,sysdate(), 'pipi');
insert into user1 values(2,sysdate(), 'sama');
insert into user1 values(3,sysdate(), 'mala');
```

output:
run the application, the table had created in memory. `45_created table.PNG`

### 14.5.1 H2 DB
url: http://localhost:8080/h2-console/
***connect to h2 db***
jdbc URL:
`jdbc:h2:mem:testdb`

`45_H2_login.PNG`, `46_h2_db_select.PNG`

## 14.6 CURD

***UserJPA.java***
```java
@Data   //  lombok
@AllArgsConstructor //  lombok
@NoArgsConstructor  //  lombok
@Entity     //  data jpa
public class UserJPA {

    /* make this a primary key**/
    @Id     //  data jpa
    @GeneratedValue
    private int id;

    private String name;

    private Date birthDate;

    /**generate setters and getters*/
}
```

***UserRepository.java***
```java
import com.pipi.bean.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;

//  [this is an interface]             [user repo]  [bean class, primary key]
public interface UserRepository extends JpaRepository<UserJPA, Integer> {
    
}
```

```java
@RestController
public class UserJPAController {

    @Autowired
    private UserRepository userRepository;

    // get all ==================================================
    @GetMapping("jpa/users")
    public List<UserJPA> retrieveAllUsers() {
        return userRepository.findAll();
    }

    // get by ID ==================================================
    @GetMapping("jpa/users/{id}")
    public Optional<UserJPA> retrieveUser2(@PathVariable int id) {
        Optional<UserJPA> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }
        return user;
    }

    // save  ==================================================
    @PostMapping("jpa/user")
    public UserJPA createUser1(@RequestBody UserJPA user) {
        UserJPA userJPA = userRepository.save(user);
        return user;
    }

    // delete ==================================================
    @DeleteMapping("jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}

```

### 1. Read
output:
`47_jpa_read.PNG` `48_jpa_read_id.PNG`

### 2. Delete
output:
`49_jpa_delete_id.PNG`

###3. create
output:
`50_jpa_create.PNG`

###4. Update
send requst with a id. then it'll update the particular row.

output:
`51_jpa_update.PNG`

## 14.7 ID generation

`51_hibernate _sequence.PNG`
* Here it'll generate the id by 1 and increment by 1. 
* Although we manually entered data 100 or some thing, when it adds through jpa, it begins from 1.

# 15 RelationsShips
`53_relationShips.PNG`

# 15.1 One to Many

***UserJPA.java***
```java
//      name of the POSTJPA column (line 36 in OPST.java)
    @OneToMany(mappedBy = "user")
    private List<PostJPA> postJPAS;
```

***PostJPA.java***
```java
/**
     * in hibernate it'll automatically fetch, so post will fetch user and user will fetch the post, this will happens
     * recurcively. by using FetchType.LAZY, unless you call you call post.getdetails(), it'll not fetch
     */
    @ManyToOne(fetch = FetchType.LAZY)  // data jpa
    @JsonIgnore /*this field will ignored*/
    private UserJPA user;

    /** here we skip user,
     * because post will print the user and user will print the user, hence it'll end-up out of memory.
     * */
    @Override
    public String toString() {
        return "PostJPA{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
```

***UserJPAController***
```java
    //<<<<<<<<<< Post >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // get by ID ==================================================
    @GetMapping("jpa/users/{id}/posts")
    public List<PostJPA> retrieveUserPosts(@PathVariable int id) {
        Optional<UserJPA> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }
        return user.get().getPostJPAS();
    }

    // save and update  =========================================
    @PostMapping("jpa/users/{id}/post")
    public PostJPA createUserPost(@PathVariable int id, @RequestBody PostJPA post) {
        Optional<UserJPA> userJPAOptional = userRepository.findById(id);
        if (!userJPAOptional.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }

        UserJPA user = userJPAOptional.get();
        post.setUser(user);

        PostJPA postJPA = postRepository.save(post);
        return postJPA;
    }

    // save and update  =========================================
    @PostMapping("jpa/user/{id}/post")
    public PostJPA createPost(@PathVariable int id, @RequestBody PostJPA post) {
        Optional<UserJPA> userJPA = userRepository.findById(id);
        if (!userJPA.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }
        UserJPA user = userJPA.get();
        post.setUser(user);
        PostJPA postJPA = postRepository.save(post);
        return postJPA;
    }
    
```

***output***
getAll: `54_oneToMany_getAll.PNG`

getByID: `55_oneToMany_getById.PNG`

Post: `56_oneToMany_post.PNG`

# 16. Rechardson Maturity Model

- how restful are you..

## LEVEL 0

- Expose SOAP web services in REST style.
    - http://servers/getPosts
    - http://servers/deletePosts
    - http://servers/doThis
   
## LEVEL 1

- Expose Resources with Proper URI
    - http://servers/accounts
    - http://servers/accounts/10

**Note:** Improper use of HTTP methods.

## LEVEL 2

LEVEL 1 + HTTP Methods

## LEVEL 3

LEVEL 2 + HATEOAS +DATA + Next Possible Actions

## 17. RESTful Web Services - Best practices

**Consumer First**

- Always think about your consumer. Have a clear idea about who your consumers are. Do you expect a mobile application 
coming in, in the future, and have a clear idea of what they want.

- Before you name your resources Think from the perspective of your customers. What do they think about those resources.
Will they be able to understand the naming of your resources. The simpler it is for your consumer to understand your 
services the better it is for you.
 
- Last but not least, have great documentation for your APIs. Swagger is one of the most popular documentation standards
for RESTful APIs. But whatever standard you are using, Make sure that your consumers understand the documentation that
you produce.

**Request Methods**

- Make the best use of whatever HTTP provides. RESTful web services are based on HTTP. Make the best use of the request 
methods. Use the right request method appropriate for your specific action, `Get, Post, Put and Delete`. 

**Response status**

- Ensure that you're sending a proper response status back. When a resource is not found, Do not send a server error.
When you create a resource do not send just success. Send created back.

**No secure info in URI**

Ensure that there is a no secure info that is sent in your URIs. Things like SSN, just don't go by the standard 
definition. Think about what you're putting in the URI and make sure that there is nothing secure that going in

**use plurals**

For example, in the course, we always used slash users. Any resource that we created was slash users slash one. 
Not slash user slash one.
I really prefer using the plurals because its more readable than using the singular. 

**Use nouns of resources**

When you think resources think nouns. It user, its account. It's not delete user. It's not a delete account. Think about
nouns. When you talk about resources think about nouns. However thinking about noun is not always possible. 

**For Exceptions:**
*Define a consistent approach:*
- /search
- PUT/gists/{di}/star
- DELETE/gists/{di}/star

There are always exceptions scenarios. For example, if there's a search link on the web page. How do you do that? For 
all these exceptional scenarios have a consistent approach. If you are searching through something then if you are 
searching through users then say slash users slash search. So all the resource have the action defined. For example, 
this is how Github does a star on the gists. So if I put a star on the gist, the way Github sends the request is to the 
resource of the gist it sends a put request with star in the URI. And the same thing in delete. If I unstar something 
this is the request which goes out. So define a consistent approach of how you would want to handle the exceptions 
scenarios. Things that don't fit into the nouns.





https://www.learningcrux.com/course/master-microservices-with-spring-boot-and-spring-cloud
https://www.learningcrux.com/video/master-microservices-with-spring-boot-and-spring-cloud/2/36