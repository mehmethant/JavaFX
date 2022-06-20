package com.example.demo3;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class DrawShape {

    public Line drawLine(double startX, double startY, double endX, double endY,String color) {
        Line line = new Line();

        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(10);
        line.setStroke(Paint.valueOf(color));

        return line;
    }

    public Rectangle drawRectangle(double startX, double startY, double endX, double endY, String color, boolean fill) {
        double height = Math.abs((startY - endY));
        double width = Math.abs((startX - endX));

        Rectangle rectangle;

        if (startX > endX && startY > endY)
            rectangle = new Rectangle(endX, endY, width, height);
        else if (startX < endX && startY > endY)
            rectangle = new Rectangle(startX, endY, width, height);
        else if (startX < endX && startY < endY)
            rectangle = new Rectangle(startX, startY, width, height);
        else {
            rectangle = new Rectangle(endX, startY, width, height);
        }

        if (fill)
            rectangle.setFill(Paint.valueOf(color));
        else {
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Paint.valueOf(color));
            rectangle.setStrokeWidth(5);
        }

        return rectangle;
    }

    public Circle drawCircle(double startX, double startY, double endX, double endY, String color, boolean fill) {

        double radius = Math.sqrt((Math.pow((startX - endX), 2)) + (Math.pow((startY - endY), 2)));
        double centerX, centerY;
        centerX = (Math.abs(startX + endX)) / 2;
        centerY = (Math.abs(startY + endY)) / 2;
        Circle circle = new Circle(centerX, centerY, radius / 2);
        if (fill)
            circle.setFill(Paint.valueOf(color));
        else {
            circle.setFill(Color.WHITE);
            circle.setStroke(Paint.valueOf(color));
            circle.setStrokeWidth(5);
        }
        return circle;
    }


    public Polygon drawPolygon(double[] cords, String color, boolean fill) {
        Polygon polygon=new Polygon(cords);

        if (fill)
            polygon.setFill(Paint.valueOf(color));
        else {
            polygon.setFill(Color.WHITE);
            polygon.setStroke(Paint.valueOf(color));
            polygon.setStrokeWidth(5);
        }
        return polygon;
    }

}