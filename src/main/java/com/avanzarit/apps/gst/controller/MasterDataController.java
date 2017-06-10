package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.model.HsnMaster;
import com.avanzarit.apps.gst.model.SacMaster;
import com.avanzarit.apps.gst.repository.HsnMasterRepository;
import com.avanzarit.apps.gst.repository.SacMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by AVANZAR on 6/7/2017.
 */
@RestController
public class MasterDataController {

    @Autowired
    private HsnMasterRepository hsnMasterRepository;

    @Autowired
    private SacMasterRepository sacMasterRepository;

    @RequestMapping(path = "/master/hsn", method = RequestMethod.GET, produces = "application/json")
    public List<HsnMaster> getAllHsnCodes() {

        return hsnMasterRepository.findAll();
    }

    @RequestMapping(path = "/master/sac", method = RequestMethod.GET, produces = "application/json")
    public List<SacMaster> getAllSacCodes() {

        return sacMasterRepository.findAll();
    }



}

