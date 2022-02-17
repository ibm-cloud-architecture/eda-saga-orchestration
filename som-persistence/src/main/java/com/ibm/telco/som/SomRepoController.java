package com.ibm.telco.som;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SomRepoController
{
    private final Logger log = LoggerFactory.getLogger(SomRepoController.class);

    @Autowired
    SomRepository somRepository;

    //retrieve all SomOrders
    @GetMapping("/somorder")
    public ResponseEntity<List<SomOrder>> getAllSomOrders(@RequestParam(required = false) String key)
    {
        log.info("...in getAllSomOrders()");
        try
        {
            List<SomOrder> somOrders = new ArrayList<>();
            somRepository.findAll().forEach(somOrders::add);

            if (somOrders.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(somOrders, HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //retrieve a SomOrder by key
    @GetMapping("/somorder/{key}")
    public ResponseEntity<SomOrder> getOrderByKey(@PathVariable("key") String key)
    {
        log.info("...in getOrderByKey()");
        Optional<SomOrder> somOrder = Optional.ofNullable(somRepository.findByKey(key));
        if (somOrder.isPresent())
        {
            log.info("Found this order: \n" +somOrder);
            return new ResponseEntity<>(somOrder.get(), HttpStatus.OK);
        }
        else
        {
            log.error("Record not found for key: "+key);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //create a new SomOrder
    @PostMapping("/somorder")
    public ResponseEntity<SomOrder> createOrder(@RequestBody SomOrder somOrder)
    {
        log.info("...in createOrder(). Creating this order:\n" +somOrder.toString());
        try
        {
            SomOrder _somOrder = somRepository.save(new SomOrder(somOrder.getKey(), somOrder.getOrder()));
            log.info("...created order:\n" + _somOrder);
            return new ResponseEntity<>(_somOrder, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            log.error("Error occurred creating new order\n" + somOrder);
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update a SomOrder by key
    @PutMapping("/somorder/{key}")
    public ResponseEntity<SomOrder> updateOrder(@PathVariable("key") String key, @RequestBody SomOrder somOrder)
    {
        log.info("...in updateOrder(). Updating this order:\n" +somOrder);
        Optional<SomOrder> targetSomOrder = Optional.ofNullable(somRepository.findByKey(key));
        if (targetSomOrder.isPresent())
        {
            SomOrder _somOrder = targetSomOrder.get();
            _somOrder.setKey(key);
            _somOrder.setOrder(somOrder.getOrder());
            return new ResponseEntity<>(somRepository.save(_somOrder), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //delete a SomOrder by key
    @DeleteMapping("/somorder/{key}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("key") String key)
    {
        log.info("...in deleteOrder() held by this key: "+key);
        try
        {
            somRepository.deleteByKey(key);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete all 
    @DeleteMapping("/somorder")
    public ResponseEntity<HttpStatus> deleteAll()
    {
        log.info("...in deleteAll() \n CAUTION: ABOUT TO DELETE ALL ORDERS IN THE DATABASE");
        try
        {
            somRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
