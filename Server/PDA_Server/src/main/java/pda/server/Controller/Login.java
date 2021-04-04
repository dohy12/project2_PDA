package pda.server.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class Login {

    @RequestMapping(value = "/{group}", method = RequestMethod.GET)
    public boolean Login(@PathVariable String group, @RequestParam String id, @RequestParam String pw)
    {

        return result;
    }

    @RequestMapping(value = "/{group}", method = RequestMethod.POST)
    public boolean UserCreate(@PathVariable String group)
    {

        return result;
    }

    private String[] LoadPW(String id)
    {
        return result;
    }

    private boolean AddUser()
    {
        return result;
    }

    private String HashingF(String pw, String salt)
    {

        return result;
    }

}
