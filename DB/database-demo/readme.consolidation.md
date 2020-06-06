# (1) create new project
5.1_create_project.PNG

## 1.1 Add dependancies.
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

## 1.2 Configurations
***apllication.property***
```properties
# === add jpa configuration ===
#= enable sql log in =
spring.jpa.show-sql=true

#= enable h2 console =
spring.h2.console.enabled=true
```

H2 console:

http://localhost:8080/h2-console/

## 1.3 insert values

create `data.sql` in resource folder

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
create table user1(
    id integer not null ,
    birthday timestamp,
    name varchar(225) not null ,
    primary key (id)
);

insert into user1 values(1,sysdate(), 'pipi');
insert into user1 values(2,sysdate(), 'sama');
insert into user1 values(3,sysdate(), 'mala');
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

# 15.3 One to Many


