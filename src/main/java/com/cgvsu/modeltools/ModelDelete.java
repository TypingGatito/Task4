package com.cgvsu.modeltools;

import com.cgvsu.modelcomponents.model.Model;
import com.cgvsu.modelcomponents.polygon.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ModelDelete {

    public static void deleteVertexes(final Model model, final List<Integer> indexesToDelete) {
        List<Integer> copyIndexesToDelete = new ArrayList<>(indexesToDelete);
        Collections.sort(copyIndexesToDelete);
        correctList(copyIndexesToDelete, model.vertices.size() - 1);

        List<Polygon> polygonsInModel = model.polygons;
        Iterator<Polygon> iterator = polygonsInModel.iterator();
        while(iterator.hasNext()) {
            Polygon polygon = iterator.next();

            boolean toDel = !movePolygonIndexes(polygon, copyIndexesToDelete);
            if (toDel) iterator.remove();
        }


        for (int j = copyIndexesToDelete.size() - 1; j >= 0; j--) model.vertices.remove((int)copyIndexesToDelete.get(j));

    }
    private static void correctList(List<Integer> indexesToDelete, int maxValue) {
        for (Integer index: indexesToDelete) {
            if (index > maxValue || index < 0) indexesToDelete.remove(index);
        }
    }

    private static boolean movePolygonIndexes(Polygon polygon, List<Integer> indexesToDelete) {
        int i = 0;
        List<Integer> indexes = polygon.getVertexIndices();
        for (Integer vertex : indexes) {
            int indexOfVertex = binarySearch(indexesToDelete, vertex);


            if (indexOfVertex < 0) {
                indexes.set(i, indexes.get(i) + indexOfVertex + 1);
                i++;
                continue;
            }

            return false;
        }

        return true;
    }

    public static int binarySearch(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;
        int middle;
        while (left <= right) {
            middle = (left + right) / 2;
            if (list.get(middle) == target) return middle;
            if (list.get(middle) < target) left = middle + 1;
            else right = middle - 1;
        }

        return -(left + 1);
    }
}
