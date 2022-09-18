package com.example.vc;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import javax.imageio.*;

public class Controller {

    public RadioButton rb2;
    public RadioButton rb4;
    public RadioButton rbPng;
    public RadioButton rgJpg;
    public RadioButton rb2px;
    public RadioButton rb4px;
    public RadioButton rbBmp;
    public Label share1Label;
    public Label share2Label;
    public RadioButton rbBinaryImage;

    String inputPath;
    BufferedImage share1;
    BufferedImage share2;

    BufferedImage superImage;
    BufferedImage cleanSuperImage;

    private Stage myStage;

    @FXML
    private Label welcomeText;


    @FXML
    protected void displayOriginal() throws FileNotFoundException {
        displayImage(inputPath, "Original Image");
    }
    @FXML
    protected void displayShare1() throws IOException {
        ImageIO.write(share1, "png", new File("src/main/resources/programFiles/share1.png"));
        displayImage("src/main/resources/programFiles/share1.png", "Share 1");
    }
    @FXML
    protected void displayShare2() throws IOException {
        ImageIO.write(share2, "png", new File("src/main/resources/programFiles/share2.png"));
        displayImage("src/main/resources/programFiles/share2.png", "Share 2");
    }
    @FXML
    protected void displayImposed() throws IOException {
        superimposeImage(share1,share2);
        ImageIO.write(superImage, "png", new File("src/main/resources/programFiles/imposed.png"));
        displayImage("src/main/resources/programFiles/imposed.png", "Superimposed Image");
    }
    @FXML
    protected void displayCleanImage() throws IOException {
        if(rb2.isSelected()) cleanSuperimposeImage1(share1,share2);
        else cleanSuperimposeImage2(share1,share2);
        ImageIO.write(cleanSuperImage, "png", new File("src/main/resources/programFiles/cleanImposed.png"));
        displayImage("src/main/resources/programFiles/cleanImposed.png", "Clean superimposed Image");
    }


    @FXML
    protected void saveShare1(){
        saveFile(share1);
    }
    @FXML
    protected void saveShare2(){
        saveFile(share2);
    }
    @FXML
    protected void saveSuper(){
        saveFile(superImage);
    }
    @FXML
    protected void saveClean(){
        saveFile(cleanSuperImage);
    }


    @FXML
    protected void loadFile() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        fileChooser.setInitialDirectory(new File("src/main/resources/images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(myStage);

        if(file != null){
            inputPath = file.getAbsolutePath();
            System.out.println(file.getAbsolutePath());
            welcomeText.setText("Current image: "+file.getName());
        }
        else welcomeText.setText("Error while loading file!");


    }
    @FXML
    protected void loadShare1() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        fileChooser.setInitialDirectory(new File("src/main/resources/images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(myStage);

        if(file != null){
            share1 = ImageIO.read(file);
            System.out.println(file.getAbsolutePath());
            share1Label.setText("Current image: "+file.getName());
        }
        else share1Label.setText("Error while loading file!");


    }
    @FXML
    protected void loadShare2() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        fileChooser.setInitialDirectory(new File("src/main/resources/images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(myStage);

        if(file != null){
            share2 = ImageIO.read(file);
            System.out.println(file.getAbsolutePath());
            share2Label.setText("Current image: "+file.getName());
        }
        else share2Label.setText("Error while loading file!");


    }


    @FXML
    protected void split() throws IOException {
        if(rb2px.isSelected()) splitMethod1();
        else splitMethod2();
        share1Label.setText("Default: share1");
        share2Label.setText("Default: share2");
    }
    @FXML
    protected void splitMethod1() throws IOException {
        File inputImage = new File(inputPath);
        BufferedImage imgBuffer = ImageIO.read(inputImage);
        share1 = new BufferedImage(imgBuffer.getWidth()*2,imgBuffer.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        share2 = new BufferedImage(imgBuffer.getWidth()*2,imgBuffer.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0;i< imgBuffer.getWidth(); i++){
            for (int j = 0;j< imgBuffer.getHeight(); j++){
                int rand = getRandomNumberUsingInts(1,3);

                Color c = new Color(imgBuffer.getRGB(i,j));

                if(rbBinaryImage.isSelected())
                    c = blackOrWhite(c);

                if(c.equals(Color.BLACK)) {
                    if(rand==1){
                        share1.setRGB(i*2, j, Color.BLACK.getRGB());
                        share1.setRGB(i*2+1, j, Color.WHITE.getRGB());
                        share2.setRGB(i*2, j, Color.WHITE.getRGB());
                        share2.setRGB(i*2+1, j, Color.BLACK.getRGB());
                    }
                    else{
                        share1.setRGB(i*2, j, Color.WHITE.getRGB());
                        share1.setRGB(i*2+1, j, Color.BLACK.getRGB());
                        share2.setRGB(i*2, j, Color.BLACK.getRGB());
                        share2.setRGB(i*2+1, j, Color.WHITE.getRGB());
                    }
                }
                else{
                    if(rand==1){
                        share1.setRGB(i*2, j, Color.BLACK.getRGB());
                        share1.setRGB(i*2+1, j, Color.WHITE.getRGB());
                        share2.setRGB(i*2, j, Color.BLACK.getRGB());
                        share2.setRGB(i*2+1, j, Color.WHITE.getRGB());
                    }
                    else{
                        share1.setRGB(i*2, j, Color.WHITE.getRGB());
                        share1.setRGB(i*2+1, j, Color.BLACK.getRGB());
                        share2.setRGB(i*2, j, Color.WHITE.getRGB());
                        share2.setRGB(i*2+1, j, Color.BLACK.getRGB());
                    }
                }

            }
        }


    }
    @FXML
    protected void splitMethod2() throws IOException {
        File inputImage = new File(inputPath);
        BufferedImage imgBuffer = ImageIO.read(inputImage);
        share1 = new BufferedImage(imgBuffer.getWidth()*2,imgBuffer.getHeight()*2, BufferedImage.TYPE_BYTE_GRAY);
        share2 = new BufferedImage(imgBuffer.getWidth()*2,imgBuffer.getHeight()*2, BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i< imgBuffer.getWidth(); i++){
            for (int j = 0; j< imgBuffer.getHeight(); j++){
                int rand = getRandomNumberUsingInts(1,7);

                Color c = new Color(imgBuffer.getRGB(i,j),true);

                if(rbBinaryImage.isSelected()) c = blackOrWhite(c);

                if(c.equals(Color.BLACK)) {
                    if(rand==1) {
                        to4subpixels(share1, i,j, 1);
                        to4subpixels(share2,i,j,2);
                    }
                    else if(rand==2){
                        to4subpixels(share1,i,j,3);
                        to4subpixels(share2, i, j, 4);
                    }
                    else if(rand==3){
                        to4subpixels(share1,i,j,5);
                        to4subpixels(share2, i, j, 6);
                    }
                    else if(rand==4){
                        to4subpixels(share1,i,j,2);
                        to4subpixels(share2, i, j, 1);
                    }
                    else if(rand==5){
                        to4subpixels(share1,i,j,4);
                        to4subpixels(share2, i, j, 3);
                    }else {
                        to4subpixels(share1,i,j,6);
                        to4subpixels(share2, i, j, 5);
                    }
                }
                else {
                    if(rand==1) {
                        to4subpixels(share1, i,j, 1);
                        to4subpixels(share2,i,j,1);
                    }
                    else if(rand==2){
                        to4subpixels(share1,i,j,2);
                        to4subpixels(share2, i, j, 2);
                    }
                    else if(rand==3){
                        to4subpixels(share1,i,j,3);
                        to4subpixels(share2, i, j, 3);
                    }
                    else if(rand==4){
                        to4subpixels(share1,i,j,4);
                        to4subpixels(share2, i, j, 4);
                    }
                    else if(rand==5){
                        to4subpixels(share1,i,j,5);
                        to4subpixels(share2, i, j, 5);
                    }else if(rand==6){
                        to4subpixels(share1,i,j,6);
                        to4subpixels(share2, i, j, 6);
                    }
                }

            }
        }
    }


    @FXML
    protected boolean superimposeImage(BufferedImage uno, BufferedImage dos){
        if(uno.getHeight()!=dos.getHeight() || dos.getWidth()!=uno.getWidth()) return false;
        superImage = new BufferedImage(uno.getWidth(), uno.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0;i< uno.getWidth(); i++){
            for (int j = 0;j< uno.getHeight(); j++){
                Color a = new Color(uno.getRGB(i,j));
                Color b = new Color(dos.getRGB(i,j));

                if( a.equals(Color.BLACK) ) superImage.setRGB(i,j,Color.BLACK.getRGB());
                else superImage.setRGB(i,j,b.getRGB());

            }
        }
        return true;
    }
    @FXML
    protected boolean cleanSuperimposeImage1(BufferedImage uno, BufferedImage dos){
        if(uno.getHeight()!=dos.getHeight() || dos.getWidth()!=uno.getWidth()) return false;
        cleanSuperImage = new BufferedImage(uno.getWidth()/2, uno.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0;i< uno.getWidth(); i=i+2){
            for (int j = 0;j< uno.getHeight(); j++){
                Color ua = new Color(uno.getRGB(i,j));
                Color da = new Color(dos.getRGB(i,j));

                if( ua.equals(da)) cleanSuperImage.setRGB(i/2,j,Color.WHITE.getRGB());
                else cleanSuperImage.setRGB(i/2,j,Color.BLACK.getRGB());

            }
        }
        return true;
    }
    @FXML
    protected boolean cleanSuperimposeImage2(BufferedImage uno, BufferedImage dos){
        if(uno.getHeight()!=dos.getHeight() || dos.getWidth()!=uno.getWidth()) return false;
        cleanSuperImage = new BufferedImage(uno.getWidth()/2, uno.getHeight()/2, BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0;i< uno.getWidth(); i=i+2){
            for (int j = 0;j< uno.getHeight(); j=j+2){
                Color a = new Color(uno.getRGB(i, j));
                Color b = new Color(dos.getRGB(i, j));

                if( !a.equals(b)) cleanSuperImage.setRGB(i/2,j/2, Color.BLACK.getRGB());
                else {
                    cleanSuperImage.setRGB(i/2,j/2, Color.WHITE.getRGB());
                }

            }
        }
        return true;
    }


    //helper methods
    private void displayImage(String inputPath, String title) throws FileNotFoundException {
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        InputStream stream = new FileInputStream(inputPath);
        Image image = new Image(stream);

        ImageView imageView = new ImageView(); //Setting image to the image view
        imageView.setImage(image);

        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);

        Scene scene = new Scene(box,image.getWidth()+10, image.getHeight() +10);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();

    }

    protected void saveFile(BufferedImage buffer){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");

        String format = "jpg";
        if(rbPng.isSelected()) format = "png";
        else if (rbBmp.isSelected()) format = "bmp";

        File file = fileChooser.showSaveDialog(myStage);
        if (file != null) {
            try {
                ImageIO.write(buffer, format, file);
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private Color blackOrWhite(Color c){
        double sum = c.getRed()+c.getGreen()+c.getBlue();
        if(sum < (255+255+255)/2) return Color.BLACK;
        else return  Color.WHITE;
    }

    public int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    private void to4subpixels(BufferedImage buff, int i, int j, int option){
        if(option==1){
            buff.setRGB(i*2, j*2, Color.WHITE.getRGB());
            buff.setRGB(i*2+1, j*2, Color.WHITE.getRGB());
            buff.setRGB(i*2, j*2+1, Color.BLACK.getRGB());
            buff.setRGB(i*2+1, j*2+1, Color.BLACK.getRGB());
        }
        else if(option==2) {
            buff.setRGB(i * 2, j*2, Color.BLACK.getRGB());
            buff.setRGB(i * 2 + 1, j*2, Color.BLACK.getRGB());
            buff.setRGB(i * 2, j*2 + 1, Color.WHITE.getRGB());
            buff.setRGB(i * 2 + 1, j*2 + 1, Color.WHITE.getRGB());
        }
        else if(option==3) {
            buff.setRGB(i * 2, j*2, Color.BLACK.getRGB());
            buff.setRGB(i * 2 + 1, j*2, Color.WHITE.getRGB());
            buff.setRGB(i * 2, j*2 + 1, Color.BLACK.getRGB());
            buff.setRGB(i * 2 + 1, j*2 + 1, Color.WHITE.getRGB());

        }else if(option==4) {
            buff.setRGB(i * 2, j*2, Color.WHITE.getRGB());
            buff.setRGB(i * 2 + 1, j*2, Color.BLACK.getRGB());
            buff.setRGB(i * 2, j*2 + 1, Color.WHITE.getRGB());
            buff.setRGB(i * 2 + 1, j*2 + 1, Color.BLACK.getRGB());
        }
        else if(option==5) {
            buff.setRGB(i * 2, j*2, Color.WHITE.getRGB());
            buff.setRGB(i * 2 + 1, j*2, Color.BLACK.getRGB());
            buff.setRGB(i * 2, j*2 + 1, Color.BLACK.getRGB());
            buff.setRGB(i * 2 + 1, j*2 + 1, Color.WHITE.getRGB());
        }
        else if(option==6) {
            buff.setRGB(i * 2, j*2, Color.BLACK.getRGB());
            buff.setRGB(i * 2 + 1, j*2, Color.WHITE.getRGB());
            buff.setRGB(i * 2, j*2 + 1, Color.WHITE.getRGB());
            buff.setRGB(i * 2 + 1, j*2 + 1, Color.BLACK.getRGB());
        }

    }


}