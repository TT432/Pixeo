package io.github.tt432.pixeo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author TT432
 */
public class LayerManager {
    private final List<List<UIElement>> layeredElements = new ArrayList<>();

    public List<UIElement> getLayer(int layer) {
        return layeredElements.size() < layer - 1 ? layeredElements.get(layer) : List.of();
    }

    public List<UIElement> lastLayer() {
        return layeredElements.isEmpty() ? List.of():layeredElements.get(layeredElements.size() - 1);
    }

    public void forEachNegate(BiConsumer<Integer, List<UIElement>> action) {
        for (int layer = layeredElements.size() - 1; layer >= 0; layer--) {
            List<UIElement> uiElements = layeredElements.get(layer);

            if (uiElements != null) {
                action.accept(layer, uiElements);
            }
        }
    }

    public void forEach(BiConsumer<Integer, List<UIElement>> action) {
        for (int layer = 0; layer < layeredElements.size(); layer++) {
            List<UIElement> uiElements = layeredElements.get(layer);

            if (uiElements != null) {
                action.accept(layer, uiElements);
            }
        }
    }

    public void updateElementLayers(List<UIElement> toplevels) {
        layeredElements.clear();

        updateElementLayersLayer(toplevels);
    }

    private void updateElementLayersLayer(List<UIElement> currentLayer) {
        layeredElements.add(currentLayer);
        List<UIElement> nextLayer = new ArrayList<>();

        for (UIElement element : currentLayer) {
            element.setLayer(layeredElements.size() - 1);
            nextLayer.addAll(element.getChildren());
        }

        if (nextLayer.isEmpty()) {
            return;
        }

        updateElementLayersLayer(nextLayer);
    }
}
