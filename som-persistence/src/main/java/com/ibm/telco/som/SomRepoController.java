package com.ibm.telco.som;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EntityScan("com.ibm.telco.som")
@RestController
@RequestMapping("/api")
public class SomRepoController
{
    private final Logger log = LoggerFactory.getLogger(SomRepoController.class);

    @Autowired
    SomMysqlRepository somRepository;

    //retrieve all Payloads
    @GetMapping("/payload")
    public ResponseEntity<List<SomPayloadEntity>> getAllPayloads(@RequestParam(required = false) String key)
    {
        log.info("...in getAllPayloads()");
        try
        {
            List<SomPayloadEntity> payloads = new ArrayList<>();
            somRepository.findAll().forEach(payloads::add);

            if (payloads.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payloads, HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //retrieve a Payload by key
    @GetMapping("/payload/{key}")
    public ResponseEntity<SomPayloadEntity> getPayloadByKey(@PathVariable("key") String key)
    {
        log.info("...in getPayloadByKey()");
        Optional<SomPayloadEntity> payload = Optional.ofNullable(somRepository.findByPayloadKey(key));
        if (payload.isPresent())
        {
            log.info("Found this payload: \n" +payload);
            return new ResponseEntity<>(payload.get(), HttpStatus.OK);
        }
        else
        {
            log.error("Record not found for key: "+key);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //create a new Payload
    @PostMapping("/payload")
    public ResponseEntity<SomPayloadEntity> createPayload(@RequestBody SomPayloadEntity payload)
    {
        log.info("...in createPayload(). Creating this payload:\n" +payload.toString());
        try
        {
            SomPayloadEntity _payload = somRepository.save(new SomPayloadEntity(payload.getPayloadKey(), payload.getPayload()));
            log.info("...created payload:\n" + _payload);
            return new ResponseEntity<>(_payload, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            log.error("Error occurred creating new payload\n" + payload);
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update a Payload by key
    @PutMapping("/payload/{key}")
    public ResponseEntity<SomPayloadEntity> updatePayload(@PathVariable("key") String key, @RequestBody SomPayloadEntity payload)
    {
        log.info("...in updatePayload(). Updating this payload:\n" +payload);
        Optional<SomPayloadEntity> targetPayload = Optional.ofNullable(somRepository.findByPayloadKey(key));
        if (targetPayload.isPresent())
        {
            SomPayloadEntity _payload = targetPayload.get();
            _payload.setPayloadKey(key);
            _payload.setPayload(payload.getPayload());
            return new ResponseEntity<>(somRepository.save(_payload), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //delete a Payload by key
    @DeleteMapping("/payload/{key}")
    public ResponseEntity<HttpStatus> deletePayload(@PathVariable("key") String key)
    {
        log.info("...in deletePayload() held by this key: "+key);
        try
        {
            somRepository.deleteById(key);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete all 
    @DeleteMapping("/payload")
    public ResponseEntity<HttpStatus> deleteAll()
    {
        log.warn("...in deleteAll() \n CAUTION: ABOUT TO DELETE ALL PAYLOADS IN THE DATABASE");
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
