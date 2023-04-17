package Routes.API;

import Controllers.InnerFlightController;
import Utility.Positive;
import Validator.DateConstraint;
import Validator.StrongJSONConstraint;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping(path = "/api")
@Validated
public class PublicRoutes {

    @GetMapping(value = "/findInnerFlightTicket")
    @ResponseBody
    public String findInnerFlightTicket(HttpServletRequest request,
                                        @RequestParam(value = "src") @NotBlank String src,
                                        @RequestParam(value = "dest") @NotBlank String dest,
                                        @RequestParam(value = "startAt") @NotBlank String startAt, // @DateConstraint
                                        @RequestParam(required = false, value = "endAt") String endAt,
                                        @RequestParam(value = "infant") @Min(0) @Max(10) int infant,
                                        @RequestParam(value = "child") @Min(0) @Max(10) int child,
                                        @RequestParam(value = "adult") @Min(0) @Max(10) int adult
    ) {
        return InnerFlightController.search(src, dest, startAt, endAt, infant, child, adult);
    }


    @PostMapping(value = "/store")
    @ResponseBody
    public String store(HttpServletRequest request,
                        @RequestBody @StrongJSONConstraint(
                                params = {
                                        "src", "dest", "infant", "child",
                                        "adult", "startDate", "result"
                                },
                                paramsType = {
                                        String.class, String.class, Positive.class,
                                        Positive.class, Positive.class, String.class,
                                        JSONObject.class
                                }
                        ) @NotBlank String jsonStr
    ) {
        return InnerFlightController.store(new JSONObject(jsonStr));
    }

}
