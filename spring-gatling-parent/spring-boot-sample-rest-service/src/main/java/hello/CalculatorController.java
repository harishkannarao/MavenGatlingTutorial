package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculator", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class CalculatorController {

    @RequestMapping(value = "/sum", method = RequestMethod.GET)
    public ResponseEntity sum(@RequestParam("value1") int value1, @RequestParam("value2") int value2) {
        CalculationResult result = new CalculationResult(value1+value2);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
