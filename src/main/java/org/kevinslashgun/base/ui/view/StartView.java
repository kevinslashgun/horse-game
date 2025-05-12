package org.kevinslashgun.base.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
@CssImport("./styles/start-view.css")
public class StartView extends VerticalLayout {

    private final ComboBox<Integer> sizeSelector = new ComboBox<>("📏 Dimensione della scacchiera");

    public StartView() {
        configureLayout();
        addTitle();
        addRules();
        configureSizeSelector();
        addActionButtons();
    }

    private void configureLayout() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        addClassName("start-view");
        setSizeFull();
    }

    private void addTitle() {
        H1 title = new H1("🐴 Horse Game");
        title.addClassName("title");
        add(title);
    }

    private void addRules() {
        Div rulesBox = new Div();
        rulesBox.addClassName("rules-box");

        Paragraph gameOverParagraph = new Paragraph("❌ GAME OVER!");
        gameOverParagraph.addClassName("game-over");

        rulesBox.add(
                new H3("⚔️ REGOLAMENTO DEL GIOCO ⚔️"),
                new Paragraph("- Clicca su una casella per posizionare il cavallo."),
                new Paragraph("- Il cavallo si muove come negli scacchi."),
                new Paragraph("- Visita tutte le caselle una sola volta."),
                new Paragraph("- Se ripassi su una casella già visitata: "),
                gameOverParagraph);

        add(rulesBox);
    }

    private void configureSizeSelector() {
        sizeSelector.addClassName("combo-box");
        sizeSelector.setItems(4, 5, 6, 7, 8);
        sizeSelector.setValue(5);
        add(sizeSelector);
    }

    private void addActionButtons() {
        Button startButton = new Button("🎮 Inizia a giocare", e -> startGame());
        Button exitButton = new Button("❌ Esci", e -> exitGame());

        startButton.addClassName("primary-button");
        exitButton.addClassName("secondary-button");

        add(startButton, exitButton);
    }

    private void startGame() {
        UI.getCurrent().navigate("game?size=" + sizeSelector.getValue());
    }

    private void exitGame() {
        UI.getCurrent().getPage().executeJs("window.close()");
    }
}