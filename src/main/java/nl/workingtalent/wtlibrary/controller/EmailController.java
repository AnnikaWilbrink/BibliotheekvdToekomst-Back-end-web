package nl.workingtalent.wtlibrary.controller;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import nl.workingtalent.wtlibrary.service.EmailService;
import nl.workingtalent.wtlibrary.dto.EmailDto;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class EmailController {

     
	

    @Autowired
    private EmailService service;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /* 
    @GetMapping("/compose")
    public ModelAndView showComposeEmailForm() {
        return new ModelAndView("email/compose");
    }
    */

    @PostMapping("email/send")
    public String sendEmail(@RequestBody EmailDto dto){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(dto.getTo());
            mailMessage.setSubject(dto.getSubject());
            mailMessage.setText(dto.getMessage());
            mailMessage.setFrom(fromEmail);
            service.sendEmail(mailMessage);

            return "ok";
        } catch (Exception e) {
            return "Email sending failed: " + e.getMessage();
        }
    }
   
}
