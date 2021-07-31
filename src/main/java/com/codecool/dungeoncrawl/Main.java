package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Alien;
import com.codecool.dungeoncrawl.logic.actors.Guard;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

import java.io.FileNotFoundException;

public class Main extends Application {
    GameMap mapLevel1 = MapLoader.loadMap(1);
    GameMap mapLevel2 = MapLoader.loadMap(2);
    GameMap map = MapLoader.loadMap(0);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label inventoryLabel = new Label();
    Label nameLabel = new Label();
    Button pickUpBtn;
    Button nextLevelBtn;


    Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    public void gameSettings(Stage primaryStage) throws FileNotFoundException {
        Button startButton = new Button("Start the Game");
        Button backButton = new Button("Back to Menu");

        startButton.setId("allbtn");
        backButton.setId("allbtn");


        HBox buttons = new HBox(startButton, backButton);
        buttons.setSpacing(25);
        Text nameLabel = new Text("Enter Your Name");
        nameLabel.setId("text");

        TextField textField = new TextField();
        textField.setId("input");
        textField.setPrefWidth(100);

        VBox settings = new VBox(nameLabel, textField, buttons);
        settings.setAlignment(Pos.CENTER);

        startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                map = mapLevel1;
                map.getPlayer().setName(textField.getText());
                gameStart(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                mainMenu(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        BorderPane menu = new BorderPane();

        menu.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setPrefWidth(1024);
        menu.setPrefHeight(600);
        menu.setCenter(settings);

        buttons.setAlignment(Pos.CENTER);
        settings.setSpacing(25);
        Scene scene = new Scene(menu);

        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    public void mainMenu(Stage primaryStage) throws FileNotFoundException, RuntimeException {
        Button startGameButton = new Button("New Adventure");
        Button loadButton = new Button("Load Game");
        Button exitGameButton = new Button("Exit Game");

        startGameButton.setId("allbtn");
        loadButton.setId("allbtn");
        exitGameButton.setId("allbtn");

        startGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                gameSettings(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        exitGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.exit(0);
        });

        VBox buttons = new VBox(startGameButton, loadButton, exitGameButton);

        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        BorderPane menuLayout = new BorderPane();

        menuLayout.setCenter(buttons);
        menuLayout.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
        menuLayout.setPrefWidth(1024);
        menuLayout.setPrefHeight(600);

        Scene scene = new Scene(menuLayout);
        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        mainMenu(primaryStage);
    }



    public void gameStart(Stage primaryStage) throws Exception {
////        context.scale(2,2);
////        //scale positioning
////        context.translate(-200, -160);
//        // positioning the corner tile to the middle
//        context.translate(Tiles.TILE_WIDTH * ((double) map.getWidth() / 2), -Tiles.TILE_WIDTH * ((double) map.getHeight() / 2));
//        context.translate((double) -Tiles.TILE_WIDTH/2, (double)-Tiles.TILE_WIDTH/2);
//        // positioning the player to the middle
//        context.translate(
//                -Tiles.TILE_WIDTH * (map.getPlayer().getCell().getX()),
//                Tiles.TILE_WIDTH * (map.getHeight() - map.getPlayer().getCell().getY()));

        pickUpBtn = new Button("Pick up item");
        nextLevelBtn = new Button("Next level");

        canvas.setFocusTraversable(false);
        pickUpBtn.setFocusTraversable(false);
        nextLevelBtn.setFocusTraversable(false);
        pickUpBtn.setId("pick-up-btn");
        inventoryLabel.setId("inventory-label");
        nameLabel.setId("name-label");
        nameLabel.setText("" + (map.getPlayer().getName()));
        nextLevelBtn.setId("next-level-btn");


        GridPane ui = new GridPane();


        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.setStyle("-fx-background-color: rgba(28, 28, 28, 0.95); -fx-background-radius: 10;");


        ui.add(nameLabel, 0, 0);
        ui.add(new Label((map.getPlayer().getName()) + "@Health:~$ "), 0, 3);
        ui.add(healthLabel, 0, 4);
        ui.add(new Label((map.getPlayer().getName()) + "@Strength:~$ "), 0, 5);
        ui.add(strengthLabel, 0, 6);
        ui.add(new Label((map.getPlayer().getName()) + "@Inventory:~$ "), 0, 7);
        ui.add(inventoryLabel, 0, 8);
        ui.add(pickUpBtn, 0, 20);
        ui.add(nextLevelBtn, 0, 20);
        hidePickUpBtn();
        hideNextLevelBtn();

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);

        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        pickUpBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            pickUp();
        });

        nextLevelBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            //TODO next level;
            Cell playerCell = mapLevel2.getPlayer().getCell();
            mapLevel1.getPlayer().setCell(playerCell);
            mapLevel2.setPlayer(mapLevel1.getPlayer());
            map = mapLevel2;

            hideNextLevelBtn();
        });

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void hidePickUpBtn() {
        pickUpBtn.setVisible(false);
    }

    private void showPickUpBtn() {
        pickUpBtn.setVisible(true);
    }

    private void hideNextLevelBtn() {
        nextLevelBtn.setVisible(false);
    }

    private void showNextLevelBtn() {
        nextLevelBtn.setVisible(true);
    }

    private void pickUp() {
        map.getPlayer().pickUpItem(map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getItem());
        hidePickUpBtn();
        refreshLabel();
    }


    private void onKeyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {
            case ESCAPE:
                System.exit(0);
            case UP:
            case W:
                step(0, -1);
                refresh();
                break;
            case DOWN:
            case S:
                step(0, 1);
                refresh();
                break;
            case LEFT:
            case A:
                step(-1, 0);
                refresh();
                break;
            case RIGHT:
            case D:
                step(1, 0);
                refresh();
                break;
        }
    }

    private void step(int x, int y) {
        map.getPlayer().fight(x, y);
//        if (map.getPlayer().moveable(x, y)) {
//            context.translate(-Tiles.TILE_WIDTH * x, -Tiles.TILE_WIDTH * y);
//        }
        hidePickUpBtn();
        hideNextLevelBtn();
        map.getPlayer().move(x, y);
        if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).isItem()) {
            showPickUpBtn();
        }
        if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).isDoor()) {
            showNextLevelBtn();
        }
        enemyMove();
    }

    private void enemyMove(String direction, Cell cell) {
        switch (direction) {
            case "UP":
                map.getCell(cell.getX(), cell.getY()).getActor().move(0, -1);
                break;
            case "DOWN":
                map.getCell(cell.getX(), cell.getY()).getActor().move(0, 1);
                break;
            case "LEFT":
                map.getCell(cell.getX(), cell.getY()).getActor().move(-1, 0);
                break;
            case "RIGHT":
                map.getCell(cell.getX(), cell.getY()).getActor().move(1, 0);
                break;
        }
    }

    private void enemyMove() {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random random = new Random();

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() instanceof Guard || cell.getActor() instanceof Alien) {
                    String direction = directions[random.nextInt(4)];
                    enemyMove(direction, cell);
                }
            }
        }
    }

    public void gameOver(Stage primaryStage) throws FileNotFoundException, RuntimeException{

        Button backToMenu = new Button("Back to Menu");
        Button exitGameButton = new Button("Exit Game");

        backToMenu.setId("go");
        exitGameButton.setId("go-exit");

        backToMenu.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                map.getPlayer().setHealth(10);
                mainMenu(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        exitGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.exit(0);
        });


        HBox buttons = new HBox(backToMenu, exitGameButton);

        VBox menu = new VBox(buttons);


        BorderPane menuLayout = new BorderPane();
        menuLayout.setPrefWidth(1024);
        menuLayout.setPrefHeight(600);
        menuLayout.setCenter(menu);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(350,5,15,5));

        buttons.setSpacing(25);



        Scene scene = new Scene(menuLayout);
        scene.getStylesheets().add("gameover.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();

    }

    private void refresh() {

        if(map.getPlayer().getHealth() <=0 ) {
            try {
                gameOver(stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

//        for (int x = -30; x < map.getWidth()+100; x++) {
//            for (int y = -30; y < map.getHeight()+100; y++) {
////                Cell cell = map.getCell(0,0);
////                Tiles.drawTile(context, cell, x, y);
//                Tiles.clearTile(context, x, y);
//            }
//        }
        ColorAdjust mediumTile = new ColorAdjust();
        mediumTile.setBrightness(0.5);
        System.out.println((double)map.getWidth()/2);
        System.out.println((double)map.getHeight()/2);

        for (int x = map.getPlayer().getX() - 100; x < map.getPlayer().getX() + 100; x++) {
            for (int y = map.getPlayer().getY() - 100; y < map.getPlayer().getY() + 100; y++) {
                Cell cell;
                try {
                    cell = map.getCell((x + map.getPlayer().getX()) - map.getWidth()/2, y + map.getPlayer().getY() - map.getHeight()/2);
                } catch (IndexOutOfBoundsException e) {
                    cell = new Cell(map, 1, 1, CellType.EMPTY);
                }
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);

                } else {
//                    if (cell.getX() == map.getPlayer().getX() - 1 && cell.getY() == map.getPlayer().getY()) Tiles.drawTileMedium(context, cell, x, y);

                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }

        refreshLabel();
    }

    private void refreshLabel() {
        healthLabel.setText("" + map.getPlayer().getHealth() + "/" + map.getPlayer().getMaxHealth());
        strengthLabel.setText("" + map.getPlayer().getStrength());
        inventoryLabel.setText("{" + map.getPlayer().itemInInventory() + "}");
    }
}
