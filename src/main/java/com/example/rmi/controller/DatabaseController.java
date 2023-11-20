package com.example.rmi.controller;

import com.example.rmi.component.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static com.example.rmi.RmiClientApplication.remoteDB;

@Controller
public class DatabaseController {

    @GetMapping("/")
    public String index(Model model) throws RemoteException {
        model.addAttribute("tables", remoteDB.getTablesNames());
        return "index";
    }

    @GetMapping("/viewTable")
    public String viewTable(Model model) throws RemoteException {
        // Assuming tableIndex 0 is your test table
        List<Row> rows = remoteDB.getRows(0);
        model.addAttribute("rows", rows);
        return "index";
    }

    @PostMapping("/editCell")
    public String editCell(
            @RequestParam Map<String, String> allParams,
            Model model) throws RemoteException {

        // Extracting rowIndex and columnIndex
        int rowIndex = Integer.parseInt(allParams.get("rowIndex"));
        int columnIndex = Integer.parseInt(allParams.get("columnIndex"));

        // Extracting the dynamic value parameter
        String newValue = allParams.get("value-" + rowIndex + "-" + columnIndex);

        System.out.println(newValue);

        // Check if newValue is present and not empty
        if (newValue != null && !newValue.trim().isEmpty()) {
            // Perform the edit operation
            System.out.println(remoteDB.editCell(0, rowIndex, columnIndex, newValue));
        }

        return "redirect:/viewTable"; // Redirect to the view table page
    }

    // Other mappings...
}
