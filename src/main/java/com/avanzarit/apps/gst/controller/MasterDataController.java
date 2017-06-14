package com.avanzarit.apps.gst.controller;

import com.avanzarit.apps.gst.model.masterdata.HsnMaster;
import com.avanzarit.apps.gst.model.masterdata.SacMaster;
import com.avanzarit.apps.gst.repository.HsnMasterRepository;
import com.avanzarit.apps.gst.repository.SacMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @RequestMapping(path = "/master/hsn/validate", method = RequestMethod.POST)
    public
    @ResponseBody
    String getHsnCode(@RequestParam Map<String, String> allRequestParams) {
        String hsnCode = allRequestParams.get("hsn");
        if (hsnMasterRepository.findByCode(hsnCode) != null) {
            return "{ \"valid\": true }";
        }
        return "{ \"valid\": false }";
    }

    @RequestMapping(path = "/master/sac/validate", method = RequestMethod.POST)
    @ResponseBody
    public String getSacCode(@RequestParam Map<String, String> allRequestParams) {
        String sacCode = allRequestParams.get("sac");
        if (sacMasterRepository.findByCode(sacCode) != null) {
            return "{ \"valid\": true }";
        }
        return "{ \"valid\": false }";
    }
}

