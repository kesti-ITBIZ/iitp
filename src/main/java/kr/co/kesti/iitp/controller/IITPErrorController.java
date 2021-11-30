package kr.co.kesti.iitp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IITPErrorController implements ErrorController {
    @GetMapping("/error")
    public String redirectErrorPath() {
        return "index.html";
    }
}
