package org.kevinslashgun.base.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
@CssImport("./styles/start-view.css")
public class StartView extends VerticalLayout {

    public StartView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        addClassName("start-view");

        // Titolo
        H1 title = new H1("🐴 Horse Game");
        title.addClassName("title");

        // Box regole
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

        // ComboBox per selezione dimensione
        ComboBox<Integer> sizeSelector = new ComboBox<>("📏 Dimensione della scacchiera");
        sizeSelector.addClassName("combo-box");
        sizeSelector.setItems(4, 5, 6, 7, 8);
        sizeSelector.setValue(5); // valore di default

        Button startButton = new Button("🎮 Inizia a giocare", e -> {
            Integer selectedSize = sizeSelector.getValue();
            UI.getCurrent().navigate("game?size=" + selectedSize);
        });

        Button exitButton = new Button("❌ Esci", e -> UI.getCurrent().getPage().executeJs("window.close()"));

        startButton.addClassName("primary-button");
        exitButton.addClassName("secondary-button");

        add(title, rulesBox, sizeSelector, startButton, exitButton);
    }
}
