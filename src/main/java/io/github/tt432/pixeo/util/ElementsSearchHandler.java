package io.github.tt432.pixeo.util;

import io.github.tt432.pixeo.ui.UIElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author TT432
 */
public class ElementsSearchHandler {
    private final List<UIElement> elements;
    private List<List<UIElement>> lastResult = new ArrayList<>();

    public ElementsSearchHandler(List<UIElement> elements) {
        this.elements = elements;
    }

    public Optional<List<UIElement>> lastLayer() {
        return lastResult.isEmpty() ? Optional.empty() : Optional.of(lastResult.get(lastResult.size() - 1));
    }

    public List<List<UIElement>> fullResult() {
        return lastResult;
    }

    public ElementsSearchHandler search(Predicate<UIElement> search) {
        List<List<UIElement>> layerElements = new ArrayList<>();
        List<UIElement> toplevel = new ArrayList<>();

        for (UIElement element : elements) {
            if (search.test(element)) {
                toplevel.add(element);
            }
        }

        if (!toplevel.isEmpty()) {
            layerElements.add(toplevel);
            searchIn(layerElements, 0, search);
        }

        lastResult = layerElements;

        return this;
    }

    private void searchIn(List<List<UIElement>> elements, int upperLayer, Predicate<UIElement> search) {
        List<UIElement> childElements = new ArrayList<>();

        for (UIElement element : elements.get(upperLayer)) {
            for (UIElement child : element.getChildren()) {
                if (search.test(child)) {
                    childElements.add(child);
                }
            }
        }

        if (!childElements.isEmpty()) {
            elements.add(childElements);
            searchIn(elements, upperLayer + 1, search);
        }
    }
}
