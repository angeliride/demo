package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
//zadanie 6
@RestController
public class ImageController {
    public void increaseBrightness(BufferedImage image, int factor) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                pixel = brightenPixel(pixel, factor);
                image.setRGB(x, y, pixel);

            }
        }
    }

    private int brightenPixel(int pixel, int factor) {
        int mask = 255;
        int blue = pixel & mask;
        int green = (pixel >> 8) & mask;
        int red = (pixel >> 16) & mask;
        blue = brightenPixelPart(blue, factor);
        green = brightenPixelPart(green, factor);
        red = brightenPixelPart(red, factor);
        return blue + (green << 8) + (red << 16);

    }

    private int brightenPixelPart(int color, int factor) {
        color += factor;
        if (color > 255) {
            return 255;
        } else {
            return color;
        }
    }
    @GetMapping("brightenImage")
    public  String increaseImageBrightness(@RequestParam String imageBase, @RequestParam int factor) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(imageBase);
        BufferedImage bf = ImageIO.read(new ByteArrayInputStream(imageBytes));
        increaseBrightness(bf, factor);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", stream);
        byte[] newImage = stream.toByteArray();
        String result = Base64.getEncoder().encodeToString(newImage);
        return result;

    }

    //zadanie 7
    @GetMapping
    public ResponseEntity<byte[]> increaseImageBrightnessBinary(@RequestParam String ImageBase, @RequestParam int factor) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(ImageBase);
        BufferedImage bf = ImageIO.read(new ByteArrayInputStream(imageBytes));
        increaseBrightness(bf, factor);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", stream);
        byte[] newImage = stream.toByteArray();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(newImage.length);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(newImage);
    }
}



