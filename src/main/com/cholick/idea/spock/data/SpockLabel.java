package com.cholick.idea.spock.data;

import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrLabeledStatement;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public enum SpockLabel {

    AND {
        public EnumSet<SpockLabel> getSuccessors() {
            return EnumSet.of(AND, EXPECT, WHEN, THEN, CLEANUP, WHERE);
        }
    },

    SETUP {
        public EnumSet<SpockLabel> getSuccessors() {
            return EnumSet.of(AND, EXPECT, WHEN, CLEANUP, WHERE);
        }
    },

    GIVEN {
        public EnumSet<SpockLabel> getSuccessors() {
            return SETUP.getSuccessors();
        }
    },

    EXPECT {
        public EnumSet<SpockLabel> getSuccessors() {
            return EnumSet.of(AND, WHEN, CLEANUP, WHERE);
        }
    },

    WHEN {
        public EnumSet<SpockLabel> getSuccessors() {
            return EnumSet.of(AND, THEN);
        }
    },

    THEN {
        public EnumSet<SpockLabel> getSuccessors() {
            return EnumSet.of(AND, EXPECT, WHEN, THEN, CLEANUP, WHERE);
        }
    },

    CLEANUP {
        public EnumSet<SpockLabel> getSuccessors() {
            return EnumSet.of(AND, WHERE);
        }
    },

    WHERE {
        public EnumSet<SpockLabel> getSuccessors() {
            return EnumSet.of(AND);
        }
    };

    public String toString() {
        return super.toString().toLowerCase();
    }

    public abstract EnumSet<SpockLabel> getSuccessors();

    private static final List<String> stringValues;

    static {
        stringValues = new LinkedList<String>();
        for (SpockLabel label : values()) {
            stringValues.add(label.toString());
        }
    }

    public static boolean contains(String label) {
        return stringValues.contains(label);
    }

    public static boolean contains(GrLabeledStatement label) {
        return stringValues.contains(label.getName().toLowerCase());
    }

    public static SpockLabel valueOf(GrLabeledStatement label) {
        return valueOf(label.getName().toUpperCase());
    }

}
