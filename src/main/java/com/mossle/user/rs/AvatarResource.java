package com.mossle.user.rs;

import java.io.InputStream;

import javax.annotation.Resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mossle.api.tenant.TenantHolder;

import com.mossle.user.service.UserAvatarService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component
@Path("avatar")
public class AvatarResource {
    private static Logger logger = LoggerFactory
            .getLogger(AvatarResource.class);
    private UserAvatarService userAvatarService;
    private TenantHolder tenantHolder;

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream view(@QueryParam("id") String id,
            @QueryParam("width") @DefaultValue("0") int width) throws Exception {
        logger.debug("width : {}", width);

        String tenantId = tenantHolder.getTenantId();

        Long longId = null;

        try {
            longId = Long.parseLong(id);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return userAvatarService.viewAvatar(longId, width, tenantId)
                .getInputStream();
    }

    // ~ ======================================================================
    @Resource
    public void setUserAvatarService(UserAvatarService userAvatarService) {
        this.userAvatarService = userAvatarService;
    }

    @Resource
    public void setTenantHolder(TenantHolder tenantHolder) {
        this.tenantHolder = tenantHolder;
    }
}
