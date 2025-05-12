package org.kevinslashgun.base.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.kevinslashgun.base.domain.service.GameService;

import java.util.Optional;

@Route("game")
@CssImport("./styles/game-view.css")
public class GameView extends VerticalLayout implements BeforeEnterObserver {

    private final transient GameService gameService;
    private Button[][] cells;

    public GameView(GameService gameService) {
        this.gameService = gameService;
        this.cells = new Button[0][0]; // Initialize with an empty array
        addClassName("game-view");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        int size = getSizeFromQuery(event);
        gameService.initializeGame(size);
        initGameBoard(size);
    }

    private int getSizeFromQuery(BeforeEnterEvent event) {
        Optional<String> sizeParam = event.getLocation()
                .getQueryParameters()
                .getParameters()
                .getOrDefault("size", java.util.List.of())
                .stream()
                .findFirst();

        return sizeParam.map(Integer::parseInt).orElse(5);
    }

    private void initGameBoard(int size) {
        removeAll();

        Div grid = createGrid(size);
        createCells(size, grid);

        add(grid);
    }

    private Div createGrid(int size) {
        Div grid = new Div();
        grid.addClassName("chess-grid");
        grid.getStyle()
                .set("grid-template-columns", "repeat(" + size + ", 60px)")
                .set("grid-template-rows", "repeat(" + size + ", 60px)");
        return grid;
    }

    private void createCells(int size, Div grid) {
        cells = new Button[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Button cell = createCell(x, y);
                cells[y][x] = cell;
                grid.add(cell);
            }
        }
    }

    private Button createCell(int x, int y) {
        Button cell = new Button();
        cell.setWidth("60px");
        cell.setHeight("60px");
        cell.addClickListener(e -> handleCellClick(x, y));
        return cell;
    }

    private void handleCellClick(int x, int y) {
        if (gameService.isVisited(x, y)) {
            showInvalidMove(x, y);
            showEndGameDialog("Hai già visitato quella casella. GAME OVER!", false);
            return;
        }

        if (gameService.makeMove(x, y)) {
            updateBoard();
            if (gameService.isGameWon()) {
                showEndGameDialog("Hai visitato tutte le caselle! Hai vinto!", true);
            }
        } else {
            showInvalidMove(x, y);
        }
    }

    private void showInvalidMove(int x, int y) {
        cells[y][x].getElement().executeJs(
                "this.classList.add('invalid-move'); setTimeout(() => this.classList.remove('invalid-move'), 300);");
    }

    private void updateBoard() {
        int size = gameService.getBoardSize();
        int[] currentPos = gameService.getCurrentPosition();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Button cell = cells[y][x];

                if (x == currentPos[0] && y == currentPos[1]) {
                    cell.setText("🐴");
                } else if (gameService.isVisited(x, y)) {
                    cell.setText(getNumberEmoji(gameService.getMoveNumber(x, y)));
                } else {
                    cell.setText("");
                }
            }
        }
    }

    private void showEndGameDialog(String message, boolean isVictory) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.setAlignItems(Alignment.CENTER);
        layout.addClassName("end-game-dialog");

        String emoji = isVictory ? "🏆" : "❌";
        Span messageSpan = new Span(emoji + " " + message);
        messageSpan.addClassName("end-game-message");
        layout.add(messageSpan);

        Button restartButton = new Button("🔁 Gioca di nuovo", e -> {
            dialog.close();
            gameService.initializeGame(gameService.getBoardSize());
            updateBoard();
        });

        Button homeButton = new Button("🏠 Torna al Menu", e -> {
            dialog.close();
            UI.getCurrent().navigate("");
        });

        restartButton.addClassName("primary-button");
        homeButton.addClassName("secondary-button");

        layout.add(restartButton, homeButton);
        dialog.add(layout);
        dialog.open();
    }

    private String getNumberEmoji(int number) {
        String[] digitEmojis = { "0️⃣", "1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣" };
        if (number < 10)
            return digitEmojis[number];

        StringBuilder emojiNumber = new StringBuilder();
        while (number > 0) {
            int digit = number % 10;
            emojiNumber.insert(0, digitEmojis[digit]);
            number /= 10;
        }
        return emojiNumber.toString();
    }
}