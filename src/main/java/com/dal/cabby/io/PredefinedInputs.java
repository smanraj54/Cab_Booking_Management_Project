package com.dal.cabby.io;

import java.util.ArrayList;
import java.util.List;

public class PredefinedInputs implements Inputs {
    private List<Object> preDefinedInputs;
    int currentIndex;

    public PredefinedInputs() {
        preDefinedInputs = new ArrayList<>();
        currentIndex = 0;
    }

    @Override
    public int getIntegerInput() {
        Object o = getElement();
        if (o instanceof Integer) {
            return (Integer) o;
        } else {
            throw new RuntimeException("Integer type not found in next element of predefined inputs.");
        }
    }

    @Override
    public String getStringInput() {
        Object o = getElement();
        if (o instanceof String) {
            return (String) o;
        } else {
            throw new RuntimeException("String type not found in next element of predefined inputs.");
        }
    }

    @Override
    public double getDoubleInput() {
        Object o = getElement();
        if (o instanceof Double) {
            return (Double) o;
        } else {
            throw new RuntimeException("Double type not found in next element of predefined inputs.");
        }
    }

    private Object getElement() {
        if (currentIndex > preDefinedInputs.size() - 1) {
            throw new RuntimeException(String.format("Index %s is outside of current inputs size"));
        }
        Object o = preDefinedInputs.get(currentIndex);
        currentIndex++;
        return o;
    }

    public PredefinedInputs add(Object o) {
        preDefinedInputs.add(o);
        return this;
    }
}
