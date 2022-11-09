//package javawebdev.petsns.exception;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @GetMapping("/error")
//    @ExceptionHandler(value = Exception.class)
//    public String exception(@ModelAttribute Exception e, Model model) {
//        model.addAttribute("exception", e);
//        return "/error";
//    }
//}
