package com.example.demo;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
@RestController
public class ImageFormController {
    private String image64Base;
    private ImageController ic;
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file, Model model, int factor) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("message","Wybierz plik do załadowania");
            return "index";
        }

        byte[] photo = file.getBytes();
        String encodedImage = Base64.getEncoder().encodeToString(photo);
        image64Base = ic.increaseImageBrightness(encodedImage, factor);

        image64Base = Base64.getEncoder().encodeToString(photo);
        model.addAttribute("image", image64Base);
        return "image";
    }
    @GetMapping("/image")
    public String show(Model model) {
        model.addAttribute("image", image64Base);
        return "image";
    }

}
