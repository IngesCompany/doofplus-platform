package com.ingescompany.doofplus.platform.manufacturing.domain.model.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.FormulaItemData;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.FormulaItemKind;
import lombok.Getter;

/**
 * Line of a master formula: a component or a process parameter.
 */
@Getter
public class FormulaItem {
    private Long id;
    private FormulaItemKind kind;
    private String name;
    private String role;
    private String target;
    private String tolerance;
    private String control;

    private FormulaItem() {
    }

    /**
     * Creates a line of a master formula.
     *
     * @param data data of the line
     */
    public FormulaItem(FormulaItemData data) {
        this.kind = data.kind();
        this.name = data.name().trim();
        this.role = trimToEmpty(data.role());
        this.target = trimToEmpty(data.target());
        this.tolerance = trimToEmpty(data.tolerance());
        this.control = trimToEmpty(data.control());
    }

    /**
     * Rebuilds a stored line.
     *
     * @return the line with its persistent identifier
     */
    public static FormulaItem reconstitute(Long id, FormulaItemKind kind, String name, String role, String target,
                                           String tolerance, String control) {
        var item = new FormulaItem();
        item.id = id;
        item.kind = kind;
        item.name = name;
        item.role = role;
        item.target = target;
        item.tolerance = tolerance;
        item.control = control;
        return item;
    }

    private static String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}
