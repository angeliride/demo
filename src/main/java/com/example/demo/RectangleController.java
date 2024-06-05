package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class RectangleController {
    //Zadanie 3
    List<Rectangle> rectangles = new ArrayList<>();
    //zasób
    @GetMapping("rectangle")
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(20, 10, 100, 213, "red");
        return rectangle;
    }  //dostaliśmy JSON

    //SVG
    @GetMapping("toSvg")
    public String toSvg() {
        StringBuilder rectanglesSvg = new StringBuilder();
        rectanglesSvg.append("<svg width=\"1000\" height=\"1000\">");
        for (Rectangle rect : rectangles) {
            rectanglesSvg.append(String.format("<rect width=\"%d\" height=\"%d\" x=\"%d\" y=\"%d\" fill=\"%s\"/>", rect.getWidth(), rect.getHeight(), rect.getX(), rect.getY(), rect.getColor()));
        }
        rectanglesSvg.append("</svg>");
        return rectanglesSvg.toString();
    }


    //zadanie 4 - zadekorujmy wszystko   //nie działa
    @PostMapping("addRectangle")
    public int addRectangle(@RequestBody Rectangle rectangle) {
        //Rectangle rectangle = new Rectangle(20, 10, 100, 213, "red");
        rectangles.add(rectangle);
        return rectangles.size();
    }

    @GetMapping("rectangles")
    public List<Rectangle> getRectangles() {
        return rectangles;
    }
    //zadanie5.1
    @GetMapping("rectangle/{id}")
    public Rectangle getRectangle(@PathVariable Long id) throws IOException {


        return rectangles.get(id.intValue());
    }
    @PutMapping("rectangle/{id}")
    public void getRectangle (@PathVariable int id, @RequestBody Rectangle rectangle) {
        rectangles.set(id, rectangle);
    }
    @DeleteMapping("rectangle/{id}")
    public void deleteRectangle(@PathVariable int id) {
        rectangles.remove(id);
    }
}
