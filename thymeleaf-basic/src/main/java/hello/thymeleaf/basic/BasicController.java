package hello.thymeleaf.basic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "<b>Hello Spring!</b>");
        return "basic/text-basic";
    }

    @GetMapping("text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "<b>Hello Spring!</b>");
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    @Data
    public class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}

/**
 * Escape
 * 만약 서버에서 속성으로 추가할 데이터에 html 태그(ex: <b></b>)를 추가해서 타임리프에서 태그효과까지 같이 사용하고 싶다면 어떻게 해야할까?
 * 컨트롤러에서 model.addAttribute("data", "<b>spring!</b>"); 이렇게 작성하면 될까? 다음 화면은 단순히 서버에서 addAttribute에 태그가 포함된 속성을 추가했을때의 결과다.
 *
 * 출력 : "Hello <b>Spring!</b>"
 * 소스보기 : Hello &lt;b&gt;Spring!&lt;/b&gt;
 *
 * 우리가 의도한대로 나오지 않고 태그까지 다 출력되버렸다.
 *
 * 타임리프가 제공하는 th:text, [[...]] 은 기본적으로 이스케이프(escape)를 제공하기 때문인데,
 * 그 때문에 HTML에서 사용하는 특수 문자(ex: <, >)를  태그의 시작이 아닌 문자로 표현할 수 있도록 &lt;,b&gt;
 * 이런식으로 변경되며 이를 HTML 엔티티라 한다.
 *
 * 이스케이프 기능을 사용하지 않으려면 어떻게 해야할까? Unescape!
 *
 * 타임리프에서는 이를 위해 두 가지 기능을 제공한다.
 * th:utext
 * [[...]] ---> [(...)]
 */
