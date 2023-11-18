package io.github.tt432.pixeo.util;

import io.github.tt432.pixeo.ui.LayerManager;
import io.github.tt432.pixeo.ui.UIElement;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author TT432
 */
public class ElementsSearchHandler {
    private final LayerManager layerManager;
    private Map<Integer, List<UIElement>> lastResult = new HashMap<>();
    private int lastLayer;

    public ElementsSearchHandler(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public Optional<List<UIElement>> lastLayer() {
        return lastResult.isEmpty() ? Optional.empty() : Optional.of(lastResult.get(lastLayer));
    }

    public Map<Integer, List<UIElement>> fullResult() {
        return lastResult;
    }

    public ElementsSearchHandler search(Predicate<UIElement> predicate) {
        Map<Integer, List<UIElement>> elements = new HashMap<>();

        layerManager.forEach((layer, layerContent) -> {
            List<UIElement> testedValues = new ArrayList<>();

            for (UIElement element : layerContent) {
                if (predicate.test(element)) {
                    testedValues.add(element);
                }
            }

            if (!testedValues.isEmpty()) {
                elements.put(layer, testedValues);
                lastLayer = layer;
            }
        });

        lastResult = elements;

        return this;
    }
}
