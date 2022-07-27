## Websockets and Stomp 

:trollface:

This project show cases how one can set up websockets for a chat functionality using springboot mvc.

#### Dependencies
* Webjars [Website](https://www.webjars.org/) 
* spring-boot-starter-websocket
* spring-boot-starter-web
* spring-boot-starter-thymeleaf

### Setting up the websocket server
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-demo").withSockJS();
    }
    
}
```

### Setting up an auto response

```java
public class UserController {    
    @MessageMapping("/user")
    @SendTo("/topic/user")
    public UserResponse getUser(User user){
        return new UserResponse("Hello "+user.getName());
    }
}
```

### Setting up the javascript in index.html 

```javaScript
 var stompClient = null;

        function setConnected(connected) {
            $("#connect").prop("disabled", connected);
            $("#disconnect").prop("disabled", !connected);
            if (connected) {
                $("#conversation").show();
            }
            else {
                $("#conversation").hide();
            }
            $("#userinfo").html("");
        }

        function connect() {
            var socket = new SockJS('/websocket-demo');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/user', function (greeting) {
                    showGreeting(JSON.parse(greeting.body).content);
                });
            });
        }

        function disconnect() {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }

        function sendName() {
            stompClient.send("/app/user", {}, JSON.stringify({'name': $("#name").val()}));
        }

        function showGreeting(message) {
            $("#userinfo").append("<tr><td>" + message + "</td></tr>");
        }

        $(function () {
            $("form").on('submit', function (e) {
                e.preventDefault();
            });
            $( "#connect" ).click(function() { connect(); });
            $( "#disconnect" ).click(function() { disconnect(); });
            $( "#send" ).click(function() { sendName(); });
        });
```



Code Reference [Link](https://github.com/udacity/JDND/tree/master/demos/c1/spring-boot-websocket/src/main/java/websocket) 

Tuitorial [Link](https://www.baeldung.com/websockets-spring)
