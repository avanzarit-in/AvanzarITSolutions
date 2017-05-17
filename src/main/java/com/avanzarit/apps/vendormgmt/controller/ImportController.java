package com.avanzarit.apps.vendormgmt.controller;

import com.avanzarit.apps.vendormgmt.auth.service.UserService;
import com.avanzarit.apps.vendormgmt.batch.step.Listener;
import com.avanzarit.apps.vendormgmt.batch.step.Processor;
import com.avanzarit.apps.vendormgmt.batch.step.Reader;
import com.avanzarit.apps.vendormgmt.batch.step.Writer;
import com.avanzarit.apps.vendormgmt.model.Vendor;
import com.avanzarit.apps.vendormgmt.repository.VendorRepository;
import com.avanzarit.apps.vendormgmt.storage.StorageFileNotFoundException;
import com.avanzarit.apps.vendormgmt.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by SPADHI on 5/5/2017.
 */
@Controller
public class ImportController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    StorageService storageService;

    @Autowired
    public VendorRepository vendorRepository;

    @Autowired
    private UserService userService;

    public Job importJob() {
        return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new Listener(vendorRepository))
                .flow(step1()).end().build();
    }


    public Step step1() {
        return stepBuilderFactory.get("step1").<Vendor, Vendor>chunk(2)
                .reader(Reader.reader(storageService))
                .processor(new Processor()).writer(new Writer(vendorRepository, userService)).build();
    }

    @GetMapping("/upload")
    public String getVendorDataUploadForm(){

        return "upload";
    }
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            storageService.store(file);
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(importJob(), jobParameters);
         //   storageService.deleteAll();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/upload";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
