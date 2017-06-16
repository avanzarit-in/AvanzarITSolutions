package com.avanzarit.apps.gst.batch.job.controller;

import com.avanzarit.apps.gst.auth.repository.RoleRepository;
import com.avanzarit.apps.gst.auth.repository.UserRepository;
import com.avanzarit.apps.gst.auth.service.UserService;
import com.avanzarit.apps.gst.batch.job.properties.BatchProperties;
import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import com.avanzarit.apps.gst.email.EmailServiceImpl;
import com.avanzarit.apps.gst.repository.CustomerRepository;
import com.avanzarit.apps.gst.repository.VendorRepository;
import com.avanzarit.apps.gst.storage.StorageFileNotFoundException;
import com.avanzarit.apps.gst.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.DataSource;
import java.io.PrintWriter;
@Controller
public class BatchJobController implements BeanFactoryAware {

    private BeanFactory beanFactory;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    public SimpleMailMessage updatePasswordMessage;
    @Autowired
    public EmailServiceImpl emailService;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    StorageService storageService;
    @Autowired
    public VendorRepository vendorRepository;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RoleRepository roleRepository;
    @Qualifier("dataSource")
    @Autowired
    public DataSource dataSource;
    @Autowired
    private UserService userService;
    @Autowired
    private BatchProperties batchProperties;



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
            storageService.store("batchlog", "customeruploadbatch.log");
            Resource logFile = storageService.loadAsResource("batchlog", "customeruploadbatch.log");
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/upload/customer";
    }

    @PostMapping("/vendorupload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = auth.getUsername();
            storageService.store("upload",file);
            Job job = (Job) beanFactory.getBean("vendorImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .addString("user", userName)
                    .toJobParameters();
            JobExecution execution=jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog","vendoruploadbatch.log");
            Resource logFile=storageService.loadAsResource("batchlog","vendoruploadbatch.log");
            PrintWriter out = new PrintWriter( logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

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
            storageService.store("batchlog", "contactpersonuploadbatch.log");
            Resource logFile = storageService.loadAsResource("batchlog", "contactpersonuploadbatch.log");
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

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
            storageService.store("batchlog", "customercontactpersonuploadbatch.log");
            Resource logFile = storageService.loadAsResource("batchlog", "customercontactpersonuploadbatch.log");
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/customerupload";
    }

    @PostMapping("/materialupload")
    public String handleMaterialFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = auth.getUsername();
            storageService.store("upload",file);
            Job job = (Job) beanFactory.getBean("materialImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution execution=jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog", "materialuploadbatch.log");
            Resource logFile = storageService.loadAsResource("batchlog", "materialuploadbatch.log");
            PrintWriter out = new PrintWriter( logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/upload";
    }


    @PostMapping("/userupload")
    public String handleUserFileUpload(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            storageService.store("upload",file);
            Job job = (Job) beanFactory.getBean("userImportJob", storageService.loadAsResource("upload", file.getOriginalFilename()));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            BatchLog logInfo = (BatchLog) execution.getExecutionContext().get("log");
            storageService.store("batchlog", "useruploadbatch.log");
            Resource logFile = storageService.loadAsResource("batchlog", "useruploadbatch.log");
            PrintWriter out = new PrintWriter(logFile.getFile());
            out.print(logInfo.getLog());
            out.flush();
            out.close();
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

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
