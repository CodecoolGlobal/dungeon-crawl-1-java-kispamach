package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

import java.io.FileNotFoundException;

public class Main extends Application {
    public int x, y = 0;
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label inventoryLabel = new Label();
    boolean onItem = false;
    private Button pick = new Button("Pick up item.");
    Stage stage;



    public static void main(String[] args) {
        launch(args);
    }

    public void gameSettings(Stage primaryStage) throws FileNotFoundException {
        Button startButton = new Button("Start the Game");
        Button backButton = new Button("Back to Menu");

        startButton.setId("allbtn");
        backButton.setId("allbtn");


        HBox buttons = new HBox(startButton,backButton);
        Text nameLabel = new Text("Enter Your Name");
        nameLabel.setId("text");

        TextField textField = new TextField();
        textField.setId("input");

        VBox settings = new VBox(nameLabel, textField, buttons);
        settings.setAlignment(Pos.CENTER);

        startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
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
        menu.setPrefWidth(500);
        menu.setPrefHeight(500);
        menu.setCenter(settings);

        buttons.setAlignment(Pos.CENTER);
        settings.setSpacing(25);
        Scene scene = new Scene(menu);

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

        VBox buttons = new VBox(startGameButton,loadButton, exitGameButton);

        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        BorderPane menuLayout = new BorderPane();

        menuLayout.setCenter(buttons);
        menuLayout.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
        menuLayout.setPrefWidth(500);
        menuLayout.setPrefHeight(500);

        Scene scene = new Scene(menuLayout);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        mainMenu(primaryStage);
    }

    public void gameStart(Stage primaryStage) throws Exception{
        canvas.setFocusTraversable(false);
        pick.setFocusTraversable(false);
        GridPane ui = new GridPane();

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Name: "), 0, 0);
        ui.add(new Label(map.getPlayer().getName()), 1, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);
        ui.add(new Label("Strength: "), 0, 2);
        ui.add(strengthLabel, 1, 2);
        ui.add(new Label("Inventory:"),0,3);
        ui.add(inventoryLabel,1,3);
        ui.add(pick, 0, 20);
        hide();

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        pick.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            refresh();
        });

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void hide() {
        pick.setVisible(false);
    }

    private void show() { pick.setVisible(true); }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                hide();
                map.getPlayer().move(0, -1);
                if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).isItem()) {
                    show();
                    pick.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
                        map.getPlayer().pickUpItem(map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getItem());
                        hide();
                    });
                }
                refresh();
                break;
            case DOWN:
                hide();
                map.getPlayer().move(0, 1);
                if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).isItem()) {
                    show();
                    pick.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
                        map.getPlayer().pickUpItem(map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getItem());
                        hide();
                    });
                }
                refresh();
                break;
            case LEFT:
                hide();
                map.getPlayer().move(-1, 0);
                if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).isItem()) {
                    show();
                    pick.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
                        map.getPlayer().pickUpItem(map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getItem());
                        hide();
                    });
                }
                refresh();
                break;
            case RIGHT:
                hide();
                map.getPlayer().move(1,0);
                if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).isItem()) {
                    show();
                    pick.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
                        map.getPlayer().pickUpItem(map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getItem());
                        hide();
                    });
                }
                refresh();
                break;
        }
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
                map.getCell(cell.getX(), cell.getY()).getActor().move(1,0);
                break;
        }
    }

    private void refresh() {

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random random = new Random();

        // enemy move
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() instanceof Guard || cell.getActor() instanceof Alien) {
                    String direction = directions[random.nextInt(4)];
                    enemyMove(direction, cell);
                }
            }
        }

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);

                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth() + "/" + map.getPlayer().getMaxHealth());
        strengthLabel.setText("" + map.getPlayer().getStrength());
        inventoryLabel.setText("" + map.getPlayer().itemInInventory());
    }
}
