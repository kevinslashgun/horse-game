package org.kevinslashgun.base.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import java.util.Optional;

@Route("game")
@CssImport("./styles/game-view.css")
public class GameView extends VerticalLayout implements BeforeEnterObserver {

    private int moveCount = 0;
    private int size = 5;
    private boolean[][] visited;
    private int knightX = -1;
    private int knightY = -1;
    private Button[][] cells;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> sizeParam = event.getLocation().getQueryParameters().getParameters()
                .getOrDefault("size", java.util.List.of()).stream().findFirst();
        sizeParam.ifPresent(param -> {
            try {
                size = Integer.parseInt(param);
            } catch (NumberFormatException ignored) {
                // Ignoring invalid size parameter, default size will be used
            }
        });

        visited = new boolean[size][size];
        cells = new Button[size][size];
        initView();
    }

    private void initView() {
        removeAll();
        addClassName("game-view");
        Div grid = new Div();
        grid.addClassName("chess-grid");
        grid.getStyle().set("grid-template-columns", "repeat(" + size + ", 60px)");
        grid.getStyle().set("grid-template-rows", "repeat(" + size + ", 60px)");

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Button cell = new Button();
                cell.setWidth("60px");
                cell.setHeight("60px");
                final int cx = x;
                final int cy = y;

                cell.addClickListener(e -> handleClick(cx, cy));
                cells[y][x] = cell;
                grid.add(cell);
            }
        }

        add(grid);
    }

    private void handleClick(int x, int y) {
        if (knightX == -1 && knightY == -1) {
            knightX = x;
            knightY = y;
            moveCount = 0;
            markVisited(x, y);
            updateBoard();
            return;
        }

        if (!isValidMove(x, y)) {
            Button invalidCell = cells[y][x];
            invalidCell.getElement().executeJs(
                    "this.classList.add('invalid-move'); setTimeout(() => this.classList.remove('invalid-move'), 300);");
            return;
        }

        if (visited[y][x]) {
            showEndGameDialog("Hai già visitato quella casella. GAME OVER!", false);
            return;
        }

        knightX = x;
        knightY = y;
        markVisited(x, y);
        updateBoard();

        if (isGameWon()) {
            showEndGameDialog("Hai visitato tutte le caselle! Hai vinto!", true);
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

        String emoji = isVictory ? "🏆" : "❌";
        layout.add(new com.vaadin.flow.component.html.Span(emoji + " " + message));

        Button restartButton = new Button("🔁 Gioca di nuovo", e -> {
            dialog.close();
            knightX = -1;
            knightY = -1;
            moveCount = 0;
            visited = new boolean[size][size];
            updateBoard();
        });

        Button homeButton = new Button("🏠 Torna al Menu", e -> {
            dialog.close();
            UI.getCurrent().navigate("");
        });

        layout.add(restartButton, homeButton);
        dialog.add(layout);
        dialog.open();
    }

    private boolean isGameWon() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (!visited[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(int x, int y) {
        int dx = Math.abs(x - knightX);
        int dy = Math.abs(y - knightY);
        return dx * dy == 2;
    }

    private void markVisited(int x, int y) {
        visited[y][x] = true;
        cells[y][x].getElement().setProperty("moveNumber", moveCount++);
    }

    private void updateBoard() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Button cell = cells[y][x];
                if (x == knightX && y == knightY) {
                    cell.setText("🐴");
                } else if (visited[y][x]) {
                    int moveNum = cell.getElement().getProperty("moveNumber", 0);
                    cell.setText(getNumberEmoji(moveNum));
                } else {
                    cell.setText("");
                }
            }
        }
    }

    // Metodo per convertire numeri in emoji
    private String getNumberEmoji(int number) {
        String[] digitEmojis = { "0️⃣", "1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣" };
        if (number < 10) {
            return digitEmojis[number];
        }

        // Per numeri > 9, combina più emoji
        StringBuilder emojiNumber = new StringBuilder();
        while (number > 0) {
            int digit = number % 10;
            emojiNumber.insert(0, digitEmojis[digit]);
            number /= 10;
        }
        return emojiNumber.toString();
    }
}
