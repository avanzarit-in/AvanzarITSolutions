package com.avanzarit.apps.gst.batch.job.controller;

import com.avanzarit.apps.gst.Layout;
import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import com.avanzarit.apps.gst.storage.StorageFileNotFoundException;
import com.avanzarit.apps.gst.storage.StorageProperties;
import com.avanzarit.apps.gst.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.PrintWriter;

@Controller
public class BatchJobController implements BeanFactoryAware {

    private BeanFactory beanFactory;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    StorageService storageService;
    @Autowired
    StorageProperties storageProperties;

    @Layout(value = "layouts/mdmdefault")
    @GetMapping("/mdm/upload")
    public String getMdmVendorDataUploadForm() {
        return "upload";
    }

    @GetMapping("/upload")
    public String getVendorDataUploadForm() {
        return "upload";
    }

    @GetMapping("/upload/customer")
    public String getCustomerDataUploadForm() {
        return "customerupload";
    }

    @GetMapping("/upload/user")
    public String getUserDataUploadForm() {
        return "userupload";
    }

    @PostMapping("/customerupload")
    public String handleCustomerFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = auth.getUsername();
            storageService.store("upload", file);
            Job job = (Job) beanFactory.getBean("customerImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .addString("user", userName)
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog", storageProperties.getCustomerUploadLogFileName());
            Resource logFile = storageService.loadAsResource("batchlog", storageProperties.getCustomerUploadLogFileName());
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        setPostUploadMessage(redirectAttributes, file, "customer");
        return "redirect:/upload/customer";
    }

    @PostMapping("/vendorupload")
    public String handleVendorFileUpload(@RequestParam("file") MultipartFile file,
                                         RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = auth.getUsername();
            storageService.store("upload", file);
            Job job = (Job) beanFactory.getBean("vendorImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .addString("user", userName)
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog", storageProperties.getVendorUploadLogFileName());
            Resource logFile = storageService.loadAsResource("batchlog", storageProperties.getVendorUploadLogFileName());
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        setPostUploadMessage(redirectAttributes, file, "vendor");
        return "redirect:/upload";
    }

    @PostMapping("/contactpersonupload")
    public String handleContactPersonFileUpload(@RequestParam("file") MultipartFile file,
                                                RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = auth.getUsername();
            storageService.store("upload", file);
            Job job = (Job) beanFactory.getBean("contactPersonImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog", storageProperties.getVendorContactPersonUploadLogFileName());
            Resource logFile = storageService.loadAsResource("batchlog", storageProperties.getVendorContactPersonUploadLogFileName());
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        setPostUploadMessage(redirectAttributes, file, "vendorcontact");
        return "redirect:/upload";
    }

    @PostMapping("/customercontactpersonupload")
    public String handleCustomerContactPersonFileUpload(@RequestParam("file") MultipartFile file,
                                                        RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = auth.getUsername();
            storageService.store("upload", file);
            Job job = (Job) beanFactory.getBean("customerContactPersonImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog", storageProperties.getCustomerContactPersonUploadLogFileName());
            Resource logFile = storageService.loadAsResource("batchlog", storageProperties.getCustomerContactPersonUploadLogFileName());
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        setPostUploadMessage(redirectAttributes, file, "customercontact");
        return "redirect:/upload/customer";
    }

    @PostMapping("/materialupload")
    public String handleMaterialFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = auth.getUsername();
            storageService.store("upload", file);
            Job job = (Job) beanFactory.getBean("materialImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog", storageProperties.getMaterialUploadLogFileName());
            Resource logFile = storageService.loadAsResource("batchlog", storageProperties.getMaterialUploadLogFileName());
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        setPostUploadMessage(redirectAttributes, file, "material");
        return "redirect:/upload";
    }


    @PostMapping("/userupload")
    public String handleUserFileUpload(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            storageService.store("upload", file);
            Job job = (Job) beanFactory.getBean("userImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog", storageProperties.getUserUploadLogFileName());
            Resource logFile = storageService.loadAsResource("batchlog", storageProperties.getUserUploadLogFileName());
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        setPostUploadMessage(redirectAttributes, file, "user");
        return "redirect:/upload/user";
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private String setPostUploadMessage(RedirectAttributes redirectAttributes, MultipartFile file, String uploadType) {

        StringBuilder sb = new StringBuilder();
        sb.append("You successfully uploaded " + file.getOriginalFilename() + "! ");
        redirectAttributes.addFlashAttribute("message", sb.toString());
        redirectAttributes.addFlashAttribute("downloadLink", "logs/" + uploadType);
        return sb.toString();
    }
}
