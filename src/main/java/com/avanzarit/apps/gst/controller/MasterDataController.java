package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.model.HsnMaster;
import com.avanzarit.apps.gst.repository.HsnMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by AVANZAR on 6/7/2017.
 */
@RestController
@RequestMapping("master")
public class MasterDataController {

    @Autowired
    private HsnMasterRepository hsnMasterRepository;

    @RequestMapping(value = "/{hsn}", method = RequestMethod.GET, produces = "application/json")
    public List<HsnMaster> getAllHsnCodes() {

        return hsnMasterRepository.findAll();
    }

}

