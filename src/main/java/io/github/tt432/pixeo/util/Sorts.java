package io.github.tt432.pixeo.util;

import io.github.tt432.pixeo.ui.component.UIComponent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @author TT432
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Sorts {
    public static List<UIComponent> sortComponents(Collection<UIComponent> components) {
        Map<Class<? extends UIComponent>, UIComponent> componentMap = new HashMap<>();
        Map<Class<? extends UIComponent>, List<Class<? extends UIComponent>>> graph = new HashMap<>();
        List<UIComponent> sortedComponents = new ArrayList<>();

        // 初始化图和组件映射
        for (var component : components) {
            componentMap.put(component.getClass(), component);
            graph.putIfAbsent(component.getClass(), new ArrayList<>());
        }

        // 填充图的边
        for (var component : components) {
            for (var after : component.getAfter()) {
                graph.get(after).add(component.getClass());
            }

            graph.get(component.getClass()).addAll(component.getBefore());
        }

        // 拓扑排序
        Set<Class<? extends UIComponent>> visited = new HashSet<>();
        Deque<Class<? extends UIComponent>> stack = new ArrayDeque<>();

        for (var componentName : graph.keySet()) {
            if (!visited.contains(componentName)) {
                topoSort(graph, visited, stack, componentName);
            }
        }

        // 生成排序后的组件列表
        while (!stack.isEmpty()) {
            sortedComponents.add(componentMap.get(stack.pop()));
        }

        return sortedComponents;
    }

    private static void topoSort(Map<Class<? extends UIComponent>, List<Class<? extends UIComponent>>> graph,
                                 Set<Class<? extends UIComponent>> visited, Deque<Class<? extends UIComponent>> stack,
                                 Class<? extends UIComponent> current) {
        visited.add(current);

        for (var neighbor : graph.get(current)) {
            if (!visited.contains(neighbor)) {
                topoSort(graph, visited, stack, neighbor);
            }
        }

        stack.push(current);
    }
}
