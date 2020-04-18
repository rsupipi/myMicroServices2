# Whitelabel Error Page
    "status": 404,
    "error": "Not Found",
`06_Error_Whitelable.PNG`

1. The Application class (eg: SpringAppApplication.java) should be at the root level, as well as the controller classes
can be placed under the same level or maybe in a subdirectory. This application class should be denoted using 
`@SpringBootApplication` or `extends SpringBootApplication`

2. You may miss @RestController annotation at top of the controller class.

3. Or can be missed the mapping annotation.( `@PostMapping("/save")` or `@GetMapping("/id")` etc.)

4. finally just check whether you are entering the correct request mapping address(URL) you may miss a slash or 
`@RequestMapping("/customer")`(if the annotation available in your controller class) or spellings error in the URL.

# Whitelabel Error Page
  `This application has no explicit mapping for /error, so you are seeing this as a fallback.`
  `There was an unexpected error (type=Internal Server Error, status=500).`
  
```java
public class Message {
    private String myMessage;

    public Message(String myMessage) {
        this.myMessage = myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }
}
```
* should define getters, If we doesn't define getter the automatic conversion not happen.
```java
    public String getMyMessage() {
        return myMessage;
    }
```
  
# Type definition error
"status = 5000"
"Internal server error"
* With the latest Jackson and SB. version default constructor is no longer needed.

# HATEOAS calss change
In case you are using HATEOAS v1.0 and above (Spring boot >= 2.2.0), do note that the classnames have changed. Notably the below classes have been renamed:

ResourceSupport changed to RepresentationModel
Resource changed to EntityModel
Resources changed to CollectionModel
PagedResources changed to PagedModel
ResourceAssembler changed to RepresentationModelAssembler