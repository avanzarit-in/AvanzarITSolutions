package com.avanzarit.apps.gst.repository;

import com.avanzarit.apps.gst.model.Attachment;
import com.avanzarit.apps.gst.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SPADHI on 5/4/2017.
 */
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    Attachment findById(long id);

    List<Attachment> findByVendor(Vendor vendor);
}
