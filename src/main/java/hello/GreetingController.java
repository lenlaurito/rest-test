package hello;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.Log;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private Log log;
    private Map<Long, Greeting> greetingMap;

    public GreetingController() {
        greetingMap = new HashMap<Long, Greeting>();
    }

//    @RequestMapping(value="/greeting/", method= RequestMethod.GET)
//    public @ResponseBody Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
//        System.out.println("TESTING");
//        return new Greeting(counter.incrementAndGet(),
//                String.format(template, name));
//    }

    @RequestMapping(value = "/greeting", method = RequestMethod.POST)
    public Greeting createGreeting(@RequestBody Greeting greeting) {
        System.out.println(greeting.getContent());
        System.out.println(greeting.getId());
        greetingMap.put(greeting.getId(), greeting);
        return greeting;
    }

    @RequestMapping(value= "/greeting", method = RequestMethod.GET)
    public List<Greeting> getAllGreetings() {
        List greetingList = new ArrayList<Greeting>();
        Set<Long> greetingIds = greetingMap.keySet();
        for(Long greetingId: greetingIds) {
            greetingList.add(greetingMap.get(greetingId));
        }
        return greetingList;
    }

    @RequestMapping(value= "/greeting/{id1}", method = RequestMethod.GET)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id1") long id) {
        Greeting greeting = greetingMap.get(id);
        if(greeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}