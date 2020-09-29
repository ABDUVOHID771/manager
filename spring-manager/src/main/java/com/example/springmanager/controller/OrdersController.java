package com.example.springmanager.controller;

import com.example.springmanager.dao.domain.*;
import com.example.springmanager.service.OrdersService;
import com.example.springmanager.service.PrinterService;
import com.example.springmanager.service.UserService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.99.100:4200"})
@RestController
@RequestMapping("/rest/orders")
public class OrdersController {

    private final OrdersService ordersService;
    private final PrinterService printerService;
    private final UserService userService;

    public OrdersController(OrdersService ordersService, PrinterService printerService, UserService userService) {
        this.ordersService = ordersService;
        this.printerService = printerService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(ordersService.getAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getMin(@AuthenticationPrincipal UserPrincipal userPrincipal) throws NotFoundException {
        User user = userService.getByUsername(userPrincipal.getUsername());
        Orders orders = ordersService.getMinOrders(user.getDirection());
        orders.setStatus(Status.SERVING);
        ordersService.updateOrders(userPrincipal, orders);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOrders(@Valid @RequestBody Orders orders, BindingResult result) throws UnsupportedEncodingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) {
            return errors;
        }

        Orders created = ordersService.create(orders);
        String direction = null;
        String department = null;
        if (created.getDirections() == Directions.IT) {
            direction = "It";
            department = "Programmist";

        } else if (created.getDirections() == Directions.PHYSIC) {
            direction = "Jismoniy";
            switch (created.getDepartments()) {
                case CASHBOX:
                    department = "Kassa";
                    break;
                case CREDIT_PHYSIC:
                    department = "Kredit";
                    break;
                case EXCHANGE_CURRENCY:
                    department = "Valyuta almashtirish";
                    break;
                case EXCHANGE_MONEY:
                    department = "Pulni almashtirish";
                    break;
                case OMONAT:
                    department = "Omonat";
                    break;
                case PLASTIC:
                    department = "Plastik";
                    break;
                case TERMINAL:
                    department = "Terminal";
                    break;
            }
        } else if (created.getDirections() == Directions.LAWYER) {
            direction = "Yuridik";
            switch (created.getDepartments()) {
                case CREDIT_LAWYER:
                    department = "Kredit";
                    break;
                case LAWYER:
                    department = "Yurist";
                    break;
                case OPERATOR:
                    department = "Operator";
                    break;
            }
        }

        String text = "";
        text = (
                "\n\n" + "----------------------------------------------------------------\n" +
                        "\nSizning raqamingiz : " + created.getOrderNumber()) +
                "\nSizning yo'nalishingiz : " + direction +
                "\nSizning bo'limingiz : " + department +
                "\n\n" +
                "\n" + "----------------------------------------------------\n";
        //print some stuff. Change the printer name to your thermal printer name.


        printerService.printString("POS-58(copy of 6)", text);
        // cut that paper!
        byte[] cutP = new byte[]{0x1d, 'V', 1};
        printerService.printBytes("POS-58(copy of 6)", cutP);

        return new ResponseEntity<>(created, HttpStatus.CREATED);

    }

    @PutMapping()
    public ResponseEntity<?> updateReadyOrders(@Valid @RequestBody Orders orders, BindingResult result) throws
            NotFoundException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) {
            return errors;
        }

        return new ResponseEntity<>(ordersService.readyOrders(orders), HttpStatus.OK);
    }

    private ResponseEntity<?> getErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
