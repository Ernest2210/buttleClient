package org.client.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import org.client.enums.FieldType;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameSceneController {
    @FXML
    private AnchorPane ap;
    private List<List<FieldType>> map;

    private List<List<FieldType>> getMap(){
        List<List<FieldType>> map = new ArrayList<>(10);

        List<FieldType> list1 = new ArrayList<>(10);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        map.add(list1);

        List<FieldType> list2 = new ArrayList<>(10);
        list2.add(FieldType.wall);
        list2.add(FieldType.wall);
        list2.add(FieldType.wall);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        map.add(list2);

        List<FieldType> list3 = new ArrayList<>(10);
        list3.add(FieldType.wall);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        list3.add(FieldType.wall);
        list3.add(FieldType.wall);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        map.add(list3);

        List<FieldType> list4 = new ArrayList<>(10);
        list4.add(FieldType.wall);
        list4.add(FieldType.space);
        list4.add(FieldType.space);
        list4.add(FieldType.wall);
        list4.add(FieldType.wall);
        list4.add(FieldType.wall);
        list4.add(FieldType.space);
        list4.add(FieldType.space);
        list4.add(FieldType.space);
        list4.add(FieldType.space);
        map.add(list4);

        List<FieldType> list5 = new ArrayList<>(10);
        list5.add(FieldType.wall);
        list5.add(FieldType.wall);
        list5.add(FieldType.space);
        list5.add(FieldType.wall);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        map.add(list5);

        List<FieldType> list6 = new ArrayList<>(10);
        list6.add(FieldType.wall);
        list6.add(FieldType.wall);
        list6.add(FieldType.space);
        list6.add(FieldType.wall);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        map.add(list6);

        List<FieldType> list7 = new ArrayList<>(10);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.wall);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        map.add(list7);

        List<FieldType> list8 = new ArrayList<>(10);
        list8.add(FieldType.space);
        list8.add(FieldType.space);
        list8.add(FieldType.space);
        list8.add(FieldType.wall);
        list8.add(FieldType.wall);
        list8.add(FieldType.space);
        list8.add(FieldType.wall);
        list8.add(FieldType.space);
        list8.add(FieldType.space);
        list8.add(FieldType.wall);
        map.add(list8);

        List<FieldType> list9 = new ArrayList<>(10);
        list9.add(FieldType.space);
        list9.add(FieldType.space);
        list9.add(FieldType.space);
        list9.add(FieldType.wall);
        list9.add(FieldType.wall);
        list9.add(FieldType.space);
        list9.add(FieldType.wall);
        list9.add(FieldType.wall);
        list9.add(FieldType.wall);
        list9.add(FieldType.wall);
        map.add(list9);

        List<FieldType> list10 = new ArrayList<>(10);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        map.add(list10);
        return map;
    }

    public void initialize(){
        this.map = getMap();
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setX(60*j);
                rectangle.setY(60*i);
                rectangle.setWidth(60);
                rectangle.setHeight(60);
                rectangle.setStrokeWidth(2);
                if(map.get(i).get(j) == FieldType.wall){
                    Image img = new Image("/assets/block.png");
                    rectangle.setFill(new ImagePattern(img));
                }else{
                    rectangle.setFill(Color.BLACK);
                    rectangle.setStroke(Color.BLACK);
                }
                ap.getChildren().add(rectangle);
            }
        }
    }
}
