package client;

import javafx.event.ActionEvent;

public class DetailedController {
    private DetailedView view;
    private Model model;

    public DetailedController(DetailedView view, Model model) {
        this.view = view;
        this.model = model;

        this.view.setPutButtonAction(this::handlePutButton);
    }

    private void handlePutButton(ActionEvent event) {
        view.setRecipeTotal(view.getRecipeAsText().getText());
        view.getRecipeAsText().setEditable(false);
        view.getStage().close();

        String ID = view.getRecipe().getID();
        System.out.print("ID: " + ID);
        String[] typeAndRecipe = new String[]{view.getRecipeType(), view.getRecipe().getUser(), view.getRecipe().getURL(), view.getRecipeAsText().getText()};
        String response = model.performRequest("PUT", ID, typeAndRecipe, null);

        System.out.print("DetailedController response: " + response);
    }
}