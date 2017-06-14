package com.avanzarit.apps.gst.batch.job.controller;

import com.avanzarit.apps.gst.batch.job.properties.BatchProperties;
import com.avanzarit.apps.gst.storage.StorageProperties;
import com.avanzarit.apps.gst.storage.StorageService;
import com.avanzarit.apps.gst.utils.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class FileDownloadController implements BeanFactoryAware {

    private BeanFactory beanFactory;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    StorageService storageService;
    @Autowired
    BatchProperties batchProperties;
    @Autowired
    StorageProperties storagePropertie;

    @RequestMapping(value = "/downloadAttachment/{vendorId}/{docType}", method = RequestMethod.GET)
    public void downloadAttachment(HttpServletResponse response, @PathVariable String docType, @PathVariable String vendorId) throws IOException {
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        String userName = roles.contains("BUSINESS_OWNER") ? vendorId : auth.getUsername();
        if (roles.contains("BUSINESS_OWNER") || (roles.contains("VENDOR") && auth.getUsername().equals(vendorId))) {
            File file = storageService.loadAsFile("attachment", userName + "/PAN");
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                System.out.println("mimetype is not detectable, will take default");
                mimeType = "application/octet-stream";
            }

            System.out.println("mimetype : " + mimeType);

            response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            //Copy bytes from source to destination(outputstream in this example), closes both streams.
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }


    }
    /*
     * Download a file from
     *   - inside project, located in resources folder.
     *   - outside project, located in File system somewhere.
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void downloadFile(HttpServletResponse response) throws IOException {

        prepareDownload();
        File file = ZipUtil.performZip(storagePropertie.getExportLocation(), storagePropertie.getVendorDownloadZipFilePath());


        if (!file.exists()) {
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        System.out.println("mimetype : " + mimeType);

        response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }


    private void prepareDownload() {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            storageService.store("export",batchProperties.getVendorExportFileName());
            storageService.store("export",batchProperties.getVendorMaterialExportFileName());
            storageService.store("export",batchProperties.getVendorContactPersonExportFileName());
            storageService.store("export", batchProperties.getServiceSacExportFileNmae());
            Map<String, String> resourceMap = new HashMap<>();
            resourceMap.put("VENDOR", batchProperties.getVendorExportFileName());
            resourceMap.put("MATERIAL", batchProperties.getVendorMaterialExportFileName());
            resourceMap.put("CONTACTPERSON", batchProperties.getVendorContactPersonExportFileName());
            resourceMap.put("SAC", batchProperties.getServiceSacExportFileNmae());
            Job job = (Job) beanFactory.getBean("vendorExportJob", resourceMap);
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }


    /*
     * Download a file from
     *   - inside project, located in resources folder.
     *   - outside project, located in File system somewhere.
     */
    @RequestMapping(value = "/download/customer", method = RequestMethod.POST)
    public void downloadCustomerFile(HttpServletResponse response) throws IOException {

        prepareCustomerDownload();
        File file = ZipUtil.performZip(storagePropertie.getExportLocation(), storagePropertie.getCustomerDownloadZipFilePath());


        if (!file.exists()) {
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        System.out.println("mimetype : " + mimeType);

        response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }


    private void prepareCustomerDownload() {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            storageService.store("export", batchProperties.getCustomerExportFileName());
            storageService.store("export", batchProperties.getCustomerContactPersonExportFileName());
            Map<String, String> resourceMap = new HashMap<>();
            resourceMap.put("CUSTOMER", batchProperties.getCustomerExportFileName());
            resourceMap.put("CUSTOMERCONTACTPERSON", batchProperties.getCustomerContactPersonExportFileName());
            Job job = (Job) beanFactory.getBean("customerExportJob", resourceMap);
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }



    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


}
