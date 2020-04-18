Note:

***application.plroperties***
```properties
logging.level.org.springframework =  debug
```
* Enable debug and see the "CONDITIONS EVALUATION REPORT"


### What is dispatcher servlet?
DS. is handling all the requests.

 
### Who is configuring dispatcher servlet?
- spring boot auto configuration

### What does dispatcher servlet do?
- when we type the URL, this request go to DS.(font controller)
- DS know all the mapping which are available.

### How does the HelloWorldBean object get converted to JSON?
It also handle by spring-boot auto configuration. Because the message converters
and the jakson beans are getting initialized

### Who is configuring the error mapping?
- spring boot auto configuration, it create default error page.

### @ResponseBody
```java
@RestController
@ResponseBody
public class HelloWordController {
    
}
```

