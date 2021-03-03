/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diamanty;


import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import java.util.*;
import javafx.concurrent.*;
import javafx.geometry.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

/**
 *
 * @author adam
 */

/**Trieda ktora vytovri nove okno kde zadavame vlastnosti hry
* pocet stlpcov a riadkov a farby kamenov */
class NewGameDialog {
    
    private Stage stage;    //Dialogove okno
    private int numCol = 0; 
    private int numRow = 0;
    private TextField txtCol;
    private TextField txtRow;
    private boolean succes; //uchovava ci je vsetko spravne zadane
    private Color [] pickerColors;
    private Label topLabel;
    
    /**Vrati pole farieb vybratych z colorPickerov */
    protected Color[] getPickerColors () {
        return pickerColors;
    }
    
    /** Vrati pocet stlpcov*/
    protected int getColGet() {
        return numCol;
    }
    
    /** Vrati pocet riadkov*/
    protected int getnumRow() {
        return numRow;
    }
    
    /** Vrati hodnotu succes*/
    protected boolean getSucces() {
        return succes;
    }
    
    /** Konstruktor inicializuje dialogove*/
    public NewGameDialog() {
        
        succes = false;
        
        BorderPane border = new BorderPane();
        
        GridPane grid = new GridPane();
        border.setCenter(grid);
        
        grid.setPadding(new Insets(10, 15, 10, 15));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        
        Label lbCol = new Label("Pocet stlpcov");
        Label lbRow = new Label("Pocet riadkov");
        lbCol.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        lbRow.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        grid.add(lbCol, 0, 0);
        grid.add(lbRow, 0, 1);
        
        txtCol = new TextField();
        txtRow = new TextField();
        txtCol.setMaxWidth(150);
        txtRow.setMaxWidth(150);
        txtCol.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        txtRow.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        grid.add(txtCol, 1, 0);
        grid.add(txtRow, 1, 1);
        
        //bottom obsahuje iba tlacitka ok a cancel
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(10, 15, 10, 15));
        bottom.setSpacing(20);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        border.setBottom(bottom);
        
        Button btnOk = new Button("Hraj");
        Button btnCancel = new Button("Cancel");
        btnOk.setMaxWidth(Double.MAX_VALUE);
        btnOk.setPrefWidth(100);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setPrefWidth(100);
        bottom.getChildren().addAll(btnOk, btnCancel);
        
        btnOk.setOnAction((ActionEvent event) -> {
           okAction(); 
        });
        
        btnCancel.setOnAction((ActionEvent event) -> {
            cancelAction();
        });
        
        //CenterTop obsahuje text co vykonat
        VBox topBox = new VBox();
        border.setTop(topBox);
        
        topLabel = new Label("Vyberte 4 rozne farby okrem ciernej \n"
                + "a pocet stlpcov a riadkov >= 4!");
        topLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        topBox.setPadding(new Insets(10, 15, 10, 15));
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().add(topLabel);
             
        //left obsahuje colorpickery
        VBox box = new VBox();
        border.setLeft(box);
        box.setPadding(new Insets(10, 15, 10, 15));
        box.setSpacing(15);
        
        ColorPicker colorPicker1 = new ColorPicker();
        ColorPicker colorPicker2 = new ColorPicker();
        ColorPicker colorPicker3 = new ColorPicker();
        ColorPicker colorPicker4 = new ColorPicker();
        box.getChildren().addAll(colorPicker1, colorPicker2, colorPicker3, colorPicker4);
        
        pickerColors = new Color[4];
        
        //ak by nahodou niekto nechal vybratu bielu
        pickerColors[0] = colorPicker1.getValue();
        pickerColors[1] = colorPicker2.getValue();
        pickerColors[2] = colorPicker3.getValue();
        pickerColors[3] = colorPicker4.getValue(); 
        
        //priradi polu pickerColor farbu s pickera
        colorPicker1.setOnAction((ActionEvent event) -> {
            pickerColors[0] = colorPicker1.getValue();
        });
        
        colorPicker2.setOnAction((ActionEvent event) -> { 
            pickerColors[1] = colorPicker2.getValue();
        });
        
        colorPicker3.setOnAction((ActionEvent event) -> { 
            pickerColors[2] = colorPicker3.getValue();
        });
        
        colorPicker4.setOnAction((ActionEvent event) -> { 
            pickerColors[3] = colorPicker4.getValue();
        });
        
        Scene scene = new Scene(border, 500, 300);
        
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Nova hra");
        stage.showAndWait();
        
 
        
    }
    
    /** metoda ktora nastavi text s instrukciami na nahodnu farbu a 
     * a s upozornenim ze su zle zadane vlastnosti*/
    private void warningLabel() {
        topLabel.setText(" Chyba skus znova! \n4 rozne farby okrem ciernej "
                + "a hodnoty >= 4");
        topLabel.setTextFill(Color.color(Math.random(), Math.random(), Math.random()));
    }
    
   
    /** metoda vykonava sa po staceni okbuttonu
     * nastavi pocet stlpcov a riadkov ak su splnene vsetky podmienky*/
    private void okAction() {
        try {
            numCol = Integer.parseInt(txtCol.getText());
            numRow = Integer.parseInt(txtRow.getText());
            
            //kontroluje ci je pocet riadkov a stlpcov vacsi ako 4
            if (numCol >= 4 && numRow >= 4) {
                //kontroluje ci su farby rozne
                if (pickerColors[0] != pickerColors[1] && pickerColors[0] != pickerColors[2]
                        && pickerColors[0] != pickerColors[3]
                        && pickerColors[1] != pickerColors[2] && pickerColors[1] != pickerColors[3]
                        && pickerColors[2] != pickerColors[3]) {
                    
                    //nesmiete vybrat ciernu lebo cierne je pozadie
                    //tato podmienka nefunguje (nahradil som to stringom)
                    //if (pickerColors[0] != Color.BLACK && pickerColors[1] != Color.BLACK
                    //        && pickerColors[2] != Color.BLACK && pickerColors[3] != Color.BLACK) {
                    if (!pickerColors[0].toString().equals("0x000000ff") 
                            && !pickerColors[1].toString().equals("0x000000ff")
                            && !pickerColors[2].toString().equals("0x000000ff")
                            && !pickerColors[3].toString().equals("0x000000ff")) {
                        succes = true;
                        stage.close();
                        
                    } else {
                        //vypis chybovej hlasky
                        warningLabel();
                    }
                } else {
                    //vypis chybovej hlasky
                    warningLabel();
                }
                
            } else {
                //vypis chybovej hlasky
                warningLabel();
            }
        } catch (Throwable e) {
            //vypis warning !!!
            warningLabel();
        }
    }
    
    /** metoda zrusi dialogove okno*/
    private void cancelAction() {
        stage.close();
    }
    
}

/** Hlavna trieda obsahuje hraciu plochu naplnenu kamenmi ktore dva vedla seba 
 * mozeme vymenit ak dostaneme po vymene zhodu 3 kamenov nasledne tie so zhodou zmiznu
 * a ostatne kamene sa posunu nizsie*/
public class Diamanty extends Application {
    
    private Stage primaryStage;
    private NewGameDialog newGameDialog;
    /**pocet riadkov hracej plochy */
    private int row; 
    /**pocet stlpcov hracej plochy */
    private int col;        
    private Menu mOptions;
    private MenuItem mNewGame;
    private MenuItem mBackMove;
    private boolean firstGame = true;
    /** pole farieb ake mozu byt kamene */
    private Color[] colors;   
    /**2D pole diamantov */
    private Stone[][] diamonds;  
    private Color[][] copyDiamonds;
    /** predstavuje jeden objekt "hraci kamen"*/
    private Stone diamondShape; 
    /** predstavuje kamen na ktory je kliknute*/
    private Stone selected;
    private Color usedColor;
    private boolean canTouch = true;
    

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        
        selected = null;
        usedColor = null;
        //ak este nebola zapnuta hra zavolam dialogove okno
        if (firstGame) {
            newGameDialog = new NewGameDialog(); 
            firstGame = false;
        }
        
        //ak vsetko prebehlo uspesne tak sa nacita plocha
        if (newGameDialog.getSucces()) {
            col = newGameDialog.getColGet();
            row = newGameDialog.getnumRow();
            colors = newGameDialog.getPickerColors().clone();
            
            BorderPane border = new BorderPane();
            
            
            //hracia plocha nastavena na cierno
            Pane canvas = new Pane();
            canvas.setStyle("-fx-background-color: black;");
            border.setCenter(canvas);
            
            diamonds = new Stone[row][col];
            //naplni plochu diamantmy
            fillTheBoard(canvas);
            copyDiamonds = new Color[row][col];
            
                     
            Scene scene = new Scene(border, col * 55 + 20, row * 55 + 50);
            
            primaryStage.setTitle("Diamanty");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show(); 
            
            primaryStage.setOnCloseRequest((WindowEvent e)-> { 
                stageClose(e);
            });
            
            
            //Lista s Menu na vrchu
            MenuBar menuBar = new MenuBar();
            border.setTop(menuBar);
                        
            mOptions = new Menu("Moznosti");
            mNewGame = new MenuItem("Nova hra");
            mNewGame.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN)); //Ctr + N
            mOptions.getItems().add(mNewGame);
            
            menuBar.getMenus().add(mOptions);
            
            mBackMove = new MenuItem("Krok dozadu");
            mOptions.getItems().add(mBackMove);
            
            //po kliknuty zavola dialogove okno novej hry
            mNewGame.setOnAction((ActionEvent e) -> { 
                newGame();
            });
            
            //po kliknuti vrati hraciu plochu o krok spat
            mBackMove.setOnAction((ActionEvent e) -> {
                backMove();
            });
        } 
          
    }
    //**metoda ktora vytvori samostatny proces ktory sa spusti po pauze pol sekundy*/
    private void sleeperFunc1() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                return null;
            }
        }; 
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                    //ak nie je zhoda povoli znova klikat
                    if(!controlMatched()) {
                        canTouch = true;
                    }
                    
            }
        });
        new Thread(sleeper).start();
    }
    
    /** metoda ktora vytvori samostatny proces po pol sekundovej pauze*/
    private void sleeperFunc2() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                return null;
            }
        }; 
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        //ak vymenim prazne policko s polickom aby sa mi vsetky posunuli dole
                        if(!diamonds[i][j].getMatch() &&
                                diamonds[i][j].getColor() == Color.BLACK && i > 0) {
                            if (diamonds[i - 1][j].getColor() != Color.BLACK) {
                                moveBlack(diamonds[i][j]);
                            }
                        }
                        if (diamonds[i][j].getMatch()) {
                            //System.out.println(diamonds[i][j].getColor());
                            diamonds[i][j].setMatch(false);
                            moveBlack(diamonds[i][j]);
                        }
                    }
                }
                //skontroluje ci existuje dalsi tah ak nie zavola koniec hry
                if (!controlNextMove()) {
                        finishAlert();
                    }
                sleeperFunc1();
            }
        });
        new Thread(sleeper).start();
    }
    
    /** metoda ktora zavola dialogove okno s oznamom ze skoncila hra a s poctom
     * kamenov ktore zostali na ploche a ktore sa uz nedaju vymenit(ak ich je 0
     * oznami ze ste vyhrali) a s moznostou
     zacat novu hru*/ 
    private void finishAlert() {
        int pocet = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (diamonds[i][j].getColor() != Color.BLACK) {
                    pocet += 1;
                }
            }
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (pocet == 0) {
            alert.setContentText("Vyhrali ste!  Chcete zacat novu hru?");
        } else {
            alert.setContentText("Koniec hry zostalo vam " + pocet + " kamenov. \n"
                + "Chcete zacat novu hru?");
        }
        alert.setTitle("Koniec hry");
        alert.setHeaderText(null);
        alert.getButtonTypes().clear();
        
        ButtonType buttonTypeYes = new ButtonType("Ano");
        ButtonType buttonTypeNo = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
       
        alert.getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            newGame();
        }
    }
    
    /** metoda ktora pri zatvarani plochy sa spytaci ci chce zatvorit
     * ak nie skonzumuje request */
    private void stageClose(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Naozaj ukonicit?");
        alert.setTitle("Naozaj ukoncit?");
        alert.setHeaderText(null);
        alert.getButtonTypes().clear();
        
        ButtonType buttonTypeYes = new ButtonType("Ano");
        ButtonType buttonTypeNo = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeNo) {
            e.consume();
        }
    };
    
    /**Metoda ktora zavola dialogove okno novej hry */
    private void newGame() {
        newGameDialog = new NewGameDialog();
        start(primaryStage);
    }
    
    /** Metoda ktora vrati stav matice pred ykonanim tahu*/
    private void backMove() {
        if (copyDiamonds[0][0] != null) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    diamonds[i][j].setDiamondColor(copyDiamonds[i][j]);

                }
            }
        }
    }
    
    /** metoda ktora skopiruje hraciu plochu aby mohol bt vykonany spetny tah*/
    private void copyDiamondsColor() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                copyDiamonds[i][j] = diamonds[i][j].getColor();
            }
        }
    }
    
    /**classa ktora reprezentuje kamen urcitej farby a uchovava poziciu
     * a ci sa zhoduje s dvoma dalsimi susednymi kamenmi */
    class Stone {
        private int j;
        private int i;
        private Shape diamond;
        private Color color;
        private boolean match; //ak sa zhoduje s kamenmi vedla seba tak am true
        
        /** nastavi zhodu kamenu*/
        public void setMatch(boolean match) {
            this.match = match;
        }
        
        /** vrati zhodu kamena*/
        public boolean getMatch() {
            return match;
        }
        
        /** nastavi farbu kamena podla
         * @param farby*/
        public void setDiamondColor(Color color) {
            diamond.setFill(color);
            this.color = color;
        }
        
        /** vrati farbu kamena*/
        public Color getColor() {
            return color;
        }
        
        /** vrati 6uholnikovu reprezentaciu kamena*/
        public Shape getDiamond() {
            return diamond;
        }
        
        /** vrati riadok v ktorom sa nachadza*/
        public int getI() {
            return i;
        }
        
        /** vrati riadok v ktorom sa nachadza*/
        public int getJ() {
            return j;
        }
        
        /** metoda vytvori kamen v tvare 6uholnika a priradi mu nahodnu farbu z pola 
         * colors a priradi mu onMouseClickAction */
        public Stone(int j ,int i) {
            match = false;
            this.j = j;
            this.i = i;
            diamond = new Polygon(
                    22.5 + j * 55, 10.0 + i * 55,    
                    47.5 + j * 55, 10.0 + i * 55,
                    60.0 + j * 55, 35.0 + i * 55,
                    47.5 + j * 55, 60.0 + i * 55,
                    22.5 + j * 55, 60.0 + i * 55,
                    10.0 + j * 55, 35.0 + i * 55
            );
            color = colors[new Random().nextInt(colors.length)];
            diamond.setFill(color);
            
            //akcia po kliknuti na kamen
            diamond.setOnMouseClicked((MouseEvent e) ->{
                //ak sa nedeje proces alebo tah mozem klikat
                if (canTouch) {
                    //ak nie je ziaden oznaceny oznacim a priradim mu tmavsiu farbu aby bol viditelny
                    if (selected == null) {
                        //v pripade ze som klikol na prazdne policko neoznacim ho ako prvy klik
                        if (this.getColor() != Color.BLACK) {
                            selected = this;

                            //skopirujem farby aktualneho stavu pred tahom
                            copyDiamondsColor();

                            usedColor = selected.getColor();
                            selected.setDiamondColor(selected.getColor().darker());

                        }
                        
                        //ak uz mame jeden oznaceny oznacime dalsi 
                    } else {
                        //ak sme klikli na ten isty(prvy) tak zrusime oznacenie prveho
                        if (this == selected) {
                            selected.setDiamondColor(usedColor);
                            selected = null;
                            //ak to nie je ten isty tak vykonam akciu
                        } else {
                             //skontroluje ci nie je tej istej farby
                            if (usedColor != this.getColor()) {
                                //skontroluje ci je zhoda po vymene ak hej vymeni a vymaze kamene
                                if (controlAndSwap(selected, this)) {
                                    
                                    canTouch = false;
                                    sleeperFunc1();

                                    selected = null;
                                }
                            }
                        }

                    } 
                }
            });
            
        }    
    }
    
    
    
    
    /** Metoda ktora naplni plochu polynomami */
    private void fillTheBoard(Pane canvas) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                while (true) {
                    diamondShape = new Stone(j,i);
                    //ak je vsetko v poriadku prida ho inak opakuje
                    if (controlColor(diamondShape, i, j)) {
                        diamonds[i][j] = diamondShape;
                        canvas.getChildren().add(diamondShape.getDiamond());
                        break;
                    } 
                }
            }
                
        }
    }
    
    /** Metoda ktora kontroluje ci vygenerovany kamen nie je treti v poradi rovnakej farby */
    private boolean controlColor(Stone diamondShape, int row, int col) {
        if (col > 1) {
           if(diamonds[row][col - 1].getColor() == diamondShape.getColor() &&
                    diamonds[row][col - 2].getColor() == diamondShape.getColor()) {
               return false;
           }
        }
        if (row > 1) {
           if(diamonds[row - 1][col].getColor() == diamondShape.getColor() &&
                    diamonds[row - 2][col].getColor() == diamondShape.getColor()) {
               return false;
           }
        }
        
        return true;
    }
    
    /** metoda ktora skontroluje ci sa da vymenit dane kamene ak hej tak ich vymeni 
     a vrati true */
    private boolean controlAndSwap(Stone selected, Stone second) {
        if (((second.getI() == selected.getI() + 1 ||second.getI() == selected.getI() - 1) 
                && second.getJ() == selected.getJ()) 
                || ((second.getJ() == selected.getJ() + 1 || second.getJ() == 
                selected.getJ() - 1) 
                && selected.getI() == second.getI())) {
            
            //Color tmpColor = selected.getColor();
            selected.setDiamondColor(second.getColor());
            second.setDiamondColor(usedColor);
            
            //kontrola zhody ak k nej nedoslo vratit
            if ( controlZhoda()) {
                return true;
            } else {
             second.setDiamondColor(selected.getColor());
             selected.setDiamondColor(usedColor.darker());
             return false;
            }
        }
        return false;
    }
    
    /**metoda ktora nastavi zhodu kamenom ktore sa zhoduju s 2 a viac kamenmi vedla seba
     a vrati true ak sa taka zhoda achadza*/
    private boolean controlM() {
        boolean match = false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i > 0 && i < row - 1) {
                    if (diamonds[i][j].getColor() != Color.BLACK) {
                        if (diamonds[i - 1][j].getColor() == diamonds[i][j].getColor()
                                && diamonds[i + 1][j].getColor() == diamonds[i][j].getColor()) {
                            diamonds[i][j].setMatch(true);
                            diamonds[i - 1][j].setMatch(true);
                            diamonds[i + 1][j].setMatch(true);
                            match = true;
                        }
                    }
                }
                
                if (j > 0 && j < col - 1) {
                    if ( diamonds[i][j].getColor() != Color.BLACK) {
                        if (diamonds[i][j - 1].getColor() == diamonds[i][j].getColor()
                                && diamonds[i][j + 1].getColor() == diamonds[i][j].getColor()) {
                            diamonds[i][j].setMatch(true);
                            diamonds[i][j - 1].setMatch(true);
                            diamonds[i][j + 1].setMatch(true);
                            match = true;
                        }
                    }   
                }
            }
        }
        return match;
        
    }
    
    /** ak existuje zhoda kamenov ostrani tie ktore maju zhodu a ostatne posunie dole*/
    private boolean controlMatched() {
        boolean match = controlM();
        
        if (match) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (diamonds[i][j].getMatch()) {
                        diamonds[i][j].setDiamondColor(Color.BLACK);
                    }
                }
            }
            sleeperFunc2();
        }   
        return match;   
    }
    
    /** metoda ktora skontroluje ci exituje dalsi tah pri ktorom vznikne zhoda 
     * podla toho vrati true alebo false*/
    private boolean controlNextMove() {
        for (int i = row - 1 ; i >= 0; i--) {
            for (int j = col - 1; j > 0; j--) {
                Color tmp = diamonds[i][j].getColor();
                diamonds[i][j].setDiamondColor(diamonds[i][j - 1].getColor());
                diamonds[i][j - 1].setDiamondColor(tmp);
                 if (controlZhoda()) {
                     diamonds[i][j - 1].setDiamondColor(diamonds[i][j].getColor());
                     diamonds[i][j].setDiamondColor(tmp);
                     return true;
                 }
                 diamonds[i][j - 1].setDiamondColor(diamonds[i][j].getColor());
                 diamonds[i][j].setDiamondColor(tmp);
            }
        }
        
        for (int i = col - 1; i >= 0; i--) {
            for (int j = row - 1; j > 0; j--) {
                Color tmp = diamonds[j][i].getColor();
                diamonds[j][i].setDiamondColor(diamonds[j - 1][i].getColor());
                diamonds[j - 1][i].setDiamondColor(tmp);
                 if (controlZhoda()) {
                     diamonds[j - 1][i].setDiamondColor(diamonds[j][i].getColor());
                     diamonds[j][i].setDiamondColor(tmp);
                     return true;
                 }
                 diamonds[j - 1][i].setDiamondColor(diamonds[j][i].getColor());
                 diamonds[j][i].setDiamondColor(tmp);
            }
        }
        return false;
    }
    
    /** metoda ktora skotroluje ci su 3 a viac kemne rovnakej farby vedla seba ak najde
     aspon jednu zhodu vrati true a nekontroluje zvysok plochy*/
    private boolean controlZhoda() {
        for (int i = row - 1 ; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                if (diamonds[i][j].getColor() != Color.BLACK) {
                    if (j < col - 1 && j > 0) {
                        if (diamonds[i][j - 1].getColor() == diamonds[i][j].getColor()
                                && diamonds[i][j + 1].getColor() == diamonds[i][j].getColor()) {
                            return true;
                        }
                        
                    }
                    if (i > 0 && i < row - 1) {
                        if (diamonds[i - 1][j].getColor() == diamonds[i][j].getColor()
                                && diamonds[i + 1][j].getColor() == diamonds[i][j].getColor()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    
    /** metoda ktora na plazdne policka posunie kamene nad prazndym polickom
     * aby nevznikla medzera */
    private void moveBlack(Stone blackStone) {
        int blackRow = blackStone.getI();
        int blackColumn = blackStone.getJ();
        
        for (int i = blackRow; i > 0; i--) {
            diamonds[i][blackColumn].setDiamondColor(diamonds[i - 1][blackColumn].getColor());
        }
        diamonds[0][blackColumn].setDiamondColor(Color.BLACK);
    } 
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
}
